package com.example.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String helloSpringBoot() {
        return "Working Rest Service via Spring-boot \n";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String postSpringBoot() {
        return "Successfully post to spring boot";
    }

    @RequestMapping("/file-not-found")
    public void testFile() throws FileNotFoundException {
        throw new FileNotFoundException("example of file not found exception with advice");
    }

    @RequestMapping("/input-output")
    public void testIO() throws IOException {
        throw new IOException("example of IO thrown in the controller without advice");
    }

    @RequestMapping("/component")
    public String component() {
        return com.getName();
    }
}
