package currenme_CSCI201_Assignment5;

import javax.swing.JOptionPane;

public class FinishingMessage extends Thread
{
	SinkingTemplate myST; 
	int myWL;  
	PlayGameGUI PGI; 
	
	FinishingMessage(SinkingTemplate ST, int WL, PlayGameGUI PG)
	{
		myST = ST; 
		myWL = WL; 
		PGI = PG; 
	}
	
	public void run()
	{
		while (myST.isAlive() == true)
		{
			
		}
		try 
		{
			sleep(2000);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		if (myWL == 0)
		{
			int reply = JOptionPane.showConfirmDialog(null,"You lost! Play again?", "Results", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION)
			{
				PGI.setVisible(false); 
				PGI.dispose(); 
				Menu men = new Menu(); 
			}
			else
			{
				PGI.setVisible(false); 
				PGI.dispose();
			}
		}
		else if (myWL == 1)
		{
			int reply = JOptionPane.showConfirmDialog(null,"You won! Play again?", "Results", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION)
			{
				PGI.setVisible(false); 
				PGI.dispose(); 
				Menu men = new Menu(); 
			}
			else
			{
				PGI.setVisible(false); 
				PGI.dispose();
			}
		}
	}
	
	
	
}
