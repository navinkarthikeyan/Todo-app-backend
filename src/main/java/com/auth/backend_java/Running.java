package com.auth.backend_java;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Running {

    @RequestMapping("/")
    public String greet(){

        return("Successfully Running");
    }
    
}




