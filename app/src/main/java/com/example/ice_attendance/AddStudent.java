package com.example.ice_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddStudent extends AppCompatActivity {

    private Toolbar toolbar;
    private String batch,term;
    private EditText nameEditText,phoneEditText,emailEditText,idEditText;
    private Spinner StudentYearSp,StudentcoursesSp;
    private Button AddStudentBtn;
    private DatabaseReference ref;
    private ArrayList<String> courseNamelist=new ArrayList<>();
    private ArrayList<String> courseCodeList=new ArrayList<>();
    private String[] year;
    private String selectedYear;
    private DatabaseReference studentRef;

    private final List<CheckableSpinnerAdapter.SpinnerItem<SpinnerObject>> course_spinner_items = new ArrayList<>();
    private final List<CheckableSpinnerAdapter.SpinnerCode<SpinnerObject>> course_spinner_code = new ArrayList<>();
    private final Set<SpinnerObject> course_selected_items = new HashSet<>();
    private final Set<SpinnerObject> selected_course=new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        toolbar = findViewById(R.id.addStudentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        batch = getIntent().getStringExtra("Batch");
        term = getIntent().getStringExtra("Term");

        nameEditText  = findViewById(R.id.addStudentNameId);
        phoneEditText = findViewById(R.id.addStudentPhoneId);
        emailEditText = findViewById(R.id.addStudentEmailId);
        idEditText = findViewById(R.id.addstudentId);

        StudentYearSp = findViewById(R.id.addStudentYear);
        StudentcoursesSp = findViewById(R.id.addStudentCourseSp);
        AddStudentBtn = findViewById(R.id.addstudentBtnId);
        year = getResources().getStringArray(R.array.year);

        studentRef = FirebaseDatabase.getInstance().getReference().child("Batches").child(batch).child("Students");

        ref = FirebaseDatabase.getInstance().getReference().child("Courses").child(term);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                courseNamelist.clear();
                courseCodeList.clear();

                if(snapshot.exists()){

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.hasChildren()){

                            Course course = dataSnapshot.getValue(Course.class);
                            String name = course.getCourse_name();
                            String code = course.getCourse_code();



                            courseNamelist.add(name);
                            courseCodeList.add(code);



                        }
                    }

                    List<SpinnerObject> all_objects = new ArrayList<>();

                    for(int i = 0;i<courseNamelist.size();i++){

                        SpinnerObject myObject = new SpinnerObject();
                        myObject.setmName(courseNamelist.get(i));
                        myObject.setC_code(courseCodeList.get(i));
                        all_objects.add(myObject);
                    }

                    for(SpinnerObject o : all_objects){
                        course_spinner_items.add(new CheckableSpinnerAdapter.SpinnerItem<>(o,o.getmName()));
                        course_spinner_code.add(new CheckableSpinnerAdapter.SpinnerCode<>(o,o.getC_code()));
                    }

                    String header = "Select Courses";
                    CheckableSpinnerAdapter cadapter = new CheckableSpinnerAdapter<>(AddStudent.this,header,course_spinner_items,course_spinner_code,course_selected_items,selected_course);
                    StudentcoursesSp.setAdapter(cadapter);


                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


        ArrayAdapter<String>yearAdapter = new ArrayAdapter<>(AddStudent.this, android.R.layout.simple_list_item_1,year);
        StudentYearSp.setAdapter(yearAdapter);

        StudentYearSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYear = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AddStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });






    }

    private void addStudent() {

        StringBuilder stringBuildert=new StringBuilder();
        for(SpinnerObject so : course_selected_items){
            stringBuildert.append(so.getmName().concat(","));
        }

        StringBuilder stringBuilderc=new StringBuilder();
        for(SpinnerObject so : selected_course){
            stringBuilderc.append(so.getC_code().concat(","));
        }

        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String id = idEditText.getText().toString();


        if(name.isEmpty()){
            nameEditText.setError("Enter Student Name");
            nameEditText.requestFocus();
        }

        else if(phone.isEmpty()){
            phoneEditText.setError("Enter phone number");
            phoneEditText.requestFocus();
        }

        else if (email.isEmpty()){

            emailEditText.setError("Enter the student email");
            emailEditText.requestFocus();

        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter valid email");
            emailEditText.requestFocus();
        }

        else if(id.isEmpty()){
            idEditText.setError("Enter Id");
            idEditText.requestFocus();
        }

        else if(stringBuildert.toString().isEmpty()){
            Toast.makeText(this, "Select Courses...", Toast.LENGTH_SHORT).show();
        }

        else if(stringBuilderc.toString().isEmpty()){

            Toast.makeText(this, "Select course codes...", Toast.LENGTH_SHORT).show();
        }

        else if(selectedYear.equals("select year")){
            Toast.makeText(this, "Select Year is please....", Toast.LENGTH_SHORT).show();
        }

        else{

            String courseNames = stringBuildert.toString();
            String courseCodes = stringBuilderc.toString();

            Student student = new Student(name,id,term,phone,email,courseNames,courseCodes,selectedYear,"1234");

            studentRef.child(term).child(id).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete( Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(AddStudent.this, "Student added successfully.....", Toast.LENGTH_SHORT).show();

                    }

                }
            });



        }


    }


}