/*
 * Server App upon TCP
 * A thread is created for each connection request from a client
 * So it can handle Multiple Client Connections at the same time
 * Ling Thang
 */

import java.net.*;
import java.io.*;

public class TCPMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverTCPSocket = null;
        Boolean listening = false;

        System.out.println("\n********** Server is Running! **********");
        System.out.println("===================================================");

        try {
            System.out.println("Waiting for client connection...");
            serverTCPSocket = new ServerSocket(5270); // Ling: 5270 Jaoquin: 5290
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5270.");
            System.exit(-1);
        }

        while (listening) {
            System.out.println("Client Connected!");
            new TCPMultiServerThread(serverTCPSocket.accept()).start();
        }

        serverTCPSocket.close();
    }
}
