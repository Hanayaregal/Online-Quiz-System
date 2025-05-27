package com.example.onlinequiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TeacherLogin extends AppCompatActivity {
EditText user_name,Password;
Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_login);
        user_name=findViewById(R.id.TeacherUser);
        Password=findViewById(R.id.TeacherPass);
        Login=findViewById(R.id.TeacherLogin);

        String Teacher_Name="Admin";
        String Teacher_Password="admin";

        Login.setOnClickListener(v->{
            String name=user_name.getText().toString();
            String pass=Password.getText().toString();
            if(name.isEmpty()){
                user_name.setError("User name is required");
            } else if (pass.isEmpty()) {
                Password.setError("password filed is required");
            }
            else{
            if(name.equals(Teacher_Name) && pass.equals(Teacher_Password)){
                Intent intent =new Intent(TeacherLogin.this, TeachersActivity.class);
           startActivity(intent);
            }
            else{
                Toast.makeText(this, "User Name or Password is Invalid", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}