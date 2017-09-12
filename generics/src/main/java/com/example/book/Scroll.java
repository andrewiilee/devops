package com.example.book;

import com.example.Generic;
import org.springframework.stereotype.Component;

/**
 * Created by alee2 on 8/15/17.
 *
 * @author alee2
 */
@Component("Scroll")
public class Scroll extends Generic<Book, Book>{
    private final String edict = "edict";
    private final String writer = "writer";

    public String getWriter() {
        return writer;
    }

    public String getEdict() {
        return edict;
    }

    @Override
    public Book request(Book script) {
        return new Book(getEdict(),getWriter());
    }
}
