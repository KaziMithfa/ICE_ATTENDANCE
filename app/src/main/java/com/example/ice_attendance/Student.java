package com.example.ice_attendance;

public class Student {
    private String name,id,term,phone,email,course_names,course_codes,year,password;

   public Student(){


    }

    public Student(String name, String id, String term, String phone, String email, String course_names, String course_codes, String year, String password) {
        this.name = name;
        this.id = id;
        this.term = term;
        this.phone = phone;
        this.email = email;
        this.course_names = course_names;
        this.course_codes = course_codes;
        this.year = year;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse_names() {
        return course_names;
    }

    public void setCourse_names(String course_names) {
        this.course_names = course_names;
    }

    public String getCourse_codes() {
        return course_codes;
    }

    public void setCourse_codes(String course_codes) {
        this.course_codes = course_codes;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
