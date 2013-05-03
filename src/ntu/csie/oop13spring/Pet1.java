package ntu.csie.oop13spring;

public class Pet1 extends POOPet{
	
	POOAction action;
	POOCoordinate location;
	public static final String imgpath = "black.png";
	
	public Pet1()
	{
		setHP(5);
		setMP(5);
		setAGI(2);
	}
	public String getImgPath()
	{
		return imgpath;
	}
	protected POOAction act(POOArena arena)
	{
		
		return action;
	}
	protected POOCoordinate move(POOArena arena)
	{
		
		return location;
	}
}
