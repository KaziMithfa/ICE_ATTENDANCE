package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPassword extends AppCompatActivity {

    Toolbar toolbar;
    EditText confirmpassword,resetpassword;
    Button confirmBtn;
    private DatabaseReference ref;
    private String key;
    private String type,confirmpass,resetpass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        toolbar = findViewById(R.id.resetpasswordtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        confirmpassword = findViewById(R.id.confirmpassword);
        resetpassword = findViewById(R.id.resetpassword);
        confirmBtn = findViewById(R.id.submissionBtn);


        type = getIntent().getStringExtra("type");

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equals("teacher")) {
                    key = getIntent().getStringExtra("Key");
                    ref = FirebaseDatabase.getInstance().getReference().child("Teachers");


                    if (TextUtils.isEmpty(confirmpassword.getText().toString())) {
                        Toast.makeText(ResetPassword.this, "Please confirm your previous password first...", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(resetpassword.getText().toString())) {
                        Toast.makeText(ResetPassword.this, "Please, reset the password....", Toast.LENGTH_SHORT).show();
                    } else {

                        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Teacher teacher = snapshot.getValue(Teacher.class);
                                    String pass = teacher.getPassword();

                                    if (confirmpassword.getText().toString().equals(pass)) {
                                        String newpass = resetpassword.getText().toString();
                                        HashMap<String, Object> resetpasswordMap = new HashMap<>();
                                        resetpasswordMap.put("password", newpass);
                                        ref.child(key).updateChildren(resetpasswordMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ResetPassword.this, "your password is reset successfully....", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ResetPassword.this, TeacherHomeActivity.class);
                                                    intent.putExtra("Key", key);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                            }
                                        });
                                    } else if (!confirmpassword.getText().toString().equals(pass)) {
                                        Toast.makeText(ResetPassword.this, "your previous  password is incorrect!!! ", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                else if (type.equals("Student")) {
                     key = getIntent().getStringExtra("key");
                    String selectedBatch = getIntent().getStringExtra("batch");
                    String selectedTerm = getIntent().getStringExtra("term");

                    ref = FirebaseDatabase.getInstance().getReference()
                            .child("Batches").child(selectedBatch).child("Students")
                            .child(selectedTerm).child(key);

                    if (TextUtils.isEmpty(confirmpassword.getText().toString())) {
                        Toast.makeText(ResetPassword.this, "Please , confirm your previous password first...", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(resetpassword.getText().toString())) {
                        Toast.makeText(ResetPassword.this, "please , reset your password...", Toast.LENGTH_SHORT).show();
                    } else {

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String pass = snapshot.child("password").getValue().toString();
                                    if (pass.equals(confirmpassword.getText().toString())) {
                                        HashMap<String, Object> resetMap = new HashMap<>();
                                        resetMap.put("password", resetpassword.getText().toString());

                                        ref.updateChildren(resetMap);
                                        Toast.makeText(ResetPassword.this, "Your password has been updated successfully...", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ResetPassword.this, StudentHome.class);
                                        intent.putExtra("key", key);
                                        intent.putExtra("batch", selectedBatch);
                                        intent.putExtra("term", selectedTerm);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(ResetPassword.this, "your previous password is incorrect!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }


                }


            }


        });
    }
}