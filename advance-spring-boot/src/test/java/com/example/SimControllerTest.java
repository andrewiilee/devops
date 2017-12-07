package com.example;

import com.example.data.OrderData;
import java.util.Arrays;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimControllerTest {
    @Configuration
    @ComponentScan(basePackages = {"com.example"})
    static class ContextConfiguration {
    }

    @Autowired
    private OrderData data;

    @Autowired
    private MockMvc mvc;

    @Before
    public void beforeTest() {
        data.getCsv("order.csv");
    }

    @Test
    public void resetOneTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/?state=DONE").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("order2")));

        mvc.perform(MockMvcRequestBuilders.get("/reset/order2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("order2", "READY", "fileScan21.txt"))));
    }

    @Test
    public void resetAllTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/?state=DONE").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("order4", "order1", "order2"))));

        mvc.perform(MockMvcRequestBuilders.get("/reset").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("order4", "READY", "fileScan21.txt"))));
    }
}
