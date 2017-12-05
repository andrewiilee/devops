package com.example.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Value("${my.example.property}")
    private String prop;
    private DemoComponent com;

    @Autowired
    DemoController(DemoComponent com) {
        this.com = com;
    }

    @RequestMapping("/")
    public String Hello() {
        return "Working Rest Service via Spring-boot \n";
    }

    @RequestMapping("/prop")
    public String Prop() {
        return prop;
    }

    @RequestMapping("/test")
    public void testFile() throws FileNotFoundException {
        throw new FileNotFoundException("example of file not found exception with advice");
    }

    @RequestMapping("/test2")
    public void test2() throws IOException {
        throw new IOException("example of IO thrown in the controller without advice");
    }

    @RequestMapping("/component")
    public String Component() {
        return com.getName();
    }
}
