package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllQuestion extends AppCompatActivity {

    RecyclerView recyclerView;
    QuizAdapter adapter;
    List<QuizItem> quizList;
    DbHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_question);

        recyclerView = findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DbHelper(this);
        quizList = db.getAllQuestions();
        adapter = new QuizAdapter(this, quizList);

        // 🔁 Set the click listener
        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(AllQuestion.this, EditQuestionActivity.class);
            intent.putExtra("question_id", item.getId()); // pass ID for editing
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

    }
}
