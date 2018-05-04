package com.example;

import com.example.model.FileState;
import com.example.model.OrderState;
import com.example.model.SFOrder;
import com.example.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Just spring test
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestPropertySource("classpath:application.properties")
public class CsvTest {
    @Configuration
    @ComponentScan(basePackages = {"com.example"})
    static class ContextConfiguration {
    }

    @Autowired
    private OrderService service;

    private String folderOrder = String.format("src%1$stest%1$sresources%1$sscan_me%1$sorder2%1$s", File.separator);
    private String folderLevelOne = folderOrder + String.format("level_one%s", File.separator);
    private String folderLevelTwo = folderLevelOne + String.format("level_two%s", File.separator);

    @Test
    public void csvFileTest() {
        assertThat("test csv file does not have at least 2",
                service.findListByState("DONE", Optional.of(99), Optional.empty()).size(),
                greaterThanOrEqualTo(2));
    }

    @Test
    public void ordersDataTest() {
        SFOrder order = service.findOneOrder("order2");
        assertThat("orderId does not match", order.getId(),
                equalTo("order2"));
        assertThat("priority does not match", order.getPriority(),
                equalTo(11));
        assertThat("order state does not match", order.getState(),
                equalTo(OrderState.valueOf("DONE")));
    }

    @Test
    public void filesDataTest() {
        SFOrder order = service.findOneOrder("order2");
        assertThat("fileId does not match", order.getSFFileList().get(0).getFilePath(),
                equalTo(folderOrder + "fileScan21.txt"));
        assertThat("fileId does not match", order.getSFFileList().get(1).getFilePath(),
                equalTo(folderOrder + "fileScan22.txt"));
        assertThat("fileId does not match", order.getSFFileList().get(2).getFilePath(),
                equalTo(folderLevelOne + "fileScan23.txt"));
        assertThat("fileId does not match", order.getSFFileList().get(3).getFilePath(),
                equalTo(folderLevelTwo + "fileScan24.txt"));
    }

    @Test
    public void relativePathTest() {
        SFOrder order = service.findOneOrder("order2");
        assertThat("file must be relative path", order.getSFFileList().get(3).getFilePath(),
                equalTo(folderLevelTwo + "fileScan24.txt"));
        assertThat("file must be DONE", order.getSFFileList().get(3).getState(),
                equalTo(FileState.valueOf("DONE")));
    }

    @Test
    public void priorityTest() {
        List<SFOrder> list = service.findListByState("DONE", Optional.of(99), Optional.empty());
        assertThat("first order is not higher priority than the next order",
                list.get(1).getPriority(),
                greaterThanOrEqualTo(list.get(0).getPriority()));
    }
}
