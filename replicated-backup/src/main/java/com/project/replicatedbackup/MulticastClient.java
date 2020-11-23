package com.project.replicatedbackup;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient implements Runnable {

    public static void main(String[] args) {
        Thread t=new Thread(new MulticastClient());
        t.start();
    }
  
    public void receiveUDPMessage() throws IOException {
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
           System.out.println("[Multicast UDP message received] >> "+msg);
           if("OK".equals(msg)) {
              System.out.println("No more message. Exiting : "+msg);
              break;
           }
        }
        socket.leaveGroup(group);
        socket.close();
    }
  
    @Override
    public void run(){
        try {
            receiveUDPMessage();
            System.out.println("Estou executando em segundo plano!");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}