package com.example.ice_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DatePickerActivity extends AppCompatActivity {

    private EditText dateEditText;
    private Button button;
    private ImageView imageView;
    private DatePickerDialog datePickerDialog;
    private String type,course,batch,term,date,teacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);


        dateEditText = findViewById(R.id.dateET);
        button = findViewById(R.id.dateNxtBTn);
        imageView = findViewById(R.id.dateIBtn);

        type = getIntent().getStringExtra("type");
        course = getIntent().getStringExtra("SC");
        batch = getIntent().getStringExtra("SB");
        term = getIntent().getStringExtra("ST");
        teacherName = getIntent().getStringExtra("teacherName");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = new DatePicker(DatePickerActivity.this);
                int currentDay = datePicker.getDayOfMonth();
                int currentMonth = datePicker.getMonth();
                int currentYear = datePicker.getYear();

                datePickerDialog = new DatePickerDialog(DatePickerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker1, int i, int i1, int i2) {

                                StringBuilder stringBuilder = new StringBuilder();


                                stringBuilder.append(i2+"-");
                                stringBuilder.append((i1+1)+"-");
                                stringBuilder.append(i);
                                dateEditText.setText(stringBuilder.toString());


                            }
                        },currentYear,currentMonth,currentDay
                );



                datePickerDialog.show();



            }
        });

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                date = dateEditText.getText().toString();

                if(!date.isEmpty()){

                    if(type.equals("TakeAttendance")){

                        Intent intent = new Intent(DatePickerActivity.this,TakekAttendance.class);
                        intent.putExtra("SC",course);
                        intent.putExtra("SB",batch);
                        intent.putExtra("ST",term);
                        intent.putExtra("date",date);
                        intent.putExtra("teacherName",teacherName);

                        startActivity(intent);

                    }

                    else if(type.equals("ViewAttendance")){

                    }

                }

                else{
                    Toast.makeText(DatePickerActivity.this, "Please , insert today's date.....", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}