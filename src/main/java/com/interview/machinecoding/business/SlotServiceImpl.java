package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.SlotDao;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.model.UserType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SlotServiceImpl implements SlotService {
    private SlotDao slotDAO;
    private CenterService centerService;
    private WorkoutService workoutService;

    private SlotServiceImpl(SlotDao slotDAO,CenterService centerService,WorkoutService workoutService) {
        this.slotDAO = slotDAO;
        this.centerService = centerService;
        this.workoutService = workoutService;
    }

    @Override
        public void addSlot(String centerArea, String workout, LocalTime startTime,int availableSeats) {
        Center center = this.centerService.getCenterByArea(centerArea);
        List<Workout> workouts = new ArrayList<>();
        workouts.add(this.workoutService.getWorkOutByName(workout));
        if (center != null) {
            Slot slot = Slot.builder()
                            .startTime(startTime)
                            .endTime(startTime.plusHours(1))
                            .availableSeats(availableSeats)
                            .workouts(workouts)
                            .build();

            center.getSlots().add(slot);
            this.slotDAO.addSlot(slot);
            this.centerService.updateCenter(center);
        }
    }

    @Override
    public Slot getSlotById(String slotId) {
        return this.slotDAO.getSlotById(slotId);
    }

    @Override
    public List<Slot> getAllSlots(String centerArea) {
        Center center = centerService.getCenterByArea(centerArea);
        if (center != null) {
            return center.getSlots();
        }
        return null;
    }

    @Override
    public void updateSlot(Slot slot) {
        this.slotDAO.updateSlot(slot);
    }

    @Override
    public List<Slot> getSlotByUserType(UserType userType) {
        return this.slotDAO.getAllSlots().stream().filter(slot -> {
            if (userType == UserType.NORMAL){
                return !slot.isVipOnly();
            }
            return true;
        }).collect(Collectors.toList());
    }
}
