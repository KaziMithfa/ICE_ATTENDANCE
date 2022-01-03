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

public class AdminLogin extends AppCompatActivity {

    private EditText NameEditText, passwordEditText;
    private Button LoginBtn;
    private DatabaseReference Ref;
    private String phone = "01837705605";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        NameEditText = findViewById(R.id.nameEditTextId);
        passwordEditText = findViewById(R.id.passwordEditTextId);
        LoginBtn = findViewById(R.id.loginBtnId);
        Ref = FirebaseDatabase.getInstance().getReference().child("admins");




        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = NameEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(AdminLogin.this, "Please , insert the name of the admin...", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(AdminLogin.this, "Please , insert the password....", Toast.LENGTH_SHORT).show();
                }

                else{

                    ValidAdmin(name,password);

                }





            }

            private void ValidAdmin(String name ,String password) {



                Ref.child(phone).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (snapshot.hasChild("name") && snapshot.hasChild("password")) {
                            String AdminName = snapshot.child("name").getValue().toString();
                            String Adminpassword = snapshot.child("password").getValue().toString();

                            if (AdminName.equals(name)) {
                                if (Adminpassword.equals(password)) {
                                    Intent intent = new Intent(AdminLogin.this, AdminActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(AdminLogin.this, "You are logged in successfully....", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AdminLogin.this, "Your password in incorrect ,please , try again!!!!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminLogin.this, "Your name is incorrect... , please ,try again!!!!!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }
        });


    }
}