package com.gehad.todotask.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gehad.todotask.data.local.model.CommentlistItemDb;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CommentlistDao {

    @Insert
    void insertAll(List<CommentlistItemDb> commentlistItemDbs);

    @Update()
    void updateAll(List<CommentlistItemDb> commentlistItemDbs);

    @Delete
    void deleteAll(List<CommentlistItemDb> commentlistItemDbs);

    @Query("SELECT * FROM comment_list_item WHERE task_id = :taskId")
    Flowable<List<CommentlistItemDb>> getCommentlistItems(long taskId);
}
