package com.example.ice_attendance;

public class Teacher {

    private String id,name,email,address,phone,designation;

    public Teacher(){

    }


    public Teacher(String id, String name, String email, String address, String phone, String designation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.designation = designation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getDesignation() {
        return designation;
    }
}
