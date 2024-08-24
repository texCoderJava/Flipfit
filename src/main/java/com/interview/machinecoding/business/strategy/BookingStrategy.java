package com.interview.machinecoding.business.strategy;

import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.User;

public interface BookingStrategy {
    boolean bookSlot(User user, Slot slot);
}
