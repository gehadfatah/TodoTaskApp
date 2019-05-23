package com.gehad.todotask.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class TaskFromFirebase implements Parcelable {

    private  long id;
    private  long dateTime;

    private  String title;
    private  String userId;
    private  int priority;

    private String description;

    private  boolean isDone;

    @Nullable
    private  long dueDate;

    private  List<CommentlistItem> commentlistItemList;


    private TaskFromFirebase(long id, long dateTime, String title, String userId, String description,
                             boolean isDone, @Nullable long dueDate, List<CommentlistItem> commentlistItemList, int priority) {
        this.id = id;
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.priority = priority;
        this.dueDate = dueDate;
        this.userId = userId;
        this.commentlistItemList = commentlistItemList;
    }
    private TaskFromFirebase() {

    }
    public long getId() {
        return id;
    }
    public long getDateTime() {
        return dateTime;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }
    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    @Nullable
    public long getDueDate() {
        return dueDate;
    }


    public List<CommentlistItem> getCommentlistItemList() {
        return commentlistItemList;
    }

    public static class Builder {

        private long id;
        private long dateTime;
        private int priority;
        private String title;
        private String userId;
        private String description;
        private boolean isDone;
        private long dueDate;
        private List<CommentlistItem> commentlistItemList;

        public Builder setId(long id) {
            this.id = id;
            return this;
        } public Builder setDateTime(long dateTime) {
            this.dateTime = dateTime;
            return this;
        }
        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        } public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setIsDone(boolean isDone) {
            this.isDone = isDone;
            return this;
        }

        public Builder setDueDate(long dueDate) {
            this.dueDate = dueDate;
            return this;
        }


        public Builder setCommentList(List<CommentlistItem> commentlistItemList) {
            this.commentlistItemList = commentlistItemList;
            return this;
        }
        public TaskFromFirebase build() {
            return new TaskFromFirebase(id, dateTime,title, userId,description, isDone, dueDate,commentlistItemList,priority);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.dateTime);
        dest.writeInt(this.priority);
        dest.writeString(this.title);
        dest.writeString(this.userId);
        dest.writeString(this.description);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
        dest.writeLong(this.dueDate);
        dest.writeList(this.commentlistItemList);
    }

    protected TaskFromFirebase(Parcel in) {
        this.id = in.readLong();
        this.dateTime = in.readLong();
        this.priority = in.readInt();
        this.title = in.readString();
        this.userId = in.readString();
        this.description = in.readString();
        this.isDone = in.readByte() != 0;
        this.dueDate =  in.readLong();
        this.commentlistItemList = new ArrayList<>();
        in.readList(this.commentlistItemList, CommentlistItem.class.getClassLoader());
    }

    public static final Creator<TaskFromFirebase> CREATOR = new Creator<TaskFromFirebase>() {
        @Override
        public TaskFromFirebase createFromParcel(Parcel source) {
            return new TaskFromFirebase(source);
        }

        @Override
        public TaskFromFirebase[] newArray(int size) {
            return new TaskFromFirebase[size];
        }
    };
}
