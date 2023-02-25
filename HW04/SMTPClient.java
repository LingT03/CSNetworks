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
            tcpSocket = new Socket(address, 5270);// Ling 5270 and Joaquin 5290
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
        String fromUser;

        // Initialize Letter informations

        Boolean continueloop = true;

        while (continueloop) {

            Boolean helocheck = true;
            Boolean mailcheck = true;
            Boolean rcptcheck = true;
            Boolean datacheck = true;

            String helo = "";
            String mail = "";
            String rcpt = "";
            String data = "";

            // Get the letter information from the user
            System.out.println("\tPlease fill in the following information:");
            System.out.println("===========================================================");

            // Check if the user has sent HELO to the server
            while (helocheck) {
                System.out.println("\nPlease send HELO to the server first!");
                helo = sysIn.readLine();
                socketOut.println(helo);
                String heloResponse = socketIn.readLine();
                if ((heloResponse.contains("250"))) {
                    System.out.println("\n" + heloResponse);
                    break;
                } else {
                    System.out.println("\n" + heloResponse);
                }
            }

            // Check if the user has sent MAIL FROM to the server

            while (mailcheck) {
                System.out.println("\nPlease send MAIL FROM to the server first!");
                mail = sysIn.readLine();
                socketOut.println(mail);
                String mailResponse = socketIn.readLine();
                if ((mailResponse.contains("205"))) {
                    System.out.println("\n" + mailResponse);
                    break;
                } else {
                    System.out.println("\n" + mailResponse);
                }
            }

            // Check if the user has sent RCPT TO to the server
            while (rcptcheck) {
                System.out.println("\nPlease send RCPT TO to the server first!");
                rcpt = sysIn.readLine();
                socketOut.println(rcpt);
                String rcptResponse = socketIn.readLine();
                if (rcptResponse.contains("250")) {
                    System.out.println("\nServer replies: " + rcptResponse);
                    break;
                } else {
                    System.out.println("\n" + rcptResponse);
                }
            }

            // Check if the user has sent DATA to the server
            while (datacheck) {
                System.out.println("\nPlease send DATA to the server first!");
                data = sysIn.readLine();
                socketOut.println(data);
                String dataResponse = socketIn.readLine();
                if (dataResponse.contains("250")) {
                    System.out.println("\n" + dataResponse);
                    int blankCount = 0;
                    int blankState = 0;
                    while (true) {
                        String line = sysIn.readLine();
                        if (line.isEmpty()) {
                            blankCount += 1;
                            // System.out.println(blankCount);
                        } else {
                            blankCount = 0;
                        }
                        if (blankCount == 4) {
                            blankState = 4;
                        }
                        if (line.equals(".") && blankState == 4) {
                            socketOut.println(line);
                            break;
                        }
                        socketOut.println(line);
                    }
                    String message = socketIn.readLine();
                    System.out.println(message);
                    break;
                } else {
                    System.out.println("\n" + dataResponse);
                }
            }

            System.out.println("Would you like to quit? (Type 'Quit' to quit)");
            System.out.print("To continue press 'ENTER': ");
            fromUser = sysIn.readLine();
            socketOut.println(fromUser);
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
