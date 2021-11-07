package com.example.kanbanboard.repository;

import com.example.kanbanboard.model.User;

import java.util.List;

public interface IUserRepository {
    User getByEmail(String email);

    List<User> getUsers();

    boolean exist(String email);

    void add(User newUser);


    void update(User user);
}
