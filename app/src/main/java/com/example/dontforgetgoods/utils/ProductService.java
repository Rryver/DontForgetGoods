package com.example.dontforgetgoods.utils;

import com.example.dontforgetgoods.RecordAdapter;
import com.example.dontforgetgoods.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductService {

    private final RecordAdapter recordAdapter;

    public ProductService(RecordAdapter recordAdapter) {
        this.recordAdapter = recordAdapter;
    }

    public void getProducts(Long recordId) {
        Call<List<Product>> call = ServerService.getInstance().getRestApi().getProducts(recordId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200
                    recordAdapter.updateProducts(recordId, response.body());
                } else {
                    // сервер вернул ошибку
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    public void updateProductStatus(Long productId) {
        Call<String> call = ServerService.getInstance().getRestApi().updateProductStatus(productId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // запрос выполнился успешно, сервер вернул Status 200

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
