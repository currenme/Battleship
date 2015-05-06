package currenme_CSCI201_Assignment5;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PlayHitAnimation extends Thread
{
	int row; 
	int col; 
	Icon hit1 = new ImageIcon("expl1.png");
	Icon hit2 = new ImageIcon("expl2.png");
	Icon hit3 = new ImageIcon("expl3.png");
	Icon hit4 = new ImageIcon("expl4.png");
	Icon hit5 = new ImageIcon("expl5.png");
	ImprovedJButton[][] thisArr; 
	Icon A = new ImageIcon("A.png"); 
	Icon B = new ImageIcon("B.png"); 
	Icon C = new ImageIcon("C.png"); 
	Icon D = new ImageIcon("D.png"); 
	Icon X = new ImageIcon("X.png"); 
	boolean isA = false, isB = false, isC = false, isD = false, isX = false; 
	
	PlayHitAnimation(int r, int c, char type, ImprovedJButton[][] arr)
	{
		row = r; 
		col = c; 
		thisArr = arr; 
		if (type == 'A')
			isA = true; 
		else if (type == 'B')
			isB = true; 
		else if (type == 'C')
			isC = true; 
		else if (type == 'D')
			isD = true; 
		else if (type == 'X')
			isX = true; 
	}
	
	public void run()
	{
		PlayHitSound PHS = new PlayHitSound(); 
		PHS.start(); 
		
		thisArr[row][col].setIcon(hit1); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(hit2); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(hit3); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(hit4); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(hit5); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		if (isA == true)
			thisArr[row][col].setIcon(A); 
		else if (isB == true)
			thisArr[row][col].setIcon(B); 
		else if (isC == true)
			thisArr[row][col].setIcon(C); 
		else if (isD == true)
			thisArr[row][col].setIcon(D); 
		else if (isX == true)
			thisArr[row][col].setIcon(X); 
	}
}
