package com.example.ice_attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TypeActivity extends AppCompatActivity {

    private CardView AdminCardView,TeacherCardView,StudentCardView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        AdminCardView = findViewById(R.id.adminCardViewId);
        TeacherCardView = findViewById(R.id.TeacherCardViewId);
        StudentCardView = findViewById(R.id.StudentCardViewId);

        AdminCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this,AdminLogin.class);
                startActivity(intent);
                finish();
            }
        });





    }
}