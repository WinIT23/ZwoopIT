package com.example.zwoopit;

import android.util.Log;

import java.util.ArrayList;

public class Wishlist {
    public String userID;
    public ArrayList<Book> wishlist = new ArrayList<>();

    public Wishlist()
    { }

    public Wishlist(String userID, ArrayList<Book> wishlist) {
        this.userID = userID;
        this.wishlist = wishlist;
    }

    public String getUserID() {
        return userID;
    }

    public ArrayList<Book> getWishlist() {
        return wishlist;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setWishlist(ArrayList<Book> wishlist) {
        this.wishlist = wishlist;
    }

    public void addToWishList(Book book)
    {
        wishlist.add(book);
    }
}

