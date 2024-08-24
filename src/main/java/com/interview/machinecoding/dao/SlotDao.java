package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Slot;

import java.util.List;

public interface SlotDao {

    Slot addSlot(Slot slot);

    Slot getSlotById(String slotId);

    List<Slot> getAllSlots();

    void updateSlot(Slot slot);
}
