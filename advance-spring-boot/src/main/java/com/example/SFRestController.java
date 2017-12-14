package com.example;

import com.example.data.OrderService;
import com.example.enumeration.OrderState;
import com.example.json.SFFile;
import com.example.json.SFOrder;
import com.example.json.SFResponse;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class SFRestController {
    private static final Logger logger = LoggerFactory.getLogger(SFRestController.class);
    private OrderService service;

    @Autowired
    private SFRestController(OrderService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFResponse queryOrderList(@RequestParam("state") String state,
                                     @RequestParam("num") Optional<Integer> num,
                                     @RequestParam("startId") Optional<Integer> startId)
            throws IllegalArgumentException {

        if (Arrays.stream(OrderState.values()).noneMatch((t) -> t.name().equals(state))) {
            throw new IllegalArgumentException("Order State is not valid");
        }

        logger.debug("state={}, num={}, startId={}", state, num, startId);
        return new SFResponse(service.findListByState(state, num, startId));
    }

    @RequestMapping(value = "/{order_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFOrder querySingleOrder(@PathVariable("order_id") String orderId) {
        logger.debug("Query Single Order");
        return service.findOneOrder(orderId);
    }

    @RequestMapping(value = "/{order_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFOrder updateSingleOrder(@PathVariable("order_id") String orderId, @RequestBody SFOrder order) {
        logger.debug("Update Single Order");
        return service.updateOneOrder(orderId, order);
    }

    @RequestMapping(value = "/{order_id}/file/{file_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFFile querySingleFile(@PathVariable("order_id") String orderId,
                                  @PathVariable("file_id") String fileId) {
        logger.debug("Query Single File");
        return service.findOneFile(orderId, fileId);
    }


    @RequestMapping(value = "/{order_id}/file/{file_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFFile updateSingleFile(@PathVariable("order_id") String orderId,
                                   @PathVariable("file_id") String fileId,
                                   @RequestBody SFFile file) {
        logger.debug("Update Single File");
        return service.updateOneFile(orderId, fileId, file);
    }
}