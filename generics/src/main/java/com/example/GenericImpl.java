package com.example;

import com.example.book.Book;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by alee2 on 8/15/17.
 *
 * @author alee2
 */
public class GenericImpl {

    public static void main(String args[]) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        Config config = ctx.getBean(Config.class);
        Book book1 = config.scroll.request(new Book(null,null));
        System.out.println(book1.getAuthor());
        System.out.println(book1.getTitle());
        Book book2 = config.volume.request(new Book(null,null));
        System.out.println(book2.getAuthor());
        System.out.println(book2.getTitle());
    }
}
