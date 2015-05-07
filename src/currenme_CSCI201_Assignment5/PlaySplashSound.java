package currenme_CSCI201_Assignment5;

public class PlaySplashSound extends Thread
{
	PlaySplashSound()
	{
		
	}
	
	public void run()
	{
		SoundLibrary.playSound("Resources/splash.wav"); 
	}
}

