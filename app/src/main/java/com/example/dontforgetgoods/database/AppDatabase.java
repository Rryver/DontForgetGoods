package com.example.dontforgetgoods.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecordDAO recordDAO();

    /** The only instance */
    private static AppDatabase sInstance;

    /**
     * Gets the singleton instance of SampleDatabase.
     *
     * @param context The context.
     * @return The singleton instance of SampleDatabase.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

}
