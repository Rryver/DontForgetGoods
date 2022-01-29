package com.example.clickapplication;

import com.example.clickapplication.model.Record;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("hello/hello")
    Call<String> sendMessage(@Query("msg") String message);

    @GET("records")
    Call<Record[]> getRecords();

}
