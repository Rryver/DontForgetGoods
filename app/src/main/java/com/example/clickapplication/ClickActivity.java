package com.example.clickapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_activity);
        TextView textView = findViewById(R.id.response_text);
        Bundle arguments = getIntent().getExtras();
        textView.setText(arguments.get("response").toString());
    }



}