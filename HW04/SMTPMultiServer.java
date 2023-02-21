/*
 * Server App upon TCP
 * A thread is created for each connection request from a client
 * So it can handle Multiple Client Connections at the same time
 * Weiying Zhu
 */

import java.net.*;
import java.io.*;

public class SMTPMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverTCPSocket = null;
        boolean listening = true;

        // INTRO OUTPUT:
        System.out.println("\n********** WELCOME TO THE SMTP Server! **********");
        System.out.println("===================================================");

        try {
            serverTCPSocket = new ServerSocket(5270);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5270.");
            System.exit(-1);
        }

        while (listening) {
            new SMTPMultiServerThread(serverTCPSocket.accept()).start();
        }

        serverTCPSocket.close();
    }
}
