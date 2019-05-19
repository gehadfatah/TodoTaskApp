package com.gehad.todotask.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentlistItem implements Parcelable {

    private final long id;

    private final String description;

    private CommentlistItem(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getdescription() {
        return description;
    }

    public static class Builder {

        private long id;
        private String description;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public CommentlistItem build() {
            return new CommentlistItem(id, description);
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
    }

    protected CommentlistItem(Parcel in) {
        this.id = in.readLong();
        this.description = in.readString();
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
