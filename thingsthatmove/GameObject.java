package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class GameObject
{
	private Dimension size, shadowSize;
	private int xShadow, yShadow;
	private double xPos, yPos;
	private Image image;
	
	public GameObject (int x, int y, Image image, Dimension size, Dimension shadowSize, int xShadow, int yShadow)
	{
		this.xPos = x;
		this.yPos = y;
		this.image = image;
		this.size = size;
		this.shadowSize = shadowSize;
		this.xShadow = xShadow;
		this.yShadow = yShadow;
	}
	
	public Dimension getShadowSize()
	{
		return shadowSize;
	}
	
	public void setShadowSize (Dimension d)
	{
		shadowSize = d;
	}
	
	public int getXShadow ()
	{
		return xShadow;
	}
	
	public int getYShadow()
	{
		return yShadow;
	}
	
	public void setXShadow (int x)
	{
		xShadow = x;
	}
	
	public void setYShadow (int y)
	{
		yShadow = y;
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
		return new Rectangle ((int)xPos, (int)yPos + (int)(size.getHeight()), (int)size.getWidth(), (int)size.getHeight());
	}
	
	public Rectangle getRockHitBox()
	{
		return new Rectangle ((int)xPos, (int)yPos, (int)size.getWidth(), (int)size.getHeight());
	}
	
	public Rectangle getShadowHitbox()
	{
		return new Rectangle ((int)xPos + xShadow, (int)yPos + yShadow, (int)shadowSize.getWidth(), (int)shadowSize.getHeight());
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
