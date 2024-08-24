package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.SlotDao;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.exception.FlipFitApplicationException;
import com.interview.machinecoding.model.UserType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SlotServiceImpl implements SlotService {
    private SlotDao slotDAO;
    private CenterService centerService;
    private WorkoutService workoutService;

    public SlotServiceImpl(SlotDao slotDAO,CenterService centerService,WorkoutService workoutService) {
        this.slotDAO = slotDAO;
        this.centerService = centerService;
        this.workoutService = workoutService;
    }

    @Override
    public Slot addSlot(String centerArea, String workout, LocalTime startTime,int availableSeats,boolean isVip) {

        Center center = this.centerService.getCenterByArea(centerArea);
        Optional<Workout> workoutOptional = center.getWorkouts().stream()
                .filter(existingWorkout -> existingWorkout.getWorkoutName().equals(workout)).findFirst();

        return workoutOptional.map(currWorkout -> {
            boolean conflict =  currWorkout.getSlots().stream().anyMatch(slot -> slot.getStartTime().equals(startTime));
            if(conflict){
                throw new FlipFitApplicationException("Conflicting slot already exist");
            }

            List<Workout> workouts = new ArrayList<>();
            workouts.add(this.workoutService.getWorkOutByName(workout));

            Slot slot = Slot.builder()
                    .slotId(UUID.randomUUID().toString())
                    .startTime(startTime)
                    .endTime(startTime.plusHours(1))
                    .availableSeats(availableSeats)
                    .isVipOnly(isVip)
                    .workouts(workouts)
                    .build();

            currWorkout.getSlots().add(slot);
            this.workoutService.updateWorkout(currWorkout);
            return slot;
        }).orElseThrow(() -> new FlipFitApplicationException("No existing workout with name " + workout + ". Add the workout first"));
    }

    @Override
    public void deleteSlot(String centerArea, String workout, LocalTime startTime) {

        Center center = this.centerService.getCenterByArea(centerArea);
        Optional<Workout> workoutOptional = center.getWorkouts().stream()
                .filter(existingWorkout -> existingWorkout.getWorkoutName().equals(workout)).findFirst();

        workoutOptional.ifPresentOrElse(currWorkout -> {
            boolean isSlotPresent =  currWorkout.getSlots().stream().anyMatch(slot -> slot.getStartTime().equals(startTime));
            if(!isSlotPresent){
                throw new FlipFitApplicationException("No slot exist for mentioned time slot");
            }

            List<Slot> slot = currWorkout.getSlots()
                    .stream().filter(existingSlot -> !existingSlot.getStartTime().equals(startTime)).toList();
            currWorkout.setSlots(slot);
            this.workoutService.updateWorkout(currWorkout);
        },() -> {
            throw new FlipFitApplicationException("No slot exist for mentioned time slot");
        });
    }

    @Override
    public Slot getSlotById(String slotId) {
        return this.slotDAO.getSlotById(slotId);
    }

    @Override
    public List<Workout> getAllWorkouts(String centerArea) {
        Center center = centerService.getCenterByArea(centerArea);
        if (center != null) {
            return center.getWorkouts();
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
