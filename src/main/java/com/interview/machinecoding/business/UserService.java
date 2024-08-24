package com.interview.machinecoding.business;

import com.interview.machinecoding.entities.Booking;
import com.interview.machinecoding.entities.User;
import com.interview.machinecoding.model.UserType;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User registerUser(String name, UserType userType);
    User getUserById(String userId);
    List<User> getAllUsers();
    void updateUser(User user);
    public List<Booking> viewUserPlan(String userId, LocalDate date);
    List<Booking> viewBooking(String userId, LocalDate date);
}
