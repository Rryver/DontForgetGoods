package com.example.dontforgetgoods;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dontforgetgoods.model.Product;
import com.example.dontforgetgoods.model.Record;
import com.example.dontforgetgoods.utils.ProductService;
import com.example.dontforgetgoods.utils.RecordService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordsActivity extends AppCompatActivity {

    final Context context = this;
    private RecordAdapter recordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_activity);
        ExpandableListView recordsListView = findViewById(R.id.records_list);
        List<Record> records = getIntent().getParcelableArrayListExtra("records");
        Map<Long, List<Product>> productMap = new HashMap<>();
        for (Record record : records) {
            productMap.put(record.getId(), Collections.emptyList());
        }
        recordAdapter = new RecordAdapter(this, records, productMap);
        for (Record record : records) {
            Long recordId = record.getId();
            ProductService productService = new ProductService(recordAdapter);
            productService.getProducts(recordId);
        }
        recordsListView.setAdapter(recordAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.record_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.addRecordButton:
                createAddRecordDialog(recordAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createAddRecordDialog(RecordAdapter recordAdapter) {
        LayoutInflater li = LayoutInflater.from(context);
        View addRecordDialogView = li.inflate(R.layout.dialog_add_record, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context)
                .setView(addRecordDialogView);

        final EditText userInput = (EditText) addRecordDialogView
                .findViewById(R.id.addRecordTitle);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String recordTitle = userInput.getText().toString();
                                if (!recordTitle.isEmpty()) {
                                    RecordService recordService = new RecordService(recordAdapter);
                                    recordService.addRecord(recordTitle);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
