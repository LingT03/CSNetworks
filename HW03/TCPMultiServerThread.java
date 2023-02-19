/*
 * Server App upon TCP
 * A thread is started to handle every client TCP connection to this server
 * Team: Jaoquin Trujillo, Ling Thang
 */

import java.io.*;
import java.io.FileReader;
import java.net.*;
import java.util.ArrayList;

public class TCPMultiServerThread extends Thread {
    private Socket clientTCPSocket = null; // Client socket

    public TCPMultiServerThread(Socket socket) {
        super("TCPMultiServerThread");
        clientTCPSocket = socket;
    }

    @Override
    public void run() {

        try {
            PrintWriter cSocketOut = new PrintWriter(
                    clientTCPSocket.getOutputStream(), true);

            BufferedReader cSocketIn = new BufferedReader(
                    new InputStreamReader(clientTCPSocket.getInputStream()));

            String fromClient;
            ArrayList<String> requestInfo = new ArrayList<String>();
            ArrayList<String> fileInfo = new ArrayList<String>();
            String method = "";
            String version = "";
            String fileName = "";
            int counter = 0;

            while ((fromClient = cSocketIn.readLine()) != null) {
                counter++;

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

                java.util.Date date = new java.util.Date();
                String dateLine = "Date: " + date + "\r\n";
                String serverLine = "Server: TEAMS'S SERVER\r\n";
                File requested = new File("/home/lthang/HW03/server" + fileName);

                // if client contains a get method and a file to be found 200 OK
                if (method.toUpperCase().contains("GET") && requested.exists()) {
                    // return HEADERS and txt or htm
                    System.out.println("200 ok");
                    String statusLine = "HTTP/" + version + " 200 OK\r\n";
                    String header = statusLine + dateLine + serverLine;
                    cSocketOut.println(header);

                    // TO ADD FILE AND APPEND FOUR LINES
                    FileReader fileReader = new FileReader(requested);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        fileInfo.add(line);
                    }
                    for (String fileInfos : fileInfo) {
                        fileInfos = fileInfos + "" + "\r\n" + "" + "\r\n" + "" + "\r\n" + "" + "\r\n";
                        cSocketOut.println(fileInfos);
                    }
                    bufferedReader.close();
                }

                // if method is get but file does NOT exist 404 error
                if (method.toUpperCase().contains("GET") && requested.exists() == false) {
                    // return only HEADER with 404 status
                    System.out.println("404");
                    String statusLine = "HTTP/" + version + " 404 NOT FOUND\r\n";
                    String header = statusLine + dateLine + serverLine;
                    cSocketOut.println(header);
                }
                // If the method not valie send a 400 error
                if (method.toUpperCase().contains("GET") == false) {
                    // returnn only header with a 400 error staus
                    System.out.println("400");
                    String statusLine = "HTTP/" + version + " 400 BAD REQUEST\r\n";
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
