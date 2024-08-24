package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.CenterDao;
import com.interview.machinecoding.dao.WorkoutDao;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Workout;

public class CenterWorkoutServiceImpl implements CenterWorkoutService {

    private CenterService centerService;
    private WorkoutService workoutService;

    public CenterWorkoutServiceImpl(CenterService centerService,WorkoutService workoutService){
        this.centerService = centerService;
        this.workoutService = workoutService;
    }
    @Override
    public void addWorkoutToCenter(String centerName, String workoutName) {
        Center center = this.centerService.getCenterByArea(centerName);
        Workout workout = this.workoutService.getWorkOutByName(workoutName);
        center.getWorkouts().add(this.workoutService.getWorkOutByName(workoutName));
        workout.getCenters().add(center);
        this.centerService.updateCenter(center);
        this.workoutService.updateWorkout(workout);
    }
}
