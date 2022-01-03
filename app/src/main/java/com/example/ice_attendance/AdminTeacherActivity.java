package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminTeacherActivity extends AppCompatActivity {


    private Toolbar TeacherListToolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference ref;
    private FirebaseRecyclerOptions<Teacher>options;
    private FirebaseRecyclerAdapter<Teacher,TeacherViewHolder>adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher);

        TeacherListToolbar = findViewById(R.id.TeacherLisToolbar);
        setSupportActionBar(TeacherListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        floatingActionButton = findViewById(R.id.floatingBtnId);


        ref = FirebaseDatabase.getInstance().getReference().child("Teachers");
        recyclerView = findViewById(R.id.recylerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        options = new FirebaseRecyclerOptions.Builder<Teacher>().setQuery(ref,Teacher.class).build();
        adapter = new FirebaseRecyclerAdapter<Teacher, TeacherViewHolder>(options) {
            @Override
            protected void onBindViewHolder( TeacherViewHolder teacherViewHolder, int i,  Teacher teacher) {



                teacherViewHolder.nameTextView.setText(teacher.getName());
                teacherViewHolder.emailTextView.setText(teacher.getEmail());
                teacherViewHolder.designationTextView.setText(teacher.getDesignation());



            }

            @NonNull

            @Override
            public TeacherViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showingteachers,parent,false);

                return new TeacherViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminTeacherActivity.this,AddTeacher.class);
                startActivity(intent);
                finish();

            }
        });
    }
}