package com.example.kanbanboard.repository;

import com.example.kanbanboard.controller.IUserRepository;
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
    public User getByAccount(String account) {
        for (User user: userList ) {
            if (user.getAccount().equals(account)) {
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
    public boolean exist(String account) {
        return getByAccount(account) != null;
    }

    @Override
    public void add(User newUser) {
        userList.add(newUser);
    }

    @Override
    public void update(User user) {
        User oldUser = getByAccount(user.getAccount());
        User.transferFields(oldUser, user);
    }
}
