package com.example.ice_attendance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherViewHolder extends RecyclerView.ViewHolder {

    TextView emailTextView,nameTextView,designationTextView;

    public TeacherViewHolder( View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextViewIdTeacher);
        emailTextView = itemView.findViewById(R.id.emailTextViewIdTeacher);
        designationTextView = itemView.findViewById(R.id.designationTextViewIdTeacher);
    }
}
