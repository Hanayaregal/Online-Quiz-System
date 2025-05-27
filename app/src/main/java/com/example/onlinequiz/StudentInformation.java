package com.example.onlinequiz;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentInformation extends AppCompatActivity {
    TableLayout tableLayout;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_information);

        tableLayout = findViewById(R.id.tableLayout);
        db = new DbHelper(this);

        addTableHeader();
        loadStudents();
    }

    private void addTableHeader() {
        TableRow header = new TableRow(this);
        header.setBackgroundColor(Color.rgb(50,9,191)); // Purple
        header.setPadding(5, 5, 5, 5);

        String[] headers = {"ID", "Name", "Department", "Mark"};

        for (String title : headers) {
            TextView tv = new TextView(this);
            tv.setText(title);
            tv.setTextColor(0xFFFFFFFF);
            tv.setPadding(10, 16, 16, 16);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(20);
            header.addView(tv);
        }

        tableLayout.addView(header);
    }

    private void loadStudents() {
        Cursor cursor = db.getStudentsWithMarks();

        int totalStudents = 0;
        int studentsAboveFive = 0;


        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                totalStudents++;

                try {
                    int mark = Integer.parseInt(cursor.getString(3));
                    if (mark > 5) {
                        studentsAboveFive++;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                row.setPadding(8, 8, 8, 8);

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    TextView tv = new TextView(this);
                    tv.setText(cursor.getString(i));
                    tv.setPadding(16, 16, 16, 16);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(17);
                    row.addView(tv);
                }

                tableLayout.addView(row);
            } while (cursor.moveToNext());

            TableRow statsRow = new TableRow(this);
            TextView statsText = new TextView(this);
            statsText.setTextSize(30);
            statsText.setText("Total students: " + totalStudents +"\n"+
                    " Students with mark > 5: " +"  "+ studentsAboveFive);
            statsText.setPadding(16, 16, 16, 16);
            statsText.setGravity(Gravity.CENTER);
            statsText.setTextColor(Color.BLUE);
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.span = 4;
            statsText.setLayoutParams(params);

            statsRow.addView(statsText);
            tableLayout.addView(statsRow);

        } else {
            // No user case
            TableRow row = new TableRow(this);
            TextView tv = new TextView(this);
            tv.setText("No user is found for now.");
            tv.setPadding(16, 16, 16, 16);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xFFFF0000); // Red text
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.span = 4;
            tv.setLayoutParams(params);
            row.addView(tv);
            tableLayout.addView(row);
        }

        if (cursor != null)
            cursor.close();
    }

}