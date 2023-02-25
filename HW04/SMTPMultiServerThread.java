/*
 * Server App upon TCP
 * A thread is started to handle every client TCP connection to this server
 * Weiying Zhu
 */

import java.net.*;
import java.io.*;

public class SMTPMultiServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public SMTPMultiServerThread(Socket socket) {
        super("SMTPMultiServerThread");
        clientTCPSocket = socket;
    }

    public void run() {

        try {
            PrintWriter cSocketOut = new PrintWriter(
                    clientTCPSocket.getOutputStream(), true);

            BufferedReader cSocketIn = new BufferedReader(
                    new InputStreamReader(clientTCPSocket.getInputStream()));
            // Getting server's IP and clients IP
            String serverIP = clientTCPSocket.getLocalAddress().getHostAddress();
            String clientIP = clientTCPSocket.getInetAddress().getHostAddress();

            String fromClient;

            if ((clientTCPSocket.isBound())) {
                System.out.println("Client connected to server!");
            }
            // System.out.println("Waiting for Client's Letter...");
            // System.out.println("===================================================");

            while (true) {
                String helo;
                String mail;
                String rcpt;
                String data;

                // INFO CHANGED ALL .equals to .contains

                // THIS CHECKS WHETHER HELO WAS SENT FIRST TO THE SERVER
                while (true) {
                    helo = cSocketIn.readLine();
                    if (helo.contains("HELO")) {
                        // This loop will not break until the helo command is sent
                        // System.out.println("Server got helo command: " + helo); //uncomment is test
                        // the server side getting helo from client
                        cSocketOut.println("250 " + serverIP + " Hello " + clientIP);
                        break;
                    } else {
                        cSocketOut.println("503 5.5.2 send 'HELO' command first");
                    }
                }

                // THIS CHECKS WHETHER MAIL FROM: IS SENT
                while (true) {
                    mail = cSocketIn.readLine();
                    if (mail.contains("MAIL FROM")) {
                        // This loop will not break until the mail from command is sent
                        // System.out.println("Server got mail from command: " + mail); //uncomment is
                        // test the server side getting helo from client
                        cSocketOut.println("205 2.1.0 Sender OK");
                        break;
                    } else {
                        cSocketOut.println("503 5.5.2 send 'MAIL FROM' command before contuining");
                    }
                }

                // THIS CHECKS WHETHER RCPT TO COMMAND IS SENT
                while (true) {
                    rcpt = cSocketIn.readLine();
                    if (rcpt.contains("RCPT TO")) {
                        // This loop will not break until the helo command is sent
                        // System.out.println("Server got the RCPT TO command: " + rcpt); //uncomment is
                        // test the server side getting helo from client
                        cSocketOut.println("250 2.1.5 RECIPIENT OK");
                        break;
                    } else {
                        cSocketOut.println("503 5.5.2 send 'RCPT TO' command");
                    }
                }

                // THIS CHECKS WHETHER DATA COMMAND WAS SENT
                while (true) {
                    data = cSocketIn.readLine();
                    if (data.contains("DATA")) {
                        // This loop will not break until the helo command is sent
                        // System.out.println("Server got helo command: " + data); //uncomment is test
                        // the server side getting helo from client
                        cSocketOut.println("250 mail input: NOTE please end with four blank lines and a '.'");
                        break;
                    } else {
                        cSocketOut.println("503 5.5.2 send 'DATA' command");
                    }
                }

                boolean keepGoing = true;
                while (keepGoing) {
                    fromClient = cSocketIn.readLine();
                    // System.out.println(fromClient);
                    if (fromClient.equals(".")) {
                        // System.out.println(fromClient);
                        cSocketOut.println("250 Message received and is going to be delivered");
                        keepGoing = false;
                    }
                }

                // IF THE CLIENT ASKS TO QUIT THE SOCKETS WILL CLOSE AND WILL BREAK OUT OF THE
                // LOOP
                String quit = cSocketIn.readLine();
                if (quit.equalsIgnoreCase("quit")) {
                    cSocketOut.close();
                    cSocketIn.close();
                    clientTCPSocket.close();
                    System.out.println("TERMINATED CONNECTION");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
