package com.project.replicatedbackup;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastServer {

    public static void sendMessage(String message) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName("224.50.50.50");
        byte[] msg = message.getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, group, 4446);
        socket.send(packet);
        socket.close();
        System.out.println("Mensagem Foi enviada!");
    }

    public static void createMSG(String content, String uuid) throws IOException {
        final String flag = "Replica";
        String msg = uuid.concat(",").concat(content).concat(",").concat(flag);
        sendMessage(msg);
    }

}
