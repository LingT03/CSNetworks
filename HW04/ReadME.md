# HW04 

# Team 
* Ling Thang 
* Joaquin Trujillo 

# General Information 
    

# TODO 
* SMTP Client Program:
    - [ ] Ask for SMTP server's Host Name or IP Address
    - [ ] Build TCP connection with the SMTP server at given port
    - [ ] Collect sender's email address, receiver's email address, subject, and email content from the user
    - [ ] Send a series of SMTP commands to the server and display the response on the standard output
    - [ ] Ask the user whether to continue or quit, and act accordingly
* SMTP Server Program:
    - [ ] Listen for incoming connections from SMTP clients
    - [ ] Create a new thread for each incoming connection request
    - [ ] Implement a 3-phase data transfer procedure for each client connection
    - [ ] Repeat step 3 until the client sends a "QUIT" command
    - [ ] Close all I/O streams and TCP socket for the client connection

# General Informatiom For HW03 Grading 
    * You'll have to do this on your own end
    * basically make a "HW02" dir on both server 
    * Server A make "server" dir in HW02 
    * Server B make "client" dir in HW02 
    * Also include a txt file with both our names in "server" ($touch team.txt)
    * Include a "testResultsClient.txt" in HW02/client on server B
use "java prog_name | tee testResultsClient.txt" 