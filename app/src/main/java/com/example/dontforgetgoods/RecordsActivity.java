package com.example.dontforgetgoods;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dontforgetgoods.model.Product;
import com.example.dontforgetgoods.model.Record;
import com.example.dontforgetgoods.utils.ProductService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_activity);
        ExpandableListView recordsListView = findViewById(R.id.records_list);
        List<Record> records = getIntent().getParcelableArrayListExtra("records");
        Map<Long, List<Product>> productMap = new HashMap<>();
        for (Record record: records) {
            productMap.put(record.getId(), Collections.emptyList());
        }
        RecordAdapter recordAdapter = new RecordAdapter(this, records, productMap);
        for (Record record: records) {
            Long recordId = record.getId();
            ProductService productService = new ProductService(recordAdapter);
            productService.getProducts(recordId);
        }
        recordsListView.setAdapter(recordAdapter);
    }

}