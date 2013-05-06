package ntu.csie.oop13spring;

import javax.swing.*;

public class ImageButton{
	

	private ImageIcon icon;
	private JButton object;
	
	public ImageButton(String imgpath)
	{
		TransparentIcon TIcon = new TransparentIcon(imgpath);
		icon = TIcon.getIcon();
		object = new JButton(icon);
		object.setOpaque(false);
		object.setContentAreaFilled(false);
	}
	public JButton getButton()
	{
		return object;
	}
}