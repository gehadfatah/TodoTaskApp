package com.gehad.todotask.domain.mapper;

import com.gehad.todotask.data.local.model.ChecklistItemDb;
import com.gehad.todotask.data.local.model.CommentlistItemDb;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.CommentlistItem;

import java.util.ArrayList;
import java.util.List;

public class CommentlistItemMapper {

    private CommentlistItemMapper() {
        throw new AssertionError();
    }

    public static CommentlistItemDb toChecklistItemDb(long taskId, CommentlistItem commentlistItem) {
        return new CommentlistItemDb(commentlistItem.getId(), taskId, commentlistItem.getdescription());
    }

    public static List<CommentlistItemDb> toChecklistItemDbList(long taskId, List<CommentlistItem> commentlistItems) {
        List<CommentlistItemDb> commentlistItemDbList = new ArrayList<>(commentlistItems.size());

        for (CommentlistItem commentlistItem : commentlistItems) {
            commentlistItemDbList.add(toChecklistItemDb(taskId, commentlistItem));
        }

        return commentlistItemDbList;
    }

    public static CommentlistItem toChecklistItem(CommentlistItemDb commentlistItemDb) {
        return new CommentlistItem.Builder()
                .setId(commentlistItemDb.getId())
                .setDescription(commentlistItemDb.getDescription())
                .build();
    }

    public static List<CommentlistItem> toChecklistItemList(List<CommentlistItemDb> commentlistItemDbs) {
        List<CommentlistItem> commentlistItems = new ArrayList<>(commentlistItemDbs.size());
        for (CommentlistItemDb checklistItemDb : commentlistItemDbs) {
            commentlistItems.add(toChecklistItem(checklistItemDb));
        }
        return commentlistItems;
    }
}
