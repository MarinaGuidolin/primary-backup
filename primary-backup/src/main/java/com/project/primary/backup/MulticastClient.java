package com.project.primary.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient extends Thread {

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
              System.out.println("No more message. Exiting : "+msg);
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

    public void tratarMsg(String msg){
        //devolver o formato normal da msg
        //String msg = uuid.concat(",").concat(content).concat(",").concat(flag);
        String msgO[] = msg.split(",");
        String uuid, content, flag;
        uuid = msgO[0];
        content = msgO[1];
        flag = msgO[2];
        if("Replica".equals(flag)){
            System.out.println("MSG "+uuid+" Recebida e confirmada!");
        }
    }
    
}
