package com.example.kanbanboard.model;

import java.util.ArrayList;
import java.util.List;

public class WorkList {
    private String name;
    private int priority;
    private List<Work> items;

    public WorkList() {
    }

    public WorkList(String name, int priority) {
        this.name = name;
        this.priority = priority;
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Work> getItems() {
        return items;
    }

    public void setItems(List<Work> items) {
        this.items = items;
    }
}
