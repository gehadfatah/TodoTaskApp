package com.gehad.todotask.domain.mapper;

import com.gehad.todotask.data.local.model.CommentlistItemDb;
import com.gehad.todotask.domain.model.CommentlistItem;

import java.util.ArrayList;
import java.util.List;

public class CommentlistItemMapper {

    private CommentlistItemMapper() {
        throw new AssertionError();
    }

    public static CommentlistItemDb toCommentlistItemDb(long taskId, CommentlistItem commentlistItem) {
        return new CommentlistItemDb(commentlistItem.getId(), taskId, commentlistItem.getdescription(),commentlistItem.getDueDate());
    }

    public static List<CommentlistItemDb> toCommentlistItemDbList(long taskId, List<CommentlistItem> commentlistItems) {
        List<CommentlistItemDb> commentlistItemDbList = new ArrayList<>(commentlistItems.size());

        for (CommentlistItem commentlistItem : commentlistItems) {
            commentlistItemDbList.add(toCommentlistItemDb(taskId, commentlistItem));
        }

        return commentlistItemDbList;
    }

    public static CommentlistItem toCommentlistItem(CommentlistItemDb commentlistItemDb) {
        return new CommentlistItem.Builder()
                .setId(commentlistItemDb.getId())
                .setDescription(commentlistItemDb.getDescription())
                .setDueDate(commentlistItemDb.getDueDate())
                .build();
    }

    public static List<CommentlistItem> toCommentlistItemList(List<CommentlistItemDb> commentlistItemDbs) {
        List<CommentlistItem> commentlistItems = new ArrayList<>(commentlistItemDbs.size());
        for (CommentlistItemDb checklistItemDb : commentlistItemDbs) {
            commentlistItems.add(toCommentlistItem(checklistItemDb));
        }
        return commentlistItems;
    }
}
