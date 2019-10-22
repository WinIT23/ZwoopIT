package com.example.zwoopit;

import java.util.ArrayList;
import java.util.List;

public class User {
    String userID;
    String mobNum;
    String email;
    String fname;
    String lname;

    public User() {
    }

    public User(String email, String fname, String lname) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }

    public String getUserID() {
        return userID;
    }

    public String getMobNum() {
        return mobNum;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}

