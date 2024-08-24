package com.interview.machinecoding.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    private String bookingId;
    private User user;
    private Slot slot;
    private Center center;
    private LocalDate date;
}
