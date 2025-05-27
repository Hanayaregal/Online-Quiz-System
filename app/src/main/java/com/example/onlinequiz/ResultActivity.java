package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    TextView resultText, studentId;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        resultText = findViewById(R.id.resultText);
        studentId = findViewById(R.id.studentId);

        Intent intent = getIntent();
        int mark = intent.getIntExtra("score", 0);
        String id = intent.getStringExtra("studentId");

        studentId.setText("ID: " + id);
        resultText.setText("Your Score is: " + mark);
    }
}

