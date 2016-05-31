package levels;

import item.Item;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import thingsthatmove.Player;

public class Room
{
	private ArrayList<Enemy> enemies;
	private ArrayList<Item> items;
	private Player player;
	private Image background;
	private Room north, east, south, west;
	
	// The room bounds
	private final int LOWER_X_BOUND = 0;
	private final int UPPER_X_BOUND = 0;
	private final int LOWER_Y_BOUND = 0;
	private final int UPPER_Y_BOUND = 0;

	
	public Room (ArrayList<Enemy> e, ArrayList<Item> i, Player p, Room north, Room east, Room south, Room west)
	{
		this.enemies = e;
		this.items = i;
		this.player = p;
		
		try {
			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public Room (ArrayList<Enemy> e, ArrayList<Item> i, Player p)
	{
		this.enemies = e;
		this.items = i;
		this.player = p;
		
		try {
			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public Room (Room north, Room east, Room south, Room west)
	{
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
		
		try {
			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public boolean setNorth (Room r)
	{
		if (north != null)
			return false;
		north = r;
		return true;
	}
	
	public boolean setSouth (Room r)
	{
		if (south != null)
			return false;
		south = r;
		return true;
	}
	
	public boolean setWest (Room r)
	{
		if (west != null)
			return false;
		west = r;
		return true;
	}
	
	public boolean setEast (Room r)
	{
		if (east != null)
			return false;
		east = r;
		return true;
	}
	
	public Room getNorth()
	{
		return north;
	}
	
	public Room getSouth()
	{
		return south;
	}
	
	public Room getWest()
	{
		return west;
	}
	
	public Room getEast()
	{
		return east;
	}
	
	public boolean isFull()
	{
		if (north != null && south != null && west != null && east != null)
			return true;
		return false;
	}
	
	
	public void startRoom ()
	{
		Thread moveEnemies = new Thread(new EnemyMovementThread());
		moveEnemies.run();
	}
	
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		
		for (Enemy currentEnemy : enemies)
			g.drawImage(currentEnemy.getImage(), currentEnemy.getX(), currentEnemy.getY(), null);
		
	}
	
	private class EnemyMovementThread implements Runnable
	{
		public void run()
		{
			while (true)
			{
				for (Enemy currentEnemy : enemies)
				{
					// Each enemy can go four directions (N,E,S,W)
					// Enemies decide on which direction to take on a random basis
					// Enemies want to (more likely) to continue moving in a direction, until they hit another game object
					// After hitting another game object they will decide another direction on a random basis
					// When enemies are within aggro-range follow players
					// Enemies cannot overlap other game objects
					
					if (currentEnemy.getMoveState())
					{
						int playerX = player.getX();
						int playerY = player.getY();
						int direction;
						Random r = new Random();
						
						// No initial direction
						if (currentEnemy.getDirection() == ' ')
						{
							direction = r.nextInt(4);
							
						}
					}
						
					
				}
			}
		}
		
	}
}
