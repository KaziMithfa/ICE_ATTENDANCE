package com.example.ice_attendance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudentFragment extends Fragment {

    private String key,selectedBatch,selectedTerm;
    private CardView InfoCardView,viewAttendanceCardView;
    private TextView email,phone;
    private DatabaseReference studentsRef;





    public StudentFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_student, container, false);
        InfoCardView = view.findViewById(R.id.StudentInfoCV);
        viewAttendanceCardView = view.findViewById(R.id.StudentViewAttendanceId);
        email = view.findViewById(R.id.studentEmailInfo);
        phone = view.findViewById(R.id.studentPhoneInfo);

        Bundle data = getArguments();

        if(data!= null){
            key = data.getString("key");
            selectedBatch= data.getString("batch");
            selectedTerm = data.getString("term");
        }

        studentsRef = FirebaseDatabase.getInstance().getReference().child("Batches")
                .child(selectedBatch).child("Students").child(selectedTerm).child(key);

        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Student student = snapshot.getValue(Student.class);
                    email.setText(student.getEmail());
                    phone.setText(student.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }
}