package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Workout;

import java.util.List;

public interface WorkoutDao {

    Workout addWorkout(Workout workout);
    Workout getWorkoutByName(String name);

    void updateWorkout(Workout workout);
    List<Workout> getAllWorkouts();
}
