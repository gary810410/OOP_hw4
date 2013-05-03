package ntu.csie.oop13spring;
//import javax.swing.*;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

import java.util.concurrent.TimeUnit;

// width = 800;
// height = 600;
// grind = 40*40;

public class LayoutManager implements MouseListener{
	
	private static final int imgwidth = 40;
	private static final int imgheight = 40;
	int width;
	int height;
	private int TotalPet;
	private POOPet[] Pets;
	private CoordinateXY[] Position;
	private JButton[] Button;
	private JLayeredPane background;
	private JButton[][] matrixbutton;
	private int currentChangePID;
	
	public LayoutManager(int width, int height, JLayeredPane background)
	{
		this.width = width;
		this.height = height;
		this.background = background;
		matrixbutton = new JButton[this.width/imgwidth][this.height/imgheight];
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j] = new JButton();
				matrixbutton[i][j].setOpaque(false);
				matrixbutton[i][j].setContentAreaFilled(false);
				matrixbutton[i][j].setBounds(i*40, j*40, 40, 40);
				this.background.add(matrixbutton[i][j],JLayeredPane.POPUP_LAYER);
				matrixbutton[i][j].addMouseListener(this);
				matrixbutton[i][j].setVisible(false);
			}
	}
	
	public void setPetInit(POOPet[] Pets)
	{
		TotalPet = Pets.length;
		this.Pets = new POOPet[TotalPet];
		this.Position = new CoordinateXY[TotalPet];
		this.Pets = Pets;
		for(int i=0; i<TotalPet; i++)
		{
			Position[i] = new CoordinateXY();
		}
		Button = new JButton[TotalPet];
	}
	
	public void setPetButton(JButton petButton, int petID)
	{
		Button[petID] = petButton;
		Button[petID].addMouseListener(this);
	}
	public int getXPosition(int petID)
	{
		return Position[petID].getX() * imgwidth;
	}
	public int getYPosition(int petID)
	{
		return Position[petID].getY() * imgheight;
	}
	
	public static void main(String[] argv)
	{
		ImageJFrame mainFrame = new ImageJFrame("battle game", "grass.png");
		JLayeredPane background = ((ImageJFrame)mainFrame).getBackGround();
		//background.setLayout(null);
		LayoutManager layout = new LayoutManager(800,600, background);
		mainFrame.setVisible(true);
		POOPet[] Pets = new POOPet[2];
		layout.setPetInit(Pets);
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			System.out.println("left");
			Object triggered = e.getSource();
			for(int i=0; i<this.width/imgwidth; i++)
				for(int j=0; j<this.height/imgheight; j++)
				{
					if((JButton)triggered == matrixbutton[i][j])
					{
						Position[currentChangePID].setXY(i, j);
						Button[currentChangePID].setBounds(i*40, j*40, 40, 40);
					}
					matrixbutton[i][j].setVisible(false);
				}
			
			for(int i=0; i<TotalPet; i++)
			{
				if((JButton)triggered == Button[i])
				{
					currentChangePID = i;
					for(int j=0; j<width/imgwidth; j++)
						for(int k=0; k<height/imgheight; k++)
						{
							if(Position[currentChangePID].distance(j, k) <= Pets[currentChangePID].getAGI())
								matrixbutton[j][k].setVisible(true);
						}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			System.out.println("right");
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}
}