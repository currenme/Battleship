package currenme_CSCI201_Assignment5;

public class DestroyerSunk extends SinkingTemplate
{
	PlayHitAnimation myThread; 
	int [][] loc; 
	ImprovedJButton[][] jbArr; 
	
	DestroyerSunk (PlayHitAnimation PHAthread, int[][] arr, ImprovedJButton[][] arr2)
	{
		myThread = PHAthread; 
		loc = arr; 
		jbArr = arr2; 
	}
	
	public void run()
	{
		while (myThread.isAlive())
		{
			//do nothing
		}
		PlaySplashAnimation splash1 = new PlaySplashAnimation(loc[0][0], loc[0][1], jbArr, 'D'); 
		PlaySplashAnimation splash2 = new PlaySplashAnimation(loc[1][0], loc[1][1], jbArr, 'D'); 
		splash1.start(); 
		splash2.start(); 
	}
}
