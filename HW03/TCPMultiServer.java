/*
 * Server App upon TCP
 * A thread is created for each connection request from a client
 * So it can handle Multiple Client Connections at the same time
 * Team: Jaoquin Trujillo, Ling Thang
 */

import java.io.*;
import java.net.*;

public class TCPMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverTCPSocket = null;
        Boolean checking = true;
        Boolean listening = true;
        Boolean printedWaitingMessage = false;

        // INTRO OUTPUT:
        System.out.println("\n\t********** Server is Running! **********");
        System.out.println("===================================================");

        try {
            // This is Server Question 1: listen to the port
            serverTCPSocket = new ServerSocket(5270); // Ling: 5270 Jaoquin: 5290
            System.out.println("Checking for connection...");
            while (checking) {
                if (!serverTCPSocket.isBound()) {
                    listening = true;
                    System.out.println("Connection Made To Server \n");
                    break;
                } else {
                    if (!printedWaitingMessage) {
                        System.out.println("Waiting...");
                        printedWaitingMessage = true;
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5290.");
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }

        while (listening) {
            System.out.println("Connection Made To Client \n");
            // Test: //long startTime = System.currentTimeMillis();
            // Handles creating new thread for every
            new TCPMultiServerThread(serverTCPSocket.accept()).start();

            // Test: //long endTime = System.currentTimeMillis();
        }

        serverTCPSocket.close();
    }
}
