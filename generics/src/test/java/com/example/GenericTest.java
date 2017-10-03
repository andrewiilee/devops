package com.example;

import com.example.book.Book;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by alee2 on 10/3/17.
 *
 * @author alee2
 */
public class GenericTest {

    private ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
    private Config config = ctx.getBean(Config.class);

    @Test
    public void genericScroll() {
        Book book = config.scroll.request();
        assertThat("Scroll getAuthor should be writer", book.getAuthor(), equalTo("writer") );
        assertThat("Scroll getTitle should be edict", book.getTitle(), equalTo("edict") );
    }


    @Test
    public void genericVolume() {
        Book book = config.volume.request();
        assertThat("Volume getAuthor should be writer", book.getAuthor(), equalTo("beholder") );
        assertThat("Volume getTitle should be edict", book.getTitle(), equalTo("volume") );
    }
}
