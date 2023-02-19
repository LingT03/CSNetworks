/*
 * Client App upon TCP
 *
 * Weiying Zhu
 *
 */

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in)); // Client input
        String hostName; // string for host name
        Socket tcpSocket = null; // tcp socket objecr

        PrintWriter socketOut = null; // output to pie**
        BufferedReader socketIn = null; // input to pipe**

        // INTRO OUTPUT:
        System.out.println("\n********** WELCOME TO THE UDP CLIENT! **********");
        System.out.println("===================================================");

        // Getting dns/Ip name of host
        /*
         * BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         * System.out.print("Enter the DNS name/ip of the HTTP server: ");
         * try {
         * hostName = reader.readLine();
         * } catch (IOException e) {
         * System.out.println("Error: " + e.getMessage());
         * System.exit(1);
         * }
         */

        hostName = "cs3700a.msudenver.edu";

        // setting hostname it inet adress
        InetAddress address = InetAddress.getByName(hostName);

        long start = System.currentTimeMillis(); // time before connection
        try {
            tcpSocket = new Socket(address, 5270); // LING: 5270 JOAQUIN: 5290
            long end = System.currentTimeMillis(); // time after connection
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true); // input to client
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream())); // output to server
            System.out.println("RTT: " + (end - start) + " ms"); // time to connect
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
            System.exit(1);
        }

        String fromServer;
        String fromUser;
        Boolean cont = true;
        int count = 0;

        // CHANGED TO BOOLEAN
        while (cont) {
            // System.out.println("Client: " + fromUser);

            // socketOut.println(fromUser);

            // Get user input for HTTP request
            System.out.print("Enter HTTP method type (e.g. GET): ");
            String method = sysIn.readLine();
            System.out.print("Enter name of htm file requested: ");
            String fileName = sysIn.readLine();
            System.out.print("Enter HTTP Version: ");
            String version = sysIn.readLine();
            System.out.print("Enter User-Agent: ");
            String userAgent = sysIn.readLine();

            // Construct HTTP message request
            String requestLine = method + " /" + fileName + " " + version + "\r\n";
            String hostHeader = "Host: " + hostName + ":5270\r\n";
            String userAgentHeader = "User-Agent: " + userAgent + "\r\n";
            String requestMessage = requestLine + hostHeader + userAgentHeader + "\r\n";
            DataOutputStream outToServer = new DataOutputStream(tcpSocket.getOutputStream());

            // Send HTTP request to server
            try {
                outToServer.flush();
                outToServer.writeBytes(requestMessage);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                System.exit(1);
            }

            // Send HTTP message request to server
            if ((fromServer = socketIn.readLine()) != null) {
                // FOR 404 and 400 CASE ONLY
                while (count < 4) {
                    System.out.println(fromServer);
                    fromServer = socketIn.readLine();
                    count++;
                }
            } else {
                System.out.println("Server replies nothing!");
                break;
            }
            System.out.println("Would you like to continue (yes/no)?");
            fromUser = sysIn.readLine();
            boolean invalid = true;

            while (invalid) {
                if (fromUser.toUpperCase().equals("YES")) {
                    outToServer.writeBytes(fromUser + "\r\n");
                    invalid = false;
                    count = 0;
                } else if (fromUser.toUpperCase().equals("NO")) {
                    // WE NEED TO SEND A BYE STRING TO SERVER SEE thread program for more info
                    outToServer.writeBytes(fromUser + "\r\n");
                    socketOut.close();
                    socketIn.close();
                    sysIn.close();
                    tcpSocket.close();
                    invalid = false;
                    cont = false;// exiting loop
                    break;
                } else {
                    System.out.println("Would you like to continue? (yes/no)");
                    fromUser = sysIn.readLine();
                }
            }
        }

    }
}
