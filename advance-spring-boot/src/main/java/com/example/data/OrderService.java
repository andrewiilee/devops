package com.example.data;

import com.example.enumeration.FileState;
import com.example.enumeration.OrderState;
import com.example.json.SFFile;
import com.example.json.SFOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private List<SFOrder> data;

    @Autowired
    private OrderService(OrderData data) {
        this.data = Collections.synchronizedList(data.getOrders());
    }

    public List<SFOrder> findListByState(String state, Optional<Integer> num, Optional<Integer> startId) {
        Integer min = startId
                .filter(p -> p >= 0 && p < data.size())
                .orElse(0);
        Integer max = num
                .filter(p -> p > 0 && p <= data.size())
                .orElse(data.size());

        logger.debug("optional min={}, optional max={}", min, max);
        logger.debug("state={}, num={}, startId={}", state, num, startId);

        return data.stream()
                .filter(p -> p.getState().equals(OrderState.valueOf(state)))
                .skip(min)
                .limit(max)
                .collect(Collectors.toList());
    }

    public SFOrder findOneOrder(String orderId) {
        return data.stream()
                .filter(p -> p.getId().equals(orderId))
                .findAny()
                .orElse(null);
    }

    public SFOrder updateOneOrder(String orderId, SFOrder order) {
        return data.stream()
                .filter(p -> p.getId().equals(orderId))
                .peek(p -> logger.info("order_id={}, updated_state={}, previous_state={}", orderId, order.getState(), p.getState()))
                .peek(p -> p.setState(order.getState()))
                .findAny()
                .orElse(null);
    }

    public SFFile findOneFile(String orderId, String fileId) {
        return data.stream()
                .filter(p -> p.getId().equals(orderId))
                .flatMap(p -> p.getSFFileList().stream())
                .filter(f -> f.getId().equals(fileId))
                .findAny()
                .orElse(null);
    }

    public SFFile updateOneFile(String orderId, String fileId, SFFile file) {
        return data.stream()
                .filter(p -> p.getId().equals(orderId))
                .flatMap(p -> p.getSFFileList().stream())
                .filter(f -> f.getId().equals(fileId))
                .peek(f -> logger.info("order_id={}, file_id={}, updated_state={}, previous_state={}", orderId, fileId, file.getState(), f.getState()))
                .peek(f -> f.setState(file.getState()))
                .findAny()
                .orElse(null);
    }

    public List<SFOrder> updateAllToReady() {
        data.stream()
                .peek(p -> p.setState(OrderState.READY))
                .flatMap(p -> p.getSFFileList().stream())
                .forEach(j -> j.setState(FileState.READY));
        return new ArrayList<>(data);
    }

    public SFOrder updateOneToReady(String orderId) {
        data.stream()
                .filter(p -> p.getId().equals(orderId))
                .peek(p -> p.setState(OrderState.READY))
                .flatMap(p -> p.getSFFileList().stream())
                .forEach(j -> j.setState(FileState.READY));
        return findOneOrder(orderId);
    }
}
