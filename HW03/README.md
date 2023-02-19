# HW03 

# Team 
* Ling Thang 
* Joaquin Trujillo 
* Carlos Gaytan Garcia (David)

# General Information 
    This is a Multithreaded TCP Network system that takes User inputs from client server and converts the string to UpperCase from client format

# TODO 
* HTTP Client Program:
    - [X] Display Message to ask User to input DNS/IP of HTTP Server 
    - [X] Display the RTT this Connection in milliseconds 
    - [X] Ask user for to input HTTP method type, name of htm file, HTTP version, User-Agent 
    - [X] Use inputs above to contruct *ONE HTTP* request message and send it to server 
    - [X] Recieve and Interpret message *line by line* 
    - [X] Ask users if they want to continue. If yes repeat 3-6 other wise terminate all i/o,tcpconnection, and client 
* HTTP Server Program:
    - [X] Listen to given port and wait for a connection request from HTTP client 
    - [X] Create a new thread for every incoming TCP connection request from a HTTP client 
    - [X] Read, Display to Standard Output, and interpret incoming HTTP request message line by line 
    - [X] Contruct *One HTTP* response message and send back to client 
    - [X] Repeat 3-4 until a "null" is read
    - [X] Close all i/o, TCP sockets and threads for client

# General Informatiom For HW03 Grading 
    * You'll have to do this on your own end
    * basically make a "HW02" dir on both server 
    * Server A make "server" dir in HW02 
    * Server B make "client" dir in HW02 
    * Also include a txt file with both our names in "server" ($touch team.txt)
    * Include a "testResultsClient.txt" in HW02/client on server B