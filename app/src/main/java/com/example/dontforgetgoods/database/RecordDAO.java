package com.example.dontforgetgoods.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDAO {

    @Query("SELECT * FROM " + Record.TABLE_NAME)
    Cursor getAll();

    /**
     * Select a record by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected cheese.
     */
    @Query("SELECT * FROM " + Record.TABLE_NAME + " WHERE " + Record.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    @Query("SELECT * FROM " + Record.TABLE_NAME + " WHERE " + Record.COLUMN_ID + " IN (:recordIds)")
    Cursor loadAllByIds(int[] recordIds);

    @Query("SELECT * FROM " + Record.TABLE_NAME + " WHERE " + Record.COLUMN_TITLE + " LIKE :title LIMIT 1")
    Cursor findByTitle(String title);

    /**
     * Inserts a record into the table.
     *
     * @param record A new record.
     * @return The row ID of the newly inserted record.
     */
    @Insert
    long insert(Record record);

    @Insert
    void insertAll(Record... records);

    @Delete
    void delete(Record record);

}
