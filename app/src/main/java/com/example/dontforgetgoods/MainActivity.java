package com.example.dontforgetgoods;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dontforgetgoods.utils.RecordService;
import com.example.dontforgetgoods.utils.SendMessageService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        SendMessageService sendMessageService = new SendMessageService(this);
        sendMessageService.sendMessage("click");
    }

    public void getRecords(View view) {
        RecordService recordService = new RecordService(this);
        recordService.getRecords();
    }

}