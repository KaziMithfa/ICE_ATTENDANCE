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

public class SelectBatch extends AppCompatActivity {

    private Toolbar selectBatchToolbar;
    private Spinner selectBatchspinner;
    private DatabaseReference BatchesRef;
    private String SelectBatch;
    private List<String> BatchList = new ArrayList<>();
    //private ArrayAdapter<String>batchAdapter;
    private Button selectBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_batch);

        selectBtn = findViewById(R.id.selectBatchId);

        selectBatchToolbar = findViewById(R.id.selectbatchToolId);
        setSupportActionBar(selectBatchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BatchesRef = FirebaseDatabase.getInstance().getReference().child("Batches");

        selectBatchspinner = findViewById(R.id.selectBatchSpinner);

        BatchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                BatchList.clear();
                BatchList.add("Select Batch");
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                       if(dataSnapshot.hasChildren())
                       {
                           String key = dataSnapshot.getKey();
                           BatchList.add(key);


                       }
                    }



                }

               ArrayAdapter batchAdapter = new ArrayAdapter<>(SelectBatch.this, android.R.layout.simple_list_item_1,BatchList);
                selectBatchspinner.setAdapter(batchAdapter);

                selectBatchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SelectBatch = adapterView.getItemAtPosition(i).toString();
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




        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectBatch.this,StudentList.class);
                intent.putExtra("Key",SelectBatch);
                startActivity(intent);
            }
        });











    }
}