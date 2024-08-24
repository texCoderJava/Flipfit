package com.interview.machinecoding.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Workout {

    private String workoutId;
    private String workoutName;
    private List<Center> centers = new ArrayList<>();
}
