package levels;

import item.Item;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import thingsthatmove.GameObject;
import thingsthatmove.Player;
import util.Util;

public class Room
{
	private ArrayList<Enemy>	enemies = new ArrayList<Enemy>();
	private ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<GameObject> roomObjects = new ArrayList<GameObject>();
	private Thread moveEnemies = new Thread(new EnemyMovementThread());
	private Player player;
	private Image background;
	private Room north, east, south, west;
	private boolean northOpen, southOpen, eastOpen, westOpen;

	private boolean inRoom;
	
	// The room bounds (CHANGE)
	private final int LOWER_X_BOUND = 0;
	private final int UPPER_X_BOUND = 0;
	private final int LOWER_Y_BOUND = 0;
	private final int UPPER_Y_BOUND = 0;

	public Room (ArrayList<Enemy> e, ArrayList<Item> i, ArrayList<GameObject> go, Player p, Room north, Room east, Room south, Room west)
	{
		this.enemies = e;
		this.items = i;
		this.roomObjects = go;
		this.player = p;
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
		
		try {
			background = ImageIO.read(getClass().getResource("/images/emptyroom.png"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public Room (ArrayList<Enemy> e, ArrayList<Item> i, ArrayList<GameObject> go, Player p)
	{
		this.enemies = e;
		this.roomObjects = go;
		this.items = i;
		this.player = p;
		
		try {
			background = ImageIO.read(getClass().getResource("/images/emptyroom.png"));
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
	
	public boolean isNorthOpen()
	{
		return northOpen;
	}

	public boolean isSouthOpen()
	{
		return southOpen;
	}
	
	public boolean isEastOpen()
	{
		return eastOpen;
	}
	
	public boolean isWestOpen()
	{
		return westOpen;
	}
	
	public void updateDoorStatus()
	{
		// Keep all doors closed if there are enemies
		if (hasEnemies())
		{
			northOpen = false;
			southOpen = false;
			eastOpen = false;
			westOpen = false;
		}
		// No enemies, open all doors
		else
		{
			northOpen = true;
			southOpen = true;
			eastOpen = true;
			westOpen = true;
		}
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void setPlayer(Player p)
	{
		this.player = p;
	}
	
	public void removePlayer()
	{
		this.player = null;
	}
	
	public boolean hasPlayer()
	{
		if (player == null)
			return false;
		return true;
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
	
	public void addEnemies(ArrayList<Enemy> e)
	{
		enemies = e;
	}
	
	public void removeEnemies()
	{
		enemies = null;
	}
	
	public boolean hasEnemies()
	{
		if (enemies == null)
			return false;
		return true;
	}

	public ArrayList<Item> getItems()
	{
		return items;
	}
	
	public void addItems(ArrayList<Item> i)
	{
		items = i;
	}
	
	public void removeItems()
	{
		items = null;
	}
	
	public boolean hasItems()
	{
		if (items == null)
			return false;
		return true;
	}
	
	public ArrayList<GameObject> getRoomObjects()
	{
		return roomObjects;
	}
	
	public void addRoomObjects(ArrayList<GameObject> go)
	{
		roomObjects = go;
	}
	
	public void removeRoomObjects()
	{
		roomObjects = null;
	}
	
	public boolean hasRoomObjects()
	{
		if (roomObjects == null)
			return false;
		return true;
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
		inRoom = true;
		moveEnemies.run();
	}
	
	public void endRoom()
	{
		inRoom = false;
	}
	
	public void updateHitboxes()
	{
		// Remove all current hitboxes
		for (int n = 0 ; n < hitboxes.size() ; n ++)
			hitboxes.remove(n);
		
		// Add all enemy hitboxes
		for (Enemy e : enemies)
			hitboxes.add(e.getHitBox());
		
		// Add all item hitboxes
		for (Item i : items)
			hitboxes.add(i.getHitBox());
	}
	
	public boolean enemyCollision (int enemyIndex, Rectangle enemyHitbox)
	{
		for (int i = 0 ; i < enemyIndex ; i ++)
		{
			if (enemyHitbox.intersects(hitboxes.get(i)));
				return true;
		}
		
		for (int j = enemyIndex + 1 ; j < hitboxes.size() ; j ++)
		{
			if (enemyHitbox.intersects(hitboxes.get(j)));
				return true;
		}
		return false;
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
			while (inRoom)
			{
				for (int n = 0 ; n < enemies.size() ; n ++)
				{
					// Each enemy can go four directions (N,E,S,W)
					// Enemies decide on which direction to take on a random basis
					// Enemies want to (more likely) to continue moving in a direction, until they hit another game object
					// After hitting another game object they will decide another direction on a random basis
					// When enemies are within aggro-range follow players
					// Enemies cannot overlap other game objects
					Enemy currentEnemy = enemies.get(n);

					if (!currentEnemy.isAlive())
						enemies.remove(n);
					// Only move moveable enemies
					else if (currentEnemy.canMove())
					{
						int direction;
						Random r = new Random();
						// Enemy is of the passive type
				
						// No initial direction
						if (currentEnemy.getDirection() == ' ')
						{
							// Get and assign a random direction
							direction = r.nextInt(4);
							if (direction == 0)
								currentEnemy.setDirection('N');
							else if (direction == 1)
								currentEnemy.setDirection('E');
							else if (direction == 2)
								currentEnemy.setDirection('S');
							else
								currentEnemy.setDirection('W');
						}
						// Already moving in a direction
						else
						{
							// Set a 20% chance to change direction
							if (r.nextInt(100) >= 80)
							{
								// Get and assign a random direction
								direction = r.nextInt(4);
								if (direction == 0)
									currentEnemy.setDirection('N');
								else if (direction == 1)
									currentEnemy.setDirection('E');
								else if (direction == 2)
									currentEnemy.setDirection('S');
								else
									currentEnemy.setDirection('W');
							}
						}
						
						// Keep track of old coordinates
						int oldX = currentEnemy.getX();
						int oldY = currentEnemy.getY();
						// Enemy is of the aggresive type
						if (currentEnemy.isAngry())
						{
							int enemyWidth = (int) currentEnemy.getSize().getWidth();
							int enemyHeight = (int) currentEnemy.getSize().getHeight();
							// Make an aggro rectangle for range
							// Aggro range is in a 5 * enemy width by 5 * enemy height box with the enemy in the centre
							Rectangle aggroBox = new Rectangle (oldX - enemyWidth * 2, oldY - enemyHeight * 2, enemyWidth * 5, enemyHeight * 5);
							// Player is within aggro range
							if (aggroBox.intersects(player.getHitBox()))
							{
								// Change direction to run at the player AGGRESIVELY
								if (player.getX() == currentEnemy.getX())
								{
									if (player.getY() > currentEnemy.getY()) 
											currentEnemy.setDirection('S');
									else
										currentEnemy.setDirection('N');
								}
								else if (player.getX() > currentEnemy.getX())
									currentEnemy.setDirection('E');
								else
									currentEnemy.setDirection('W');
							}
						}
						// Move the enemy in its direction
						currentEnemy.moveInDirection();
						
						// There is a collision with another ENEMY or ITEM (not player) so move back
						if (enemyCollision(n, currentEnemy.getHitBox()))
							currentEnemy.move(oldX, oldY);
					}
				}
	   		   try {
    			   Thread.sleep(20);
    		   }catch (Exception exc) {}
			}
		}
		
	}
}
