package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectCourseActivity extends AppCompatActivity {

    private Spinner courseSpinner,termSpinner;
    private Button sendButton;
    private String key,teacherName;
    private List<String> courseNameList = new ArrayList<>();
    private List<String> courseTermList = new ArrayList<>();
    private String selectedCourse,selectedTerm;
    private DatabaseReference courseRef;
    private DatabaseReference selcetCourseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        courseSpinner = findViewById(R.id.courseTitleSpinnerT);
        termSpinner = findViewById(R.id.courseTermSpinnerT);
        sendButton = findViewById(R.id.tsNextBtn);

        courseRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        selcetCourseRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        key = getIntent().getStringExtra("Key");
        teacherName = getIntent().getStringExtra("Name");



        fetchTerm();



    }



    private void fetchTerm() {


        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseTermList.clear();
                courseTermList.add(0,"Select Term");

                if(snapshot.exists()){

                    for(DataSnapshot snapshot1 :snapshot.getChildren()){

                        String term = snapshot1.getKey();
                        courseTermList.add(term);

                    }

                }

                ArrayAdapter<String>termAdapter = new ArrayAdapter<>(SelectCourseActivity.this,
                        android.R.layout.simple_list_item_1,courseTermList);
                termSpinner.setAdapter(termAdapter);
                termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedTerm = adapterView.getItemAtPosition(i).toString();
                       courseRef.child(selectedTerm).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                courseNameList.clear();
                                courseNameList.add(0,"Select Course");
                                if(snapshot.exists()){
                                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                        if(snapshot1.hasChildren()){

                                            Course course = snapshot1.getValue(Course.class);
                                            if(course.getTeacher().equals(teacherName)){
                                                String name = course.getCourse_name();
                                                courseNameList.add(name);

                                            }

                                        }
                                    }
                                }

                                ArrayAdapter<String>courseNameAdapter = new ArrayAdapter<>(SelectCourseActivity.this, android.R.layout
                                        .simple_list_item_1,courseNameList);
                                courseSpinner.setAdapter(courseNameAdapter);
                                courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectedCourse = adapterView.getItemAtPosition(i).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
}