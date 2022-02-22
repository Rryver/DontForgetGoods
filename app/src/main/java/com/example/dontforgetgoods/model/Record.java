package com.example.dontforgetgoods.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Record implements Parcelable {

    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "created_at")
    private Long createdAt;
    @JsonProperty(value = "updated_at")
    private Long updatedAt;

    public Record() {}

    public Record(String title) {
        this.title = title;
    }

    public Record(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.createdAt = in.readLong();
        this.updatedAt = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(createdAt);
        dest.writeLong(updatedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

}
