package com.gehad.todotask.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.gehad.todotask.data.local.model.ChecklistItemDb;
import com.gehad.todotask.domain.model.ChecklistItem;

public class ChecklistItemMapper {

    private ChecklistItemMapper() {
        throw new AssertionError();
    }

    public static ChecklistItemDb toChecklistItemDb(long taskId, ChecklistItem checklistItem) {
        return new ChecklistItemDb(checklistItem.getId(), taskId, checklistItem.getName());
    }

    public static List<ChecklistItemDb> toChecklistItemDbList(long taskId, List<ChecklistItem> checklistItems) {
        List<ChecklistItemDb> checklistItemDbList = new ArrayList<>(checklistItems.size());

        for (ChecklistItem checklistItem : checklistItems) {
            checklistItemDbList.add(toChecklistItemDb(taskId, checklistItem));
        }

        return checklistItemDbList;
    }

    public static ChecklistItem toChecklistItem(ChecklistItemDb checklistItemDb) {
        return new ChecklistItem.Builder()
                .setId(checklistItemDb.getId())
                .setName(checklistItemDb.getName())
                .build();
    }

    public static List<ChecklistItem> toChecklistItemList(List<ChecklistItemDb> checklistItemDbs) {
        List<ChecklistItem> checklistItems = new ArrayList<>(checklistItemDbs.size());
        for (ChecklistItemDb checklistItemDb : checklistItemDbs) {
            checklistItems.add(toChecklistItem(checklistItemDb));
        }
        return checklistItems;
    }
}
