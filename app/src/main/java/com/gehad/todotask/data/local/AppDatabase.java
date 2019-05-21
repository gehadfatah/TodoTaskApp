package com.gehad.todotask.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.gehad.todotask.data.local.converters.DateConverter;
import com.gehad.todotask.data.local.converters.LocalDateConverter;
import com.gehad.todotask.data.local.model.CommentlistItemDb;
import com.gehad.todotask.data.local.model.TaskDb;

@Database(version = 1, entities = {TaskDb.class,  CommentlistItemDb.class})
@TypeConverters({DateConverter.class,LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TasksDao tasksDao();

    public abstract CommentlistDao commentlistDao();
}
