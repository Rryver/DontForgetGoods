package com.example.dontforgetgoods;

import com.example.dontforgetgoods.model.Product;
import com.example.dontforgetgoods.model.Record;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @GET("hello/hello")
    Call<String> sendMessage(@Query("msg") String message);

    @GET("records")
    Call<List<Record>> getRecords();

    @GET("records/{recordId}")
    Call<List<Product>> getProducts(@Path("recordId") Long recordId);

    @GET("products/toggle-status/{productId}")
    Call<String> updateProductStatus(@Path("productId") Long productId);

}
