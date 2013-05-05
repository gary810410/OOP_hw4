package ntu.csie.oop13spring;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Skill_menu implements MouseListener{
	
	private static final int width = 100;
	private static final int height = 15;
	private JLabel menu;
	private CoordinateXY location;
	private PetBase Pet;
	private JButton[] skillList;
	private int skillNum;
	private boolean activate;
	private int buttonOutput;
	private JButton Button;
	private Font font;
	
	public Skill_menu(PetBase pet, String[] skillList, JButton Button,JLayeredPane background)
	{
		Pet = pet;
		skillNum = skillList.length;
		menu = new JLabel();
		menu.setLayout(null);
		menu.setBounds(0, 0, width, height*skillNum);
		this.skillList = new JButton[skillNum];
		for(int i=0; i<skillNum; i++)
		{
			this.skillList[i] = new JButton(skillList[i]);
			this.skillList[i].setHorizontalAlignment(JButton.LEFT);
			this.skillList[i].setBounds(0, height*i, width, height);
			this.skillList[i].addMouseListener(this);
			font = this.skillList[i].getFont();
			Font labelFont = font.deriveFont((float) 10.0);
			this.skillList[i].setFont(labelFont);
			menu.add(this.skillList[i]);
		}
		
		background.add(menu, JLayeredPane.DRAG_LAYER);
		menu.setVisible(false);
		this.Button = Button;
		this.Button.addMouseListener(this);
		
		location = new CoordinateXY(0,0);
	}
	public void setActivate(boolean b)
	{
		activate = b;
	}
	public void setLocation(int x, int y)
	{
		location.setXY(x, y);
		menu.setBounds(x*40+40, y*40, width, height*skillNum);
	}
	public JLabel getMenu()
	{
		return menu;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(activate)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				
					for(int i=0; i<skillNum; i++)
					{
						if(e.getSource() == skillList[i])
						{
							buttonOutput = i;
							((PetBase)Pet).setActionNum(buttonOutput);
						}
					}
				menu.setVisible(false);
			}
			else if(e.getButton() == MouseEvent.BUTTON3 && activate)
			{
				location = Pet.getLocation();
				menu.setBounds(location.getX()*40+40, location.getY()*40, width, height*skillNum);
				menu.setVisible(true);
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
