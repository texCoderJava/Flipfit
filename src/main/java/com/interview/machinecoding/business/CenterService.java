package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Location;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.Workout;

import java.util.List;

public interface CenterService {
    Center addCenter(String center, String city, Location location);

    Center getCenterByArea(String centerArea);
    List<Center> getAllCenters();
    void updateCenter(Center center);

    List<Workout> getWorkoutByCenter(String center);
}