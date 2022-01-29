package com.example.clickapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clickapplication.model.Record;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.220/api-v1/") // Адрес сервера
            .addConverterFactory(GsonConverterFactory.create()) // говорим ретрофиту что для сериализации необходимо использовать GSON
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        RestApi service = retrofit.create(RestApi.class);
        Call<String> call = service.sendMessage("click");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                    Intent intent = new Intent(MainActivity.this, ClickActivity.class);
                    intent.putExtra("response", response.body());
                    startActivity(intent);
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void getRecords(View view) {
        RestApi service = retrofit.create(RestApi.class);
        Call<Record[]> call = service.getRecords();
        call.enqueue(new Callback<Record[]>() {
            @Override
            public void onResponse(Call<Record[]> call, Response<Record[]> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                    Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
                    intent.putExtra("records", response.body());
                    startActivity(intent);
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<Record[]> call, Throwable t) {

            }
        });
    }

}