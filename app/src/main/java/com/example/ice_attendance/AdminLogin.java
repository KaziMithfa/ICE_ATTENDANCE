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

    private EditText PhoneEditText, passwordEditText;
    private Button LoginBtn;
    private DatabaseReference Ref;
    private String phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        PhoneEditText = findViewById(R.id.phoneEditTextId);
        passwordEditText = findViewById(R.id.passwordEditTextId);
        LoginBtn = findViewById(R.id.loginBtnId);
        Ref = FirebaseDatabase.getInstance().getReference().child("admins");




        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = PhoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if(TextUtils.isEmpty(phone))
                {
                    Toast.makeText(AdminLogin.this, "Please , insert the name of the admin...", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(AdminLogin.this, "Please , insert the password....", Toast.LENGTH_SHORT).show();
                }

                else{

                    ValidAdmin(phone,password);

                }





            }

            private void ValidAdmin(String phone ,String password) {

              Ref.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if(snapshot.child(phone).exists()){

                          String pass = snapshot.child(phone).child("password").getValue().toString();

                          if(pass.equals(password)){


                              Toast.makeText(AdminLogin.this, "Welcome you are logged in successfully...!!!!", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(AdminLogin.this,AdminActivity.class);
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              startActivity(intent);

                          }

                          else{
                              Toast.makeText(AdminLogin.this, "your password is incorrect..", Toast.LENGTH_SHORT).show();
                          }



                      }

                      else{
                          Toast.makeText(AdminLogin.this, "This admin number is not valid.....", Toast.LENGTH_SHORT).show();
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });


            }
        });


    }
}