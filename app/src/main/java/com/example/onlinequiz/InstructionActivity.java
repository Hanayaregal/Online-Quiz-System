package com.example.onlinequiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InstructionActivity extends AppCompatActivity {
Button startQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instruction);
        startQuiz=findViewById(R.id.startQuizBtn);
        Intent intent= getIntent();
        String showId=intent.getStringExtra("studentId");
        startQuiz.setOnClickListener(v->{
            Intent quizIntent = new Intent(InstructionActivity.this, Questiions.class);
            quizIntent.putExtra("studentId", showId);
            startActivity(quizIntent);
            finish();
        });
    }
}