package com.example.kanbanboard.model;

public enum LoginStatus {
    LOGOUT("LOGOUT"), UNLOGOUT("UNLOGOUT");
    private String value;

    LoginStatus(String value) {
        this.value = value;
    }
}
