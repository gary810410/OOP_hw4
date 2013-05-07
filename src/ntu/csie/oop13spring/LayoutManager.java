package ntu.csie.oop13spring;
//import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private int[][] floorEffectTime;
	private int[][] floorEffectCauseBy;
	private int[][] floorSlowDown;
	private boolean[][] floorVisible;
	private POOSkill[][] floorSkill;
	
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
		floorEffectTime = new int[this.width/imgwidth][this.height/imgheight];
		floorSkill = new POOSkill[this.width/imgwidth][this.height/imgheight];
		floorEffectCauseBy = new int[this.width/imgwidth][this.height/imgheight];
		floorSlowDown = new int[this.width/imgwidth][this.height/imgheight];
		floorVisible = new boolean[this.width/imgwidth][this.height/imgheight];
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
		{
			floorSkill[location.getX()][location.getY()] = skill;
			floorEffectTime[location.getX()][location.getY()] = skill.getFloorEffectTime();
			floorEffectCauseBy[location.getX()][location.getY()] = CurrentPetID;
			floorSlowDown[location.getX()][location.getY()] = skill.getSlowDown();
			floorVisible[location.getX()][location.getY()] = skill.floorVisible();
			if(skill.GetFloorImg() != null)
			{
				TransparentIcon TIcon = new TransparentIcon(skill.GetFloorImg());
				icon = TIcon.getIcon();
				matrixbutton[location.getX()][location.getY()].setIcon(icon);
				if(!skill.floorVisible())
					matrixbutton[location.getX()][location.getY()].setVisible(false);
			}else
			{
				matrixbutton[location.getX()][location.getY()].setIcon(null);
			}
			matrixbutton[location.getX()][location.getY()].setBorder(defaultBorder);
		}
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
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(floorEffectCauseBy[i][j] == PetID && floorEffectTime[i][j] > 0)
				{
					floorEffectTime[i][j] --;
					if(floorEffectTime[i][j] == 0)
					{
						floorSkill[i][j] = null;
						floorEffectCauseBy[i][j] = -1;
						matrixbutton[i][j].setIcon(null);
					}
				}
			}
	}
	public void FloorEffect(POOPet pet, CoordinateXY location)
	{
		
		if(floorSkill[location.getX()][location.getY()] != null)
		{
			if(((SkillList)(floorSkill[location.getX()][location.getY()])).effectSelf)
			{
				((SkillList)(floorSkill[location.getX()][location.getY()])).FloorEffect(pet);
			}
			else if(CurrentPetID != floorEffectCauseBy[location.getX()][location.getY()])
			{
				((SkillList)(floorSkill[location.getX()][location.getY()])).FloorEffect(pet);
			}
		}
	}
	public void setFloorEffect(CoordinateXY location, int time, int slowdown, boolean visible)
	{
		floorEffectTime[location.getX()][location.getY()] = time;
		floorEffectCauseBy[location.getX()][location.getY()] = CurrentPetID;
		floorSlowDown[location.getX()][location.getY()] = slowdown;
		floorVisible[location.getX()][location.getY()] = visible;
	}
	public int exitCost(CoordinateXY location)
	{
		int cost = 1;
		if(floorSkill[location.getX()][location.getY()] != null)
			cost += floorSlowDown[location.getX()][location.getY()];
		return cost;
	}
	public void setVisibleFloor(int ID)
	{
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(floorSkill[i][j] != null)
				{
					if(floorEffectCauseBy[i][j] == ID || floorVisible[i][j])
					{
						matrixbutton[i][j].setVisible(true);
					}
				}
			}
	}
	public void resetVisibleFloor(int ID)
	{
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(floorSkill[i][j] != null)
				{
					if(floorEffectCauseBy[i][j] == ID && !floorVisible[i][j])
					{
						matrixbutton[i][j].setVisible(false);
					}
				}
			}
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