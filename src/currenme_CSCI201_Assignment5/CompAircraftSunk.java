package currenme_CSCI201_Assignment5;

public class CompAircraftSunk extends SinkingTemplate
{
	PlayHitAnimation myThread; 
	int [][] loc; 
	ImprovedJButton[][] jbArr; 
	
	CompAircraftSunk (PlayHitAnimation PHAthread, int[][] arr, ImprovedJButton[][] arr2)
	{
		myThread = PHAthread; 
		loc = arr; 
		jbArr = arr2; 
	}
	
	public void run()
	{
		while (myThread.isAlive())
		{ }
		PlaySplashAnimation splash1 = new PlaySplashAnimation(loc[0][0], loc[0][1], jbArr, 'X'); 
		PlaySplashAnimation splash2 = new PlaySplashAnimation(loc[1][0], loc[1][1], jbArr, 'X'); 
		PlaySplashAnimation splash3 = new PlaySplashAnimation(loc[2][0], loc[2][1], jbArr, 'X'); 
		PlaySplashAnimation splash4 = new PlaySplashAnimation(loc[3][0], loc[3][1], jbArr, 'X'); 
		PlaySplashAnimation splash5 = new PlaySplashAnimation(loc[4][0], loc[4][1], jbArr, 'X'); 
		splash1.start(); 
		splash2.start(); 
		splash3.start(); 
		splash4.start(); 
		splash5.start(); 
	}
}