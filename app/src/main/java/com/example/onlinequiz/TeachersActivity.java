package com.example.onlinequiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TeachersActivity extends AppCompatActivity {
AppCompatButton add,delete,update,all,info,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teachers);

        add=findViewById(R.id.addQuestion);
        delete=findViewById(R.id.deleteQuestion);
        update=findViewById(R.id.updateQuestion);
        info=findViewById(R.id.studInfo);
        logout=findViewById(R.id.logOut);
        all=findViewById(R.id.allQuestion);

        add.setOnClickListener(v->{
            Intent intent= new Intent(TeachersActivity.this, AddQuestion.class);
        startActivity(intent);
        });
        delete.setOnClickListener(v->{
            Intent intent= new Intent(TeachersActivity.this, DeleteQuestion.class);
            startActivity(intent);
        });
        update.setOnClickListener(v->{
            Intent intent= new Intent(TeachersActivity.this, UpdateQuestion.class);
            startActivity(intent);
        });
        all.setOnClickListener(v->{
            Intent intent= new Intent(TeachersActivity.this, AllQuestion.class);
            startActivity(intent);
        });
        info.setOnClickListener(v->{
            Intent intent= new Intent(TeachersActivity.this, StudentInformation.class);
            startActivity(intent);
        });
        logout.setOnClickListener(v->{
            Intent intent= new Intent(TeachersActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}