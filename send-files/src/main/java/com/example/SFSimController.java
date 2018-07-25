package com.example;

import com.example.service.OrderService;
import com.example.model.SFOrder;
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
    private OrderService service;

    @Autowired
    private SFSimController(OrderService service) {
        this.service = service;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SFOrder> resetAll() {
        logger.debug("all reset call initiated");
        return service.updateAllToReady();
    }

    @RequestMapping(value = "/reset/{order_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFOrder resetOne(@PathVariable("order_id") String orderId) {
        logger.debug("reset call initiated on orderId = {}", orderId);
        return service.updateOneToReady(orderId);
    }
}
