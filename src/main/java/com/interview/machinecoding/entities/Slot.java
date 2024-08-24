package com.interview.machinecoding.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Slot {

    private String slotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private int availableSeats;
    private List<Workout> workouts;
    private boolean isVipOnly;
    private List<User> users;
    private Center center;
    private List<Booking> bookings;
}
