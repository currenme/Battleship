package currenme_CSCI201_Assignment5;

public class PlayHitSound extends Thread
{
	PlayHitSound()
	{	}
	
	public void run()
	{
		SoundLibrary.playSound("Resources/explode.wav"); 
	}
}