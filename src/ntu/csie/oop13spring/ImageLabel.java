package ntu.csie.oop13spring;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLabel extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1111;
	private ImageIcon icon;
	private JLabel object;
	
	public ImageLabel(String imgpath)
	{
		icon = new ImageIcon(imgpath);
		object = new JLabel(icon);
	}
	public JLabel getLabel()
	{
		return object;
	}
}
