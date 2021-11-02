package com.example.kanbanboard.model;

public enum Role {
    ADMIN("ADMIN"), USER("USER");
    private String value;

    private Role(String value) {
        this.value = value;
    }
}
