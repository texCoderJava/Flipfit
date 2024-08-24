package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.WorkoutDao;
import com.interview.machinecoding.entities.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutServiceImpl implements WorkoutService{

    private WorkoutDao workoutDao;

    public WorkoutServiceImpl(WorkoutDao workoutDao){
        this.workoutDao = workoutDao;
    }

    @Override
    public Workout addNewWorkOut(String workoutName) {
        Workout workout = Workout.builder()
                .workoutId(UUID.randomUUID().toString())
                .workoutName(workoutName)
                .slots(new ArrayList<>())
                .centers(new ArrayList<>())
                .build();
        return this.workoutDao.addWorkout(workout);
    }

    @Override
    public Workout getWorkOutByName(String workoutName) {
        return this.workoutDao.getWorkoutByName(workoutName);
    }

    @Override
    public void updateWorkout(Workout workout) {
        this.workoutDao.updateWorkout(workout);
    }

    @Override
    public List<Workout> getAllWorkOut() {
        return this.workoutDao.getAllWorkouts();
    }
}
