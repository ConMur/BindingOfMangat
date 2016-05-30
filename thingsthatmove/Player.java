package thingsthatmove;
import item.Item;
import java.awt.Dimension;
import java.awt.Image;

public class Player extends MoveableObject
{
	private int projectile; 
	private Item currentItem;
	
	public Player (int dmg, int hp, int speed, int x, int y, Image i, Dimension s, int maxHP, int projectile, Item item)
	{
		super(dmg, hp, speed, x, y, i, s, maxHP);
		this.projectile = projectile;
		this.currentItem = item;
	}
	
	/**
	 * Sets the projectile number of the player
	 * @param projectileNumber the projectile number
	 */
	public void setProjectile (int projectileNumber)
	{
		projectile = projectileNumber;
	}
	
	/**
	 * Returns the projectile type
	 * @return the projectile number
	 */
	public int getProjectile ()
	{
		return projectile;
	}
	
	/**
	 * 
	 * @return the current item in possession
	 */
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
	
	public void useItem ()
	{
		String itemName = currentItem.getName();
		this.setItem(null);
		
		// FINISH ITEMS
	}
	

}
