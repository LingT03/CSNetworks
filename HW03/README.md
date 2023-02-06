# HW03 

# Team 
* Ling Thang 
* Joaquin Trujillo 
* Carlos Gaytan Garcia (David)

# General Information 
    This is a Multithreaded TCP Network system that takes User inputs from client server and converts the string to UpperCase from client format

# TODO 
* HTTP Client Program:
    - [ ] Display Message to ask User to input DNS/IP of HTTP Server 
    - [ ] Display the RTT this Connection in milliseconds 
    - [ ] Ask user for to input HTTP method type, name of htm file, HTTP version, User-Agent 
    - [ ] Use inputs above to contruct *ONE HTTP* request message and send it to server 
    - [ ] Recieve and Interpret message *line by line* 
    - [ ] Ask users if they want to continue. If yes repeat 3-6 other wise terminate all i/o,tcpconnection, and client 
* HTTP Server Program:
    - [ ] Listen to given port and wait for a connection request from HTTP client 
    - [ ] Create a new thread for every incoming TCP connection request from a HTTP client 
    - [ ] Read, Display to Standard Output, and interpret incoming HTTP request message line by line 
    - [ ] Contruct *One HTTP* response message and send back to client 
    - [ ] Repeat 3-4 until a "null" is read
    - [ ] Close all i/o, TCP sockets and threads for client

# General Informatiom For HW03 Grading 

<h3>
    <ul>
        <li>You'll have to do this on your own end </li>
        <li>basically make a "HW02" dir on both server </li>
        <li>Server A make "server" dir in HW02 </li>
        <li>Server B make "client" dir in HW02 </li>
        <li>Also include a txt file with both our names in "server" ($touch team.txt) </li>
        <li>Include a "testResultsClient.txt" in HW02/client on server B </li> </br>
                **Java prog_name_args | tee testResultsClient.txt** // copys output to .txt file</br>
                **Cat file-name** // displays contents of file
    </ul>   
</h3>
