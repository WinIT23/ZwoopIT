package com.example.zwoopit;

import java.util.ArrayList;
import java.util.List;

public class User {
    String userID;
    String mobNum;
    String EMAIL;
    String FNAME;
    String LNAME;
    ArrayList<String> addedBooks;
    ArrayList<String> wishlistedBooks;
    ArrayList<String> purchasedBooks;

    public User() {
    }

    public User(String EMAIL, String FNAME, String LNAME) {
        this.EMAIL = EMAIL;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        addedBooks = null;
        wishlistedBooks = null;
        purchasedBooks = null;
    }

    public User(String EMAIL, String FNAME, String LNAME, ArrayList<String> addedBooks) {
        this.EMAIL = EMAIL;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        this.addedBooks = addedBooks;
    }

    public User(String EMAIL, String FNAME, String LNAME, ArrayList<String> addedBooks, ArrayList<String> wishlistedBooks) {
        this.EMAIL = EMAIL;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        this.addedBooks = addedBooks;
        this.wishlistedBooks = wishlistedBooks;
    }

    public User(String EMAIL, String FNAME, String LNAME, ArrayList<String> addedBooks, ArrayList<String> wishlistedBooks, ArrayList<String> purchasedBooks) {
        this.EMAIL = EMAIL;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        this.addedBooks = addedBooks;
        this.wishlistedBooks = wishlistedBooks;
        this.purchasedBooks = purchasedBooks;
    }

    public User(String userID, String EMAIL, String FNAME, String LNAME, ArrayList<String> addedBooks, ArrayList<String> wishlistedBooks, ArrayList<String> purchasedBooks) {
        this.userID = userID;
        this.EMAIL = EMAIL;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        this.addedBooks = addedBooks;
        this.wishlistedBooks = wishlistedBooks;
        this.purchasedBooks = purchasedBooks;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
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

    public List<String> getAddedBooks() {
        return addedBooks;
    }

    public List<String> getWishlistedBooks() {
        return wishlistedBooks;
    }

    public List<String> getPurchasedBooks() {
        return purchasedBooks;
    }

    public String getUserID() {
        return userID;
    }

    public String getMobNum() {
        return mobNum;
    }
}

