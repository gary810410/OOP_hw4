package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.MatteBorder;

public class Status_control {
	
	private JLabel Base, HP, MP, AGI;
	private JLabel total_HP, total_MP;
	private int total_hp, total_mp, total_agi;
	private int current_hp, current_mp, current_agi;
	private static final int width = 80;
	private static final int height = 11;
	private Font font;
	
	public static final Color greenColor = new Color(0, 255 ,0);
	public static final MatteBorder greenBorder = new MatteBorder(1,1,1,1,greenColor);
	public static final Color blueColor = new Color(0, 0, 255);
	public static final MatteBorder blueBorder = new MatteBorder(1,1,1,1,blueColor);
	
	public Status_control(int hp, int mp, int agi, JLayeredPane background)
	{
		
		total_hp = hp; total_mp= mp; total_agi = agi;
		current_hp = hp; current_mp = mp; current_agi = agi;
		Base = new JLabel(); Base.setBounds(0, 0, width, height*3);
		
		HP = new JLabel(); HP.setBounds(0, 0, width, height);
		set_label_property(HP, new Color(0,0,0), new Color(0,160,0));
		HP.setOpaque(true);
		MP = new JLabel(); MP.setBounds(0, height, width, height);
		set_label_property(MP, new Color(0,0,0), new Color(0,0,160));
		MP.setOpaque(true);
		
		AGI = new JLabel(); AGI.setBounds(0, height*2, width, height);
		set_label_property(AGI, Color.white, Color.black);
		AGI.setOpaque(true);
		AGI.setText(current_agi +"/" + total_agi);

		total_HP = new JLabel(); total_HP.setBounds(0, 0, width, height);
		set_label_property(total_HP, Color.white, Color.black);
		total_HP.setBorder(greenBorder);
		total_HP.setText(current_hp + "/" + total_hp);
		total_MP = new JLabel(); total_MP.setBounds(0, height, width, height);
		set_label_property(total_MP, Color.white, Color.black);
		total_MP.setBorder(blueBorder);;
		total_MP.setText(current_mp + "/" + total_mp);
		
		Base.setLayout(null);
		Base.add(total_HP);
		Base.add(total_MP);
		Base.add(HP);
		Base.add(MP);
		Base.add(AGI);
		Base.setVisible(false);
		background.add(Base, JLayeredPane.DRAG_LAYER);
	}
	public JLabel getStatus()
	{
		return Base;
	}
	public void set_status(int hp, int mp, int agi)
	{
		current_hp = hp; current_mp = mp; current_agi = agi;
		HP.setBounds(0, 0, width*current_hp/total_hp, height);
		MP.setBounds(0, height, width*current_mp/total_mp, height);
		AGI.setBounds(0, height*2, width, height);
		total_HP.setText(current_hp + "/" + total_hp);
		total_MP.setText(current_mp + "/" + total_mp);
		AGI.setText(current_agi + "/" + total_agi);
	}
	public void set_location(int x, int y)
	{
		Base.setBounds(x, y, width, height*3);
	}
	public void set_label_property(JLabel target, Color Fc, Color Bc)
	{
		font = target.getFont();
		Font labelFont = font.deriveFont((float) 10.0);
		target.setFont(labelFont);
		target.setForeground(Fc);
		target.setBackground(Bc);
		target.setHorizontalAlignment(JLabel.CENTER);
		target.setVerticalAlignment(JLabel.CENTER);
	}
	public static void main(String argv[])
	{
		ImageJFrame mainFrame = new ImageJFrame("battle game", "grass.png");
		JLayeredPane background = mainFrame.getBackGround();
		background.setLayout(null);
		Status_control c = new Status_control(5,5,2,background);
		c.set_location(100, 100);
		JLabel l = c.getStatus();
		l.setVisible(true);
		c.set_status(4,4,4);
		mainFrame.setVisible(true);
	}
}
