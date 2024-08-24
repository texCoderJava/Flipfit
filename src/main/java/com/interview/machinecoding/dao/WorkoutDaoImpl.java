package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.exception.FlipFitApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WorkoutDaoImpl implements WorkoutDao{

    private Map<String, Workout> workoutMap = new ConcurrentHashMap<>();

    public Workout addWorkout(Workout workout) {
        Optional.ofNullable(this.workoutMap.get(workout.getWorkoutName())).ifPresent(existingWorkout ->{
            throw new FlipFitApplicationException("Workout Already exist");
        });
        this.workoutMap.put(workout.getWorkoutName(), workout);
        return workout;
    }

    public Workout getWorkoutByName(String workoutName) {
        return this.workoutMap.get(workoutName);
    }

    @Override
    public void updateWorkout(Workout workout) {
        Optional.ofNullable(this.workoutMap.get(workout.getWorkoutName()))
                .orElseThrow(() -> new FlipFitApplicationException("Add a workout before update"));
    }

    public List<Workout> getAllWorkouts() {
        return new ArrayList<>(this.workoutMap.values());
    }
}
