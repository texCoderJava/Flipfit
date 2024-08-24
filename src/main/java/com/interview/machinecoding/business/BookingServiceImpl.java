package com.interview.machinecoding.business;

import com.interview.machinecoding.business.strategy.BookingStrategy;
import com.interview.machinecoding.business.strategy.NormalUserBookingStrategy;
import com.interview.machinecoding.business.strategy.VIPBookingStrategy;
import com.interview.machinecoding.dao.BookingDao;
import com.interview.machinecoding.entities.Booking;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.User;
import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.model.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private BookingDao bookingDao;
    private SlotService slotService;
    private UserService userService;
    private CenterService centerService;
    private BookingStrategy vipBookingStrategy;
    private BookingStrategy normalBookingStrategy;
    private Map<String, Queue<String>> waitingQueue;

    public BookingServiceImpl(BookingDao bookingDao,SlotService slotService,UserService userService,CenterService centerService,
                              BookingStrategy vipBookingStrategy,BookingStrategy normalBookingStrategy) {
        this.bookingDao = bookingDao;
        this.slotService = slotService;
        this.userService = userService;
        this.centerService = centerService;
        this.vipBookingStrategy = vipBookingStrategy;
        this.normalBookingStrategy = normalBookingStrategy;
        waitingQueue = new ConcurrentHashMap<>();
    }

    @Override
    public boolean bookSlot(String userId, String slotId, String centerArea) {
        User user = userService.getUserById(userId);
        Slot slot = slotService.getSlotById(slotId);
        Center center = this.centerService.getCenterByArea(centerArea);
        if (user == null || slot == null) {
            return false;
        }

        if (!slot.getCenter().getName().equals(centerArea)) {
            System.out.println("Slot does not belong to the given center.");
            return false;
        }

        if (slot.isVipOnly() && user.getUserType() != UserType.VIP) {
            System.out.println("Normal users cannot book VIP slots.");
            return false;
        }

        long bookedSlotsCount = user.getBookings().stream()
                .filter(b -> b.getCenter().getName().equals(centerArea) && b.getDate().equals(LocalDate.now()))
                .count();

        if (bookedSlotsCount >= 3) {
            System.out.println("User has already booked 3 slots at this center today.");
            return false;
        }

        BookingStrategy strategy = getBookingStrategy(user.getUserType());
        boolean isBooked = strategy.bookSlot(user, slot);

        if (isBooked) {
            Booking booking = new Booking(UUID.randomUUID().toString(), user, slot, center, LocalDate.now());
            user.getBookings().add(booking);
            this.bookingDao.createBooking(booking);
            this.userService.updateUser(user);
        } else {
            waitingQueue.putIfAbsent(slotId, new LinkedList<>());
            waitingQueue.get(slotId).add(userId);
        }

        return isBooked;
    }


    @Override
    public void cancelBooking(String userId, String slotId, String centerArea) {
        User user = userService.getUserById(userId);
        Slot slot = slotService.getSlotById(slotId);
        Center center = this.centerService.getCenterByArea(centerArea);
        if (user == null || slot == null) {
            return;
        }

        if (!slot.getCenter().getName().equals(centerArea)) {
            System.out.println("Slot does not belong to the given center.");
            return;
        }

        Optional<Booking> bookingToCancel = user.getBookings().stream()
                .filter(booking -> booking.getSlot().getSlotId().equals(slotId) && booking.getCenter().getName().equals(centerArea))
                .findFirst();

        if (bookingToCancel.isPresent()) {
            List<User> users = slot.getUsers().stream().filter(currentUser -> !user.getUserId().equals(userId)).toList();
            List<Slot> slots = user.getSlots().stream().filter(currentSlot -> !currentSlot.getSlotId().equals(slotId)).toList();

            slot.setUsers(users);
            user.setSlots(slots);

            this.slotService.updateSlot(slot);
            this.userService.updateUser(user);

            Queue<String> waitingUsers = waitingQueue.get(slotId);
            if (waitingUsers != null && !waitingUsers.isEmpty()) {
                List<String> vipUsers = new ArrayList<>();
                List<String> normalUsers = new ArrayList<>();

                while (!waitingUsers.isEmpty()) {
                    String nextUserId = waitingUsers.poll();
                    User nextUser = this.userService.getUserById(nextUserId);

                    if (nextUser.getUserType() == UserType.VIP) {
                        vipUsers.add(nextUserId);
                    } else {
                        normalUsers.add(nextUserId);
                    }
                }

                for (String vipUserId : vipUsers) {
                    User vipUser = userService.getUserById(vipUserId);
                    if (vipUser != null) {
                        BookingStrategy strategy = getBookingStrategy(vipUser.getUserType());
                        if (strategy.bookSlot(vipUser, slot)) {
                            Booking newBooking = new Booking(UUID.randomUUID().toString(), vipUser, slot, center, LocalDate.now());
                            vipUser.getBookings().add(newBooking);
                            this.bookingDao.createBooking(newBooking);
                            this.userService.updateUser(vipUser);
                            notifyUser(vipUserId, slotId);
                            break;
                        }
                    }
                }

                if (slot.getAvailableSeats() > 0) {
                    for (String normalUserId : normalUsers) {
                        User normalUser = userService.getUserById(normalUserId);
                        if (normalUser != null) {
                            BookingStrategy strategy = getBookingStrategy(normalUser.getUserType());
                            if (strategy.bookSlot(normalUser, slot)) {
                                Booking newBooking = new Booking(UUID.randomUUID().toString(), normalUser, slot, center, LocalDate.now());
                                normalUser.getBookings().add(newBooking);
                                this.bookingDao.createBooking(newBooking);
                                this.userService.updateUser(normalUser);
                                notifyUser(normalUserId, slotId);
                                break;
                            }
                        }
                    }
                }
            }

            notifyUser(userId, slotId);
            System.out.println("Booking canceled and waiting list processed.");
        } else {
            System.out.println("Booking not found for the user in this slot.");
        }
    }


    @Override
    public List<Slot> viewBookings(String userId, String date) {
        User user = userService.getUserById(userId);
        if (user != null) {
            List<Slot> bookings = new ArrayList<>();
            for (Slot slot : user.getSlots()) {
                bookings.add(slotService.getSlotById(slot.getSlotId()));
            }
            return bookings;
        }
        return null;
    }

    @Override
    public Map<String,List<Slot>> viewSlots(String centerArea, String userId) {
        User user = this.userService.getUserById(userId);
        Map<String, List<Slot>> slotsByWorkoutName = new HashMap<>();
        Center center = this.centerService.getCenterByArea(centerArea);
            for (Workout workout : center.getWorkouts()) {
                String workoutName = workout.getWorkoutName();

                List<Slot> slotsList = slotsByWorkoutName.computeIfAbsent(workoutName, k -> new ArrayList<>());

                if(user.getUserType() == UserType.VIP){
                    slotsList.addAll(workout.getSlots());
                } else {
                    slotsList.addAll(workout.getSlots().stream().filter(slot -> !slot.isVipOnly()).toList());
                }
            }

            return slotsByWorkoutName;
    }

    private BookingStrategy getBookingStrategy(UserType userType) {
        if (userType == UserType.VIP) {
            return this.vipBookingStrategy;
        } else {
            return this.normalBookingStrategy;
        }
    }

    private void notifyUser(String userId, String slotId) {
        System.out.println("User " + userId + " has been notified about slot " + slotId);
    }
}