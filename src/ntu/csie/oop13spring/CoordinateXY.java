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
	public double distance(int x, int y)
	{
		return Math.abs(this.x-x)+Math.abs(this.y-y);
		//return Math.sqrt(Math.pow(this.x-x, 2) + Math.pow(this.y-y, 2));
	}
	public double distance(CoordinateXY another)
	{
		return Math.abs(x-another.getX()) + Math.abs(y-another.getY());
		//return Math.sqrt(Math.pow(this.x-another.getX(), 2) + Math.pow(this.y-another.getY(), 2));
	}
}
