package currenme_CSCI201_Assignment5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client extends Thread
{
	PrintWriter pw;
	BufferedReader br;
	Socket s;
	String IP; 
	int port; 
	String Name, otherName; 
	boolean gotServerName = false; 
	boolean correctPort = false; 
	EditGameGUI clientEGI = null; 
	public Client(String _IP, int _port, String _Name)
	{
		IP = _IP; 
		port = _port; 
		Name = _Name; 
	}
	public void run()
	{
		try
		{
			try
			{
				s = new Socket(IP, port); 
				correctPort = true;
			}
			catch(ConnectException ce)
			{ 
				correctPort = false; 
				JOptionPane.showMessageDialog(null, "Connection to the host failed!","Connection Error",JOptionPane.ERROR_MESSAGE);
			}
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			if (correctPort)
			{
				sendMessage("Name:"+Name ); 
			}
			while(true)
			{
				String line = br.readLine();
				if (line != null)
				{
					//System.out.println(line); 
					String[] parts = line.split(":"); 
					if (parts[0].equals("Name"))
					{
						otherName = parts[1]; 
						gotServerName = true; 
					}
					if (parts[0].equals("Aircraft"))
					{
						if (clientEGI == null)
						{
							
						}
						else
						{
							//System.out.println("CA"); 
							String tempA = parts[1]; 
							clientEGI.tempComp[Character.getNumericValue(tempA.charAt(0))][Character.getNumericValue(tempA.charAt(1))] = 'A';
							clientEGI.tempComp[Character.getNumericValue(tempA.charAt(2))][Character.getNumericValue(tempA.charAt(3))] = 'A';
							clientEGI.tempComp[Character.getNumericValue(tempA.charAt(4))][Character.getNumericValue(tempA.charAt(5))] = 'A';
							clientEGI.tempComp[Character.getNumericValue(tempA.charAt(6))][Character.getNumericValue(tempA.charAt(7))] = 'A';
							clientEGI.tempComp[Character.getNumericValue(tempA.charAt(8))][Character.getNumericValue(tempA.charAt(9))] = 'A';
						}
					}
					if (parts[0].equals("Battleship"))
					{
						if (clientEGI == null)
						{
							
						}
						else
						{
							//System.out.println("CB"); 
							String tempB = parts[1]; 
							clientEGI.tempComp[Character.getNumericValue(tempB.charAt(0))][Character.getNumericValue(tempB.charAt(1))] = 'B';
							clientEGI.tempComp[Character.getNumericValue(tempB.charAt(2))][Character.getNumericValue(tempB.charAt(3))] = 'B';
							clientEGI.tempComp[Character.getNumericValue(tempB.charAt(4))][Character.getNumericValue(tempB.charAt(5))] = 'B';
							clientEGI.tempComp[Character.getNumericValue(tempB.charAt(6))][Character.getNumericValue(tempB.charAt(7))] = 'B';
						}
					}
					if (parts[0].equals("Cruiser"))
					{
						if (clientEGI == null)
						{
							
						}
						else
						{
							//System.out.println("CC"); 
							String tempC = parts[1]; 
							clientEGI.tempComp[Character.getNumericValue(tempC.charAt(0))][Character.getNumericValue(tempC.charAt(1))] = 'C';
							clientEGI.tempComp[Character.getNumericValue(tempC.charAt(2))][Character.getNumericValue(tempC.charAt(3))] = 'C';
							clientEGI.tempComp[Character.getNumericValue(tempC.charAt(4))][Character.getNumericValue(tempC.charAt(5))] = 'C';
						}
					}
					if (parts[0].equals("Destroyer1"))
					{
						if (clientEGI == null)
						{
							
						}
						else
						{
							//System.out.println("CD1"); 
							String tempD1 = parts[1]; 
							clientEGI.tempComp[Character.getNumericValue(tempD1.charAt(0))][Character.getNumericValue(tempD1.charAt(1))] = 'D';
							clientEGI.tempComp[Character.getNumericValue(tempD1.charAt(2))][Character.getNumericValue(tempD1.charAt(3))] = 'D';
							clientEGI.CompDestroyer1.startRow = Character.getNumericValue(tempD1.charAt(0)); 
							clientEGI.CompDestroyer1.startCol = Character.getNumericValue(tempD1.charAt(1)); 
							clientEGI.CompDestroyer1.endRow = Character.getNumericValue(tempD1.charAt(2)); 
							clientEGI.CompDestroyer1.endCol = Character.getNumericValue(tempD1.charAt(3)); 
						}
					}
					if (parts[0].equals("Destroyer2"))
					{
						if (clientEGI == null)
						{
							
						}
						else
						{
							//System.out.println("CD2"); 
							String tempD2 = parts[1]; 
							clientEGI.tempComp[Character.getNumericValue(tempD2.charAt(0))][Character.getNumericValue(tempD2.charAt(1))] = 'D';
							clientEGI.tempComp[Character.getNumericValue(tempD2.charAt(2))][Character.getNumericValue(tempD2.charAt(3))] = 'D';
							clientEGI.CompDestroyer2.startRow = Character.getNumericValue(tempD2.charAt(0)); 
							clientEGI.CompDestroyer2.startCol = Character.getNumericValue(tempD2.charAt(1)); 
							clientEGI.CompDestroyer2.endRow = Character.getNumericValue(tempD2.charAt(2)); 
							clientEGI.CompDestroyer2.endCol = Character.getNumericValue(tempD2.charAt(3)); 
						}
					}
					if (parts[0].equals("Ready"))
					{
						if (clientEGI == null)
						{
							
						}
						else
							clientEGI.recServerReady = true; 
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
			try 
			{
				if (br != null) 
				{
					br.close();
				}
				if (pw != null) 
				{
					pw.close();
				}
				if (s != null) 
				{
					s.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void sendMessage(String message)
	{
		pw.println(message); 
		pw.flush(); 
	}
	public String ServerName()
	{
		if (gotServerName)
			return otherName; 
		return null; 
	}
	public void setClientEGI(EditGameGUI e)
	{
		clientEGI = e; 
	}
}
