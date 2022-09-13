package com.example.dontforgetgoods.database;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(tableName = Record.TABLE_NAME)
public class Record {

    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "record";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_TITLE = "title";

    /** The name of the name column. */
    public static final String COLUMN_CREATED_AT = "created_at";

    @PrimaryKey
    @ColumnInfo(index = true, name = COLUMN_ID)
    private Long id;

    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = COLUMN_CREATED_AT)
    private Long createdAt;

    @ColumnInfo(name = "updated_at")
    private Long updatedAt;

    /**
     * Create a new {@link Record} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain {@link #COLUMN_TITLE}.
     * @return A newly created {@link Record} instance.
     */
    @NonNull
    public static Record fromContentValues(@Nullable ContentValues values) {
        final Record record = new Record();
        if (values != null && values.containsKey(COLUMN_ID)) {
            record.id = values.getAsLong(COLUMN_ID);
        }
        if (values != null && values.containsKey(COLUMN_TITLE)) {
            record.title = values.getAsString(COLUMN_TITLE);
        }
        if (values != null && values.containsKey(COLUMN_CREATED_AT)) {
            record.createdAt = values.getAsLong(COLUMN_CREATED_AT);
        }
        return record;
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

}
