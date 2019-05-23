package com.gehad.todotask.ui.edittask.adapter.model;

import java.util.Date;

public class CommentlistDataCollectorForFirebase {

    private long id;
    private long taskId;

    private String description;
    private Date dueDate;


    public CommentlistDataCollectorForFirebase() {

    }

    public CommentlistDataCollectorForFirebase(long id, long taskId, String description, Date dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.taskId = taskId;

    }

    public long getId() {
        return id;
    }

    public long gettaskId() {
        return taskId;
    }

    public void settaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
