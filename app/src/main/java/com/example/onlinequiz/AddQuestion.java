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

public class AddQuestion extends AppCompatActivity {
TextView question_id;
EditText Question,choice_one,choice_two,choice_three,choice_four,answer;
AppCompatButton clear_button,save_button;
DbHelper db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_question);

        question_id=findViewById(R.id.question_ID);
        Question=findViewById(R.id.Question);
        choice_one=findViewById(R.id.A);
        choice_two=findViewById(R.id.B);
        choice_three=findViewById(R.id.C);
        choice_four=findViewById(R.id.D);
        answer=findViewById(R.id.answer);
        clear_button=findViewById(R.id.clear);
        save_button=findViewById(R.id.save);
        db=new DbHelper(this);
        clear_button.setOnClickListener(v->{
            clearButtons();
        });
        save_button.setOnClickListener(v->{
            String mainQuestion=Question.getText().toString();
            String one=choice_one.getText().toString();
            String two=choice_two.getText().toString();
            String three=choice_three.getText().toString();
            String four=choice_four.getText().toString();
            String ans=answer.getText().toString();
            if(one.isEmpty()){
                choice_one.setError("This Filled is Required");
            }
            else if (two.isEmpty()) {
                choice_two.setError("This Filled is Required");
            }   else if (three.isEmpty()) {
                choice_three.setError("This Filled is Required");
            }   else if (four.isEmpty()) {
                choice_four.setError("This Filled is Required");
            }   else if (ans.isEmpty()) {
                answer.setError("This Filled is Required");
            }else if (mainQuestion.isEmpty()) {
                Question.setError("This Filled is Required");
            }

            else if (
                    !ans.equalsIgnoreCase(one) &&
                            !ans.equalsIgnoreCase(two) &&
                            !ans.equalsIgnoreCase(three) &&
                            !ans.equalsIgnoreCase(four)
            ) {
                Toast.makeText(this, "Answer must match one of the four choices", Toast.LENGTH_LONG).show();
            }
           else{
              long result=db.insertQuestion(mainQuestion,one,two,three,four,ans);
              if(result !=-1){
                  Toast.makeText(this, "Questions Are Successfully Saved", Toast.LENGTH_SHORT).show();
              }
              else{
                  Toast.makeText(this, "Error unable to submit the Questions", Toast.LENGTH_SHORT).show();
              }
                clearButtons();
            }

        });
    }
  private void clearButtons(){
      choice_one.setText("");
      choice_two.setText("");
      choice_three.setText("");
      choice_four.setText("");
      answer.setText("");
      Question.setText("");
    }
}