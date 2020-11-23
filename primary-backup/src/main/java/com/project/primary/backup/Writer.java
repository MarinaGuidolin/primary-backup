package com.project.primary.backup;
import java.io.*;
public class Writer {

    BufferedWriter writer;

    public Writer() throws IOException {
       this.writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/file.txt"));
    }

    public void write (User user) throws IOException {
        writer.write("Request number: " + user.getId() + " User's name: " + user.getContent());
        writer.newLine();

    }

}