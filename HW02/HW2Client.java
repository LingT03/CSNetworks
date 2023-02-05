/*
 * Client App upon UDP
 * Ling Thang 
 * Joaqin Trujillo
 */

import java.io.*;
import java.net.*;

public class HW2Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: java UDPClient <hostname>");
            return;
        }

        // create a UDP socket
        DatagramSocket udpSocket = new DatagramSocket();

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String fromServer;
        String fromUser;

        // send request
        InetAddress address = InetAddress.getByName(args[0]);
        System.out.println("");
        System.out.println("	             DNS/IP Address Check!");
        System.out.println("=========================================================");
        System.out.println("    Before we begin, please make sure that your Address is correct.");
        System.out.println("    this is your DNS/IP Address: " + address);
        System.out.println("    If you do not recognize this address, Please enter your DNS/IP Address: ");
        System.out.println("    Other wise press enter to continue:");
        while ((line = sysIn.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            } else {
                address = InetAddress.getByName(line);
                System.out.println("Your DNS/IP Address is: " + address);
                break;
            }
        }

        System.out.println("");
        System.out.println("	             Welcome to the UDP Client Server!");
        System.out.println("	========================================================");
        System.out.println("	Here is a List of items you can request from the server:");
        System.out.println("	Item ID                                  Item Description");
        System.out.println("	00001                                    New Inspiron 15");
        System.out.println("	00002                                    New Inspiron 17");
        System.out.println("	00003                                    New Inspiron 15R");
        System.out.println("	00004                                    New Inspiron 15z Ultrabook");
        System.out.println("	00005                                    XPS 14 Ultrabook");
        System.out.println("	00006                                    New XPS 12 Ultrabook XPS");

        while ((fromUser = sysIn.readLine()) != null) {

            if (fromUser.equals("00001") || fromUser.equals("00002") || fromUser.equals("00003")
                    || fromUser.equals("00004") || fromUser.equals("00005") || fromUser.equals("00006")) {
                System.out.println("\n You have requested for item: " + fromUser);
            } else {
                System.out.println("\n Invalid Item ID. Please try again.");
                continue;
            }

            // display user input
            System.out.println("Fetching Information for: " + fromUser);

            byte[] buf = fromUser.getBytes();
            DatagramPacket udpPacket = new DatagramPacket(buf, buf.length, address, 5270); // my port number
            udpSocket.send(udpPacket);

            // get response
            byte[] buf2 = new byte[256];
            DatagramPacket udpPacket2 = new DatagramPacket(buf2, buf2.length);
            udpSocket.receive(udpPacket2);

            // display response
            fromServer = new String(udpPacket2.getData(), 0, udpPacket2.getLength());
            System.out.println("From Server: " + fromServer);

            if (fromUser.equals("Bye."))
                break;
        }

        udpSocket.close();
    }
}
