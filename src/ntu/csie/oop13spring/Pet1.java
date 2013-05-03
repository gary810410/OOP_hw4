package ntu.csie.oop13spring;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Pet1 extends POOPet implements MouseListener, ListSelectionListener{
	
	POOAction action;
	CoordinateXY location;
	private static final int imgwidth = 40;
	private static final int imgheight = 40;
	public static final String imgpath = "black.png";
	int width = 800;
	int height = 600;
	int movestep = 0;
	int ID;
	private LayoutManager layout;
	private JButton[][] matrixbutton;
	private JLayeredPane background;
	JButton itself;
	
	// functional skill
	String[] listItems = {"quit", "A", "B"};
	
	JList<String> list;
	
	public Pet1()
	{
		setHP(5);
		setMP(5);
		setAGI(2);
	}
	public String getImgPath()
	{
		return imgpath;
	}
	protected POOAction act(POOArena arena)
	{
		
		return action;
	}
	
	public void setALL(CoordinateXY location, JButton it, int ID, POOArena arena)
	{
		this.location = location;
		this.ID = ID;
		itself = it;
		it.addMouseListener(this);
		background = ((Arena1)arena).getBackground();
		layout = ((Arena1)arena).getLayoutManager();
		matrixbutton = layout.getButtons();
		list = new JList<String>( listItems);
		list.setVisible(false);
		list.addListSelectionListener(this);
		background.add(list, JLayeredPane.POPUP_LAYER);
	}
	protected POOCoordinate move(POOArena arena)
	{
		movestep = 1;
		background = ((Arena1)arena).getBackground();
		layout = ((Arena1)arena).getLayoutManager();
		matrixbutton = layout.getButtons();
		while(movestep != 0)
		{
			try{
			TimeUnit.MICROSECONDS.sleep(100);
			}catch(Exception e)
			{
				
			}
		}
		return location;
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			System.out.println("left");
			if(movestep == 1)
			{
				layout.setCurrentChangePID(ID);
				for(int i=0; i<this.width/imgwidth; i++)
					for(int j=0; j<this.height/imgheight; j++)
					{
						if(location.distance(i, j) <= this.getAGI())
						{
							matrixbutton[i][j].setVisible(true);
						}
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
						matrixbutton[i][j].setVisible(false);
						matrixbutton[i][j].addMouseListener(layout);
					}
				movestep = 0;
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			System.out.println("right");
			if(movestep == 1)
			{
				list.setBounds(location.getX()*40+40, location.getY()*40, 50, listItems.length * 20);
				list.setVisible(true);
				
				movestep = 3;
			}
		}
	}
	
	// for list selection
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
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		int index = arg0.getFirstIndex();
		System.out.println(listItems[index]);
		list.setVisible(false);
		movestep = 0;
		
	}
}
