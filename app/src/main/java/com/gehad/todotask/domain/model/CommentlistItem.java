package com.gehad.todotask.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

import java.util.Date;

public class CommentlistItem implements Parcelable {

    private final long id;

    private final String description;
    @Nullable
    private final Date dueDate;

    public CommentlistItem( long id, String description, @Nullable Date dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;

    }

    public long getId() {
        return id;
    }

    public String getdescription() {
        return description;
    }

    @Nullable
    public Date getDueDate() {
        return dueDate;
    }

    public static class Builder {

        private long id;
        private String description;
        private Date dueDate;

        public Builder setId( long id) {
            this.id = id;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public CommentlistItem build() {
            return new CommentlistItem(id, description, dueDate);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.description);
        dest.writeSerializable(this.dueDate);

    }

    protected CommentlistItem(Parcel in) {
        this.id = in.readLong();
        this.description = in.readString();
        this.dueDate = (Date) in.readSerializable();

    }

    public static final Creator<CommentlistItem> CREATOR = new Creator<CommentlistItem>() {
        @Override
        public CommentlistItem createFromParcel(Parcel source) {
            return new CommentlistItem(source);
        }

        @Override
        public CommentlistItem[] newArray(int size) {
            return new CommentlistItem[size];
        }
    };
}
