package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class Arena1 extends POOArena implements MouseListener, KeyListener{
	
	private POOPet[] Pets;
	private CoordinateXY[] position;
	private ImageButton[] PetImg;
	private ImageJFrame mainFrame;
	private JButton[] Button;
	private JLayeredPane background;
	private LayoutManager layout;
	private static int TotalPetNumber;
	private int currentPetID;
	private JButton start;
	// boarder color
	public static final Color activeColor = new Color(225,127,39);
	public static final MatteBorder activeBorder = new MatteBorder(3,3,3,3,activeColor);
	public static final Color defaultColor = new Color(0,0,0);
	public static final MatteBorder defaultBorder = new MatteBorder(0,0,0,0,defaultColor);
	public static final Color greenColor = new Color(0,255,0);
	public static final MatteBorder greenBorder = new MatteBorder(2,2,2,2,greenColor);
	private boolean ready;
	private int gameStep;
	public static int deathCount = 0;
	
	public Arena1() throws IOException
	{
		ready = false;
		gameStep = 0;
	}
	public void setPets()
	{
		Pets = getAllPets();
		TotalPetNumber = Pets.length;
		PetImg = new ImageButton[TotalPetNumber];
		
		
		
		// main frame
		mainFrame = new ImageJFrame("battle game", "grass.png");
		background = mainFrame.getBackGround();
		background.setLayout(null);
		layout = new LayoutManager(800, 600, background);
		mainFrame.setVisible(true);
		
		// Start menu
		start = new JButton("Game Start");
		start.setBounds(0, 0, 800, 600);
		Font font = start.getFont();
		Font labelFont = font.deriveFont((float) 40.0);
		start.setFont(labelFont);
		start.setHorizontalAlignment(JLabel.CENTER);
		start.setVerticalAlignment(JLabel.CENTER);
		start.setBorder(defaultBorder);
		start.setOpaque(false);
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		start.addKeyListener(this);
		start.addMouseListener(this);
		background.add(start, new Integer(1000));
		while(gameStep == 0)
		{
			try{
				TimeUnit.MICROSECONDS.sleep(100);
			}catch(Exception e){}
		}
		start.setVisible(false);
		start.removeMouseListener(this);
		start.removeKeyListener(this);

		// set pets
		layout.setPetInit(Pets);
		position = new CoordinateXY[TotalPetNumber];
		Button = new JButton[TotalPetNumber];
		for(int i=0; i<TotalPetNumber; i++)
		{
			
			PetImg[i] = new ImageButton(((PetBase)Pets[i]).getImgPath());
			Button[i] = PetImg[i].getButton();
			Button[i].addMouseListener(this);
			Button[i].addKeyListener(this);
			position[i] = layout.getPosition(i);
			((PetBase)Pets[i]).setALL(position[i], Button[i], i, this);
			Button[i].setBounds(layout.getXPosition(i),layout.getYPosition(i),40,40);
			background.add(Button[i], JLayeredPane.POPUP_LAYER);
			
		}
		mainFrame.addKeyListener(this);
		start.setText("mark what monsters you want to use.");
		start.setVisible(true);
		try{
			TimeUnit.MICROSECONDS.sleep(2000000);
		}catch(Exception e){}
		start.setText(" Enter space or enter to fight.");
		try{
			TimeUnit.MICROSECONDS.sleep(2000000);
		}catch(Exception e){}
		start.setVisible(false);
		while(gameStep == 1)
		{
			try{
				TimeUnit.MICROSECONDS.sleep(100);
			}catch(Exception e){}
		}
		mainFrame.removeKeyListener(this);
		for(int i=0; i<TotalPetNumber; i++)
		{
			Button[i].removeMouseListener(this);
			Button[i].removeKeyListener(this);
		}
        ready = true;
	}
	
	public boolean fight()
	{
		if(ready == false)
			setPets();
		for(int i=0; i<TotalPetNumber; i++)
		{
			layout.setCurrentPetID(i);
			layout.newRound(i);
			layout.setVisibleFloor(i);
			layout.FloorEffect(Pets[i], ((PetBase)Pets[i]).getLocation());
			if(deathCount >= TotalPetNumber-1)
			{
				start.setText("Game over");
				start.setVisible(true);
				start.addKeyListener(this);
				start.addMouseListener(this);
				while(gameStep == 2)
				{
					try{
						TimeUnit.MICROSECONDS.sleep(100);
					}catch(Exception e){}
				}
				start.removeMouseListener(this);
				start.removeKeyListener(this);
				return false;
			}
			if(((PetBase)Pets[i]).checkAlive())
			{
				Button[i].setBorder(activeBorder);
				getPosition(Pets[i]);
				Pets[i].act(this);
				if(((PetBase)Pets[i]).ifAI())
					Button[i].setBorder(defaultBorder);
				else
					Button[i].setBorder(greenBorder);
				layout.resetVisibleFloor(i);
			}
			
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
	
	// listeners
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("key");
		if(gameStep == 0)
			gameStep = 1;
		else if(gameStep == 1)
			gameStep = 2;
		else if(gameStep == 2 && (e.getKeyCode() ==KeyEvent.VK_ENTER || e.getKeyCode() ==KeyEvent.VK_SPACE))
			gameStep = 3;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(gameStep == 0)
			gameStep = 1;
		else if(gameStep == 2)
			gameStep = 3;
		else if(gameStep == 1)
		{
			Object triggered = arg0.getSource();
			for(int i=0; i<TotalPetNumber; i++)
			{
				if(triggered == Button[i])
				{
					if(((PetBase)Pets[i]).ifAI())
					{
						Button[i].setBorder(greenBorder);
						((PetBase)Pets[i]).setAI(false);
					}else
					{
						Button[i].setBorder(defaultBorder);
						((PetBase)Pets[i]).setAI(true);
					}
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}



