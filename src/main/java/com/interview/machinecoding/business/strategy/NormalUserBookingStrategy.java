package com.interview.machinecoding.business.strategy;

import com.interview.machinecoding.business.SlotService;
import com.interview.machinecoding.business.UserService;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.User;

public class NormalUserBookingStrategy implements BookingStrategy {

    private UserService userService;
    private SlotService slotService;

    public NormalUserBookingStrategy(UserService userService,SlotService slotService){
        this.userService = userService;
        this.slotService = slotService;
    }

    @Override
    public boolean bookSlot(User user, Slot slot) {
        if (slot.getAvailableSeats() > 0) {
            slot.getUsers().add(user);
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            user.getSlots().add(slot);
            this.slotService.updateSlot(slot);
            this.userService.updateUser(user);
            return true;
        } else {
            System.out.println("Normal user added to waiting list.");
            return false;
        }
    }
}
