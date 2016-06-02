package thingsthatmove;

import item.Item;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Player extends MoveableObject
{
	private int projectile;
	private Item currentItem;
	private boolean movingNorth;
	private boolean movingWest;
	private boolean movingEast;
	private boolean movingSouth;

	public Player(int dmg, int hp, int speed, int x, int y, Image i,
			Dimension s, int maxHP, int projectile, Item item)
	{
		super(dmg, hp, speed, x, y, i, s, maxHP);
		this.projectile = projectile;
		this.currentItem = item;
		boolean movingNorth = false;
		boolean movingWest = false;
		boolean movingEast = false;
		boolean movingSouth = false;
	}

	/**
	 * Sets the projectile number of the player
	 * @param projectileNumber the projectile number
	 */
	public void setProjectile(int projectileNumber)
	{
		projectile = projectileNumber;
	}

	/**
	 * Returns the projectile type
	 * @return the projectile number
	 */
	public int getProjectile()
	{
		return projectile;
	}

	/**
	 * 
	 * @return the current item in possession
	 */
	public Item getCurrentItem()
	{
		return currentItem;
	}

	public String getCurrentItemName()
	{
		return currentItem.getName();
	}

	public void setItem(Item i)
	{
		currentItem = i;
	}

	public void useItem()
	{
		String itemName = currentItem.getName();
		this.setItem(null);

		// FINISH ITEMS
	}

	public void updatePosition()
	{
		if (movingNorth)
			moveNorth();
		else if (movingSouth)
			moveSouth();
		else if (movingWest)
			moveWest();
		else if (movingEast)
			moveEast();
	}

	public void draw(Graphics g)
	{
		g.drawImage(getImage(), getX(), getY(), null);
	}

	public void keyPressed(int key)
	{
		if (key == KeyEvent.VK_W)
		{
			movingNorth = true;
			movingSouth = false;
			movingWest = false;
			movingEast = false;
		}
		else if (key == KeyEvent.VK_A)
		{
			movingNorth = false;
			movingSouth = false;
			movingWest = true;
			movingEast = false;
		}
		else if (key == KeyEvent.VK_S)
		{
			movingNorth = false;
			movingSouth = true;
			movingWest = false;
			movingEast = false;
		}
		else if (key == KeyEvent.VK_D)
		{
			movingNorth = false;
			movingSouth = false;
			movingWest = false;
			movingEast = true;
		}
	}

	public void keyReleased(int key)
	{
		if (key == KeyEvent.VK_W)
			movingNorth = false;
		else if (key == KeyEvent.VK_A)
			movingWest = false;
		else if (key == KeyEvent.VK_S)
			movingSouth = false;
		else if (key == KeyEvent.VK_D)
			movingEast = false;
	}

}
