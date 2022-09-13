package com.example.dontforgetgoods;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dontforgetgoods.database.AppDatabase;
import com.example.dontforgetgoods.database.Record;
import com.example.dontforgetgoods.database.RecordDAO;
import com.example.dontforgetgoods.sync.RecordProvider;
import com.example.dontforgetgoods.sync.SyncService;
import com.example.dontforgetgoods.utils.RecordService;
import com.example.dontforgetgoods.utils.SendMessageService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    // Constants
    // Content provider authority
    public static final String AUTHORITY = "com.example.dontforgetgoods.provider";
    // Account
    public static final String ACCOUNT = "default_account";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.android.example.datasync";
    // Sync interval constants
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;
    // Global variables
    // A content resolver for accessing the provider
    ContentResolver mResolver;
    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccount = CreateSyncAccount(this);

        // Get the content resolver for your app
        mResolver = getContentResolver();
        /*
         * Turn on periodic syncing
         */
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                SYNC_INTERVAL);

//        db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database-name").build();
    }

    public void sendMessage(View view) {
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);

        SendMessageService sendMessageService = new SendMessageService(this);
        sendMessageService.sendMessage("click");
    }

    public void getRecords(View view) {
//        RecordDAO recordDAO = db.recordDAO();
//        List<Record> records = recordDAO.getAll();

        RecordService recordService = new RecordService(this, mResolver);
        recordService.getRecords();
    }

    /**
     * Create a new placeholder account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }

}