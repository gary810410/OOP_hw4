package ntu.csie.oop13spring;


//import java.awt.Event.*;
import javax.swing.*;

public class ImageJFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11111;

	private Icon icon;
	private JLayeredPane background;
	private JLabel backgroundimg;
	
	public static void main(String[] args)
	{
		ImageJFrame x = new ImageJFrame("test", "grass.png");
		x.setVisible(true);
	}
	
	public ImageJFrame(String title, String imgpath)
	{
		super(title);
		icon = new ImageIcon(imgpath);
		backgroundimg = new JLabel(icon);
		backgroundimg.setSize(800,600);
		background = new JLayeredPane();
		background.add(backgroundimg, JLayeredPane.DEFAULT_LAYER);
		background.setSize(800,600);
		this.setSize(850,650);
		this.add(background);
		//this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public JLayeredPane getBackGround()
	{
		return background;
	}
}
