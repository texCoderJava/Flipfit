package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.model.UserType;

import java.time.LocalTime;
import java.util.List;

public interface SlotService {
    Slot addSlot(String centerArea, String workout, LocalTime startTime, int availableSeats,boolean isVip);
    Slot getSlotById(String slotId);
    List<Workout> getAllWorkouts(String centerArea);

    void updateSlot(Slot slot);
    List<Slot> getSlotByUserType(UserType userType);
    void deleteSlot(String centerArea, String workout, LocalTime startTime);
}
