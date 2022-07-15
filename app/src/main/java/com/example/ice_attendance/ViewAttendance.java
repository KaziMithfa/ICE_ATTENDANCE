package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class ViewAttendance extends AppCompatActivity implements ViewAttendanceOnclick{

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    int  presentNumber =  0;
    int absentNumber = 0;

    private String selectedBatch,selectedCourse,selectedTerm,selectedTeacher;

    private DatabaseReference StudentsRef;
    private List<Student>studentList;
    ViewAttendanceOnclick onclick;
    private DatabaseReference AttendanceRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        recyclerView = findViewById(R.id.viewAttendanceRV);
        toolbar = findViewById(R.id.viewAttendanceToolbar);

        selectedBatch = getIntent().getStringExtra("SB");
        selectedCourse = getIntent().getStringExtra("SC");
        selectedTerm = getIntent().getStringExtra("ST");
        selectedTeacher = getIntent().getStringExtra("teacherName");

        studentList = new ArrayList<>();

        StudentsRef = FirebaseDatabase.getInstance().getReference().child("Batches").child(selectedBatch)
                .child("Students").child(selectedTerm);

        StudentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                studentList.clear();

                if(snapshot.exists()){

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Student student = dataSnapshot.getValue(Student.class);

                        if(student.getCourse_names().contains(selectedCourse)){
                            studentList.add(student);

                        }


                    }

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewAttendance.this));
                    ViewAttendanceAdapter adapter = new ViewAttendanceAdapter(ViewAttendance.this,studentList,ViewAttendance.this::onClick);
                    recyclerView.setAdapter(adapter);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    @Override
    public void onClick(int position) {

        String id = studentList.get(position).getId();
        String name = studentList.get(position).getName();

        AttendanceRef = FirebaseDatabase.getInstance().getReference().child("Attendance")
                .child(selectedBatch).child(selectedCourse).child(selectedTeacher);

        AttendanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                presentNumber = 0;
                absentNumber = 0;



                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                        String Number = dataSnapshot.child("Number").getValue().toString();
                        if(dataSnapshot.hasChild("Present List")){
                          if(dataSnapshot.child("Present List").hasChild(id)){
                              presentNumber = presentNumber + 1*Integer.valueOf(Number);
                          }
                          else{
                              absentNumber = absentNumber + 1 *Integer.valueOf(Number);
                          }

                        }

                        else{

                            absentNumber = absentNumber + 1 *Integer.valueOf(Number);

                        }

                    }
                }

                final AlertDialog alertDialog = new AlertDialog.Builder(ViewAttendance.this).create();
                View view = LayoutInflater.from(ViewAttendance.this).inflate(R.layout.viewattendancepopup,null);

                TextView presentTV,absentTV,nameofStudent,idofStudent;
                Button button;

                presentTV=view.findViewById(R.id.presentStudentTV1);
                absentTV=view.findViewById(R.id.absentStudentTV1);
                nameofStudent =view.findViewById(R.id.vName);
                idofStudent = view.findViewById(R.id.vID);
                button=view.findViewById(R.id.Okbtn);

                nameofStudent.setText(name);
                idofStudent.setText(id);
                presentTV.setText(presentNumber+"");
                absentTV.setText(absentNumber+"");

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(view);
                alertDialog.setCancelable(true);
                alertDialog.show();






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }
}