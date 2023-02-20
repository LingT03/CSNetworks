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

        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null;

        // INTRO OUTPUT:
        System.out.println("\n********** WELCOME TO THE UDP CLIENT! **********");
        System.out.println("===================================================");

        // Getting dns/Ip name of host
        BufferedReader gethost = new BufferedReader(new InputStreamReader(System.in)); // Client input
        String hostName = ""; // string for host name
        System.out.print("Enter the DNS name/ip of the HTTP server: ");

        // Getting dns/Ip name of host
        hostName = gethost.readLine();

        // setting hostname it inet adress
        InetAddress address = InetAddress.getByName(hostName);

        try {
            tcpSocket = new Socket(address, 5270);
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + args[0]);
            System.exit(1);
        }

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromUser = sysIn.readLine()) != null) {

            System.out.println("Client: " + fromUser);

            socketOut.println(fromUser);

            if ((fromServer = socketIn.readLine()) != null) {
                System.out.println("Server: " + fromServer);
            } else {
                System.out.println("Server replies nothing!");
                break;
            }

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
