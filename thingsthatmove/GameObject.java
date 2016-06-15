package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * The super class for any object that is in the game. A GameObject has an x and y position, a hitbox and
 * an image
 * @author Matthew Sun, Connor Murphy
 */
public class GameObject
{
	private Dimension size, shadowSize;
	private int xShadow, yShadow;
	private double xPos, yPos;
	private Image image;

	/**
	 * Creates a new game object with the given values
	 * @param x the x position of this game object
	 * @param y the x position of this game object
	 * @param image the visual representation of this game object
	 * @param size the size of this game object
	 * @param shadowSize the size of this game object's shadow
	 * @param xShadow the x coordinate of this game object's shadow
     * @param yShadow the y coordinate of this game object's shadow
     */
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

	/**
	 * Returns the size of this game object's shadow
	 * @return the size of this game object's shadow
     */
	public Dimension getShadowSize()
	{
		return shadowSize;
	}

	/**
	 * Sets the size of this game object's shadow
	 * @param d the new size of this game object's shadow
     */
	public void setShadowSize (Dimension d)
	{
		shadowSize = d;
	}

	/**
	 * Returns the x position of this game object's shadow
	 * @return the x position of this game object's shadow
     */
	public int getXShadow ()
	{
		return xShadow;
	}

	/**
	 * Returns the y position of this game object's shadow
	 * @return the y position of this game object's shadow
	 */
	public int getYShadow()
	{
		return yShadow;
	}

	/**
	 * Sets the x position of this game object's shadow
	 * @param x the new position of this game object's shadow
     */
	public void setXShadow (int x)
	{
		xShadow = x;
	}

	/**
	 * Sets the y position of this game object's shadow
	 * @param y the new position of this game object's shadow
	 */
	public void setYShadow (int y)
	{
		yShadow = y;
	}

	/**
	 * Sets the visual representation of this game object
	 * @param newImage the new image for this game object
     */
	public void setImage (Image newImage)
	{
		image = newImage;
	}

	/**
	 * Returns the image of this game object
	 * @return the image this game object
     */
	public Image getImage ()
	{
		return image;
	}

	/**
	 * Sets the size of this game object
	 * @param d the new size of this game object
     */
	public void setSize (Dimension d)
	{
		size = d;
	}

	/**
	 * Returns the size of this game object
	 * @return the size of this game object
     */
	public Dimension getSize ()
	{
		return size;
	}

	/**
	 * Returns the hitbox for this game object
	 * @return the hitbox for this game object
     */
	public Rectangle getHitBox()
	{
		return new Rectangle ((int)xPos, (int)yPos + (int)(size.getHeight()), (int)size.getWidth(), (int)size.getHeight());
	}

	/**
	 * Return the hitbox of a rock
	 * @return the hitbox of a rock
     */
	public Rectangle getRockHitBox()
	{
		return new Rectangle ((int)xPos, (int)yPos, (int)size.getWidth(), (int)size.getHeight());
	}

	/**
	 * Returns the hitbox of this game object's shadow
	 * @return the hitbox of this game object's shadow
     */
	public Rectangle getShadowHitbox()
	{
		return new Rectangle ((int)xPos + xShadow, (int)yPos + yShadow, (int)shadowSize.getWidth(), (int)shadowSize.getHeight());
	}

	/**
	 * Returns the x position of this game object
	 * @return the x position of this game object
     */
	public double getX ()
	{
		return xPos;
	}

	/**
	 * Returns the y position of this game object
	 * @return the y position of this game object
     */
	public double getY ()
	{
		return yPos;
	}

	/**
	 * Sets the x position of this game object
	 * @param x the new x position of this game object
     */
	public void setX (double x)
	{
		xPos = x;
	}

	/**
	 * Sets the y position of this game object
	 * @param y the new y position of this game object
     */
	public void setY (double y)
	{
		yPos = y;
	}
	
	
}
