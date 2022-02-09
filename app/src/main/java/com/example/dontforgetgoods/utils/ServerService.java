package com.example.dontforgetgoods.utils;

import com.example.dontforgetgoods.RestApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerService {

    private static final String BASE_URL = "http://192.168.0.220/api/v1/";

    private static ServerService mInstance;
    private final Retrofit retrofit;

    private ServerService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServerService getInstance() {
        if (mInstance == null) {
            mInstance = new ServerService();
        }
        return mInstance;
    }

    public RestApi getRestApi() {
        return retrofit.create(RestApi.class);
    }

}
