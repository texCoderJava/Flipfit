package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkoutDaoImpl implements WorkoutDao{

    private Map<String, Workout> workoutMap = new ConcurrentHashMap<>();

    public Workout addWorkout(Workout workout) {
        return this.workoutMap.put(workout.getWorkoutName(), workout);
    }

    public Workout getWorkoutByName(String workoutName) {
        return this.workoutMap.get(workoutName);
    }

    public List<Workout> getAllWorkouts() {
        return new ArrayList<>(this.workoutMap.values());
    }
}
