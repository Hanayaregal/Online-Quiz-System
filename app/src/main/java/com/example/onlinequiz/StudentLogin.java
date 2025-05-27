package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentLogin extends AppCompatActivity {
    EditText id_no,pass;
    AppCompatButton signin,signup;
    DbHelper db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_login);
        signin =findViewById(R.id.login);
        id_no=findViewById(R.id.stud_id);
        pass=findViewById(R.id.btnpass);
        db=new DbHelper(this);
        signup=findViewById(R.id.forward);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNo=id_no.getText().toString().trim();
                String password=pass.getText().toString();
                if(idNo.isEmpty()){
                    id_no.setError("ID Number can not be empty");
                } else if (password.isEmpty()) {
                    pass.setError("password filed can not be empty");
                }
                else {
                  if( db.checkStudent( idNo,password)){
                      Intent intent = new Intent(StudentLogin.this, Questiions.class);
                          startActivity(intent);

                  }
                  else {
                      Toast.makeText(StudentLogin.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
                  }
                }

            } });
        signup.setOnClickListener(v->{
            Intent intent = new Intent(StudentLogin.this, StudentRegistration.class);
            startActivity(intent);
        });
    }
    }