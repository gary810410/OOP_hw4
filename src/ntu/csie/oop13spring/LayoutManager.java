package ntu.csie.oop13spring;
//import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


// width = 800;
// height = 600;
// grind = 40*40;

public class LayoutManager implements MouseListener{
	
	private static final int imgwidth = 40;
	private static final int imgheight = 40;
	int width;
	int height;
	private int TotalPet;
	private CoordinateXY[] Position;
	private JButton[] Button;
	private JLayeredPane background;
	private JButton[][] matrixbutton;
	private int[][] floorEffectTime;
	private int[][] floorEffectCauseBy;
	private POOSkill[][] floorSkill;
	private int CurrentPetID;
	private Icon icon;
	
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
		floorEffectTime = new int[this.width/imgwidth][this.height/imgheight];
		floorSkill = new POOSkill[this.width/imgwidth][this.height/imgheight];
		floorEffectCauseBy = new int[this.width/imgwidth][this.height/imgheight];
		CurrentPetID = -1;
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
		floorSkill[location.getX()][location.getY()] = skill;
		floorEffectTime[location.getX()][location.getY()] = skill.getFloorEffectTime();
		floorEffectCauseBy[location.getX()][location.getY()] = CurrentPetID;
		if(skill.GetFloorImg() != null)
		{
			TransparentIcon TIcon = new TransparentIcon(skill.GetFloorImg());
			icon = TIcon.getIcon();
			matrixbutton[location.getX()][location.getY()].setIcon(icon);
		}
	}
	public void newRound(int PetID)
	{
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
			((SkillList)(floorSkill[location.getX()][location.getY()])).FloorEffect(pet);
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