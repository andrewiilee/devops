package com.example;

import com.example.book.Book;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alee2 on 8/15/17.
 *
 * @author alee2
 */
@Configuration
@ComponentScan
public class Config {
    @Resource(name = "Volume")
    Generic<Book, Book> volume;

    @Autowired
    @Qualifier("Scroll")
    public Generic<Book, Book> scroll;
}
