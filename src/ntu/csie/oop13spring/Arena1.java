package ntu.csie.oop13spring;

import java.awt.Color;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class Arena1 extends POOArena{
	
	private POOPet[] Pets;
	private CoordinateXY[] position;
	private ImageButton[] PetImg;
	private ImageJFrame mainFrame;
	private JButton[] Button;
	private JLayeredPane background;
	private LayoutManager layout;
	private static int TotalPetNumber;
	private int currentPetID;
	
	// boarder color
	public static final Color activeColor = new Color(225,127,39);
	public static final MatteBorder activeBorder = new MatteBorder(3,3,3,3,activeColor);
	public static final Color defaultColor = new Color(0,0,0);
	public static final MatteBorder defaultBorder = new MatteBorder(0,0,0,0,defaultColor);
	
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
		position = new CoordinateXY[TotalPetNumber];
		Button = new JButton[TotalPetNumber];
		for(int i=0; i<TotalPetNumber; i++)
		{
			PetImg[i] = new ImageButton("black.png");
			Button[i] = PetImg[i].getButton();
			position[i] = layout.getPosition(i);
			((Pet1)Pets[i]).setALL(position[i], Button[i], i, this);
			Button[i].setBounds(layout.getXPosition(i),layout.getYPosition(i),40,40);
			//layout.setPetButton(Button[i], i);
			//background.add(Button[i], JLayeredPane.PALETTE_LAYER);
			background.add(Button[i], JLayeredPane.POPUP_LAYER);
		}
        mainFrame.setVisible(true);
	}
	
	public boolean fight()
	{
		for(int i=0; i<TotalPetNumber; i++)
		{
			Button[i].setBorder(activeBorder);
			getPosition(Pets[i]);
			Pets[i].act(this);
			Button[i].setBorder(defaultBorder);
		}
		return true;
	}
	public void show()
	{
		
	}
	public POOCoordinate getPosition(POOPet p)
	{
		return p.move(this);
	}
	public LayoutManager getLayoutManager()
	{
		return layout;
	}
	public JButton getCurrentPet()
	{
		return Button[currentPetID];
	}
	public JLayeredPane getBackground()
	{
		return background;
	}
	public POOPet[] getPets()
	{
		return Pets;
	}
	public static void main(String argv[])
	{
		Arena1 a;
		try{
			a = new Arena1();
			while(true)
			{
				a.fight();
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
}



