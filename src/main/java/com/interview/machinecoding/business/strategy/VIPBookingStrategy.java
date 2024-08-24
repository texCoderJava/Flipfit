package com.interview.machinecoding.business.strategy;

import com.interview.machinecoding.business.SlotService;
import com.interview.machinecoding.business.UserService;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.User;

import java.util.ArrayList;

public class VIPBookingStrategy implements BookingStrategy {

    private UserService userService;
    private SlotService slotService;

    public VIPBookingStrategy(UserService userService,SlotService slotService){
        this.userService = userService;
        this.slotService = slotService;
    }

    @Override
    public boolean bookSlot(User user, Slot slot) {
        if (slot.getAvailableSeats() > 0) {
            slot.setUsers(new ArrayList<>(slot.getUsers()));
            slot.getUsers().add(user);
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            user.getSlots().add(slot);
            this.slotService.updateSlot(slot);
            this.userService.updateUser(user);
            return true;
        } else {
            System.out.println("VIP user added to priority waiting list.");
            return false;
        }
    }
}
