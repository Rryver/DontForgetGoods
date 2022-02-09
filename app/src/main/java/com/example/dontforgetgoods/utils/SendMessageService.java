package com.example.dontforgetgoods.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.dontforgetgoods.ClickActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageService {

    private final Activity activity;

    public SendMessageService(Activity activity) {
        this.activity = activity;
    }

    public void sendMessage(String message) {
        Call<String> call = ServerService.getInstance().getRestApi().sendMessage(message);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                    Intent intent = new Intent(activity, ClickActivity.class);
                    intent.putExtra("response", response.body());
                    activity.startActivity(intent);
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
