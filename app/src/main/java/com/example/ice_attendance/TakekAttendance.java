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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TakekAttendance extends AppCompatActivity {

    private DatabaseReference studentsRef,Ref;

    private String batch,selectedCourse,selectedTerm,date,teacherName;
    private List<Student> studentList = new ArrayList<>();
    private RecyclerView recyclerView;
     private TakeAttendanceAdapter takeAttendanceAdapter;
     private DatabaseReference AttendanceRef,PresentRef,AbsentRef;
     private EditText NumberofClasses;
     private String number ;

   private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takek_attendance);

        batch = getIntent().getStringExtra("SB");
        selectedCourse = getIntent().getStringExtra("SC");
        selectedTerm = getIntent().getStringExtra("ST");
        date = getIntent().getStringExtra("date");
        teacherName = getIntent().getStringExtra("teacherName");
        

        recyclerView = findViewById(R.id.tARv);
        submitBtn = findViewById(R.id.submitAttendancebtn);
        NumberofClasses = findViewById(R.id.numberofClassesId);





        studentsRef = FirebaseDatabase.getInstance().getReference().child("Batches").child(batch).child("Students")
        .child(selectedTerm);
        
        AttendanceRef = FirebaseDatabase.getInstance().getReference().child("Attendance").child(batch)
                .child(selectedCourse).child(teacherName).child(date);

        PresentRef = AttendanceRef.child("Present List");
        AbsentRef = AttendanceRef.child("Absent List");


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

                AlertDialog dialog = new AlertDialog.Builder(TakekAttendance.this).create();
                View view1 = LayoutInflater.from(TakekAttendance.this).inflate(R.layout.attendancepopup,null);
                TextView total,present,absent;
                Button cancelBtn,confirmBtn;

                total = view1.findViewById(R.id.totalStudentV);
                present = view1.findViewById(R.id.presentStudentV);
                absent = view1.findViewById(R.id.absentStudentTV);

                cancelBtn = view1.findViewById(R.id.cancelbtn);
                confirmBtn = view1.findViewById(R.id.confirmbtn);

                total.setText(Integer.toString(studentList.size()));
                present.setText(Integer.toString(TakeAttendanceAdapter.presentList.size()));
               absent.setText(Integer.toString(TakeAttendanceAdapter.absentList.size()));



               dialog.setCancelable(true);
               dialog.setView(view1);




                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TakeAttendanceAdapter.presentList.clear();
                        TakeAttendanceAdapter.absentList.clear();
                        dialog.dismiss();
                    }
                });
                
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String presentId = "";

                        for(int i = 0;i<TakeAttendanceAdapter.presentList.size();i++){

                           presentId = TakeAttendanceAdapter.presentList.get(i).toString();
                            PresentRef.child(presentId).setValue(presentId);
                            
                        }
                        String Absent = "";

                        for(int i = 0;i<TakeAttendanceAdapter.absentList.size();i++){


                            Absent = TakeAttendanceAdapter.absentList.get(i).toString();
                            AbsentRef.child(Absent).setValue(Absent);
                        }



                        TakeAttendanceAdapter.presentList.clear();
                        TakeAttendanceAdapter.absentList.clear();
                        number = NumberofClasses.getText().toString();
                        AttendanceRef.child("Number").setValue(number);
                        dialog.cancel();

                        Toast.makeText(TakekAttendance.this, "Attendance data is added successfully.....", Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.show();


            }
        });





    }
}