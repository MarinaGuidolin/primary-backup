package com.project.replicatedbackup;
import java.io.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReplicatedBackupApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ReplicatedBackupApplication.class, args);
		Writer w = new Writer();
		w.write("escrevendo");
	}

}
