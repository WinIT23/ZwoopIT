package com.example.zwoopit;

public class User {
    String EMAIL;
    String FNAME;
    String LNAME;

    public User() {
    }

    public User(String EMAIL, String FNAME, String LNAME) {
        this.EMAIL = EMAIL;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getFNAME() {
        return FNAME;
    }

    public String getLNAME() {
        return LNAME;
    }
}

