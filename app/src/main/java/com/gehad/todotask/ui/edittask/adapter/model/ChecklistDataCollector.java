package com.gehad.todotask.ui.edittask.adapter.model;

public class ChecklistDataCollector {

    private long id;

    private String name;

    private boolean hasIdSet;

    public ChecklistDataCollector() {

    }

    public ChecklistDataCollector(long id, String name) {
        this.id = id;
        this.name = name;
        hasIdSet = true;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIdSet() {
        return hasIdSet;
    }
}
