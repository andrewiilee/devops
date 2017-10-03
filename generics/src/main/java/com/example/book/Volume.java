package com.example.book;

import com.example.Generic;
import org.springframework.stereotype.Component;

/**
 * Created by alee2 on 8/15/17.
 *
 * @author alee2
 */
@Component("Volume")
public class Volume extends Generic<Book>{
    private String getBeholder() {
        return "beholder";
    }

    private String getVolume() {
        return "volume";
    }

    @Override
    public Book request() {
        return new Book(getVolume(),getBeholder());
    }
}
