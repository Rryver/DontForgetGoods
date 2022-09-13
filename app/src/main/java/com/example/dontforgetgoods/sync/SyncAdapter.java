package com.example.dontforgetgoods.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;

import com.example.dontforgetgoods.RecordsActivity;
import com.example.dontforgetgoods.model.Record;
import com.example.dontforgetgoods.utils.RecordService;
import com.example.dontforgetgoods.utils.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver contentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        contentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Call<List<Record>> call = ServerService.getInstance().getRestApi().getRecords();
        try {
            Response<List<Record>> response = call.execute();
            List<Record> records = response.body();
            for (Record record: records) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", record.getId());
                contentValues.put("title", record.getTitle());
                provider.insert(RecordProvider.URI_RECORD, contentValues);
            }
        } catch (IOException | RemoteException e) {
            e.printStackTrace();
        }
    }

}
