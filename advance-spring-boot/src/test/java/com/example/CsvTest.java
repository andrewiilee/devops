package com.example;

import com.example.enumeration.FileState;
import com.example.enumeration.OrderState;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Just spring test
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestPropertySource("classpath:application.properties")
public class CsvTest {
    @Configuration
    @ComponentScan(basePackages = {"com.example"})
    static class ContextConfiguration {}

    @Autowired
    private OrderData data;

    @Test
    public void csvFileTest() {
        assertThat("test csv file does not have at least 2", data.getOrders().size(),
                greaterThanOrEqualTo(2));
    }

    @Test
    public void ordersDataTest() throws Throwable {
        assertThat("orderId does not match", data.getOrders().get(3).getId(),
                equalTo("order2"));
        assertThat("priority does not match", data.getOrders().get(3).getPriority(),
                equalTo(11));
        assertThat("order state does not match", data.getOrders().get(3).getState(),
                equalTo(OrderState.valueOf("DONE")));
    }

    @Test
    public void filesDataTest(){
        assertThat("fileId does not match", data.getOrders().get(3).getSFFileList().get(0).getFilePath(),
                equalTo("src/test/resources/scan_me/order2/fileScan21.txt"));
        assertThat("fileId does not match", data.getOrders().get(3).getSFFileList().get(1).getFilePath(),
                equalTo("src/test/resources/scan_me/order2/fileScan22.txt"));
        assertThat("fileId does not match", data.getOrders().get(3).getSFFileList().get(2).getFilePath(),
                equalTo("src/test/resources/scan_me/order2/level_one/fileScan23.txt"));
        assertThat("fileId does not match", data.getOrders().get(3).getSFFileList().get(3).getFilePath(),
                equalTo("src/test/resources/scan_me/order2/level_one/level_two/fileScan24.txt"));
    }

    @Test
    public void relativeAndDonessTest() {
        assertThat("file must be relative path", data.getOrders().get(3).getSFFileList().get(3).getFilePath(),
                equalTo("src/test/resources/scan_me/order2/level_one/level_two/fileScan24.txt"));
        assertThat("file must be DONE", data.getOrders().get(3).getSFFileList().get(3).getState(),
                equalTo(FileState.valueOf("DONE")));
    }

    @Test
    public void priorityTest() throws Throwable {
        assertThat("first order is not higher priority than last order", data.getOrders().get(3).getPriority(),
                greaterThanOrEqualTo(data.getOrders().get(3).getPriority()));
    }
}
