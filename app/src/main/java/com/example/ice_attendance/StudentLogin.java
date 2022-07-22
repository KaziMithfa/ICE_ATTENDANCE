package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLogin extends AppCompatActivity {

    private EditText  StudentId,StudentPassword;
    private Button LoginBtn;

    private DatabaseReference StudentsRef;
    private String selectedBatch,selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);



        selectedBatch = getIntent().getStringExtra("Batch");
        selectedTerm = getIntent().getStringExtra("Term");

        StudentId = findViewById(R.id.StudentLoginId);
        StudentPassword = findViewById(R.id.StudentPassword);
        LoginBtn = findViewById(R.id.StudentLoginBtn);




        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id,password;
                id = StudentId.getText().toString();
                password = StudentPassword.getText().toString();

                if(TextUtils.isEmpty(id)){
                    Toast.makeText(StudentLogin.this, "Please, insert the id....", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(StudentLogin.this, "Please , insert the password...", Toast.LENGTH_SHORT).show();
                }
                else{
                    ValidateStudent(id,password);
                }
            }
        });


    }

    private void ValidateStudent( String id,  String password) {

        StudentsRef = FirebaseDatabase.getInstance().getReference().child("Batches")
                .child(selectedBatch).child("Students").child(selectedTerm);

        StudentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(id)){

                    StudentsRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String pass = snapshot.child("password").getValue().toString();


                                if(password.equals(pass)){
                                    Intent intent = new Intent(StudentLogin.this,StudentHome.class);
                                    intent.putExtra("key",id);
                                    intent.putExtra("batch",selectedBatch);
                                    intent.putExtra("term",selectedTerm);
                                    startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(StudentLogin.this, "your  password is incorrect!!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                else{
                    Toast.makeText(StudentLogin.this, "The student id is not authentic....", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}