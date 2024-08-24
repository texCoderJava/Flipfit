package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.CenterDao;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Location;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.exception.FlipFitApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CenterServiceImpl implements CenterService {
    private CenterDao centerDAO;


    public CenterServiceImpl(CenterDao centerDAO) {
        this.centerDAO = centerDAO;
    }

    @Override
    public Center addCenter(String center,String city, Location location) {

        Center newCenter = Center.builder()
                .id(UUID.randomUUID().toString())
                .name(center)
                .city(city)
                .slots(new ArrayList<>())
                .location(location)
                .workouts(new ArrayList<>())
                .build();

        return this.centerDAO.addCenter(newCenter);
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
        return this.centerDAO.getByCenterArea(center).getWorkouts();
    }
}
