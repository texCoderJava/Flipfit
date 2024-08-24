package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Workout;

import java.util.List;

public interface WorkoutService {

    Workout addWorkout(String centerName,String workoutName);
    Workout getWorkOutByName(String workoutName);

    List<Workout> getAllWorkOut();
}
