package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.User;

import java.util.List;

public interface UserDao {

    User addUser(User user);

    User getUserById(String id);

    List<User> getAllUsers();

    void updateUser(User user);
}
