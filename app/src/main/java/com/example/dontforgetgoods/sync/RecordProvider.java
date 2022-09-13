package com.example.dontforgetgoods.sync;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dontforgetgoods.database.AppDatabase;
import com.example.dontforgetgoods.database.Record;
import com.example.dontforgetgoods.database.RecordDAO;

/*
 * Define an implementation of ContentProvider that stubs out
 * all methods
 */
public class RecordProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "com.example.dontforgetgoods.provider";

    /** The URI for the Record table. */
    public static final Uri URI_RECORD = Uri.parse(
            "content://" + AUTHORITY + "/" + Record.TABLE_NAME);

    /** The match code for some items in the Record table. */
    private static final int CODE_RECORD_DIR = 1;

    /** The match code for an item in the Record table. */
    private static final int CODE_RECORD_ITEM = 2;

    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Record.TABLE_NAME, CODE_RECORD_DIR);
        MATCHER.addURI(AUTHORITY, Record.TABLE_NAME + "/*", CODE_RECORD_ITEM);
    }

    /*
     * Always return true, indicating that the
     * provider loaded correctly.
     */
    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_RECORD_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Record.TABLE_NAME;
            case CODE_RECORD_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Record.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_RECORD_DIR || code == CODE_RECORD_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            RecordDAO recordDAO = AppDatabase.getInstance(context).recordDAO();
            final Cursor cursor;
            if (code == CODE_RECORD_DIR) {
                cursor = recordDAO.getAll();
            } else {
                cursor = recordDAO.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_RECORD_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = AppDatabase.getInstance(context).recordDAO()
                        .insert(Record.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_RECORD_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
    /*
     * delete() always returns "no rows affected" (0)
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
    /*
     * update() always returns "no rows affected" (0)
     */
    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {
        return 0;
    }
}