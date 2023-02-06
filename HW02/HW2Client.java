/*     
 * Client App upon UDP
 * Ling Thang 
 * Joaqin Trujillo
 */

import java.io.*;
import java.net.*;

public class HW2Client {
    public static void main(String[] args) throws IOException {

        // create a UDP socket
        DatagramSocket udpSocket = new DatagramSocket();

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser; // string for user input
        String hostName; // string for host name
        String itemID; // itemId string

        System.out.println("\n********** WELCOME TO THE UDP CLIENT! **********");
        System.out.println("===================================================");
        // Getting dns/Ip name of host
        System.out.println("\nPlease enter the DNS/IP adress of the host:");
        hostName = sysIn.readLine();

        // send request
        InetAddress address = InetAddress.getByName(hostName);

        System.out.println("\n\n\nHere is a List of items you can request from the server:");
        System.out.println("______________________________________________________________________");
        System.out.println("	Item ID                                  Item Description");
        System.out.println("______________________________________________________________________");
        System.out.println("	00001                                    New Inspiron 15");
        System.out.println("	00002                                    New Inspiron 17");
        System.out.println("	00003                                    New Inspiron 15R");
        System.out.println("	00004                                    New Inspiron 15z Ultrabook");
        System.out.println("	00005                                    XPS 14 Ultrabook");
        System.out.println("	00006                                    New XPS 12 Ultrabook XPS");

        System.out.println("\n\n\nPlease enter the Item ID number you would like to search:");
        boolean ContinueLoop = true;

        while ((ContinueLoop)) {
            fromUser = null;
            itemID = null;
            boolean IDCheck = true;

            while ((IDCheck)) {

                fromUser = sysIn.readLine();

                if (fromUser.equals("00001")) {
                    itemID = "00001";
                    IDCheck = false;
                } else if (fromUser.equals("00002")) {
                    itemID = "00002";
                    IDCheck = false;
                } else if (fromUser.equals("00003")) {
                    itemID = "00003";
                    IDCheck = false;
                } else if (fromUser.equals("00004")) {
                    itemID = "00004";
                    IDCheck = false;
                } else if (fromUser.equals("00005")) {
                    itemID = "00005";
                    IDCheck = false;
                } else if (fromUser.equals("00006")) {
                    itemID = "00006";
                    IDCheck = false;
                } else {
                    System.out.println("\nPlease enter a valid ID number");
                }
            }

            // display user input
            System.out.println("\n\t\tFetching Information for: " + fromUser + "\n");

            byte[] buf = fromUser.getBytes();
            DatagramPacket Packetout = new DatagramPacket(buf, buf.length, address, 5270); // my port number
            long startTime = System.currentTimeMillis();
            udpSocket.send(Packetout);

            // get response
            byte[] buf2 = new byte[1024];
            DatagramPacket Packetin = new DatagramPacket(buf2, buf2.length);
            udpSocket.receive(Packetin);
            long endTime = System.currentTimeMillis();
            fromServer = new String(Packetin.getData(), 0, Packetin.getLength());
            String[] responseData = fromServer.split(",");

            // display response
            System.out.println("Item ID\t\tItem Description\tUnit Price\t\tInventory\t\tRTT of Query");
            System.out.println(itemID + "\t\t" + responseData[0] + "\t\t" + responseData[1] + "\t\t\t" +
                    responseData[2] + "\t\t\t" + (endTime - startTime) + " ms");

            System.out.println("\n\nWould you like to request another item? (Yes/No)");

            // fromUser = sysIn.readLine();
            // fromUser = fromUser.toUpperCase();

            boolean cont = true;

            while ((cont)) {

                fromUser = sysIn.readLine();
                fromUser = fromUser.toUpperCase();

                if (fromUser.equals("NO")) {
                    ContinueLoop = false;
                    cont = false;
                } else if (fromUser.equals("YES")) {
                    System.out.println("\nEnter another item ID: ");
                    cont = false;
                } else {
                    System.out.println("\n\nWould you like to request another item? (Yes/No)");
                }
            }
        }

        udpSocket.close();
    }
}