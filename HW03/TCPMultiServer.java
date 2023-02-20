/*
 * Server App upon TCP
 * A thread is created for each connection request from a client
 * So it can handle Multiple Client Connections at the same time
 * Weiying Zhu
 */

import java.net.*;
import java.io.*;

public class TCPMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverTCPSocket = null;
        Boolean listening = true;

        // INTRO OUTPUT:
        System.out.println("\n\t********** Server is Running! **********");
        System.out.println("===================================================");

        try {
            // This is Server Question 1: listen to the port
            serverTCPSocket = new ServerSocket(5270); // Ling: 5270 Jaoquin: 5290
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5290.");
            System.exit(-1);
        }

        while (listening) {

            // Test: //long startTime = System.currentTimeMillis();
            // Handles creating new thread for every
            new TCPMultiServerThread(serverTCPSocket.accept()).start();

            // Test: //long endTime = System.currentTimeMillis();
        }

        serverTCPSocket.close();
    }
}
