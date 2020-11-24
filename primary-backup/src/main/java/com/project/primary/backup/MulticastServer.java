package com.project.primary.backup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.ArrayList;

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

    public static void createMSG(String type, String content, String uuid) throws IOException {
        final String flag = "Master";
        String msg = uuid.concat(",").concat(type).concat(",").concat(content).concat(",").concat(flag);
        //System.out.println(msg);
        sendMessage(msg);
    }

}
