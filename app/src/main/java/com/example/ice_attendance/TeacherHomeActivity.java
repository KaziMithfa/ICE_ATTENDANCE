package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

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

public class TeacherHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar teacherToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView teacherNavigationView;
    private String key;
    private TextView name,email;
    private DatabaseReference teachersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        teacherToolbar = findViewById(R.id.teacher_toolbar_id);
        teacherNavigationView = findViewById(R.id.teacher_nav_view_id);
        setSupportActionBar(teacherToolbar);

        key = getIntent().getStringExtra("Key");

        if(savedInstanceState == null){

            //getSupportFragmentManager().beginTransaction().replace(R.id.teacherfragmetncontainer,
                    //new TeacherFragment()).commit();

            TeacherFragment teacherFragment = new TeacherFragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            Bundle data = new Bundle();

            data.putString("token",key);
            teacherFragment.setArguments(data);
            fragmentTransaction.replace(R.id.teacherfragmetncontainer,teacherFragment).commit();

            teacherNavigationView.setCheckedItem(R.id.teacherHome);

        }


        teachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(key);

        drawerLayout = findViewById(R.id.teacherdrawer);
        teacherToolbar = findViewById(R.id.teacher_toolbar_id);
        teacherNavigationView = findViewById(R.id.teacher_nav_view_id);
        teacherNavigationView.setNavigationItemSelectedListener(this);




        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(TeacherHomeActivity.this,
                drawerLayout,teacherToolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View headerView = teacherNavigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.teacherNameDis);
        email = headerView.findViewById(R.id.teacherEmailDis);

        teachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Teacher teacher = snapshot.getValue(Teacher.class);
                    name.setText(teacher.getName());
                    email.setText(teacher.getEmail());
                }

            }

            @Override
            public void onCancelled( DatabaseError error) {

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
    public boolean onNavigationItemSelected( MenuItem item) {


        switch (item.getItemId()){


            case R.id.teacherHome:

                /*getSupportFragmentManager().beginTransaction().replace(R.id.teacherfragmetncontainer,
                        new TeacherFragment()).commit();*/

                TeacherFragment teacherFragment = new TeacherFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                Bundle data = new Bundle();
                data.putString("Key",key);
                teacherFragment.setArguments(data);
                fragmentTransaction.replace(R.id.teacherfragmetncontainer,new TeacherFragment()).commit();


                break;

            case R.id.teacherLogout:

                Intent intent = new Intent(TeacherHomeActivity.this,TeacherLogin.class);
                startActivity(intent);
                Toast.makeText(this, "You are log out successfully...", Toast.LENGTH_SHORT).show();
                finish();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return false;






    }
}