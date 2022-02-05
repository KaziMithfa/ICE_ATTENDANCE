package com.example.ice_attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private Context context;
    private List<Student> studentList;

    public StudentListAdapter(Context context,List<Student>studentList){
        this.context = context;
        this.studentList = studentList;
    }




    @Override
    public StudentListAdapter.StudentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.students_item,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder( StudentListAdapter.StudentViewHolder holder, int position) {

      Student student = studentList.get(position);
      holder.Name.setText(student.getName());
      holder.Id.setText(student.getId());
      holder.Courses.setText(student.getCourse_codes());


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder  extends RecyclerView.ViewHolder{
        TextView Name,Id,Courses;

        public StudentViewHolder( View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.sStudentName);
            Id = itemView.findViewById(R.id.sStudentID);
            Courses = itemView.findViewById(R.id.sStudentCourse);
        }
    }
}
