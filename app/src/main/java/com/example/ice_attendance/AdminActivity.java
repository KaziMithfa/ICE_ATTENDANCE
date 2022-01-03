package com.example.ice_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    private Button sendBtn,coursesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sendBtn = findViewById(R.id.sendteacherBtnId);
        coursesBtn = findViewById(R.id.sendCoursesBtnId);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminActivity.this,AdminTeacherActivity.class);
                startActivity(intent);


            }
        });

        


    }
}