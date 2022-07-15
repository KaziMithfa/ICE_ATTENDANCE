package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCourses extends AppCompatActivity {
    private Toolbar courseTitleToolbar;
    private Spinner CourseTitleSp,CourseBatchSp,CourseTeacherSp;
    private EditText CourseCodeEditText;
    private Button AddCourseBtn;
    private List<String> teacherList,batchList,teacherIdList,courseTitleList,courseCodeList;
    private String Key;
    private String CourseTitle,TeacherName,BatchName;
    private DatabaseReference CourseRef;
    private DatabaseReference TeachersRef;
    private DatabaseReference BatchRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        courseTitleToolbar = findViewById(R.id.addcourseToolbarId);
        setSupportActionBar(courseTitleToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CourseTitleSp = findViewById(R.id.courseTitlesp);
        CourseBatchSp = findViewById(R.id.courseBatchSp);
        CourseTeacherSp = findViewById(R.id.courseTeacherSp);
        CourseCodeEditText = findViewById(R.id.courseCode);
        AddCourseBtn = findViewById(R.id.addcourseBtn);
        Key = getIntent().getStringExtra("Key");
        teacherList = new ArrayList<>();
        batchList = new ArrayList<>();
        courseTitleList = new ArrayList<>();
        courseCodeList = new ArrayList<>();



        CourseRef = FirebaseDatabase.getInstance().getReference().child("Courses").child(Key);
        CourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                courseCodeList.clear();
                courseCodeList.add(0,"Course Code");
                courseTitleList.clear();
                courseTitleList.add(0,"Course Title");



                if(snapshot.exists()){

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                        Course course = dataSnapshot.getValue(Course.class);
                        String courseName = course.getCourse_name();
                        String courseCode = course.getCourse_code();

                        courseTitleList.add(courseName);
                        courseCodeList.add(courseCode);



                    }
                }



                ArrayAdapter<String>CourseTitleAdapter = new ArrayAdapter<>(AddCourses.this, android.R.layout.simple_list_item_1,courseTitleList);
                CourseTitleSp.setAdapter(CourseTitleAdapter);

                CourseTitleSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        CourseTitle = adapterView.getItemAtPosition(i).toString();
                        CourseCodeEditText.setText(courseCodeList.get(i));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


        TeachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers");

        TeachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                teacherList.clear();
                teacherList.add(0,"Select Teacher");


                if(snapshot.exists())

                {

                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Teacher teacher = dataSnapshot.getValue(Teacher.class);
                        String name = teacher.getName();
                        teacherList.add(name);
                    }

                }

                ArrayAdapter<String>teacherArrayAdapter = new ArrayAdapter<>(AddCourses.this, android.R.layout.simple_list_item_1,teacherList);
                CourseTeacherSp.setAdapter(teacherArrayAdapter);
                CourseTeacherSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        TeacherName = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

        BatchRef = FirebaseDatabase.getInstance().getReference().child("Batches");
        BatchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                batchList.clear();
                batchList.add(0,"Select Batch");

                if(snapshot.exists()){

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String key1 = dataSnapshot.getKey().toString();
                        batchList.add(key1);

                    }

                }

                ArrayAdapter<String>Batchadapter = new ArrayAdapter<>(AddCourses.this, android.R.layout.simple_list_item_1,batchList);
                CourseBatchSp.setAdapter(Batchadapter);
                CourseBatchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         BatchName = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

        AddCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCourses();
            }
        });


    }

    public void AddCourses(){
        String courseCode = CourseCodeEditText.getText().toString();

        if(CourseTitle.isEmpty() && CourseTitle.equals("Course Title")){
            Toast.makeText(this, "Please, select the course name", Toast.LENGTH_SHORT).show();
        }


        else if(courseCode.isEmpty()){
            Toast.makeText(this, "Please , select the course code", Toast.LENGTH_SHORT).show();
        }

        else if(TeacherName.isEmpty() && TeacherName.equals("Select Teacher")){
            Toast.makeText(this, "Please, select the teacher name....", Toast.LENGTH_SHORT).show();
        }

        else if(BatchName.isEmpty() && BatchName.equals("Select Batch")){
            Toast.makeText(this, "Select the batch name...", Toast.LENGTH_SHORT).show();
        }

        else{

            Course course = new Course(courseCode,CourseTitle,TeacherName,BatchName);
            CourseRef.child(courseCode).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete( Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(AddCourses.this, "The course is added successfully...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCourses.this,CourseListActivity.class);
                        intent.putExtra("Course_Term",Key);
                        startActivity(intent);
                        finish();
                    }

                }
            });




        }

    }
}