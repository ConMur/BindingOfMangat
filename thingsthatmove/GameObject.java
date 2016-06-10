package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class GameObject
{
	private Dimension size;
	private double xPos, yPos;
	private Image image;
	
	public GameObject (int x, int y, Image image, Dimension size)
	{
		this.xPos = x;
		this.yPos = y;
		this.image = image;
		this.size = size;
	}
	
	public void setImage (Image newImage)
	{
		image = newImage;
	}
	
	public Image getImage ()
	{
		return image;
	}
	
	public void setSize (Dimension d)
	{
		size = d;
	}
	
	public Dimension getSize ()
	{
		return size;
	}
	
	public Rectangle getHitBox()
	{
		return new Rectangle ((int)xPos, (int)yPos + (int)(size.getHeight() * 0.5), (int)size.getWidth(), (int)size.getHeight());
	}
	
	public Rectangle getRockHitBox()
	{
		return new Rectangle ((int)xPos, (int)yPos, (int)size.getWidth(), (int)size.getHeight());
	}
	
	public double getX ()
	{
		return xPos;
	}
	
	public double getY ()
	{
		return yPos;
	}
	
	public void setX (double x)
	{
		xPos = x;
	}
	
	public void setY (double y)
	{
		yPos = y;
	}
	
	
}
