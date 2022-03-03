package com.example.ice_attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceAdapter extends RecyclerView.Adapter<TakeAttendanceAdapter.TakeAttendanceViewHolder>  {

    Context context;
    List<Student>StudentList = new ArrayList<>();
    public  static List<String> presentList=new ArrayList<>();
    public  static List<String> absentList=new ArrayList<>();

    public TakeAttendanceAdapter(Context context, List<Student> studentList) {
        this.context = context;
        StudentList = studentList;
    }

    @NonNull
    @Override
    public TakeAttendanceAdapter.TakeAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.take_attendance,parent,false);
        return  new TakeAttendanceViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull TakeAttendanceAdapter.TakeAttendanceViewHolder holder, int position) {

       Student student = StudentList.get(position);
       holder.studentName.setText(student.getName());
       holder.studentId.setText(student.getId());

       holder.presentBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                   presentList.add(student.getId());

               }
           }
       });

       holder.absentBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                   absentList.add(student.getId());
               }
           }
       });



    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }


    public class TakeAttendanceViewHolder extends RecyclerView.ViewHolder{


        TextView studentName,studentId;
        RadioButton presentBtn,absentBtn;

        public TakeAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.takeattendanceStudentName);
            studentId = itemView.findViewById(R.id.takeattendanceStudentID);
            presentBtn = itemView.findViewById(R.id.presentRadioBtn);
            absentBtn = itemView.findViewById(R.id.absentRadioBtn);




        }
    }
}
