package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditQuestionActivity extends AppCompatActivity {

    EditText editQuestion, editOption1, editOption2, editOption3, editOption4,editAnswer;
    Button btnUpdate,btnDelete;
    DbHelper db;
    int questionId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        editQuestion = findViewById(R.id.editQuestion);
        editOption1 = findViewById(R.id.editOption1);
        editOption2 = findViewById(R.id.editOption2);
        editOption3 = findViewById(R.id.editOption3);
        editOption4 = findViewById(R.id.editOption4);
        editAnswer = findViewById(R.id.editAnswer);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btndelete);

        db = new DbHelper(this);
        questionId = getIntent().getIntExtra("question_id", -1);

        if (questionId != -1) {
            QuizItem item = db.getQuestionByIdd(questionId);  // You’ll need to implement this in DbHelper
            editQuestion.setText(item.getQuestion());
            editOption1.setText(item.getChoice1());
            editOption2.setText(item.getChoice2());
            editOption3.setText(item.getChoice3());
            editOption4.setText(item.getChoice4());
            editAnswer.setText("Answer:  "+item.getAnswer());
        }

        btnUpdate.setOnClickListener(v -> {
            QuizItem updated = new QuizItem(
                    questionId,
                    editQuestion.getText().toString(),
                    editOption1.getText().toString(),
                    editOption2.getText().toString(),
                    editOption3.getText().toString(),
                    editOption4.getText().toString(),
                    editAnswer.getText().toString());
            db.updateQuestions(updated);
            Toast.makeText(this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
            finish();
        });


            btnDelete.setOnClickListener(v -> {
                db.deleteQuestion(String.valueOf(questionId));
                Toast.makeText(this, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                finish();
            });



    }
}
