package com.example.dontforgetgoods.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;

import com.example.dontforgetgoods.RecordAdapter;
import com.example.dontforgetgoods.RecordsActivity;
import com.example.dontforgetgoods.model.Record;
import com.example.dontforgetgoods.sync.RecordProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordService {

    private Activity activity;
    private RecordAdapter recordAdapter;
    private ContentResolver resolver;

    public RecordService(RecordAdapter recordAdapter) {
        this.recordAdapter = recordAdapter;
    }

    public RecordService(Activity activity, ContentResolver resolver) {
        this.activity = activity;
        this.resolver = resolver;
    }

    public void getRecords() {
        ArrayList<Record> list = new ArrayList<>();
        Cursor cursor = resolver.query(RecordProvider.URI_RECORD, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Record record = new Record();
                record.setId(cursor.getLong(0));
                record.setTitle(cursor.getString(1));
                record.setCreatedAt(cursor.getLong(2));
                list.add(record);
            }
            cursor.close();
        }

        Intent intent = new Intent(activity, RecordsActivity.class);
        intent.putParcelableArrayListExtra("records", list);
        activity.startActivity(intent);

//        Call<List<Record>> call = ServerService.getInstance().getRestApi().getRecords();
//        call.enqueue(new Callback<List<Record>>() {
//            @Override
//            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
//                if (response.isSuccessful()) {
//                    // запрос выполнился успешно, сервер вернул Status 200
//                    Intent intent = new Intent(activity, RecordsActivity.class);
//                    intent.putParcelableArrayListExtra("records", (ArrayList<? extends Parcelable>) response.body());
//                    activity.startActivity(intent);
//                } else {
//                    // сервер вернул ошибку
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Record>> call, Throwable t) {
//
//            }
//        });
    }

    public void addRecord(String recordTitle) {
        Call<Record> call = ServerService.getInstance().getRestApi().addRecord(new Record(recordTitle));
        call.enqueue(new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                    recordAdapter.addRecord(response.body());
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {

            }
        });
    }
}
