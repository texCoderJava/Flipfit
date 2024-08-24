package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Slot;

import java.util.List;
import java.util.Map;

public interface BookingService {
    boolean bookSlot(String userId, String slotId,String workout, String centerArea);
    void cancelBooking(String userId, String slotId, String centerArea);
    List<Slot> viewBookings(String userId, String date);
    Map<String,List<Slot>> viewSlots(String centerArea, String userId);
}