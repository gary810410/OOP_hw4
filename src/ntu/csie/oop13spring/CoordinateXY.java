package ntu.csie.oop13spring;

import java.util.Random;

public class CoordinateXY extends POOCoordinate{
	
	private static final int widthnumber = 20;
	private static final int heightnumber = 15;
	
	public CoordinateXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public CoordinateXY()
	{
		
		Random random = new Random();
		x = random.nextInt(widthnumber);
		y = random.nextInt(heightnumber);
	}
	
	public boolean equals(POOCoordinate other)
	{
		if(x == other.x && y == other.y)
			return true;
		else
			return false;
	}
	public void setXY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int distance(int x, int y)
	{
		return Math.abs(this.x-x)+Math.abs(this.y-y);
	}
}
