package com.example.clickapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clickapplication.model.Record;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_activity);
        ListView recordsListView = findViewById(R.id.records_list);
        Record[] records = (Record[]) getIntent().getExtras().get("records");
        RecordAdapter recordAdapter = new RecordAdapter(this, records);
        recordsListView.setAdapter(recordAdapter);
    }

}