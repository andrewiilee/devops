package com.example;

import com.example.data.OrderData;
import com.example.enumeration.OrderState;
import com.example.json.SFFile;
import com.example.json.SFOrder;
import com.example.json.SFResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private OrderData data;

    @Autowired
    private SFRestController(OrderData data) {
        this.data = data;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFResponse queryOrderList(@RequestParam("state") String state,
                                     @RequestParam("num") Optional<Integer> num,
                                     @RequestParam("startId") Optional<Integer> startId)
            throws IllegalArgumentException {

        if (Arrays.stream(OrderState.values()).noneMatch((t) -> t.name().equals(state))) {
            throw new IllegalArgumentException("Order State is not valid");
        }

        Integer min = startId
                .filter(p -> p >= 0 && p < data.getOrders().size())
                .orElse(0);
        Integer max = num
                .filter(p -> p > 0 && p <= data.getOrders().size())
                .orElse(data.getOrders().size());

        logger.debug("optional min={}, optional max={}", min, max);
        logger.debug("state={}, num={}, startId={}", state, num, startId);

        return new SFResponse(data.getOrders()
                .stream()
                .filter(p -> p.getState().equals(OrderState.valueOf(state)))
                .skip(min)
                .limit(max)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{order_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFOrder querySingleOrder(@PathVariable("order_id") String orderId) {
        logger.debug("Query Single Order");
        return data.getOrders()
                .stream()
                .filter(p -> p.getId().equals(orderId))
                .findAny()
                .orElse(null);
    }

    @RequestMapping(value = "/{order_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFOrder updateSingleOrder(@PathVariable("order_id") String orderId, @RequestBody SFOrder SFOrder) {
        logger.debug("Update Single Order");
        return data.getOrders()
                .stream()
                .filter(p -> p.getId().equals(orderId))
                .peek(p -> logger.info("order_id={}, updated_state={}, previous_state={}", orderId, SFOrder.getState(), p.getState()))
                .peek(p -> p.setState(SFOrder.getState()))
                .findAny()
                .orElse(null);
    }

    @RequestMapping(value = "/{order_id}/file/{file_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFFile querySingleFile(@PathVariable("order_id") String orderId,
                                  @PathVariable("file_id") String fileId) {
        logger.debug("Query Single File");
        return data.getOrders()
                .stream()
                .filter(p -> p.getId().equals(orderId))
                .flatMap(p -> p.getSFFileList().stream())
                .filter(f -> f.getId().equals(fileId))
                .findAny()
                .orElse(null);
    }


    @RequestMapping(value = "/{order_id}/file/{file_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SFFile updateSingleFile(@PathVariable("order_id") String orderId,
                                   @PathVariable("file_id") String fileId,
                                   @RequestBody SFFile file) {
        logger.debug("Update Single File");
        return data.getOrders()
                .stream()
                .filter(p -> p.getId().equals(orderId))
                .flatMap(p -> p.getSFFileList().stream())
                .filter(f -> f.getId().equals(fileId))
                .peek(f -> logger.info("order_id={}, file_id={}, updated_state={}, previous_state={}", orderId, fileId, file.getState(), f.getState()))
                .peek(f -> f.setState(file.getState()))
                .findAny()
                .orElse(null);
    }
}