package com.example.demo;

import com.example.demo.json.ErrorJson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class DemoErrorController implements ErrorController {
    private static final String PATH = "/error";
    private ErrorAttributes errorAttributes;

    @Autowired
    DemoErrorController(ErrorAttributes error) {
        this.errorAttributes = error;
    }

    @RequestMapping(value = PATH)
    public ErrorJson error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
        return new ErrorJson(response.getStatus(),
                errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), false));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}