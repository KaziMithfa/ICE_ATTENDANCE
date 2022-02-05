package com.example.ice_attendance;

public class Course {

    private String course_code,course_name,teacher,selected_batch;

    public Course(){

    }

    public Course(String course_code, String course_name, String teacher, String selected_batch) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.teacher = teacher;
        this.selected_batch = selected_batch;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSelected_batch() {
        return selected_batch;
    }

    public void setSelected_batch(String selected_batch) {
        this.selected_batch = selected_batch;
    }
}
