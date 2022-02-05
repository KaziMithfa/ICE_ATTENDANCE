package com.example.ice_attendance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TeacherFragment extends Fragment {

    private String key;
    private TextView name,id,designation;
    private DatabaseReference teachersRef;


    public TeacherFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_teacher, container, false);


        name = view.findViewById(R.id.teacherInfoName);
        id = view.findViewById(R.id.teacherInfoId);
        designation = view.findViewById(R.id.teacherInfoDesig);

        Bundle data = getArguments();

        if(data != null){
            key = data.getString("token");

        }

        teachersRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(key);


        teachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Teacher teacher = snapshot.getValue(Teacher.class);
                    name.setText(teacher.getName());
                    id.setText(teacher.getId());
                   designation.setText(teacher.getDesignation());
                }

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

        return view;
    }
}