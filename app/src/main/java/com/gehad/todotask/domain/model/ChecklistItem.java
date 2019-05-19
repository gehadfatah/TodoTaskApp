package com.gehad.todotask.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChecklistItem implements Parcelable {

    private final long id;

    private final String name;

    private ChecklistItem(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private long id;
        private String name;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public ChecklistItem build() {
            return new ChecklistItem(id, name);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    protected ChecklistItem(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ChecklistItem> CREATOR = new Parcelable.Creator<ChecklistItem>() {
        @Override
        public ChecklistItem createFromParcel(Parcel source) {
            return new ChecklistItem(source);
        }

        @Override
        public ChecklistItem[] newArray(int size) {
            return new ChecklistItem[size];
        }
    };
}
