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
        String content = write(user, uuid);
        sendMessage("post", content, uuid);
        return user;
    }

    public String write(User user, String uuid) throws IOException {
        FileWriter writer = new FileWriter(currentDirectory + "/file.txt", true);
        String content = "UserID: " + user.getId() + " User's name: " + user.getContent();
        writer.write("Request number: " + uuid + " " + content);
        writer.write("\n");
        writer.close();
        return content;
    }

    public void sendMessage(String type, String content, String uuid) throws IOException {
        //type -> post, put, delete, get
        server.createMSG(type, content, uuid);
    }
    
}