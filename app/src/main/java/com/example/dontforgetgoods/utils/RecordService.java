package com.example.dontforgetgoods.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.example.dontforgetgoods.RecordsActivity;
import com.example.dontforgetgoods.model.Record;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordService {

    private final Activity activity;

    public RecordService(Activity activity) {
        this.activity = activity;
    }

    public void getRecords() {
        Call<List<Record>> call = ServerService.getInstance().getRestApi().getRecords();
        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                    Intent intent = new Intent(activity, RecordsActivity.class);
                    intent.putParcelableArrayListExtra("records", (ArrayList<? extends Parcelable>) response.body());
                    activity.startActivity(intent);
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {

            }
        });
    }

}
