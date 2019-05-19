package com.gehad.todotask.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Task implements Parcelable {

    private final long id;

    private final String title;
    private final String userId;

    private final String description;

    private final boolean isDone;

    @Nullable
    private final LocalDate dueDate;

    private final List<ChecklistItem> checklistItemList;

    private Task(long id, String title,String userId, String description,
                 boolean isDone, @Nullable LocalDate dueDate, List<ChecklistItem> checklistItemList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.dueDate = dueDate;
        this.userId = userId;
        this.checklistItemList = checklistItemList;
    }

    public long getId() {
        return id;
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
    public LocalDate getDueDate() {
        return dueDate;
    }

    public List<ChecklistItem> getChecklistItemList() {
        return checklistItemList;
    }

    public static class Builder {

        private long id;
        private String title;
        private String userId;
        private String description;
        private boolean isDone;
        private LocalDate dueDate;
        private List<ChecklistItem> checklistItemList;

        public Builder setId(long id) {
            this.id = id;
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

        public Builder setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder setChecklistItemList(List<ChecklistItem> checklistItemList) {
            this.checklistItemList = checklistItemList;
            return this;
        }

        public Task build() {
            return new Task(id, title, userId,description, isDone, dueDate, checklistItemList);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.userId);
        dest.writeString(this.description);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.dueDate);
        dest.writeList(this.checklistItemList);
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.userId = in.readString();
        this.description = in.readString();
        this.isDone = in.readByte() != 0;
        this.dueDate = (LocalDate) in.readSerializable();
        this.checklistItemList = new ArrayList<>();
        in.readList(this.checklistItemList, ChecklistItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
