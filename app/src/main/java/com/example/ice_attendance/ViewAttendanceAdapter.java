package com.example.ice_attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewAttenanceViewHolder> {

    Context context;
    private List<Student> studentList;
    ViewAttendanceOnclick onclick;




    @NonNull
    @Override
    public ViewAttenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentsattendanceview,parent,false);

        return new ViewAttenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAttenanceViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.Name.setText(student.getName());
        holder.Id.setText(student.getId());

    }

    public ViewAttendanceAdapter(Context context,List<Student>studentList,ViewAttendanceOnclick onclick){
        this.context = context;
        this.studentList = studentList;
        this.onclick = onclick;
    }



    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewAttenanceViewHolder extends RecyclerView.ViewHolder{

        private TextView  Id,Name;

        public ViewAttenanceViewHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.attendanceId);
            Name = itemView.findViewById(R.id.attendanceName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick.onClick(getAdapterPosition());
                }
            });
        }
    }
}
