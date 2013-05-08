package ntu.csie.oop13spring;
//import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.Border;


// width = 800;
// height = 600;
// grind = 40*40;

public class LayoutManager implements MouseListener{
	
	private static final int imgwidth = 40;
	private static final int imgheight = 40;
	int width;
	int height;
	public static final Border defaultBorder = UIManager.getBorder("Button.border");
	private int TotalPet;
	private CoordinateXY[] Position;
	private JButton[] Button;
	private JLayeredPane background;
	private JButton[][] matrixbutton;
	
	private floorList floorlist;
	private floorControl tmp;
	
	private int CurrentPetID;
	private Icon icon;
	private JButton Attack;
	private int showTime;
	
	
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
				this.background.add(matrixbutton[i][j], JLayeredPane.PALETTE_LAYER);
				matrixbutton[i][j].addMouseListener(this);
			}
		Attack = new JButton();
		Attack.setBounds(0,0,40,40);
		Attack.setVisible(false);
		Attack.setContentAreaFilled(false);
		Attack.setOpaque(false);
		this.background.add(Attack, JLayeredPane.POPUP_LAYER);
		floorlist = new floorList(background);
		CurrentPetID = -1;
		showTime = 0;
	}
	
	public void setPetInit(POOPet[] Pets)
	{
		TotalPet = Pets.length;
		this.Position = new CoordinateXY[TotalPet];
		for(int i=0; i<TotalPet; i++)
		{
			Position[i] = new CoordinateXY();
		}
		Button = new JButton[TotalPet];
	}
	public void setCurrentPetID(int ID)
	{
		CurrentPetID = ID;
	}
	public void setPetButton(JButton petButton, int petID)
	{
		Button[petID] = petButton;
		
	}
	public CoordinateXY getPosition(int petID)
	{
		return Position[petID];
	}
	public int getXPosition(int petID)
	{
		return Position[petID].getX() * imgwidth;
	}
	public int getYPosition(int petID)
	{
		return Position[petID].getY() * imgheight;
	}
	public JButton[][] getButtons()
	{
		return matrixbutton;
	}
	
	// floor effect
	public void setFloor(SkillList skill, CoordinateXY location)
	{
		if(skill.isFloorSkill())
			floorlist.add(skill, skill.getFloorEffectTime(), skill.floorVisible(), location, skill.GetFloorImg(), CurrentPetID, skill.getSlowDown());
		if(!skill.ShowOnce() || showTime ==0)
		{
			TransparentIcon TIcon = new TransparentIcon(skill.getAttackImg());
			icon = TIcon.getIcon();
			Attack.setIcon(icon);
			CoordinateXY place = skill.getLocation();
			Attack.setBounds((location.getX()+place.getX())*40,(location.getY()+place.getY())*40,skill.getImgSize(),skill.getImgSize());
			Attack.setVisible(true);
			try{
				TimeUnit.MICROSECONDS.sleep(1000000);
				}catch(Exception e){}
			Attack.setVisible(false);
			showTime ++;
		}
	}
	public void newRound(int PetID)
	{
		showTime = 0;
		floorlist.newRound(PetID);

	}
	public void FloorEffect(POOPet pet, CoordinateXY location)
	{
		LinkedList<floorControl> linkedList = floorlist.search(location);
		while(true)
		{
			if(!linkedList.isEmpty())
			{
				tmp = linkedList.removeLast();
				if(tmp.skill.effectSelf())
					tmp.skill.FloorEffect(pet);
				else if(CurrentPetID != tmp.causeBy)
					tmp.skill.FloorEffect(pet);
			}
			else
				break;
		}
	}
	public void setFloorEffect(CoordinateXY location, int time, int slowdown, boolean visible, SkillList skill)
	{
		if(tmp != null)
		{
			tmp.time = time;
			tmp.slowdown = slowdown;
			tmp.visible = visible;
			tmp.causeBy = CurrentPetID;
			resetVisibleFloor(CurrentPetID);
			setVisibleFloor(CurrentPetID);
		}
	}
	public int exitCost(CoordinateXY location)
	{
		int cost = 1;
		LinkedList<floorControl> linkedList = floorlist.search(location);
		while(true)
		{
			if(!linkedList.isEmpty())
			{
				tmp = linkedList.removeLast();
				cost += tmp.slowdown;
			}
			else
				break;
		}
		return cost;
	}
	public void setVisibleFloor(int ID)
	{	
		floorlist.setVisible(ID);
	}
	public void resetVisibleFloor(int ID)
	{
		floorlist.resetVisible(ID);
	}
	
	
	
	// main function & event handle
	public static void main(String[] argv)
	{
		ImageJFrame mainFrame = new ImageJFrame("battle game", "grass.png");
		JLayeredPane background = ((ImageJFrame)mainFrame).getBackGround();
		LayoutManager layout = new LayoutManager(800,600, background);
		mainFrame.setVisible(true);
		POOPet[] Pets = new POOPet[2];
		layout.setPetInit(Pets);
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
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

class floorControl{
	public SkillList skill;
	public int time;
	public boolean visible;
	public CoordinateXY location;
	public String img;
	public int causeBy;
	public int slowdown;
	public JButton button;
	
	floorControl(SkillList skill, int time, boolean visible, CoordinateXY location, String img, int causeBy, int slowdown, JButton button)
	{
		this.skill = skill;
		this.time = time;
		this.visible = visible;
		this.location = new CoordinateXY(location.getX(), location.getY());
		this.img = img;
		this.causeBy = causeBy;
		this.slowdown = slowdown;
		this.button = button;
	}
}

class floorList{
	private LinkedList<floorControl> linkedListA;
	private LinkedList<floorControl> linkedListB;
	private JButton button;
	private boolean AorB;
	private floorControl status;
	private JLayeredPane background;
	
	public floorList(JLayeredPane background)
	{
		this.background = background;
		linkedListA = new LinkedList<floorControl>();
		linkedListB = new LinkedList<floorControl>();
		AorB = true;
	}
	public void add(SkillList skill, int time, boolean visible, CoordinateXY location, String img, int causeBy, int slowdown)
	{
		
		button = new JButton();
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBounds(location.getX()*40, location.getY()*40, 40, 40);
		this.background.add(button, new Integer(50));
		if(img != null)
		{
			ImageIcon icon;
			TransparentIcon TIcon = new TransparentIcon(skill.GetFloorImg());
			icon = TIcon.getIcon();
			button.setIcon(icon);
			if(!skill.floorVisible())
				button.setVisible(false);
		}else
		{
			button.setIcon(null);
		}
		
		status = new floorControl(skill, time, visible, location, img, causeBy, slowdown, button);
		if(AorB)
			linkedListA.addLast(status);
		else
			linkedListB.addLast(status);
	}
	public LinkedList<floorControl> search(CoordinateXY location)
	{
		status = null;
		LinkedList<floorControl> linkedList = new LinkedList<floorControl>();
		while(true)
		{
			if(AorB && !linkedListA.isEmpty())
			{
				status = linkedListA.removeLast();
				linkedListB.addLast(status);
				if(status.location.equals(location))
					linkedList.addLast(status);
			}
			else if(!AorB && !linkedListB.isEmpty())
			{
				status = linkedListB.removeLast();
				linkedListA.addLast(status);
				if(status.location.equals(location))
					linkedList.addLast(status);
			}
			else
				break;
		}
		AorB = !AorB;
		return linkedList;
	}
	public void newRound(int ID)
	{
		while(true)
		{
			if(AorB && !linkedListA.isEmpty())
			{
				status = linkedListA.removeLast();
				
				if(status.causeBy == ID)
				{
					if(status.time > 0)
						status.time--;
					if(status.time == 0)
						status.button.setVisible(false);
					else
						linkedListB.addLast(status);
				}
				else
					linkedListB.addLast(status);
			}
			else if(!AorB && !linkedListB.isEmpty())
			{
				status = linkedListB.removeLast();
				
				if(status.causeBy == ID)
				{
					if(status.time > 0)
						status.time--;
					if(status.time == 0)
						status.button.setVisible(false);
					else
						linkedListA.addLast(status);
				}
				else
					linkedListA.addLast(status);
			}
			else
				break;
		}
		AorB = !AorB;
	}
	public void setVisible(int ID)
	{
		while(true)
		{
			if(AorB && !linkedListA.isEmpty())
			{
				status = linkedListA.removeLast();
				linkedListB.addLast(status);
				if(status.visible)
					status.button.setVisible(true);
				else if(ID == status.causeBy)
					status.button.setVisible(true);
			}
			else if(!AorB && !linkedListB.isEmpty())
			{
				status = linkedListB.removeLast();
				linkedListA.addLast(status);
				if(status.visible)
					status.button.setVisible(true);
				else if(ID == status.causeBy)
					status.button.setVisible(true);
			}
			else
				break;
		}
		AorB = !AorB;
	}
	public void resetVisible(int ID)
	{
		while(true)
		{
			if(AorB && !linkedListA.isEmpty())
			{
				status = linkedListA.removeLast();
				linkedListB.addLast(status);
				if(!status.visible)
					status.button.setVisible(false);
			}
			else if(!AorB && !linkedListB.isEmpty())
			{
				status = linkedListB.removeLast();
				linkedListA.addLast(status);
				if(!status.visible)
					status.button.setVisible(false);
			}
			else
				break;
		}
		AorB = !AorB;
	}
}