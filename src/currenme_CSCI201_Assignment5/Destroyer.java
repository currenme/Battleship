package currenme_CSCI201_Assignment5;

public class Destroyer 
{
	public int startRow; 
	public int startCol; 
	public int endRow; 
	public int endCol; 
	public boolean isPlaced = false; 
	public boolean isSunk = false; 
	public int hitCount = 0; 

	Destroyer()
	{ }
	
	public void setCoordinates (int Sr, int Sc, int Er, int Ec)
	{
		startRow = Sr; 
		startCol = Sc; 
		endRow = Er; 
		endCol = Ec; 
	}
}