package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class GameObject
{
	private Dimension size;
	private int xPos, yPos;
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
	
	public Rectangle2D getHitBox()
	{
		return new Rectangle (xPos, yPos, (int)size.getWidth(), (int)size.getHeight());
	}
	
	public int getX ()
	{
		return xPos;
	}
	
	public int getY ()
	{
		return yPos;
	}
	
	public void setX (int x)
	{
		xPos = x;
	}
	
	public void setY (int y)
	{
		yPos = y;
	}
	
	
}
