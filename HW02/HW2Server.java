/*
 * Server App upon UDP
 * Ling Thang
 * Joaqin Trujillo
 */

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class HW2Server {
    public static void main(String[] args) throws IOException {

        DatagramSocket udpServerSocket = new DatagramSocket(5270); // changing port number to mine
        DatagramPacket udpPacket = null, udpPacket2 = null;
        String fromClient = null, toClient = null, clientInfo = null;
        boolean morePackets = true;

        HashMap<String, String> items = new HashMap<String, String>();
        items.put("00001", "New Inspiron 15,$379.99,157");
        items.put("00002", "New Inspiron 17,$449.99,128");
        items.put("00003", "New Inspiron 15R,$549.99,202");
        items.put("00004", "New Inspiron 15z Ultrabook,$749.99,315");
        items.put("00005", "XPS 14 Ultrabook,$999.99,261");
        items.put("00006", "New XPS 12 UltrabookXPS,$1199.99,178");

        byte[] buf = new byte[256];

        while (morePackets) {
            try {

                // receive UDP packet from client
                udpPacket = new DatagramPacket(buf, buf.length);
                udpServerSocket.receive(udpPacket);

                fromClient = new String(
                        udpPacket.getData(), 0, udpPacket.getLength(), "UTF-8");
                // System.out.println(fromClient);//testing 0000n

                // get the response
                toClient = fromClient.toUpperCase();
                clientInfo = items.get(toClient);
                // System.out.println(items.get(toClient)); //testing info

                // send the response to the client at "address" and "port"
                InetAddress address = udpPacket.getAddress();
                int port = udpPacket.getPort();
                byte[] buf2 = clientInfo.getBytes("UTF-8");// test
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
