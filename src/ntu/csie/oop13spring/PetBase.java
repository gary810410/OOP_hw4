package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
//import javax.swing.JList;
//import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;

public  abstract class PetBase extends POOPet implements MouseListener, KeyListener{
	
	
	protected int imgwidth = 40;
	protected int imgheight = 40;
	protected boolean AI;
	
	protected String[] ListItems;
	
	public static final Color blueColor = new Color(0, 0, 255);
	public static final Color redColor = new Color(255, 0, 0);
	public static final MatteBorder blueBorder = new MatteBorder(1,1,1,1,blueColor);
	public static final MatteBorder redBorder = new MatteBorder(1,1,1,1,redColor);
	public static final Border defaultBorder = UIManager.getBorder("Button.border");
	public static final Color greenColor = new Color(0,255,0);
	public static final MatteBorder greenBorder = new MatteBorder(2,2,2,2,greenColor);
	
	int width = 800;
	int height = 600;
	
	protected int ID;
	protected POOAction[] action;
	protected CoordinateXY location;
	protected LayoutManager layout;
	protected int exitCost;
	protected JButton[][] matrixbutton;
	private JLayeredPane background;
	private Status_control status;
	private JLabel statusLabel;
	protected JButton itself;
	protected static POOPet[] Pets;
	
	protected int movestep = 0;
	private boolean targeted;
	protected POOAction currentAction;
	protected static CoordinateXY dest;
	private static int CurrentActID;
	private Skill_menu menu;
	private MovingStatus moveList;
	protected int AGIused;
	private boolean alive;
	
	protected int MAXhp;
	protected int MAXmp;
	protected int MAXagi;
	
	public PetBase()
	{
		MAXhp = 1; setHP(1);
		MAXmp = 1; setMP(1);
		MAXagi = 1;setAGI(1);
		action = new POOAction[1];
		action[0] = new POOAction();
		action[0].skill = new SkillList();
		currentAction = new POOAction();
		targeted = false;
		movestep = -1;
		moveList = new MovingStatus();
		alive = true;
		AI = true;
	}
	
	// need implement
	abstract protected void AIAction();
	
	public boolean checkAlive()
	{
		return alive;
	}
	public void setRIP()
	{
		alive = false;
	}
	public String getImgPath()
	{
		String imgpath = "black.png";
		return imgpath;
	}
	public String getDeathImg()
	{
		String imgpath = "RIP.png";
		return imgpath;
	}
	protected POOAction act(POOArena arena)
	{
		CurrentActID = ID;
		while(movestep != 4)
		{
			try{
			TimeUnit.MICROSECONDS.sleep(100);
			}catch(Exception e){}
		}
		resetMatrixButton();
		// do skill action
		if(currentAction.skill != null)
		{
			if(((SkillList)(currentAction.skill)).needAssignPet())
			{
				for(int i=0; i<Pets.length; i++)
				{
					if(dest.distance(((PetBase)Pets[i]).getLocation()) <= ((SkillList)(currentAction.skill)).getSplashRange())
					{
						currentAction.dest = Pets[i];
						if(i !=  ID || ((SkillList)(currentAction.skill)).effectSelf())
							currentAction.skill.act(currentAction.dest);
					}		
				}
			}
			else
			{
				for(int i=0; i<Pets.length; i++)
					if(((PetBase)Pets[i]).isTargeted())
						currentAction.skill.act(Pets[i]);
			}
			
			// set floor effect
			for(int i=0; i<this.width/imgwidth; i++)
				for(int j=0; j<this.height/imgheight; j++)
					if(dest.distance(new CoordinateXY(i,j)) <= ((SkillList)(currentAction.skill)).getSplashRange())
						layout.setFloor((SkillList)currentAction.skill, new CoordinateXY(i,j));
		}
		// reset for the next pet
		for(int i=0; i<Pets.length; i++)
		{
			((PetBase)Pets[i]).setTarget(false);
			((PetBase)Pets[i]).setStatus();
		}
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j].setBorder(defaultBorder);
				matrixbutton[i][j].removeMouseListener(this);
				matrixbutton[i][j].addMouseListener(layout);
			}
		movestep = -1;
		return action[1];
	}
	protected String[] getItemsList()
	{
		ListItems = new String[]{"TinyAttack", "Wait"};
		return ListItems;
	}
	public void setALL(CoordinateXY location, JButton it, int ID, POOArena arena)
	{
		this.location = location;
		this.ID = ID;
		itself = it;
		Pets = ((Arena1)arena).getPets();
		itself.addMouseListener(this);
		itself.addKeyListener(this);
		background = ((Arena1)arena).getBackground();
		layout = ((Arena1)arena).getLayoutManager();
		matrixbutton = layout.getButtons();
		menu = new Skill_menu(this, getItemsList(), itself, background);
		status = new Status_control(getMAXhp(), getMAXmp(), getMAXagi(), background);
		statusLabel = status.getStatus();
	}
	protected POOCoordinate move(POOArena arena)
	{
		movestep = 1;
		currentAction.skill = null;
		CurrentActID = ID;
		AGIused = 0;
		if(AI)
		{
			AIAction();
		}
		else
		{
			menu.setActivate(true);
			while(movestep == 1 || movestep == 2)
			{
				try{
				TimeUnit.MICROSECONDS.sleep(100);
				}catch(Exception e)
				{
				}
			}
			menu.setActivate(false);
		}
		return location;
	}
	protected CoordinateXY getLocation()
	{
		return location;
	}
	protected void setLocation(int x, int y)
	{
		location.setXY(x,y);
	}
	public JButton getitself()
	{
		return itself;
	}
	public void setMoveStep(int i)
	{
		movestep = i;
	}
	public int getMoveStep()
	{
		return movestep;
	}
	public void setTarget(boolean b)
	{
		targeted = b;
	}
	public boolean isTargeted()
	{
		return targeted;
	}
	public void setStatus()
	{
		status.set_status(getHP(), getMP(), getAGI());
	}
	public int getMAXhp()
	{
		return MAXhp;
	}
	public int getMAXmp()
	{
		return MAXmp;
	}
	public int getMAXagi()
	{
		return MAXagi;
	}
	public int getAGIused()
	{
		return AGIused;
	}
	public POOPet getCurrentPet()
	{
		return Pets[CurrentActID];
	}
	public JLayeredPane getBackGround()
	{
		return background;
	}
	public LayoutManager getLayout()
	{
		return layout;
	}
	public int getID()
	{
		return ID;
	}
	public void setAI(boolean b)
	{
		AI = b;
	}
	public boolean ifAI()
	{
		return AI;
	}
	public void setMatrixButton()
	{
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(location.distance(i, j) <= this.getAGI()-AGIused)
				{
					matrixbutton[i][j].setBorder(blueBorder);
				}
				matrixbutton[i][j].removeMouseListener(layout);
				matrixbutton[i][j].addMouseListener(this);
			}
	}
	public void resetMatrixButton()
	{
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j].setBorder(defaultBorder);
				matrixbutton[i][j].removeMouseListener(this);
				matrixbutton[i][j].addMouseListener(layout);
			}
	}
	public boolean setActionNum(int num)
	{
		currentAction.skill = action[num].skill;
		if(getMP() < ((SkillList)(currentAction.skill)).getMPcost())
			return false;
		setMP(getMP() - ((SkillList)(currentAction.skill)).getMPcost());
		double range = ((SkillList)(currentAction.skill)).getRange();
		System.out.println(((SkillList)(currentAction.skill)).getName());
		resetMatrixButton();
		for(int i=0; i<Pets.length; i++)
			if(location.distance(((PetBase)(Pets[i])).getLocation()) <= range && (i != ID  || ((SkillList)(currentAction.skill)).effectSelf()))
				((PetBase)(Pets[i])).setTarget(true);
		if(((SkillList)(action[num].skill)).needAssignPet())
		{
			
			for(int i=0; i<this.width/imgwidth; i++)
				for(int j=0; j<this.height/imgheight; j++)
				{
					if(location.distance(i, j) <= range)
					{
						matrixbutton[i][j].setBorder(redBorder);
					}
					matrixbutton[i][j].removeMouseListener(layout);
					matrixbutton[i][j].addMouseListener(this);
				}
			movestep = 3;
		}
		else
		{
			for(int i=0; i<this.width/imgwidth; i++)
				for(int j=0; j<this.height/imgheight; j++)
				{
					if(location.distance(i, j) <= range)
					{
						matrixbutton[i][j].setBorder(redBorder);
					}
					matrixbutton[i][j].removeMouseListener(layout);
					matrixbutton[i][j].addMouseListener(this);
				}
			try{
				TimeUnit.MICROSECONDS.sleep(200000);
			}catch(Exception e){}
			dest = location;
			movestep = 4;
		}
		return true;
	}
	
	// for AI
	void autoMove(CoordinateXY destination)
	{
		double shortest = location.distance(destination);
		while(true)
		{
			exitCost = layout.exitCost(location);
			boolean judge = false;
			if(movestep != 1)
				break;
			if(AGIused+exitCost <= getAGI() && shortest > 1)
			{
				if(location.getX() < destination.getX())
					judge = next(new CoordinateXY(location.getX()+1,location.getY()));
				if(judge == false && location.getX() > destination.getX())
					judge = next(new CoordinateXY(location.getX()-1,location.getY()));
				if(judge == false && location.getY() > destination.getY())
					judge = next(new CoordinateXY(location.getX(),location.getY()-1));
				if(judge == false && location.getY() < destination.getY())
					judge = next(new CoordinateXY(location.getX(),location.getY()+1));
				if(judge == false)
					break;
			}else
				break;
			itself.setBounds(location.getX()*40, location.getY()*40, imgwidth, imgheight);
			resetMatrixButton();
			setMatrixButton();
			try{
				TimeUnit.MICROSECONDS.sleep(200000);
			}catch(Exception e){}
		}
	}
	public boolean next(CoordinateXY next)
	{
    	boolean cover = false;
		for(int i=0; i<Pets.length; i++)
    		if(next.equals(((PetBase)Pets[i]).getLocation()) && ((PetBase)Pets[i]).checkAlive())
    			cover = true;
    	if(cover == false)
    	{
    		location.setXY(next.getX(), next.getY());
    		layout.FloorEffect(this, location);
    		AGIused += exitCost;
    		return true;
    	}
		return false;
	}
	
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			if(movestep == -1 && targeted == true)
			{
				dest = ((PetBase)Pets[ID]).getLocation();
				for(int i=0; i<this.width/imgwidth; i++)
					for(int j=0; j<this.height/imgheight; j++)
					{
						if(location.distance(i, j) <= this.getAGI())
						{
							matrixbutton[i][j].setBorder(defaultBorder);
						}
						matrixbutton[i][j].removeMouseListener((PetBase)Pets[CurrentActID]);
						matrixbutton[i][j].addMouseListener(layout);
					}
				((PetBase)Pets[CurrentActID]).setMoveStep(4);
			}
			else if(movestep == 1)
			{
				setMatrixButton();
			}
			else if(movestep == 3)
			{
				Object triggered = e.getSource();
				if(triggered == itself)
					dest = location;
				for(int i=0; i<this.width/imgwidth; i++)
					for(int j=0; j<this.height/imgheight; j++)
					{
						if(triggered == matrixbutton[i][j])
						{
							dest = new CoordinateXY(i, j);
							
						}
					}
				movestep = 4;
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
		}
	}
	
	// for list selection
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == itself )
		{
			setStatus();
			status.set_location(location.getX()*40-20, location.getY()*40-30);
			statusLabel.setVisible(true);
		}
		else
			statusLabel.setVisible(false);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		statusLabel.setVisible(false);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}
	
	// for key listener
	public void keyPressed(KeyEvent e) {
		exitCost = layout.exitCost(location);
		if(CurrentActID == ID && movestep == 1)
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				
				StatusObject obj;
				obj = moveList.back();
				if(obj != null)
				{
					setHP(obj.getHP());
					setMP(obj.getMP());
					setAGI(obj.getAGI());
					location = obj.getLocation();
					AGIused = obj.getAGIused();
					itself.setBounds(location.getX()*40, location.getY()*40, imgwidth, imgheight);
					resetMatrixButton();
					setMatrixButton();
				}
			}
		}
		if(CurrentActID == ID && movestep == 1 && AGIused+exitCost <= getAGI())
		{
			boolean cover = false;
			if (e.getKeyCode() == KeyEvent.VK_LEFT && location.getX()-1 >= 0)
			{
	            CoordinateXY next = new CoordinateXY(location.getX()-1, location.getY());
	        	for(int i=0; i<Pets.length; i++)
	        		if(next.equals(((PetBase)Pets[i]).getLocation()) && ((PetBase)Pets[i]).checkAlive())
	        			cover = true;
	        	if(cover == false)
	        	{
	        		moveList.add(location, getHP(), getMP(), getAGI(), AGIused);
	        		location.setXY(location.getX()-1, location.getY());
	        		layout.FloorEffect(this, location);
	        		AGIused += exitCost;
	        	}
	        }
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT && location.getX()+1 < width/imgwidth)
	        {
	        	CoordinateXY next = new CoordinateXY(location.getX()+1, location.getY());
	        	for(int i=0; i<Pets.length; i++)
	        		if(next.equals(((PetBase)Pets[i]).getLocation()) && ((PetBase)Pets[i]).checkAlive())
	        			cover = true;
	        	if(cover == false)
	        	{
	        		moveList.add(location, getHP(), getMP(), getAGI(), AGIused);
	        		location.setXY(location.getX()+1, location.getY());
	        		layout.FloorEffect(this, location);
	        		AGIused += exitCost;
	        	}
	        }
			else if (e.getKeyCode() == KeyEvent.VK_UP && location.getY()-1 >= 0)
	        {
	        	CoordinateXY next = new CoordinateXY(location.getX(), location.getY()-1);
	        	for(int i=0; i<Pets.length; i++)
	        		if(next.equals(((PetBase)Pets[i]).getLocation()) && ((PetBase)Pets[i]).checkAlive())
	        			cover = true;
	        	if(cover == false)
	        	{
	        		moveList.add(location, getHP(), getMP(), getAGI(), AGIused);
	        		location.setXY(location.getX(), location.getY()-1);
	        		layout.FloorEffect(this, location);
	        		AGIused += exitCost;
	        	}
	        }
			else if (e.getKeyCode() == KeyEvent.VK_DOWN && location.getY()+1 < height/imgheight) {
	        	CoordinateXY next = new CoordinateXY(location.getX(), location.getY()+1);
	        	for(int i=0; i<Pets.length; i++)
	        		if(next.equals(((PetBase)Pets[i]).getLocation()) && ((PetBase)Pets[i]).checkAlive())
	        			cover = true;
	        	if(cover == false)
	        	{
	        		moveList.add(location, getHP(), getMP(), getAGI(), AGIused);
	        		location.setXY(location.getX(), location.getY()+1);
	        		layout.FloorEffect(this, location);
	        		AGIused += exitCost;
	        	}
	        }
			itself.setBounds(location.getX()*40, location.getY()*40, imgwidth, imgheight);
			resetMatrixButton();
			setMatrixButton();
		}
		
    }
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// nothing
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		// nothing
	}
}

// status object
class StatusObject
{
	private CoordinateXY location;
	int hp; int mp; int agi; int agiused;
	StatusObject(CoordinateXY location, int hp, int mp, int agi, int agiused)
	{
		this.location = new CoordinateXY(location.getX(), location.getY());
		this.hp = hp;
		this.mp = mp;
		this.agi = agi;
		this.agiused = agiused;
	}
	public CoordinateXY getLocation()
	{
		return this.location;
	}
	public int getHP()
	{
		return this.hp;
	}
	public int getMP()
	{
		return this.mp;
	}
	public int getAGI()
	{
		return this.agi;
	}
	public int getAGIused()
	{
		return this.agiused;
	}
	
}

// move status
class MovingStatus{
	
	
	private LinkedList<StatusObject> linkedList;
	private StatusObject status;
	public MovingStatus()
	{
		linkedList = new LinkedList<StatusObject>();
	}
	public void add(CoordinateXY location, int hp, int mp, int agi, int agiused)
	{
		status = new StatusObject(location,hp,mp,agi, agiused);
		linkedList.addLast(status);
	}
	public StatusObject back()
	{
		if(!linkedList.isEmpty())
		{
			status = linkedList.getLast();
			linkedList.removeLast();
		}
		else
		{
			status = null;
		}
		return status;
	}
}

