package com.example.kanbanboard.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String account;
    private String password;
    private Role role;
    private String email;
    private String phone;
    private List<Workspace> workspace;

    public User() {
    }

    public User(String account, String password, String email, String phone) {
        this.account = account;
        this.password = password;
        this.role = Role.USER;
        this.email = email;
        this.phone = phone;
        workspace = new ArrayList<>();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        oldUser.account = newUser.account;
        oldUser.password = newUser.password;
        oldUser.email = newUser.email;
        oldUser.phone = newUser.phone;
        oldUser.workspace = newUser.workspace;
    }
}
