package com.interview.machinecoding.entities;

import com.interview.machinecoding.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String userId;
    private String name;
    private UserType userType;
    private List<Slot> slots;
    private List<Booking> bookings;
}
