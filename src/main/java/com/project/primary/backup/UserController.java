package com.project.primary.backup;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String template = "Hello:  %s";
    private final AtomicLong counter = new AtomicLong();


    @GetMapping("/")
    public String start() {
        return "Start";
    }

    @PostMapping("/user")
    public User user(@RequestBody User usuario) {
        return usuario;
    }
}