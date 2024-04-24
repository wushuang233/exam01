package com.example.exam.model;

/**
 * @author wushuang
 */
public class Book {
    String number;
    String name;
    String author;
    String description;

    public Book() {
    }

    public Book(String number, String name, String author, String description) {
        this.number = number;
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
