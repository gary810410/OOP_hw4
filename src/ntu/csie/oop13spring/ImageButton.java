package ntu.csie.oop13spring;


import javax.swing.*;

public class ImageButton{
	

	private ImageIcon icon;
	private JButton object;
	
	public ImageButton(String imgpath)
	{
		icon = new ImageIcon(imgpath);
		object = new JButton(icon);
	}
	public JButton getButton()
	{
		return object;
	}
	
}
