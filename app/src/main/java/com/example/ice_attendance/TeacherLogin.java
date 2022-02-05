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

import java.util.ArrayList;
import java.util.List;

public class TeacherLogin extends AppCompatActivity {

    private EditText Id,password;
    private Button loginBtn;

    private static String key = "";
    private DatabaseReference TeachersRef;
    private List<String> IdList = new ArrayList<>();
    private List<String> passwordList = new ArrayList<>();
    private  int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        Id = findViewById(R.id.teacheridID);
        password = findViewById(R.id.teacherpasswordID);
        loginBtn = findViewById(R.id.teacherLoginBtnId);

        TeachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers");




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String id = Id.getText().toString();
               String pass = password.getText().toString();


                if(TextUtils.isEmpty(id)){
                    Toast.makeText(TeacherLogin.this, "Please , insert the Id", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(TeacherLogin.this, "Please , insert the password", Toast.LENGTH_SHORT).show();
                }

                else{
                    verifyTeacher(id,pass);
                }
            }
        });


    }

    private void verifyTeacher(String id, String pass) {

        TeachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                IdList.clear();
                passwordList.clear();

                if(snapshot.hasChildren()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                         key = dataSnapshot.getKey();
                        Teacher teacher = dataSnapshot.getValue(Teacher.class);

                        IdList.add(teacher.getId());
                        passwordList.add(teacher.getPassword());

                        if(IdList.contains(id)){
                            i =1;
                            break;
                        }


                    }


                }


                if(i== 1){
                    if(passwordList.contains(pass)){
                        Intent intent = new Intent(TeacherLogin.this,TeacherHomeActivity.class);
                        intent.putExtra("Key",key);
                        startActivity(intent);

                        Toast.makeText(TeacherLogin.this, "You are logged in successfully......", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(TeacherLogin.this, "your password is incorrect...!", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(TeacherLogin.this, "This id is not registered.....", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });





    }
}