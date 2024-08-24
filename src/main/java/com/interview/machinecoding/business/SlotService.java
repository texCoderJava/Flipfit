package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.model.UserType;

import java.time.LocalTime;
import java.util.List;

public interface SlotService {
    void addSlot(String centerArea, String workout, LocalTime startTime, int availableSeats);
    Slot getSlotById(String slotId);
    List<Slot> getAllSlots(String centerArea);

    void updateSlot(Slot slot);
    List<Slot> getSlotByUserType(UserType userType);
}
