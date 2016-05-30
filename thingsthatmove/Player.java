package thingsthatmove;
import item.Item;
import java.awt.Dimension;
import java.awt.Image;

public class Player extends MoveableObject
{
	private int dmg, hp, speed;
	private int projectile; 
	private Item currentItem;
	private int xPos, yPos;
	private Image image;
	private Dimension size;
	
	
	public Player (int dmg, int hp, int speed, int x, int y, Image i, Dimension s)
	{
		super(dmg, hp, speed, x, y, i, s);
	}
	
	/**
	 * Sets the projectile number of the player
	 * @param projectileNumber the projectile number
	 */
	public void setProjectile (int projectileNumber)
	{
		projectile = projectileNumber;
	}
	
	public Item getCurrentItem ()
	{
		return currentItem;
	}
	
	public String getCurrentItemName()
	{
		return currentItem.getName();
	}
	
	public void setItem (Item i)
	{
		currentItem = i;
	}
	
	public boolean isAlive ()
	{
		if (hp <=0)
			return false;
		return true;
	}
}
