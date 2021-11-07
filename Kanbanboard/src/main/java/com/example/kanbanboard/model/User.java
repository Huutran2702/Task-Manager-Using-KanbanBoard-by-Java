package com.example.kanbanboard.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private Role role;
    private LoginStatus loginStatus;
    private String phone;
    private List<Workspace> workspace;

    public User() {
    }

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.phone = phone;
        workspace = new ArrayList<>();
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Workspace> getWorkspace() {
        return workspace;
    }

    public void setWorkspace(List<Workspace> workspace) {
        this.workspace = workspace;
    }

    public static void transferFields(User oldUser, User newUser) {
        oldUser.name = newUser.name;
        oldUser.email = newUser.email;
        oldUser.password = newUser.password;
        oldUser.phone = newUser.phone;
        oldUser.workspace = newUser.workspace;
    }
}
