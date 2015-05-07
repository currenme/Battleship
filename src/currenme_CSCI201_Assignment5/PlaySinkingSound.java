package currenme_CSCI201_Assignment5;

public class PlaySinkingSound extends Thread
{
	PlaySinkingSound()
	{ }
	
	public void run()
	{
		SoundLibrary.playSound("Resources/sinking.wav"); 
	}
}