package com.gehad.todotask.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

class CommentTodo implements Parcelable {


    private String description;

    private Long date;
    public final static Parcelable.Creator<CommentTodo> CREATOR = new Creator<CommentTodo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CommentTodo createFromParcel(Parcel in) {
            return new CommentTodo(in);
        }

        public CommentTodo[] newArray(int size) {
            return (new CommentTodo[size]);
        }

    };

    protected CommentTodo(Parcel in) {
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    public CommentTodo() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(description);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}