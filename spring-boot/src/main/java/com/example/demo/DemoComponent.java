package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * Spring boot automatically component scans
 */
@Component
public class DemoComponent {
    private String name;

    public DemoComponent() {
        name = "DemoComponent";
    }

    public String getName() {
        return name;
    }
}
