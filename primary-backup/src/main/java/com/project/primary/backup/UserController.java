package com.project.primary.backup;

import java.io.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    String currentDirectory = System.getProperty("user.dir");
    //Writer writer = new Writer(); 
    
    @GetMapping("/")
    public String start() throws IOException {
        return "Start";
    }

    @RequestMapping("/user")
    public User addUser(@RequestBody User user) throws IOException {      
        FileWriter writer = new FileWriter(currentDirectory + "/file.txt", true);        
        writer.write("Request number: " + user.getId() + " User's name: " + user.getContent());
        writer.write("\n");
        writer.close();
        return user;
    }
    
}