package ntu.csie.oop13spring;

public class CoordinateXY extends POOCoordinate{

	public CoordinateXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(POOCoordinate other)
	{
		if(x == other.x && y == other.y)
			return true;
		else
			return false;
	}
}
