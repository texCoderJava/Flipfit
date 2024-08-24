package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Workout;

import java.util.List;

public interface WorkoutService {

    Workout addNewWorkOut(String workoutName);
    Workout getWorkOutByName(String workoutName);

    void updateWorkout(Workout workout);

    List<Workout> getAllWorkOut();
}
