package com.example.ice_attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {

    Context context;
    List<Course>courseArrayList;

    public CourseListAdapter(Context context, List<Course> courseArrayList) {
        this.context = context;
        this.courseArrayList = courseArrayList;
    }

    public CourseListAdapter(){

    }

    @NonNull

    @Override
    public CourseListViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.singlecourselist,parent,false);

        return new CourseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CourseListAdapter.CourseListViewHolder holder, int position) {

        Course course = courseArrayList.get(position);
        holder.courseName.setText(course.getCourse_name());
        holder.courseTeacher.setText(course.getTeacher());
        holder.courseCode.setText(course.getCourse_code());

    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    public class CourseListViewHolder extends RecyclerView.ViewHolder {

        TextView courseName,courseTeacher,courseCode;
        public CourseListViewHolder( View itemView) {
            super(itemView);



            courseName = itemView.findViewById(R.id.courseNamesample);
            courseTeacher = itemView.findViewById(R.id.teacherNamesample);
            courseCode= itemView.findViewById(R.id.courseIdsample);
        }
    }
}
