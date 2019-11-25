package com.example.zwoopit;

public class Book {
    String bookID;
    String ownerID;

    String bookName;
    String authorName;
    String category;
    String publication;

    String edition;
    String price;
    String discount="0";

    String imageuri;
    String search;
    String filter;

    public Book() {
    }

    public Book(String bookName, String authorName, String category, String edition, String price) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
        this.edition = edition;
        this.price = price;
    }

    public Book(String bookName, String authorName, String category, String edition, String price, String ownerID) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
        this.edition = edition;
        this.price = price;
        this.ownerID = ownerID;
    }

    public Book(String bookName, String authorName, String category, String edition, String price, String ownerID, String publication) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
        this.edition = edition;
        this.price = price;
        this.ownerID = ownerID;
        this.publication = publication;
    }


    public String getPublication() {
        return publication;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCategory() {
        return category;
    }

    public String getEdition() {
        return edition;
    }

    public String getPrice() {
        return price;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getDiscount() {
        return discount;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
