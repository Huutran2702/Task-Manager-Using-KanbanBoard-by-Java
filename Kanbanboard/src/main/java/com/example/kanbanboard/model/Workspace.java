package com.example.kanbanboard.model;

import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private String name;
    private List<WorkList> work;

    public Workspace() {
    }

    public Workspace(String name) {
        this.name = name;
        work = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkList> getWork() {
        return work;
    }

    public void setWork(List<WorkList> work) {
        this.work = work;
    }
}
