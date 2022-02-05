package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    private Toolbar studentListToolbar;
    private RecyclerView recyclerView;

    private FloatingActionButton addBtn;
    private String Batch,Term;
    private DatabaseReference StudentsRef;
    private List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Batch = getIntent().getStringExtra("Batch");
        Term = getIntent().getStringExtra("Term");

        addBtn = findViewById(R.id.addnewStudentbtnId);

        studentListToolbar = findViewById(R.id.studentListToolbar);
        setSupportActionBar(studentListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.studentListRecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StudentsRef = FirebaseDatabase.getInstance().getReference().child("Batches").child(Batch).child("Students");
        StudentsRef.child(Term).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                studentList.clear();

                if(snapshot.exists()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        Student student = snapshot1.getValue(Student.class);
                        studentList.add(student);

                    }
                }

                StudentListAdapter studentListAdapter = new StudentListAdapter(StudentList.this,studentList);
                studentListAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(studentListAdapter);


            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });







            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StudentList.this,AddStudent.class);
                    intent.putExtra("Batch",Batch);
                    intent.putExtra("Term",Term);
                    startActivity(intent);
                    finish();
                }
            });

        }

    }

