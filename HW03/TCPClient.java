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

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String hostName; // string for host name
        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null;

        System.out.println("\n********** WELCOME TO THE UDP CLIENT! **********");
        System.out.println("===================================================");
        // Getting dns/Ip name of host
        System.out.println("\nPlease enter the DNS/IP adress of the host:");
        hostName = sysIn.readLine();

        // send request
        InetAddress address = InetAddress.getByName(hostName);

        try {
            tcpSocket = new Socket(args[0], 4567);
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + args[0]);
            System.exit(1);
        }

        String fromServer;
        String fromUser;

        while ((fromUser = sysIn.readLine()) != null) {
            System.out.println("Client: " + fromUser);

            socketOut.println(fromUser);

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
            String request = method + " /" + fileName + " HTTP/" + version + "\r\n" +
                    "Host: " + hostName + "\r\n" +
                    "User-Agent: " + userAgent + "\r\n\r\n";
            // Send HTTP request to server
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(request);

            // Send HTTP message request to server
            if ((fromServer = socketIn.readLine()) != null) {
                System.out.println("Server: " + fromServer);
            } else {
                System.out.println("Server replies nothing!");
                break;
            }

            System.out.println("Would you like to continue (y/n)?");
            if (fromUser.equals("Bye.")) {
                socketOut.close();
                socketIn.close();
                sysIn.close();
                tcpSocket.close();
                break;
            }
        }

    }
}
