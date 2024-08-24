package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.CenterDao;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Location;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.Workout;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CenterServiceImpl implements CenterService {
    private CenterDao centerDAO;

    private WorkoutService workoutService;

    private CenterServiceImpl(CenterDao centerDAO,WorkoutService workoutService) {
        this.centerDAO = centerDAO;
        this.workoutService = workoutService;
    }

    @Override
    public Center addCenter(String center,String city, Location location) {
        Center newCenter = Center.builder()
                .id(UUID.randomUUID().toString())
                .name(center)
                .city(city)
                .location(location)
                .build();
        return this.centerDAO.addCenter(newCenter);
    }


    @Override
    public void addNewWorkOut(String centre, String workout) {
        Center center = this.centerDAO.getByCenterArea(centre);
        center.getWorkouts().add(this.workoutService.getWorkOutByName(workout));
        this.centerDAO.updateCenter(center);
    }

    @Override
    public Center getCenterByArea(String centerArea) {
        return centerDAO.getByCenterArea(centerArea);
    }

    @Override
    public List<Center> getAllCenters() {
        return this.centerDAO.getAllCenters();
    }

    @Override
    public void updateCenter(Center center) {
        this.centerDAO.updateCenter(center);
    }

    @Override
    public List<Workout> getWorkoutByCenter(String center) {
        return this.workoutService.getAllWorkOut().stream()
                .filter(workout -> workout.getCenters().stream().anyMatch(workoutCenters -> workoutCenters.getName().equals(center)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Slot> getAllAvailableSlots(String center) {
        return this.centerDAO.getByCenterArea(center).getSlots().stream().filter(slot -> slot.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }
}
