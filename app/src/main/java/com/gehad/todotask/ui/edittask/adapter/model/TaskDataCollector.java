package com.gehad.todotask.ui.edittask.adapter.model;

import org.threeten.bp.LocalDate;

public class TaskDataCollector {

    private long id;

    private String title;
    private String userId;

    private String description;

    private boolean isDone;

    private LocalDate dueDate;

    public TaskDataCollector() {

    }

    public TaskDataCollector(long id, String title,String userId, String description, boolean isDone, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getuserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
