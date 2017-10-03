package com.example.book;

import com.example.Generic;
import org.springframework.stereotype.Component;

/**
 * Created by alee2 on 8/15/17.
 *
 * @author alee2
 */
@Component("Scroll")
public class Scroll extends Generic<Book>{
    private String getWriter() {
        return "writer";
    }

    private String getEdict() {
        return "edict";
    }

    @Override
    public Book request() {
        return new Book(getEdict(),getWriter());
    }
}
