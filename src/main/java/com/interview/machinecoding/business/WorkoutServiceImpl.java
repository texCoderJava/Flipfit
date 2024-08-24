package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.WorkoutDao;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutServiceImpl implements WorkoutService{

    private WorkoutDao workoutDao;
    private CenterService centerService;

    public WorkoutServiceImpl(WorkoutDao workoutDao,CenterService centerService){
        this.workoutDao = workoutDao;
        this.centerService = centerService;
    }

    @Override
    public Workout addWorkout(String centerName,String workoutName) {
        List<Center> centers = new ArrayList<>();
        centers.add(this.centerService.getCenterByArea(centerName));
        Workout workout = Workout.builder()
                .workoutId(UUID.randomUUID().toString())
                .workoutName(workoutName)
                .centers(centers)
                .build();
        return this.workoutDao.addWorkout(workout);
    }

    @Override
    public Workout getWorkOutByName(String workoutName) {
        return this.workoutDao.getWorkoutByName(workoutName);
    }

    @Override
    public List<Workout> getAllWorkOut() {
        return this.workoutDao.getAllWorkouts();
    }
}
