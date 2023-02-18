/*
 * Client App upon TCP
 *
 * Weiying Zhu
 *
 */

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in)); //Client input
        String hostName; // string for host name
        Socket tcpSocket = null; //tcp socket objecr

        PrintWriter socketOut = null; //output to pie**
        BufferedReader socketIn = null; //input to pipe**

        //INTRO OUTPUT:
        System.out.println("\n********** WELCOME TO THE UDP CLIENT! **********");
        System.out.println("===================================================");
        
        // Getting dns/Ip name of host
        System.out.println("\nPlease enter the DNS/IP adress of the host:");
        hostName = sysIn.readLine();

        //setting hostname it inet adress 
        InetAddress address = InetAddress.getByName(hostName);

        try {
            tcpSocket = new Socket(address, 5290); //LING: 5270 JOAQUIN: 5290
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true); //setting up input to client
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream())); //setting output to server
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
            System.exit(1);
        }

        String fromServer;
        String fromUser;
	Boolean cont = true;
	int count = 0;
	//CHANGED TO BOOLEAN
        while (cont) {
           // System.out.println("Client: " + fromUser);

            //socketOut.println(fromUser);
            // Get user input for HTTP request
            System.out.print("Enter HTTP method type (e.g. GET): ");
            String method = sysIn.readLine();
            System.out.print("Enter name of htm file requested: ");
            String fileName = sysIn.readLine();
            System.out.print("Enter HTTP Version: ");
            String version = sysIn.readLine();
            System.out.print("Enter User-Agent: ");
            String userAgent = sysIn.readLine();

            // Construct HTTP message request
            String request = method + " /" + fileName + " HTTP/" + version + "\r\n" +
                    "Host: " + hostName + "\r\n" +
                    "User-Agent: " + userAgent + "\r\n\r\n";

            // Send HTTP request to server
            DataOutputStream outToServer = new DataOutputStream(tcpSocket.getOutputStream());
            outToServer.writeBytes(request);

            // Send HTTP message request to server
            if ((fromServer = socketIn.readLine()) != null) {
		//FOR 404 and 400 CASE ONLY
		while(count<4){
		System.out.println(fromServer);
		fromServer = socketIn.readLine();
		count++;
		}
            } else {
                System.out.println("Server replies nothing!");
                break;
            }
            System.out.println("Would you like to continue (yes/no)?");
	    fromUser = sysIn.readLine();
	    boolean invalid = true;

	    while(invalid){
	            if (fromUser.toUpperCase().equals("YES")) {
                        outToServer.writeBytes(fromUser + "\r\n");
                        invalid = false;
              		count = 0;
           	    }
	   	    else if (fromUser.toUpperCase().equals("NO")){
			//WE NEED TO SEND A BYE STRING TO SERVER SEE thread program for more info
                        outToServer.writeBytes(fromUser+"\r\n");
                        socketOut.close();
                        socketIn.close();
                        sysIn.close();
                        tcpSocket.close();
                        invalid = false;
                        cont = false;//exiting loop
                        break;
		    }
	   	    else{
			System.out.println("Would you like to continue? (yes/no)");
			fromUser = sysIn.readLine();
	    	    }
	     }
        }

    }
}
