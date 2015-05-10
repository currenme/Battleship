package currenme_CSCI201_Assignment5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
	ServerSocket ss; 
	Socket s = null; 
	BufferedReader br; 
	PrintWriter pw;
	int thisPort; 
	String readIn; 
	public boolean clientConnected = false; 
	public boolean gotClientName = false; 
	String otherName; 
	String Name; 
	EditGameGUI serverEGI = null; 
	PlayGameGUI serverPGG = null; 
	public Server(int port, String _Name)
	{
		thisPort = port; 
		ss = null; 
		Name = _Name; 
	}
	public void run()
	{
		try
		{
			ss = new ServerSocket(thisPort); 
			while(true)
			{
				if (s == null)
				{
					s = ss.accept(); 
					clientConnected = true; 
					ss.close(); 
					br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					pw = new PrintWriter(s.getOutputStream());
					sendMessage("Name:"+Name); 
				}
				else if (s!= null)
				{
					readIn = br.readLine(); 
					if (readIn != null)
					{
						System.out.println(readIn); 
						String[] parts = readIn.split(":"); 
						if (parts[0].equals("Guess"))
						{
							if (serverPGG == null)
							{
								
							}
							else
							{
								System.out.println("Server recieved guess"); 
								serverPGG.recNetworkGuess(Integer.parseInt(parts[1]), Integer.parseInt(parts[1])); 
							}
						}
						if (parts[0].equals("Name"))
						{
							otherName = parts[1]; 
							gotClientName = true; 
						}
						if (parts[0].equals("Aircraft"))
						{
							if (serverEGI == null)
							{
								
							}
							else
							{
								String tempA = parts[1]; 
								serverEGI.tempComp[Character.getNumericValue(tempA.charAt(0))][Character.getNumericValue(tempA.charAt(1))] = 'A';
								serverEGI.tempComp[Character.getNumericValue(tempA.charAt(2))][Character.getNumericValue(tempA.charAt(3))] = 'A';
								serverEGI.tempComp[Character.getNumericValue(tempA.charAt(4))][Character.getNumericValue(tempA.charAt(5))] = 'A';
								serverEGI.tempComp[Character.getNumericValue(tempA.charAt(6))][Character.getNumericValue(tempA.charAt(7))] = 'A';
								serverEGI.tempComp[Character.getNumericValue(tempA.charAt(8))][Character.getNumericValue(tempA.charAt(9))] = 'A';
							}
						}
						if (parts[0].equals("Battleship"))
						{
							if (serverEGI == null)
							{
								
							}
							else
							{
								String tempB = parts[1]; 
								serverEGI.tempComp[Character.getNumericValue(tempB.charAt(0))][Character.getNumericValue(tempB.charAt(1))] = 'B';
								serverEGI.tempComp[Character.getNumericValue(tempB.charAt(2))][Character.getNumericValue(tempB.charAt(3))] = 'B';
								serverEGI.tempComp[Character.getNumericValue(tempB.charAt(4))][Character.getNumericValue(tempB.charAt(5))] = 'B';
								serverEGI.tempComp[Character.getNumericValue(tempB.charAt(6))][Character.getNumericValue(tempB.charAt(7))] = 'B';
							}
						}
						if (parts[0].equals("Cruiser"))
						{
							if (serverEGI == null)
							{
								
							}
							else
							{
								String tempC = parts[1]; 
								serverEGI.tempComp[Character.getNumericValue(tempC.charAt(0))][Character.getNumericValue(tempC.charAt(1))] = 'C';
								serverEGI.tempComp[Character.getNumericValue(tempC.charAt(2))][Character.getNumericValue(tempC.charAt(3))] = 'C';
								serverEGI.tempComp[Character.getNumericValue(tempC.charAt(4))][Character.getNumericValue(tempC.charAt(5))] = 'C';
							}
						}
						if (parts[0].equals("Destroyer1"))
						{
							if (serverEGI == null)
							{
								
							}
							else
							{
								String tempD1 = parts[1]; 
								serverEGI.tempComp[Character.getNumericValue(tempD1.charAt(0))][Character.getNumericValue(tempD1.charAt(1))] = 'D';
								serverEGI.tempComp[Character.getNumericValue(tempD1.charAt(2))][Character.getNumericValue(tempD1.charAt(3))] = 'D';
								serverEGI.CompDestroyer1.startRow = Character.getNumericValue(tempD1.charAt(0)); 
								serverEGI.CompDestroyer1.startCol = Character.getNumericValue(tempD1.charAt(1)); 
								serverEGI.CompDestroyer1.endRow = Character.getNumericValue(tempD1.charAt(2)); 
								serverEGI.CompDestroyer1.endCol = Character.getNumericValue(tempD1.charAt(3)); 
							}
						}
						if (parts[0].equals("Destroyer2"))
						{
							if (serverEGI == null)
							{
								
							}
							else
							{
								String tempD2 = parts[1]; 
								serverEGI.tempComp[Character.getNumericValue(tempD2.charAt(0))][Character.getNumericValue(tempD2.charAt(1))] = 'D';
								serverEGI.tempComp[Character.getNumericValue(tempD2.charAt(2))][Character.getNumericValue(tempD2.charAt(3))] = 'D';
								serverEGI.CompDestroyer2.startRow = Character.getNumericValue(tempD2.charAt(0)); 
								serverEGI.CompDestroyer2.startCol = Character.getNumericValue(tempD2.charAt(1)); 
								serverEGI.CompDestroyer2.endRow = Character.getNumericValue(tempD2.charAt(2)); 
								serverEGI.CompDestroyer2.endCol = Character.getNumericValue(tempD2.charAt(3)); 
							}
						}
						if (parts[0].equals("Ready"))
						{
							if (serverEGI == null)
							{
								
							}
							else
								serverEGI.recClientReady = true; 
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
		finally
		{
			if (ss!=null)
			{
				try
				{
					ss.close(); 
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public void sendMessage(String message)
	{
		pw.println(message); 
		pw.flush(); 
	}
	
	public String ClientName()
	{
		if (gotClientName)
			return otherName; 
		return null; 
	}
	public void setServerEGI(EditGameGUI e)
	{
		serverEGI = e; 
	}	
	public void setServerPGG(PlayGameGUI s)
	{
		serverPGG = s; 
	}
}