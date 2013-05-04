package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class PetBase extends POOPet implements MouseListener, ListSelectionListener{
	
	
	protected int imgwidth = 40;
	protected int imgheight = 40;

	protected static String[] ListItems;
	
	public static final Color blueColor = new Color(0, 0, 255);
	public static final Color redColor = new Color(255, 0, 0);
	public static final MatteBorder blueBorder = new MatteBorder(1,1,1,1,blueColor);
	public static final MatteBorder redBorder = new MatteBorder(1,1,1,1,redColor);
	public static final Border defaultBorder = UIManager.getBorder("Button.border");
	
	int width = 800;
	int height = 600;
	
	private int ID;
	protected POOAction[] action;
	private CoordinateXY location;
	private LayoutManager layout;
	private JButton[][] matrixbutton;
	private JLayeredPane background;
	private Status_control status;
	private JLabel statusLabel;
	private JButton itself;
	private static POOPet[] Pets;
	
	private int movestep = 0;
	private boolean targeted;
	private POOAction currentAction;
	private static CoordinateXY dest;
	private static int CurrentActID;
	private static boolean listEnter;
	
	
	// functional skill
	
	private JList<String> list;
	
	public PetBase()
	{
		setHP(1);
		setMP(1);
		setAGI(1);
		action = new POOAction[2];
		action[0] = new POOAction();
		action[0].skill = new ActionTinyAttack();
		action[1] = new POOAction();
		action[1].skill = new SkillList();
		currentAction = new POOAction();
		targeted = false;
	}
	public String getImgPath()
	{
		String imgpath = "black.png";
		return imgpath;
	}
	protected POOAction act(POOArena arena)
	{
		movestep = 3;
		CurrentActID = ID;
		while(movestep != 0)
		{
			try{
			TimeUnit.MICROSECONDS.sleep(100);
			}catch(Exception e){}
		}
		if(((SkillList)(currentAction.skill)).needAssignPet())
		{
			for(int i=0; i<Pets.length; i++)
			{
				if(dest.distance(((PetBase)Pets[i]).getLocation()) <= ((SkillList)(currentAction.skill)).getSplashRange())
				{
					currentAction.dest = Pets[i];
					currentAction.skill.act(currentAction.dest);
				}
					
			}
		}
		else
		{
			for(int i=0; i<Pets.length; i++)
			{
				if(((PetBase)Pets[i]).isTargeted() && ((SkillList)(currentAction.skill)).effectSelf())
					currentAction.skill.act(Pets[i]);
			}
		}
		for(int i=0; i<Pets.length; i++)
			((PetBase)Pets[i]).setTarget(false);
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j].setBorder(defaultBorder);
				matrixbutton[i][j].removeMouseListener(this);
				matrixbutton[i][j].addMouseListener(layout);
			}
		listEnter = false;
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
		background = ((Arena1)arena).getBackground();
		layout = ((Arena1)arena).getLayoutManager();
		matrixbutton = layout.getButtons();
		list = new JList<String>(getItemsList());
		list.setVisible(false);
		list.addListSelectionListener(this);
		background.add(list, JLayeredPane.DRAG_LAYER);
		status = new Status_control(getHP(), getMP(), getAGI(), background);
		statusLabel = status.getStatus();
	}
	protected POOCoordinate move(POOArena arena)
	{
		movestep = 1;
		CurrentActID = ID;
		while(movestep == 1 || movestep == 2)
		{
			try{
			TimeUnit.MICROSECONDS.sleep(100);
			}catch(Exception e)
			{
			}
		}
		movestep = -1;
		return location;
	}
	protected CoordinateXY getLocation()
	{
		return location;
	}
	public JButton getitself()
	{
		return itself;
	}
	public void setMoveStep(int i)
	{
		movestep = i;
	}
	public void setTarget(boolean b)
	{
		targeted = b;
	}
	public boolean isTargeted()
	{
		return targeted;
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			if(movestep == -1 && targeted == true)
			{
				dest = ((PetBase)Pets[ID]).getLocation();
				((PetBase)Pets[CurrentActID]).setMoveStep(0);
			}
			else if(movestep == 1)
			{
				layout.setCurrentChangePID(ID);
				for(int i=0; i<this.width/imgwidth; i++)
					for(int j=0; j<this.height/imgheight; j++)
					{
						if(location.distance(i, j) <= this.getAGI())
						{
							matrixbutton[i][j].setBorder(blueBorder);
						}
						matrixbutton[i][j].removeMouseListener(layout);
						matrixbutton[i][j].addMouseListener(this);
					}
				movestep = 2;
			}
			else if(movestep == 2)
			{
				Object triggered = e.getSource();
				for(int i=0; i<this.width/imgwidth; i++)
					for(int j=0; j<this.height/imgheight; j++)
					{
						if((JButton)triggered == matrixbutton[i][j])
						{
							location.setXY(i, j);
							itself.setBounds(i*40, j*40, 40, 40);
						}
						//matrixbutton[i][j].setVisible(false);
						matrixbutton[i][j].setBorder(defaultBorder);
						matrixbutton[i][j].removeMouseListener(this);
						matrixbutton[i][j].addMouseListener(layout);
					}
				movestep = 0;
			}
			else if(movestep == 4)
			{
				Object triggered = e.getSource();
				if(triggered == itself)
					dest = location;
				for(int i=0; i<this.width/imgwidth; i++)
					for(int j=0; j<this.height/imgheight; j++)
					{
						if(triggered == matrixbutton[i][j])
							dest = new CoordinateXY(i, j);
					}
				movestep = 0;
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			if(movestep == 1 || movestep == 3)
			{
				list.setBounds(location.getX()*40+40, location.getY()*40, 50, ListItems.length * 20);
				list.setVisible(true);
				
				movestep = 3;
			}
		}
	}
	
	// for list selection
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == itself )
		{
			status.set_status(getHP(), getMP(), getAGI());
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
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		//if(list.getValueIsAdjusting() && CurrentActID == ID)
		int index = arg0.getLastIndex();
		if(CurrentActID == ID && listEnter == false && movestep == 3)
		{
			
			listEnter = true;
			System.out.println(ListItems[index]);
			currentAction.skill = action[index].skill;
			double range = ((SkillList)(currentAction.skill)).getRange();
			for(int i=0; i<Pets.length; i++)
				if(location.distance(((PetBase)(Pets[i])).getLocation()) <= range)
					((PetBase)(Pets[i])).setTarget(true);
			if(((SkillList)(action[index].skill)).needAssignPet())
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
				movestep = 4;
			}
			else
			{
				movestep = 0;
			}
		}
		list.clearSelection();
		//list.setVisible(false);
	}
}