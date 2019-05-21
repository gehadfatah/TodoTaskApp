package com.gehad.todotask.ui.edittask.adapter.model;

import org.threeten.bp.LocalDate;

import java.util.Date;

public class CommentlistDataCollector {

    private long id;

    private String description;
    private Date dueDate;

    private boolean hasIdSet;

    public CommentlistDataCollector() {

    }

    public CommentlistDataCollector(long id, String description,Date dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;

        hasIdSet = true;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIdSet() {
        return hasIdSet;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
