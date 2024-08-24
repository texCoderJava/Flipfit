package com.interview.machinecoding.business;

import com.interview.machinecoding.dao.UserDao;
import com.interview.machinecoding.entities.Booking;
import com.interview.machinecoding.entities.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserDao userDAO;

    private UserServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public void registerUser(User user) {
        this.userDAO.addUser(user);
    }

    @Override
    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }

    @Override
    public void updateUser(User user) {
        this.userDAO.updateUser(user);
    }

    public List<Booking> viewUserPlan(String userId, LocalDate date) {
        User user = this.userDAO.getUserById(userId);

        if (user == null) {
            return new ArrayList<>();
        }

        return user.getBookings().stream()
                .filter(booking -> booking.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> viewBooking(String userId, LocalDate date) {

        return this.userDAO.getUserById(userId).getBookings().stream().filter(booking -> booking.getDate().equals(date))
                .collect(Collectors.toList());
    }
}