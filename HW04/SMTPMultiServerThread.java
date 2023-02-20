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

            String fromClient, toClient;

            if ((clientTCPSocket.isBound())) {
                System.out.println("Client connected to server!");
            }

            System.out.println("Waiting for Client's Letter...");
            System.out.println("===================================================");

            while ((fromClient = cSocketIn.readLine()) != null) {

                System.out.println("The Client Sends: \n " + fromClient);
                toClient = "Your letter has been received by the server!";
                cSocketOut.println(toClient);

                if (fromClient.toLowerCase().equals("quit"))
                    break;
            }

            cSocketOut.close();
            cSocketIn.close();
            clientTCPSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
