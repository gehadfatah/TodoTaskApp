package com.gehad.todotask.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.gehad.todotask.data.local.model.CommentlistItemDb;
import com.gehad.todotask.data.local.model.TaskDb;
import com.gehad.todotask.domain.model.CommentlistItem;
import com.gehad.todotask.domain.model.Task;

public class TaskMapper {

    private TaskMapper() {
        throw new AssertionError();
    }

    public static TaskDb toTaskDb(Task task) {
        return new TaskDb(task.getId(),task.getDateTime(), task.getTitle(),
                task.getDescription(),task.getPriority(), task.isDone(),
                task.getDueDate(),task.getUserId());
    }


    public static List<TaskDb> toTasklistItemDbList( List<Task> taskList) {
        List<TaskDb> taskDbs = new ArrayList<>(taskList.size());

        for (Task commentlistItem : taskList) {
            taskDbs.add(toTaskDb( commentlistItem));
        }

        return taskDbs;
    }
    public static Task fromTaskDbAndCommentlistDbList(TaskDb taskDb, List<CommentlistItemDb> commentlistItemList) {
        List<CommentlistItem> commentlistItemLists = CommentlistItemMapper.toCommentlistItemList(commentlistItemList);
        return new Task.Builder()
                .setId(taskDb.getId())
                .setTitle(taskDb.getTitle())
                .setDateTime(taskDb.getDateTime())

                .setUserId(taskDb.getUserId())
                .setDescription(taskDb.getDescription())
                .setPriority(taskDb.getPriority())
                .setIsDone(taskDb.isDone())
                .setDueDate(taskDb.getDueDate())
                .setCommentList(commentlistItemLists)
                .build();
    }
}
