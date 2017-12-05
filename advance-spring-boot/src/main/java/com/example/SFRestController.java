package com.example;

import com.example.enumeration.OrderState;
import com.example.json.SFOrder;
import com.example.json.SFResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

        logger.trace("state={}, num={},startId={}", state, num, startId);
        if (Arrays.stream(OrderState.values()).noneMatch((t) -> t.name().equals(state))) {
            throw new IllegalArgumentException("Order State is not valid");
        }

        Integer min = startId
                .filter(p -> p >= 0 && p < data.getOrders().size())
                .orElse(0);
        Integer max = num
                .filter(p -> p > 0 && p <= data.getOrders().size())
                .orElse(data.getOrders().size());

        logger.debug("Optional min={} and Optional max={}", min, max);
        List<SFOrder> SFOrders = data.getOrders()
                .stream()
                .filter(p -> p.getState().equals(OrderState.valueOf(state)))
                .skip(min)
                .limit(max)
                .collect(Collectors.toList());

        return new SFResponse(SFOrders);
    }
}