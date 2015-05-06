package currenme_CSCI201_Assignment5;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PlaySplashAnimation extends Thread
{
	int row; 
	int col; 
	Icon splash1 = new ImageIcon("splash1.png");
	Icon splash2 = new ImageIcon("splash2.png");
	Icon splash3 = new ImageIcon("splash3.png");
	Icon splash4 = new ImageIcon("splash4.png");
	Icon splash5 = new ImageIcon("splash5.png");
	Icon splash6 = new ImageIcon("splash6.png");
	Icon splash7 = new ImageIcon("splash7.png");
	Icon imageMiss = new ImageIcon("M.png"); 
	ImprovedJButton[][] thisArr; 
	Icon A = new ImageIcon("A.png"); 
	Icon B = new ImageIcon("B.png"); 
	Icon C = new ImageIcon("C.png"); 
	Icon D = new ImageIcon("D.png"); 
	Icon X = new ImageIcon("X.png"); 
	boolean isA = false, isB = false, isC = false, isD = false, isX = false; 
	
	PlaySplashAnimation(int r, int c, ImprovedJButton[][] arr, char type)
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
		if (isX == true)
		{
			PlaySplashSound PSS = new PlaySplashSound(); 
			PSS.start(); 
		}
		if (!isX)
		{
			PlaySinkingSound PSinkS = new PlaySinkingSound(); 
			PSinkS.start(); 
		}
		thisArr[row][col].setIcon(splash1); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(splash2); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(splash3); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(splash4); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(splash5); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(splash6); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		thisArr[row][col].setIcon(splash7); 
		try 
		{
			sleep(100);
		} catch (InterruptedException ie) 
		{
			System.out.println("interrupted");
		}
		if (isX == true)
			thisArr[row][col].setIcon(imageMiss);
		if (isA == true)
			thisArr[row][col].setIcon(A); 
		if (isB == true)
			thisArr[row][col].setIcon(B); 
		if (isC == true)
			thisArr[row][col].setIcon(C); 
		if (isX == true)
			thisArr[row][col].setIcon(imageMiss); 
		if (isD == true)
			thisArr[row][col].setIcon(D); 
	}
}
