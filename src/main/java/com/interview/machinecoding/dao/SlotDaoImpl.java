package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlotDaoImpl implements SlotDao{
    Map<String,Slot> slotMap = new ConcurrentHashMap<>();
    @Override
    public Slot addSlot(Slot slot) {
        this.slotMap.put(slot.getSlotId(),slot);
        return slot;
    }

    @Override
    public Slot getSlotById(String slotId) {
        return this.slotMap.get(slotId);
    }

    @Override
    public List<Slot> getAllSlots() {
        return new ArrayList<>(this.slotMap.values());
    }

    @Override
    public void updateSlot(Slot slot) {
        this.slotMap.put(slot.getSlotId(),slot);
    }
}
