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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectbatchandTerm extends AppCompatActivity {

    private Toolbar toolbar;

    private Spinner BatchSp,TermSp;
    private Button nxtBtn;
    private DatabaseReference BatchRef,TermRef;
    private List<String> BatchList;
    private List<String> TermList;
    private String selectedTerm,selectedBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectbatchand_term);

        BatchSp = findViewById(R.id.selectBatchstdnt);
        TermSp = findViewById(R.id.selectTermstdnt);
        nxtBtn = findViewById(R.id.nextStudentListBtn);
        toolbar = findViewById(R.id.selctBatchTermId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BatchList = new ArrayList<>();
        TermList = new ArrayList<>();



        BatchRef = FirebaseDatabase.getInstance().getReference().child("Batches");
        TermRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        BatchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {


                BatchList.clear();
                BatchList.add(0,"Select Batch");

                if(snapshot.exists()){

                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        String key = snapshot1.getKey();
                        BatchList.add(key);
                    }



                }

                ArrayAdapter<String>batchAdapter = new ArrayAdapter<String>(SelectbatchandTerm.this, android.R.layout.simple_list_item_1,BatchList);
                BatchSp.setAdapter(batchAdapter);
                BatchSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        selectedBatch = adapterView.getItemAtPosition(i).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }





            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });




       TermRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {


                TermList.clear();
                TermList.add(0,"Select Term");

                if(snapshot.exists()){

                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        String key = snapshot1.getKey();
                        TermList.add(key);
                    }



                }


                ArrayAdapter<String>termAdapter = new ArrayAdapter<String>(SelectbatchandTerm.this, android.R.layout.simple_list_item_1,TermList);
                TermSp.setAdapter(termAdapter);
                TermSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectbatchandTerm.this,StudentList.class);
                intent.putExtra("Batch",selectedBatch);
                intent.putExtra("Term",selectedTerm);
                startActivity(intent);
            }
        });







    }
}