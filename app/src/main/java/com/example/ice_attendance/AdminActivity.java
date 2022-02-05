package com.example.ice_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    private Button sendBtn,coursesBtn,studentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sendBtn = findViewById(R.id.sendteacherBtnId);
        coursesBtn = findViewById(R.id.sendCoursesBtnId);
        studentBtn = findViewById(R.id.sendStudentId);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminActivity.this,AdminTeacherActivity.class);
                startActivity(intent);


            }
        });


        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,SelectbatchandTerm.class);
                startActivity(intent);
                finish();
            }
        });


        coursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminActivity.this,SelectCourseTerm.class);
                startActivity(intent);


            }
        });
        


    }
}