/*
 * Server App upon TCP
 * A thread is started to handle every client TCP connection to this server
 * Weiying Zhu
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.io.FileWriter;

public class TCPMultiServerThread extends Thread {
    private Socket clientTCPSocket = null; // Client socket

    public TCPMultiServerThread(Socket socket) {
        super("TCPMultiServerThread");
        clientTCPSocket = socket;
    }

    public void run() {

        try {
            PrintWriter cSocketOut = new PrintWriter(
                    clientTCPSocket.getOutputStream(), true);

            BufferedReader cSocketIn = new BufferedReader(
                    new InputStreamReader(clientTCPSocket.getInputStream()));

            String fromClient, toClient;
            ArrayList<String> requestInfo = new ArrayList<String>();
            String method = "";
            String version = "";
            String fileName = "";
            int counter = 0;

            while ((fromClient = cSocketIn.readLine()) != null) {
                toClient = fromClient.toUpperCase();// Uppercase protocol
                // cSocketOut.println(toClient);// send back to client
                counter++;
                // String method, fileName, version;

                // TO DO : SPLIT UP THE FROM CLIENT INFO SO WE CAN GET THE METHOD AND
                // THE FILE NAME SO WE CAN PUT INTO IF STATEMENTS
                String[] info = fromClient.split(" ");
                for (String infos : info) {
                    requestInfo.add(infos);
                }
                if (counter == 1) {
                    method = requestInfo.get(0);
                    fileName = requestInfo.get(1);
                    version = requestInfo.get(2);
                }
                System.out.println("Method: " + method);
                System.out.println("File Name: " + fileName);
                System.out.println("Version: " + version);

                File requested = new File("/home/lthang/HW03/server" + fileName);

                // if client contains a get method and a file to be found 200 OK
                if (method.toUpperCase().contains("GET") && requested.exists()) {
                    // return HEADERS and txt or htm
                    System.out.println("200 ok");
                    String statusLine = "HTTP/" + version + " 200 OK\r\n";
                    java.util.Date date = new java.util.Date();
                    String dateLine = "Date: " + date + "\r\n";
                    String serverLine = "Server: TEAMS'S SERVER\r\n";
                    String header = statusLine + dateLine + serverLine + "\r\n";
                    // TO ADD FILE AND APPEND FOUR LINES
                    cSocketOut.println(header);

                }

                // if method is get but file does NOT exist 404 error
                if (method.toUpperCase().contains("GET") && requested.exists() == false) {
                    // return only HEADER with 404 status
                    System.out.println("404");
                    String statusLine = "HTTP/" + version + " 404 NOT FOUND\r\n";
                    java.util.Date date = new java.util.Date();
                    String dateLine = "Date: " + date + "\r\n";
                    String serverLine = "Server: TEAMS'S SERVER\r\n";
                    String header = statusLine + dateLine + serverLine;
                    cSocketOut.println(header);
                }
                // If the method not valie send a 400 error
                if (method.toUpperCase().contains("GET") == false) {
                    // returnn only header with a 400 error staus
                    System.out.println("400");
                    String statusLine = "HTTP/" + version + " 400 BAD REQUEST\r\n";
                    java.util.Date date = new java.util.Date();
                    String dateLine = "Date: " + date + "\r\n";
                    String serverLine = "Server: TEAM'S SERVER\r\n";
                    String header = statusLine + dateLine + serverLine;
                    cSocketOut.println(header);
                }

                // When the client says no and breaks connection we need to send a output to
                // server that says bye
                System.out.println("********" + fromClient + "*******");
                if (fromClient.toUpperCase().equals("NO")) {
                    System.out.println("GoodBye! \n Terminating connection");

                }

                if (fromClient.toUpperCase().equals("YES")) {
                    requestInfo.clear();
                    counter = 0;
                    continue;
                }
                System.out.println(counter);
            }

            cSocketOut.close();
            cSocketIn.close();
            clientTCPSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
