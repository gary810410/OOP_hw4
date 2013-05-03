package ntu.csie.oop13spring;
//import javax.swing.*;

import java.util.*;

// width = 800;
// height = 600;
// grind = 40*40;

public class LayoutManager{
	
	private static final int imgwidth = 40;
	private static final int imgheight = 40;
	int width;
	int height;
	private POOPet[] Pets;
	private PetPosition[] Position;
	
	public LayoutManager(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public void setPetInit(POOPet[] Pets)
	{
		this.Pets = new POOPet[Pets.length];
		this.Position = new PetPosition[Pets.length];
		this.Pets = Pets;
		for(int i=0; i<this.Pets.length; i++)
		{
			Position[i] = new PetPosition();
		}
	}
	public int getXPosition(int petID)
	{
		return Position[petID].getXposition() * imgwidth;
	}
	public int getYPosition(int petID)
	{
		return Position[petID].getYposition() * imgheight;
	}

}

class PetPosition{
	private int x;
	private int y;
	private static final int widthnumber = 20;
	private static final int heightnumber = 15;
	
	
	public PetPosition()
	{
		Random random = new Random();
		x = random.nextInt(widthnumber);
		y = random.nextInt(heightnumber);
	}
	public PetPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getXposition()
	{
		return x;
	}
	public int getYposition()
	{
		return y;
	}
	public boolean move(int x, int y)
	{
		if(x < widthnumber && y < heightnumber)
		{
			this.x = x;
			this.y = y;
			return true;
		}
		else
			return false;
		
	}
}