Features that I implemented (based on the grading document): 

	- The menu contains all the required fields
	
	- The menu displays the user's IP 
	
	- The menu can refresh if no connection is found 
		- To see this, open the menu without Internet access
		- Then, while the menu program is open and running, turn on WiFi
		- Click refresh and wait and the public IP address should appear
		
	- The host waits 30 seconds before timing out
		- Note that if you let the host timeout it will go back to the menu
		- Once this happens you should should change to a different port 
		
	- If a connection is made between two players, a battleship grid appears with both player's names
	
	- If a player attempts to join a server that is already in use, or join a port which is not hosted by any server, then an error message will be thrown
	
	- The user can retrieve .battle files from the CSCI201 web site and use them in a single-player game mode against the computer
	
	- A non .battle file type is used to transmit map information from server to client 
	
	- The two-Destroyer ship issue is solved in multiple-player mode
		- Check out the Destroyer class or the destroyer1 and destroyer2 variables in EditGameGUI to see this
	
	- The player names are used in the event log 
	
	