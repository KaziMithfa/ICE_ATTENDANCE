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

public class CourseListActivity extends AppCompatActivity {

    private String Key;
    private FloatingActionButton addCoursesBtn;
    private DatabaseReference CourseRef;
    private List<Course>courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar courseListToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        Key = getIntent().getStringExtra("Course_Term");
        addCoursesBtn = findViewById(R.id.addCoursesBtn);
        CourseRef = FirebaseDatabase.getInstance().getReference().child("Courses").child(Key);
        recyclerView = findViewById(R.id.courseListRecylerViewId);
        courseListToolbar = findViewById(R.id.courseListId);

        setSupportActionBar(courseListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        CourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                courseList.clear();

                if(snapshot.exists()){

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.hasChild("selected_batch") && dataSnapshot.hasChild("teacher")){
                            Course course = dataSnapshot.getValue(Course.class);
                            courseList.add(course);
                        }
                    }









                }

                CourseListAdapter courseListAdapter = new CourseListAdapter(CourseListActivity.this, (ArrayList<Course>) courseList);
                recyclerView.setLayoutManager(new LinearLayoutManager(CourseListActivity.this));
                courseListAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(courseListAdapter);

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


        addCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListActivity.this,AddCourses.class);
                intent.putExtra("Key",Key);
                startActivity(intent);
                finish();
            }
        });



    }
}