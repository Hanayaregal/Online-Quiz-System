package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questiions extends AppCompatActivity {
    TextView id, min, sec, question, QNo;
    RadioButton choice1, choice2, choice3, choice4;
    AppCompatButton Next, Back;
    RadioGroup radioGroup;
    private int minute = 10;
    private int second = 0;
    DbHelper db;
    private int currentQuestionId = 1;
    private int autoIncrement = 1;
    private int totalQuestions = 0;
    private int mark = 0;
    private List<Integer> questionIds = new ArrayList<>();
    private int currentIndex = 0;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 10 * 60 * 1000;

    // Store selected answers
    private Map<Integer, String> selectedAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questiions);

        id = findViewById(R.id.id);
        min = findViewById(R.id.min);
        sec = findViewById(R.id.sec);
        question = findViewById(R.id.question);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        QNo = findViewById(R.id.QNo);
        Next = findViewById(R.id.next);
        Back = findViewById(R.id.back);
        db = new DbHelper(this);
        radioGroup = findViewById(R.id.radioGroup);

        Intent intent = getIntent();
        String showId = intent.getStringExtra("studentId");
        id.setText(showId);

        questionIds = db.getAllQuestionIds();
        totalQuestions = questionIds.size();

        if (!questionIds.isEmpty()) {
            loadQuestion(questionIds.get(currentIndex));
            QNo.setText("Q " + (currentIndex + 1)+" ");
        }

        Next.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();
                selectedAnswers.put(currentIndex, selectedAnswer);

                currentIndex++;
                autoIncrement++;

                if (currentIndex < totalQuestions) {
                    loadQuestion(questionIds.get(currentIndex));
                    QNo.setText("Q " + (currentIndex + 1));
                } else {
                    // Calculate score before going to result
                    mark = calculateScore();
                    String studentId = id.getText().toString();
                    db.updateStudentMark(studentId, mark);

                    Intent resultIntent = new Intent(Questiions.this, ResultActivity.class);
                    resultIntent.putExtra("score", mark);
                    resultIntent.putExtra("studentId", studentId);
                    startActivity(resultIntent);
                    finish();
                }

            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        });

        Back.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                autoIncrement--;
                loadQuestion(questionIds.get(currentIndex));
                QNo.setText("Q " + (currentIndex + 1));
            } else {
                Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show();
            }
        });

        startTimer();
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questionIds.size(); i++) {
            String selectedAnswer = selectedAnswers.get(i);
            String correctAnswer = db.getCorrectAnswer(questionIds.get(i));
            if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
                score++;
            }
        }
        return score;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                min.setText(String.format("%02d", minutes));
                sec.setText(String.format("%02d", seconds));
            }

            public void onFinish() {
                Toast.makeText(Questiions.this, "Time is Up!", Toast.LENGTH_SHORT).show();
                min.setText("00");
                sec.setText("00");

                mark = calculateScore();
                String studentId = id.getText().toString();
                db.updateStudentMark(studentId, mark);

                Intent resultIntent = new Intent(Questiions.this, ResultActivity.class);
                resultIntent.putExtra("score", mark);
                resultIntent.putExtra("studentId", studentId);
                startActivity(resultIntent);
                finish();
            }
        }.start();
    }

    @SuppressLint("Range")
    private void loadQuestion(int questionId) {
        Cursor cursor = db.getQuestion(questionId);
        if (cursor.moveToFirst()) {
            question.setText(cursor.getString(1));
            choice1.setText(cursor.getString(2));
            choice2.setText(cursor.getString(3));
            choice3.setText(cursor.getString(4));
            choice4.setText(cursor.getString(5));
        }
        cursor.close();
        radioGroup.clearCheck();

        // Restore previously selected answer if available
        String savedAnswer = selectedAnswers.get(currentIndex);
        if (savedAnswer != null) {
            if (choice1.getText().toString().equals(savedAnswer)) choice1.setChecked(true);
            else if (choice2.getText().toString().equals(savedAnswer)) choice2.setChecked(true);
            else if (choice3.getText().toString().equals(savedAnswer)) choice3.setChecked(true);
            else if (choice4.getText().toString().equals(savedAnswer)) choice4.setChecked(true);
        }
    }
}
