package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView studentNavigationView;
    private DatabaseReference studentsRef;
    private TextView name,studentId;

    private String id,selectedBatch,selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        id = getIntent().getStringExtra("key");
        selectedBatch = getIntent().getStringExtra("batch");
        selectedTerm = getIntent().getStringExtra("term");
        toolbar = findViewById(R.id.student_toolbar_id);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.studentDrawer);
        studentNavigationView = findViewById(R.id.student_nav_view_id);
        studentNavigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(StudentHome.this
        ,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View headerView = studentNavigationView.getHeaderView(0);
        CircleImageView image = headerView.findViewById(R.id.studentprofileimage);
         name = headerView.findViewById(R.id.studentprofileNameset);
         studentId = headerView.findViewById(R.id.studentprofileIdset);





        studentsRef = FirebaseDatabase.getInstance().getReference().child("Batches");

        studentsRef.child(selectedBatch).child("Students").child(selectedTerm)
                .child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String setName = snapshot.child("name").getValue().toString();
                    String setId = snapshot.child("id").getValue().toString();






                    if(snapshot.hasChild("image")){
                        String setImage = snapshot.child("image").getValue().toString();

                        Picasso.get().load(setImage).into(image);
                        name.setText(setName);
                        studentId.setText(setId);

                    }
                    else{

                        name.setText(setName);
                        studentId.setText(setId);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.student_settings:
                Intent intent = new Intent(StudentHome.this,StudentsSettings.class);
                intent.putExtra("key",id);
                intent.putExtra("batch",selectedBatch);
                intent.putExtra("term",selectedBatch);
                startActivity(intent);
                finish();
                break;

            case R.id.student_password:
                Intent intent1 = new Intent(StudentHome.this,StudentPassword.class);
                intent1.putExtra("key",id);
                intent1.putExtra("batch",selectedBatch);
                intent1.putExtra("term",selectedBatch);
                startActivity(intent1);
                finish();
                break;

            case R.id.student_logout:
                Intent intent2 = new Intent(StudentHome.this,TypeActivity.class);
                startActivity(intent2);
                finish();
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}