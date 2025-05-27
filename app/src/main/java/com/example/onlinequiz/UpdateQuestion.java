package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateQuestion extends AppCompatActivity {

    EditText searchID;
    AppCompatButton searchBtn,updBtn;
    DbHelper db;
    EditText updQuestionText,updTextOne,updTextTwo,updTextThree,updTextFour,updAnswer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_question);
        searchID = findViewById(R.id.upd_Qid);
        searchBtn = findViewById(R.id.upd_search);
        updBtn = findViewById(R.id.updateBtn);
        updQuestionText = findViewById(R.id.upd_Question);
        updTextOne = findViewById(R.id.upd_A);
        updTextTwo = findViewById(R.id.upd_B);
        updTextThree = findViewById(R.id.upd_C);
        updTextFour = findViewById(R.id.upd_D);
        updAnswer = findViewById(R.id.upd_Answer);

        db = new DbHelper(this);

        searchBtn.setOnClickListener(v -> {
            String queIdStr = searchID.getText().toString().trim();
            if (queIdStr.isEmpty()) {
                searchID.setError("This field is required");
            }
       else{
            int queId = Integer.parseInt(queIdStr);
            QuestionData question = db.getQuestionById(queId); // you'll create this method

            if (question == null) {
                Toast.makeText(this, "Question not found", Toast.LENGTH_SHORT).show();
                clearTexts();
            } else {
                updQuestionText.setText(question.getQuestion());
                updTextOne.setText(question.getChoice1());
                updTextTwo.setText(question.getChoice2());
                updTextThree.setText(question.getChoice3());
                updTextFour.setText(question.getChoice4());
                updAnswer.setText(question.getAnswer());

            }
          }
        });


        updBtn.setOnClickListener(v -> {
            String idStr = searchID.getText().toString().trim();
            if (idStr.isEmpty()) {
                searchID.setError("This field is required");
            }
       else {
                int id = Integer.parseInt(idStr);
                String question = updQuestionText.getText().toString().trim();
                String a = updTextOne.getText().toString().trim();
                String b = updTextTwo.getText().toString().trim();
                String c = updTextThree.getText().toString().trim();
                String d = updTextFour.getText().toString().trim();
                String ans = updAnswer.getText().toString().trim();

                if (question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()|| ans.isEmpty()){
                    Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean updated = db.updateQuestion(id, question, a, b, c, d, ans);

                if (updated) {
                    Toast.makeText(this, "Question updated successfully", Toast.LENGTH_SHORT).show();
                    clearTexts();
                } else {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void clearTexts() {
    searchID.setText("");
    updQuestionText.setText("");
    updTextOne.setText("");
    updTextTwo.setText("");
    updTextThree.setText("");
    updTextFour.setText("");
    updAnswer.setText("");
    }
}