package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeacher extends AppCompatActivity {

    private Toolbar AddTeacherToolbar;
    private EditText TeacherName,TeacherEmail,TeacherAddress,TeacherPhone,TeacherId;
    private Spinner TeacherDesignationsp;
    private String[] desigList;
    private String selectDesig;
    private Button AddTeacherBtn;
    private DatabaseReference TeachersRef;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        AddTeacherToolbar = findViewById(R.id.addTeacherToolbar);
        setSupportActionBar(AddTeacherToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TeacherName = findViewById(R.id.addTeacherName);
        TeacherEmail = findViewById(R.id.addTeacherEmail);
        TeacherAddress = findViewById(R.id.addteacherAddress);
        TeacherPhone = findViewById(R.id.addTeacherPhone);
        TeacherId = findViewById(R.id.addTeacherId);
        TeacherDesignationsp = findViewById(R.id.teacherDesignationSp);
        AddTeacherBtn = findViewById(R.id.addTBtn);

        desigList = getResources().getStringArray(R.array.desig);


        ArrayAdapter<String>arrayAdapter = new ArrayAdapter<>(AddTeacher.this, android.R.layout.simple_list_item_1,desigList);
        TeacherDesignationsp.setAdapter(arrayAdapter);

       TeacherDesignationsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               selectDesig = adapterView.getItemAtPosition(i).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


        AddTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTeacher();
            }
        });

    }

    public void AddTeacher()

    {
        String name = TeacherName.getText().toString();
        String Email = TeacherEmail.getText().toString();
        String address = TeacherAddress.getText().toString();
        String phone = TeacherPhone.getText().toString();
        String Id = TeacherId.getText().toString();

        if(name.isEmpty())
        {
            TeacherName.setError("Enter teacher Name");
            TeacherName.requestFocus();
        }
        else if(Email.isEmpty()){
            TeacherEmail.setError("Enter teacher Email");
            TeacherEmail.requestFocus();
        }


        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            TeacherEmail.setError("Enter Email");
            TeacherEmail.requestFocus();
        }

        else if(address.isEmpty()){
            TeacherAddress.setError("Enter teacher Email");
            TeacherAddress.requestFocus();
        }

        else if(phone.isEmpty()){
            TeacherPhone.setError("Enter teacher Email");
            TeacherPhone.requestFocus();
        }

        else if (selectDesig.isEmpty() && selectDesig.equals("Select Designation"))
        {
            Toast.makeText(this, "Select designation", Toast.LENGTH_SHORT).show();
        }

        else if (Id.isEmpty()){
            TeacherId.setError("Please enter the id");
            TeacherId.requestFocus();
        }

        else{

            TeachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
            String key = TeachersRef.push().getKey();


            Teacher teacher = new Teacher(Id,name,Email,address,phone,selectDesig,"1234");
            TeachersRef.child(key).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull  Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddTeacher.this, "Teacher data is added successfully.....", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddTeacher.this,AdminTeacherActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            });

        }






    }
}