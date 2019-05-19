package com.gehad.todotask.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.gehad.todotask.data.local.converters.LocalDateConverter;
import com.gehad.todotask.data.local.model.ChecklistItemDb;
import com.gehad.todotask.data.local.model.CommentlistItemDb;
import com.gehad.todotask.data.local.model.TaskDb;

@Database(version = 1, entities = {TaskDb.class, ChecklistItemDb.class, CommentlistItemDb.class})
@TypeConverters(LocalDateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TasksDao tasksDao();

    public abstract ChecklistDao checklistDao();
    public abstract CommentlistDao commentlistDao();
}
