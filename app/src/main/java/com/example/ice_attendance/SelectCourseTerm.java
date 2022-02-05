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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectCourseTerm extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinner;
    private Button button;
    private ArrayList<String> TermList;
    private String selectedTerm;


    private DatabaseReference TermRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_term);


        toolbar = findViewById(R.id.selectTermToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        button = findViewById(R.id.chooseTermId);

        spinner = findViewById(R.id.selectTermSpinner);
        TermList = new ArrayList<>();

        TermRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        TermRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                TermList.clear();
                TermList.add(0,"Select Term");

                if(snapshot.exists())
                {

                    for(DataSnapshot dataSnapshot : snapshot.getChildren())

                    {
                        String key = dataSnapshot.getKey();
                        TermList.add(key);
                    }



                }


                ArrayAdapter<String>arrayAdapter = new ArrayAdapter<>(SelectCourseTerm.this, android.R.layout.simple_list_item_1,TermList);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        selectedTerm = adapterView.getItemAtPosition(i).toString();


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




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCourseTerm.this,CourseListActivity.class);
                intent.putExtra("Course_Term",selectedTerm);
                startActivity(intent);
            }
        });


    }
}