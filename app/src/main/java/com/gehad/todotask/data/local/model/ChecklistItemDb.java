package com.gehad.todotask.data.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
        tableName = "check_list_item",
        indices = @Index("task_id"),
        foreignKeys = @ForeignKey(
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE,
                entity = TaskDb.class,
                parentColumns = "id",
                childColumns = "task_id"
        ))
public class ChecklistItemDb {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @ColumnInfo(name = "task_id")
    private final long taskId;

    @ColumnInfo(name = "first_name")
    private final String name;

    public ChecklistItemDb(long id, long taskId, String name) {
        this.id = id;
        this.taskId = taskId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }
}
