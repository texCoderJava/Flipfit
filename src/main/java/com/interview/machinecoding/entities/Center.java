package com.interview.machinecoding.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Center {

    private String id;
    private String name;
    private String city;
    private Location location;
    List<Workout> workouts;
}
