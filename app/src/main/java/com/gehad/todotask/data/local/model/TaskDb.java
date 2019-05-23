package com.gehad.todotask.data.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

@Entity(tableName = "task")
public class TaskDb {

    @PrimaryKey(autoGenerate = true)
    private final long id;
    private final int priority;

    private final String title;
    private final String userId;

    private final String description;

    @ColumnInfo(name = "is_done")
    private final boolean isDone;

    @Nullable
    @ColumnInfo(name = "due_date")
    private final LocalDate dueDate;
    private final long dateTime;

    public TaskDb(long id, long dateTime,String title, String description, int priority, boolean isDone, @Nullable LocalDate dueDate, String userId) {
        this.id = id;
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.dueDate = dueDate;
        this.userId = userId;
        this.priority = priority;

    }

    public long getId() {
        return id;
    }
    public long getDateTime() {
        return dateTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Nullable
    public LocalDate getDueDate() {
        return dueDate;
    }
}
