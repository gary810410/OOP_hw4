package ntu.csie.oop13spring;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Arena1 extends POOArena{
	
	private POOPet[] Pets;
	private ImageButton[] PetImg;
	private ImageJFrame mainFrame;
	private JButton[] Button;
	private JLayeredPane background;
	private LayoutManager layout;
	private static int TotalPetNumber;
	
	public Arena1() throws IOException
	{
		//Pets = getAllPets();
		Pets = new POOPet[2];
		Pets[0] = new Pet1();
		Pets[1] = new Pet1();
		TotalPetNumber = Pets.length;
		PetImg = new ImageButton[TotalPetNumber];
		
		
		// main frame
		mainFrame = new ImageJFrame("battle game", "grass.png");
		background = mainFrame.getBackGround();
		background.setLayout(null);
		layout = new LayoutManager(800, 600, background);
		//background.addActionListener()
		

		// set pets
		layout.setPetInit(Pets);
		
		Button = new JButton[TotalPetNumber];
		for(int i=0; i<TotalPetNumber; i++)
		{
			PetImg[i] = new ImageButton("black.png");
			Button[i] = PetImg[i].getButton();
			layout.setPetButton(Button[i], i);
			//Button[i].addMouseListener(this);
			Button[i].setBounds(layout.getXPosition(i),layout.getYPosition(i),40,40);
			background.add(Button[i], JLayeredPane.PALETTE_LAYER);
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



