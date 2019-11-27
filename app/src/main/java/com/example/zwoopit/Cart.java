package com.example.zwoopit;

import java.util.ArrayList;

public class Cart {
    String userId;
    ArrayList<Book> cart = new ArrayList<>();

    public Cart()
    { }

    public Cart(String userId, ArrayList<Book> cart) {
        this.userId = userId;
        this.cart = cart;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Book> getCart() {
        return cart;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCart(ArrayList<Book> cart) {
        this.cart = cart;
    }

    public void addToCart(Book book)
    {
        cart.add(book);
    }
}
