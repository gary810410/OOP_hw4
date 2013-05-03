package ntu.csie.oop13spring;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JLabel;

public class Arena1 extends POOArena{
	
	private POOPet[] Pets;
	private ImageLabel[] PetImg;
	private ImageJFrame mainFrame;
	private JLabel label;
	private JLabel background;
	private LayoutManager layout;
	
	public Arena1() throws IOException
	{
		//Pets = getAllPets();
		Pets = new POOPet[2];
		Pets[0] = new Pet1();
		Pets[1] = new Pet1();
		
		PetImg = new ImageLabel[Pets.length];
		layout = new LayoutManager(800, 600);
		
		// main frame
		mainFrame = new ImageJFrame("battle game", "grass.png");
		mainFrame.setLayout(new FlowLayout());
		background = mainFrame.getBackGround();
		background.setLayout(null);

		// set pets
		layout.setPetInit(Pets);
		for(int i=0; i<Pets.length; i++)
		{
			PetImg[i] = new ImageLabel("black.png");
			label = PetImg[i].getLabel();
			label.setBounds(layout.getXPosition(i),layout.getYPosition(i),40,40);
			background.add(label);
		}
        mainFrame.setVisible(true);
	}
	
	public boolean fight()
	{
		
		return true;
	}
	public void show()
	{
		
	}
	public POOCoordinate getPosition(POOPet p)
	{
		return p.move(this);
	}
	
	public static void main(String argv[])
	{
		try{
			new Arena1();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
}



