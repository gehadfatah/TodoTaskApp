package com.gehad.todotask.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.threeten.bp.LocalDate;

import java.util.List;

import com.gehad.todotask.data.local.model.TaskDb;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TasksDao {

    @Insert
    long insertTask(TaskDb taskDb);

    @Update
    int updateTask(TaskDb taskDb);

    @Query("SELECT * FROM task WHERE is_done = 0")
    Flowable<List<TaskDb>> getToDoTasks();
    @Query("SELECT * FROM task WHERE is_done = 0")
    Single<List<TaskDb>> getToDoTasksSingle();
    @Query("SELECT * FROM task WHERE is_done = 1")
    Flowable<List<TaskDb>> getDoneTasks();
    @Query("SELECT * FROM task WHERE is_done = 1")
    Single<List<TaskDb>> getDoneTasksSingle();
    @Query("SELECT * FROM task WHERE userId=:userId")
    Flowable<List<TaskDb>> getAllTasks(String userId);
    @Query("SELECT * FROM task WHERE is_done = 0 AND due_date < :localDate")
    Flowable<List<TaskDb>> getTaskWithDueDateBefore(LocalDate localDate);

    @Delete
    void deleteTask(TaskDb taskDb);
}
