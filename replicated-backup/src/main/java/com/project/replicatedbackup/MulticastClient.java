package com.project.replicatedbackup;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient extends Thread {

    final String msgOK = "OK";
    String currentDirectory = System.getProperty("user.dir");
    long current = System.currentTimeMillis();

    public void receiveMessage() throws IOException {
        byte[] buffer=new byte[1024];
        MulticastSocket socket=new MulticastSocket(4446);
        InetAddress group=InetAddress.getByName("224.50.50.50");
        socket.joinGroup(group);
        while(true){
           System.out.println("Waiting for multicast message...");
           DatagramPacket packet=new DatagramPacket(buffer,
              buffer.length);
           socket.receive(packet);
           String msg=new String(packet.getData(),
           packet.getOffset(),packet.getLength());
           tratarMsg(msg);
           if("OK".equals(msg)) {
              break;
           }
        }
        socket.leaveGroup(group);
        socket.close();
    }

    public void run() {
        try {
            receiveMessage();
            System.out.println("Estou executando em segundo plano!");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void tratarMsg(String msg) throws IOException {
        //devolver o formato normal da msg
        //String msg = uuid.concat(",").concat(type).concat(",").concat(content).concat(",").concat(flag);
        //1606187186557,normal,UserID: 1 User's name: teste,Master
        //System.out.println(msg);
        long current = System.currentTimeMillis();
        String msgO[] = msg.split(",");
        if(msgO.length > 3){
            String uuid, type, content, flag;
            uuid = msgO[0];
            type = msgO[1];
            content = msgO[2];
            flag = msgO[3];
            if("Master".equals(flag)){
                System.out.println("Dando tratamento à msg: "+uuid);
                //confirmo o recebimento e arquivamento da msg enviando OK de volta
                switch(type){
                    case "post":
                        System.out.println("Msg POST");
                        break;
                    case "put":
                        System.out.println("Msg PUT");
                        break;
                    case "delete":
                        System.out.println("Msg Delete");
                        break;
                    case "get":
                        System.out.println("Msg GET");
                        break;
                }
                //por hora apenas chamo o metodo e gravo
                String contentMsg = "Request number: "+uuid+" "+content;
                write(contentMsg);
                //update(content); // sem o request number, apenas id de usuario e usuario
                MulticastServer server = new MulticastServer();
                server.createMSG(msgOK,uuid);
            }
        }
        //se for replica, só ignora a msg
    }

    public String write(String content) throws IOException {
        
        FileWriter writer = new FileWriter(currentDirectory + "/" +  Long.toString(this.current) + "file.txt", true);
        writer.write(content);
        writer.write("\n");
        writer.close();
        System.out.println(content);
        return content;
    }

    public void update(String content) throws IOException  {
        BufferedReader reader = new BufferedReader(new FileReader(currentDirectory + "/" +  Long.toString(this.current) + "file.txt"));
        StringBuffer inputBuffer = new StringBuffer();
        String uuid = "";
        String line; 
        while((line = reader.readLine()) != null) {
            if(!line.contains(content)){
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            uuid = line.substring(16,28);
        }
        reader.close();
        String inputString = inputBuffer.toString();
        FileOutputStream fileOut = new FileOutputStream(currentDirectory + "/" +  Long.toString(this.current) + "file.txt");
        fileOut.write(inputString.getBytes());
        fileOut.close();      
            
        write("Request number: "+uuid+" "+content);

        
    }
}
