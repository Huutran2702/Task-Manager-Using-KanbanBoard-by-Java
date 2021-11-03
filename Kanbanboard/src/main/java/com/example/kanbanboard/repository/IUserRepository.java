package com.example.kanbanboard.repository;

import com.example.kanbanboard.model.User;

import java.util.List;

public interface IUserRepository {
    User getByAccount(String account);

    List<User> getUsers();

    boolean exist(String account);

    void add(User newUser);


    void update(User user);
}
