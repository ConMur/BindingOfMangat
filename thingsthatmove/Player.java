package thingsthatmove;

import item.Item;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player extends MoveableObject
{
	private int projectile;
	private Item currentItem;
	private boolean movingNorth;
	private boolean movingWest;
	private boolean movingEast;
	private boolean movingSouth;
	private ArrayList<Projectile> currentProjectiles;
	private Thread pThread;
	private boolean isShooting;

	private BufferedImage mangatFront, mangatBack, mangatLeft, mangatRight;
	
	public Player(int dmg, int hp, int speed, int x, int y, Image i,
			Dimension s, int maxHP, int projectile, Item item)
	{
		super(dmg, hp, speed, x, y, i, s, maxHP);
		currentProjectiles = new ArrayList<Projectile>();
		this.projectile = projectile;
		this.currentItem = item;
		boolean movingNorth = false;
		boolean movingWest = false;
		boolean movingEast = false;
		boolean movingSouth = false;


		try {
			mangatFront =ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatfront.png"));
			mangatBack = ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatback.png"));
			mangatLeft =  ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatleft.png"));
			mangatRight = ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatright.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		pThread = new Thread(new ProjectileFiringThread());
		pThread.start();
	}
	

	private class ProjectileFiringThread implements Runnable {
		public void run()
		{
			while (true)
			{
				for (int p = 0 ; p < currentProjectiles.size() ; p ++)
				{
					
				}
			}
			
		}
	}

	public ArrayList<Projectile> getAllPlayerProjectiles()
	{
		return currentProjectiles;
	}
	
	public void setCurrentProjectiles(ArrayList<Projectile> p)
	{
		currentProjectiles = p;
	}
	
	/**
	 * Sets the projectile number of the player
	 * @param projectileNumber the projectile number
	 */
	public void setProjectile(int projectileNumber)
	{
		projectile = projectileNumber;
	}
	
	public void shootProjectile(char direction)
	{
		Projectile p = new Projectile(getProjectile(), direction);
		p.setX(getX());
		p.setY(getY());
		currentProjectiles.add(p);
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
		g.drawImage(getImage(), (int)getX(), (int)getY(), null);
		
		for (Projectile p : currentProjectiles)
			g.drawImage(p.getImage(), (int)p.getX(), (int)p.getY(), null);
	}

	public void keyPressed(int key)
	{
		if (key == KeyEvent.VK_W)
		{
			if (!movingNorth)
			{
					setImage(mangatBack);
			}
			movingNorth = true;
		}
		else if (key == KeyEvent.VK_A)
		{
			if (!movingWest)
			{
					setImage(mangatLeft);
			}
			movingWest = true;
		}
		else if (key == KeyEvent.VK_S)
		{
			if (!movingSouth)
			{
					setImage(mangatFront);
			}
			movingSouth = true;
		}
		else if (key == KeyEvent.VK_D)
		{
			if (!movingEast)
			{
					setImage(mangatRight);
			}
			movingEast = true;
		}

		if (key == KeyEvent.VK_SPACE)
		{
			isShooting = true;
			if (movingNorth && movingEast)
				shootProjectile('1');
			else if (movingSouth && movingEast)
				shootProjectile('2');
			else if (movingSouth && movingWest)
				shootProjectile('3');
			else if (movingNorth && movingWest)
				shootProjectile('4');
			else if (movingNorth)
				shootProjectile('N');
			else if (movingEast)
				shootProjectile('E');
			else if (movingSouth)
				shootProjectile('S');
			else if (movingWest)
				shootProjectile('W');
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
