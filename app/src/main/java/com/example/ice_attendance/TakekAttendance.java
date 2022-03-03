package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TakekAttendance extends AppCompatActivity {

    private DatabaseReference studentsRef,Ref;

    private String batch,selectedCourse,selectedTerm;
    private List<Student> studentList = new ArrayList<>();
    private RecyclerView recyclerView;
     private TakeAttendanceAdapter takeAttendanceAdapter;

   private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takek_attendance);

        batch = getIntent().getStringExtra("SB");
        selectedCourse = getIntent().getStringExtra("SC");
        selectedTerm = getIntent().getStringExtra("ST");

        recyclerView = findViewById(R.id.tARv);
        submitBtn = findViewById(R.id.submitbtn);



        studentsRef = FirebaseDatabase.getInstance().getReference().child("Batches").child(batch).child("Students")
        .child(selectedTerm);


        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                studentList.clear();


                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Student student = snapshot1.getValue(Student.class);
                        if(student.getCourse_names().contains(selectedCourse)){

                           studentList.add(student);

                        }
                    }

                    takeAttendanceAdapter = new TakeAttendanceAdapter(getApplicationContext(),studentList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(TakekAttendance.this));
                    takeAttendanceAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(takeAttendanceAdapter);



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        submitBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Toast.makeText(TakekAttendance.this,studentList.size(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}