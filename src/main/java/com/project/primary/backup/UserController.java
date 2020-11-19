package com.project.primary.backup;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String template = "Hello:  %s";

    @GetMapping("/")
    public String start() {
        return "Start";
    }

    @RequestMapping("/user")
    public User addUser(@RequestBody User usuario) {
        return new User(usuario.getId(), usuario.getContent());
    }


}