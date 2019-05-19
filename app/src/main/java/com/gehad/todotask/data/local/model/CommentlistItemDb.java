package com.gehad.todotask.data.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
        tableName = "comment_list_item",
        indices = @Index("task_id"),
        foreignKeys = @ForeignKey(
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE,
                entity = TaskDb.class,
                parentColumns = "id",
                childColumns = "task_id"
        ))
public class CommentlistItemDb {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @ColumnInfo(name = "task_id")
    private final long taskId;

    @ColumnInfo(name = "description")
    private final String description;

    public CommentlistItemDb(long id, long taskId, String description) {
        this.id = id;
        this.taskId = taskId;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }
}
