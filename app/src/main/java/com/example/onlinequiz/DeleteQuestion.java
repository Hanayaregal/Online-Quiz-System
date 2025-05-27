package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class DeleteQuestion extends AppCompatActivity {

    EditText SearchID;
    AppCompatButton SearchBtn, DeleteBtn;
    TextView QuestionText, TextOne, TextTwo, TextThree, TextFour;
    DbHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_question);

        SearchID = findViewById(R.id.del_Qid);
        SearchBtn = findViewById(R.id.del_search);
        DeleteBtn = findViewById(R.id.del_delete);
        QuestionText = findViewById(R.id.del_Question);
        TextOne = findViewById(R.id.del_A);
        TextTwo = findViewById(R.id.del_B);
        TextThree = findViewById(R.id.del_C);
        TextFour = findViewById(R.id.del_D);

        db = new DbHelper(this);

        SearchBtn.setOnClickListener(v -> {
            String strid = SearchID.getText().toString().trim();
            if (strid.isEmpty()) {
                SearchID.setError("This field is required");
            } else {
                // search the question
                QuestionModel question = db.getQuestionById(strid);
                if (question != null) {
                    QuestionText.setText(question.getQuestion());
                    TextOne.setText(question.getChoice1());
                    TextTwo.setText(question.getChoice2());
                    TextThree.setText(question.getChoice3());
                    TextFour.setText(question.getChoice4());
                } else {
                    Toast.makeText(this, "Question not found", Toast.LENGTH_SHORT).show();
                    clearTexts();
                }
            }
        });

        DeleteBtn.setOnClickListener(v -> {
            String deleteQuestionId = SearchID.getText().toString().trim();
            if (deleteQuestionId.isEmpty()) {
                SearchID.setError("This field is required");
            } else {
                // Show confirmation dialog
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Deletion")
                        .setMessage("Are you sure you want to delete this question?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            boolean deleted = db.deleteQuestion(deleteQuestionId);
                            if (deleted) {
                                Toast.makeText(this, "Question deleted successfully", Toast.LENGTH_SHORT).show();
                                clearTexts();
                            } else {
                                Toast.makeText(this, "Failed to delete. Check ID.", Toast.LENGTH_SHORT).show();
                                SearchID.setError("The ID is not found");
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss(); // Just close the dialog
                        })
                        .show();
            }
        });

    }

    private void clearTexts() {
        QuestionText.setText("");
        TextOne.setText("");
        TextTwo.setText("");
        TextThree.setText("");
        TextFour.setText("");
        SearchID.setText("");
    }
}
