/*
 * Server App upon UDP
 * Ling Thang
 * Joaqin Trujillo
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class HW2Server {
    public static void main(String[] args) throws IOException {

        DatagramSocket udpServerSocket = new DatagramSocket(5270); // changing port number to mine
        BufferedReader in = null;
        DatagramPacket udpPacket = null, udpPacket2 = null;
        String fromClient = null, toClient = null;
        boolean morePackets = true;

        Map<String, Item> items = new HashMap<>();
        items.put("00001", new Item("New Inspiron 15", "$379.99", 157));
        items.put("00002", new Item("New Inspiron 17", "$449.99", 128));
        items.put("00003", new Item("New Inspiron 15R", "$549.99", 202));
        items.put("00004", new Item("New Inspiron 15z Ultrabook", "$749.99", 315));
        items.put("00005", new Item("XPS 14 Ultrabook", "$999.99", 261));
        items.put("00006", new Item("New XPS 12 UltrabookXPS", "$1199.99", 178));

        byte[] buf = new byte[256];

        while (morePackets) {
            try {

                // receive UDP packet from client
                udpPacket = new DatagramPacket(buf, buf.length);
                udpServerSocket.receive(udpPacket);

                fromClient = new String(
                        udpPacket.getData(), 0, udpPacket.getLength(), "UTF-8");

                // get the response
                toClient = fromClient.toUpperCase();

                // send the response to the client at "address" and "port"
                InetAddress address = udpPacket.getAddress();
                int port = udpPacket.getPort();
                byte[] buf2 = toClient.getBytes("UTF-8");
                udpPacket2 = new DatagramPacket(buf2, buf2.length, address, port);
                udpServerSocket.send(udpPacket2);

            } catch (IOException e) {
                e.printStackTrace();
                morePackets = false;
            }
        }

        // close the socket and resume
        udpServerSocket.close();

    }
}
