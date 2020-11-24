package com.project.replicatedbackup;
import java.io.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

public class Writer {
        
    String currentDirectory = System.getProperty("user.dir");
    
    long currentTime;

    public Writer (long currentTime){

        this.currentTime = currentTime;
    }
    
    public void write(String content) throws IOException {
        FileWriter writer = new FileWriter(currentDirectory + "/" +  Long.toString(this.currentTime) + "file.txt", true);        
        writer.write(content);
        writer.write("\n");
        writer.close();
    }
}
