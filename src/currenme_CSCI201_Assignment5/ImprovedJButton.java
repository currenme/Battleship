package currenme_CSCI201_Assignment5;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImprovedJButton extends JButton
{
	static final Image water1 = new ImageIcon("Resources/water1.PNG").getImage();
	static final Image water2 = new ImageIcon("Resources/water2.PNG").getImage();
	boolean isWater1 = true; 
	Image Water; 
	public void paintComponent(Graphics g)
	{ 
		if (isWater1)
		{
			Water = water2; 
			isWater1 = false; 
		}
		else
		{
			Water = water1; 
			isWater1 = true; 
		}
		g.drawImage(Water, 0, 0, null);
		super.paintComponent(g);
	}
}
