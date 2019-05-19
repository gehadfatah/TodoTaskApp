package com.gehad.todotask.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.gehad.todotask.data.local.model.ChecklistItemDb;
import io.reactivex.Flowable;

@Dao
public interface ChecklistDao {

    @Insert
    void insertAll(List<ChecklistItemDb> checklistItemDbs);

    @Update()
    void updateAll(List<ChecklistItemDb> checklistItemDbs);

    @Delete
    void deleteAll(List<ChecklistItemDb> checklistItemDbs);

    @Query("SELECT * FROM check_list_item WHERE task_id = :taskId")
    Flowable<List<ChecklistItemDb>> getChecklistItems(long taskId);
}
