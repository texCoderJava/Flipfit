package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Booking;
import com.interview.machinecoding.entities.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface UserService {
    void registerUser(User user);
    User getUserById(String userId);
    List<User> getAllUsers();
    void updateUser(User user);
    public List<Booking> viewUserPlan(String userId, LocalDate date);
    List<Booking> viewBooking(String userId, LocalDate date);
}
