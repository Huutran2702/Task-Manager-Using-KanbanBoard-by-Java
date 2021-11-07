package com.example.kanbanboard.repository;

import com.example.kanbanboard.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    public List<User> userList;

    public UserRepository() {
        userList  = new ArrayList<>(
        );
    }

    @Override
    public User getByEmail(String email) {
        for (User user: userList ) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return userList;
    }

    @Override
    public boolean exist(String email) {
        return getByEmail(email) != null;
    }

    @Override
    public void add(User newUser) {
        userList.add(newUser);
    }

    @Override
    public void update(User user) {
        User oldUser = getByEmail(user.getEmail());
        User.transferFields(oldUser, user);
    }
}
