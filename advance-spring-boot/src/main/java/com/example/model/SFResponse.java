package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SFResponse {
    private int count;
    private List<String> ids;

    public SFResponse(List<SFOrder> orders) {
        count = orders.size();
        ids = new ArrayList<>();
        orders.forEach(p -> ids.add(p.getId()));
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

