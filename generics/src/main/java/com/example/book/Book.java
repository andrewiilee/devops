package com.example.book;

/**
 * Created by alee2 on 8/2/17.
 *
 * @author alee2
 */
public class Book {
    private final String title;
    private final String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
