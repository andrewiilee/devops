package com.example;

import com.example.service.OrderService;
import com.example.model.FileState;
import com.example.model.OrderState;
import com.example.model.SFFile;
import com.example.model.SFOrder;
import gherkin.deps.com.google.gson.Gson;
import java.util.Arrays;
import static org.hamcrest.Matchers.stringContainsInOrder;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {
    @Configuration
    @ComponentScan(basePackages = {"com.example"})
    static class ContextConfiguration {}

    @Autowired
    private OrderService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void badSingleQuery404Test() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void queryOrderListTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/?state=DONE").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("order4","order1","order2"))));
    }

    @Test
    public void querySingleOrderTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/order4").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("order4","DONE","fileScan21.txt"))));
    }

    @Test
    public void updateSingleOrderTest() throws Exception {
        SFOrder order = new SFOrder();
        order.setId("order3");
        order.setState(OrderState.TRANSFERRING);

        mvc.perform(MockMvcRequestBuilders.post("/order3").accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(order)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("order3","TRANSFERRING","fileCount","fileScan1.txt"))));
    }

    @Test
    public void querySingleFileTest() throws Exception {
        SFFile file = service.findOneOrder("order4").getSFFileList().get(0);

        assert file != null;
        mvc.perform(MockMvcRequestBuilders.get("/order4/file/" + file.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("priority","filePath","state"))));
    }

    @Test
    public void updateSingleFileTest() throws Exception {
        SFFile file = service.findOneOrder("order1").getSFFileList().get(0);

        assert file != null;
        SFFile newFile = new SFFile();
        newFile.setState(FileState.RETRY);
        newFile.setId(file.getId());
        mvc.perform(MockMvcRequestBuilders.post("/order1/file/" + file.getId()).accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(newFile)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(Arrays.asList("priority","filePath","RETRY"))));
    }
}