package com.example.domaci3.model;

public class Contact {

    private int id;
    private String fullName;
    private String picture;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Contact(int id, String fullName, String phoneNumber, String email, String picture) {
        this.id = id;
        this.firstName = fullName.split(" ")[0];
        this.lastName = fullName.split(" ")[1];
        this.fullName = fullName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
