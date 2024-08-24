package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDaoImpl implements UserDao{
    private Map<String, User> usersMap = new ConcurrentHashMap<>();

    public User addUser(User user) {
        this.usersMap.put(user.getUserId(),user);
        return user;
    }

    public User getUserById(String userId) {
        return this.usersMap.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(this.usersMap.values());
    }

    @Override
    public void updateUser(User user) {
        this.usersMap.put(user.getUserId(),user);
    }
}
