package com.example;

import com.example.data.OrderData;
import com.example.enumeration.FileState;
import com.example.enumeration.OrderState;
import com.example.json.SFOrder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SFSimController {
    private static final Logger logger = LoggerFactory.getLogger(SFSimController.class);
    private OrderData data;

    @Autowired
    private SFSimController(OrderData data) {
        this.data = data;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SFOrder> resetAll() {
        logger.debug("all reset call initiated");
        data.getOrders()
                .stream()
                .peek(p -> p.setState(OrderState.READY))
                .flatMap(p -> p.getSFFileList().stream())
                .forEach(j -> j.setState(FileState.READY));

        return data.getOrders();
    }

    @RequestMapping(value = "/reset/{order_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFOrder resetOne(@PathVariable("order_id") String orderId) {
        logger.debug("reset call initiated on orderId = {}", orderId);

        data.getOrders()
                .stream()
                .filter(p -> p.getId().equals(orderId))
                .peek(p -> p.setState(OrderState.READY))
                .flatMap(p -> p.getSFFileList().stream())
                .forEach(j -> j.setState(FileState.READY));

        return data.getOrders()
                .stream()
                .filter(p -> p.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
