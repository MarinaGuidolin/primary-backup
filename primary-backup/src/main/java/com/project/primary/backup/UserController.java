package com.project.primary.backup;

import java.io.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    String currentDirectory = System.getProperty("user.dir");
    //Writer writer = new Writer();
    
    //server multicast
    MulticastServer server = new MulticastServer();
    
    @GetMapping("/")
    public String start() throws IOException {
        return "Start";
    }

    @RequestMapping("/user")
    public User addUser(@RequestBody User user) throws IOException {
        String uuid = ""+System.currentTimeMillis();

        FileWriter writer = new FileWriter(currentDirectory + "/file.txt", true);
        String content = "UserID: " + user.getId() + " User's name: " + user.getContent();
        writer.write("Request number: " + uuid + " " + content);
        writer.write("\n");
        writer.close();

        //enviar msg para as replicas
        server.createMSG("normal", content, uuid);

        return user;
    }
    
}