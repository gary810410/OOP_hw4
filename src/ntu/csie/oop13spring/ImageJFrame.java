package ntu.csie.oop13spring;

//import java.awt.*;
//import java.awt.Event.*;
import javax.swing.*;

public class ImageJFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11111;

	private Icon icon;
	private JLabel background;
	
	public static void main(String[] args)
	{
		new ImageJFrame("test", "grass.png");
	}
	
	public ImageJFrame(String title, String imgpath)
	{
		super(title);
		icon = new ImageIcon(imgpath);
		background = new JLabel(icon);
		this.setSize(850,650);
		this.add(background);
		//this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public JLabel getBackGround()
	{
		return background;
	}
}
