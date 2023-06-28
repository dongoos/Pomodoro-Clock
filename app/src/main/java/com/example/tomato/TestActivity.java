package com.example.tomato;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    Button btnAppUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_test);
        btnAppUsage=findViewById(R.id.btn_app_usage);
        btnAppUsage.setOnClickListener(v -> startActivity(new Intent(TestActivity.this, AppUsageActivity.class)));
    }
}
