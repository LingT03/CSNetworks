/*
 * Client App upon TCP
 *
 * Weiying Zhu
 *
 */

import java.io.*;
import java.net.*;

public class SMTPClient {
    public static void main(String[] args) throws IOException {

        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null;

        // INTRO OUTPUT:
        System.out.println("\n********** WELCOME TO THE TCP CLIENT! **********");
        System.out.println("===================================================");

        // Client 1: Getting dns/Ip name of host
        BufferedReader gethost = new BufferedReader(new InputStreamReader(System.in)); // Client input
        String hostName = ""; // string for host name
        System.out.print("Enter the DNS name/ip of the HTTP server: ");

        // Getting dns/Ip name of host
        hostName = gethost.readLine();

        InetAddress address = InetAddress.getByName(hostName);

        try {
            tcpSocket = new Socket(address, 5270);
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + address);
            System.exit(1);
        }

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        // Initialize Letter informations
        String letter = "";
        String sender = "";
        String recipient = "";
        String subject = "";
        String content = "";

        while ((fromUser = sysIn.readLine()) != null) {

            System.out.println("\tPlease fill in the following information:");
            System.out.println("===========================================================");
            System.out.print("From: ");
            sender = sysIn.readLine();
            System.out.print("To: ");
            recipient = sysIn.readLine();
            System.out.print("Subject: ");
            subject = sysIn.readLine();
            System.out.println("Content: ");
            content = sysIn.readLine();
            System.out.println("===========================================================");

            // Create the letter
            letter = "" + "From: " + sender + "\r\n" + "To: " + recipient + "\r\n" + "Subject: " + subject + "\r\n"
                    + "Content: " + content + "\r\n";

            // Send the letter to the server
            socketOut.println(letter);

            // Get the response from the server
            if ((fromServer = socketIn.readLine()) != null) {
                System.out.println("Server: " + fromServer);
            } else {
                System.out.println("Server replies nothing!");
                break;
            }

            System.out.println("Would you like to quit? (Type 'Quit' to quit)");
            System.out.print("To continue press 'ENTER': ");

            if (fromUser.toLowerCase().equals("quit")) {
                socketOut.close();
                socketIn.close();
                sysIn.close();
                tcpSocket.close();
                break;
            }
        }
        System.out.println("===========================================================");
        System.out.println("********** THANK YOU FOR USING THE SMTP CLIENT! **********\n");
        return;

    }
}
