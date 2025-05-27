package com.example.onlinequiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentRegistration extends AppCompatActivity {
AppCompatButton register;
    AppCompatButton log_page;
    Spinner unvi,dept;
EditText id_no,user_name,password,confirm_pass;
DbHelper db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_registration);

        id_no=findViewById(R.id.idno);
        user_name=findViewById(R.id.studname);
        password=findViewById(R.id.pass);
        confirm_pass=findViewById(R.id.btnconfirm);
        unvi=findViewById(R.id.unvi);
        dept=findViewById(R.id.dept);
        register=findViewById(R.id.Register);
        log_page=findViewById(R.id.back);
        db=new DbHelper(this);

        String [] university_list = {"Medela Amba University","Adama Science and Technology University","Addis Ababa Science and Technology University","Addis Ababa University","Debre Berhan University","Debre Markos University","Dire Dawa University","Gondar University","Harar University","Mekele University"};
        ArrayAdapter<String> universityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,university_list);
        unvi.setAdapter(universityAdapter);
        String [] Dept_list = {"Software Engineering","IT","CS","Sport","Biology"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Dept_list);
        dept.setAdapter(yearAdapter);

        register.setOnClickListener(v->{
            String idNo=id_no.getText().toString().trim();
            String name=user_name.getText().toString();
            String pass=password.getText().toString();
            String confirm=confirm_pass.getText().toString();
            String university=unvi.getSelectedItem().toString();
            String department=dept.getSelectedItem().toString();
            if(idNo.isEmpty()||name.isEmpty()||pass.isEmpty()||confirm.isEmpty()||university.isEmpty()||department.isEmpty()){
                id_no.setError("This filled is can not be empty");
                user_name.setError("This filled is can not be empty");
                password.setError("This filled is can not be empty");
                confirm_pass.setError("This filled is can not be empty");

                register.setClickable(false);
            }
           else  if(!confirm.equals(pass)){
            confirm_pass.setError("Your password is not matched");
            }
           else{
               long inserted=db.studentRegister(idNo,name,pass,university,department);
               if(inserted !=-1){
                   Toast.makeText(this, "You are successfully Registered ", Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(StudentRegistration.this, InstructionActivity.class);
                   intent.putExtra("studentId",idNo);
                   startActivity(intent);
               }
               else {
                Toast.makeText(this, "Ops sorry Registration failed ", Toast.LENGTH_LONG).show();
            }
           }
        });

        log_page.setOnClickListener(v->{
            Intent intent =new Intent(StudentRegistration.this,StudentLogin.class);
            startActivity(intent);
        });
    }
}