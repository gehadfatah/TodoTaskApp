package com.gehad.todotask.domain.mapper;

import java.util.List;

import com.gehad.todotask.data.local.model.ChecklistItemDb;
import com.gehad.todotask.data.local.model.TaskDb;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;

public class TaskMapper {

    private TaskMapper() {
        throw new AssertionError();
    }

    public static TaskDb toTaskDb(Task task) {
        return new TaskDb(task.getId(), task.getTitle(),
                task.getDescription(), task.isDone(),
                task.getDueDate(),task.getUserId());
    }

    public static Task fromTaskDbAndChecklistDbList(TaskDb taskDb, List<ChecklistItemDb> checklist) {
        List<ChecklistItem> checklistItems = ChecklistItemMapper.toChecklistItemList(checklist);
        return new Task.Builder()
                .setId(taskDb.getId())
                .setTitle(taskDb.getTitle())
                .setUserId(taskDb.getUserId())
                .setDescription(taskDb.getDescription())
                .setIsDone(taskDb.isDone())
                .setDueDate(taskDb.getDueDate())
                .setChecklistItemList(checklistItems)
                .build();
    }
}
