package com.examples.book;

import java.util.Date;

/**
 * Created by alee2 on 8/2/17.
 *
 * @author alee2
 */
public class Book {
    private final String title;
    private final String author;
    private final Date published;

    public Book(String title, String author, Date published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public Date getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
