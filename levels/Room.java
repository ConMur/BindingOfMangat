package levels;

import item.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import states.GameStateManager;
import states.State;
import thingsthatmove.*;

/**
 * A Room in the game. It can contain Enemies, Items and other GameObjects and
 * has a rock pattern in it. The room manages everything inside it except the
 * player.
 *
 * @author Matthew Sun, Connor Murphy
 */
public class Room
{
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> movementHitboxes = new ArrayList<Rectangle>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<GameObject> roomObjects = new ArrayList<GameObject>();
	private ArrayList<GameObject> roomRocks = new ArrayList<GameObject>();

	private Thread moveEnemies, playerEnemyCollision, enemyProjectile;
	private Player player;
	private Image background, hud;
	private Image northClosedDoor, southClosedDoor, eastClosedDoor,
			westClosedDoor;
	private Image northOpenDoor, southOpenDoor, eastOpenDoor, westOpenDoor;
	private BufferedImage northLockedDoor, southLockedDoor, eastLockedDoor,
			westLockedDoor;
	private BufferedImage northClosedBossDoor, southClosedBossDoor,
			eastClosedBossDoor, westClosedBossDoor;
	private BufferedImage northOpenBossDoor, southOpenBossDoor,
			eastOpenBossDoor, westOpenBossDoor;
	private Room north, east, south, west;
	private boolean northOpen, southOpen, eastOpen, westOpen;
	private boolean atNorthDoor, atSouthDoor, atEastDoor, atWestDoor;

	private boolean inRoom;
	private boolean takenDamage;
	private boolean visited;
	private boolean isLocked;
	private boolean found;

	private boolean pauseRoom;

	// CHANGE THIS FOR DIFFERENT ROOM PATTERNS (1-7 FOR NOW)
	private RockPattern roomPattern;

	// The trap door shown when the player defeats the boss
	private boolean showTrapDoor;
	private BufferedImage trapDoor;

	private Item bossItem;

	// The room bounds
	private final int LOWER_X_BOUND = 130;
	private final int UPPER_X_BOUND = 850;
	private final int LOWER_Y_BOUND = 330;
	private final int UPPER_Y_BOUND = 575;

	private final int NORTH_DOOR_X = 450;
	private final int NORTH_DOOR_Y = 315;
	private final int SOUTH_DOOR_X = 450;
	private final int SOUTH_DOOR_Y = 675;
	private final int EAST_DOOR_X = 920;
	private final int EAST_DOOR_Y = 420;
	private final int WEST_DOOR_X = 120;
	private final int WEST_DOOR_Y = 420;

	private final int ROOM_CENTRE_X = 450;
	private final int ROOM_CENTRE_Y = 495;

	public enum RoomType {
		NORMAL, BOSS, SHOP
	}

	private RoomType roomType;

	/**
	 * Creates the room
	 *
	 * @param e the Enemies in this room
	 * @param i the Items in this room
	 * @param go the various game objects in this room
	 * @param p the player reference
	 * @param locked if the room is a locked room
	 * @param type the type of room this room is
	 * @param pattern the pattern of rocks this room has
	 * @param north the Room the north door connects to
	 * @param east the Room the east door connects to
	 * @param south the Room the south door connects to
	 * @param west the Room the west door connects to
	 */
	public Room(ArrayList<Enemy> e, ArrayList<Item> i,
			ArrayList<GameObject> go, Player p, boolean locked, RoomType type,
			RockPattern pattern,
			Room north, Room east,
			Room south, Room west)
	{
		this.enemies = e;
		this.items = i;
		this.roomObjects = go;
		this.player = p;
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;

		try
		{
			hud = ImageIO.read(getClass().getResource("/images/hud.png"));
			background = ImageIO.read(getClass().getResource(
					"/images/emptyroomSCALED.png"));
			// Closed doors
			northClosedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/closeddoornorth.png"));
			southClosedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/closeddoorsouth.png"));
			eastClosedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/closeddooreast.png"));
			westClosedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/closeddoorwest.png"));
			// Open doors
			northOpenDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/opendoornorth.png"));
			southOpenDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/opendoorsouth.png"));
			eastOpenDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/opendooreast.png"));
			westOpenDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/opendoorwest.png"));

			// Locked doors
			northLockedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/lockeddoornorth.png"));
			southLockedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/lockeddoorsouth.png"));
			eastLockedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/lockeddooreast.png"));
			westLockedDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/lockeddoorwest.png"));

			// Trap door
			trapDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/trapdoor.png"));

			// Boss closed doors
			northClosedBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoorclosednorth.png"));
			southClosedBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoorclosedsouth.png"));
			eastClosedBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoorclosedeast.png"));
			westClosedBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoorclosedwest.png"));

			// Boss open doors
			northOpenBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoornorth.png"));
			southOpenBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoorsouth.png"));
			eastOpenBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdooreast.png"));
			westOpenBossDoor = ImageIO.read(getClass().getResourceAsStream(
					"/images/doors/bossdoorwest.png"));

		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		atNorthDoor = false;
		atSouthDoor = false;
		atEastDoor = false;
		atWestDoor = false;

		takenDamage = false;
		visited = false;
		isLocked = locked;
		found = false;

		showTrapDoor = false;

		this.roomType = type;
		roomPattern = pattern;
		roomRocks = roomPattern.getRocks();

		// If this is a boss room then set the item the boss drops
		if (roomType == RoomType.BOSS)
		{
			bossItem = items.remove(0);
		}

		pauseRoom = false;
	}

	/**
	 * Creates the room
	 *
	 * @param e the Enemies in this room
	 * @param i the Items in this room
	 * @param go the various game objects in this room
	 * @param p the player reference
	 * @param locked if the room is a locked room
	 * @param type the type of room this room is
	 * @param pattern the pattern of rocks this room has
	 */
	public Room(ArrayList<Enemy> e, ArrayList<Item> i,
			ArrayList<GameObject> go, Player p, boolean locked, RoomType type,
			RockPattern pattern)
	{
		this(e, i, go, p, locked, type, pattern, null, null, null, null);
		found = false;
	}

	/**
	 * Creates the room
	 *
	 * @param locked if the room is a locked room
	 * @param type the type of room this room is
	 * @param pattern the pattern of rocks this room has
	 * @param north the Room the north door connects to
	 * @param east the Room the east door connects to
	 * @param south the Room the south door connects to
	 * @param west the Room the west door connects to
	 */
	public Room(boolean locked, RoomType type, RockPattern pattern, Room north,
			Room east,
			Room south, Room west)
	{
		this(null, null, null, null, locked, type, pattern, north, east, south,
				west);
		found = false;
	}

	/**
	 * Returns if the room was found by the player
	 *
	 * @return if the room was found by the player
	 */
	public boolean isFound()
	{
		return found;
	}

	/**
	 * Sets if the room was found by the player
	 *
	 * @param b the boolean for if the player found the room or not
	 */
	public void setFound(boolean b)
	{
		found = b;
	}

	/**
	 * Returns if the north door is open
	 *
	 * @return if the north door is open
	 */
	public boolean isNorthOpen()
	{
		return northOpen;
	}

	/**
	 * Returns if the south door is open
	 *
	 * @return if the south door is open
	 */
	public boolean isSouthOpen()
	{
		return southOpen;
	}

	/**
	 * Returns if the east door is open
	 *
	 * @return if the east door is open
	 */
	public boolean isEastOpen()
	{
		return eastOpen;
	}

	/**
	 * Returns if the west door is open
	 *
	 * @return if the west door is open
	 */
	public boolean isWestOpen()
	{
		return westOpen;
	}

	/**
	 * Sets the rock pattern of this room to the given one
	 *
	 * @param r the rock pattern to set this room to
	 */
	public void setRockPattern(RockPattern r)
	{
		roomPattern = r;
	}

	/**
	 * Returns the rock pattern associated with this room
	 *
	 * @return the rock pattern associated with this room
	 */
	public RockPattern getRockPattern()
	{
		return roomPattern;
	}

	/**
	 * Updates the room
	 */
	public void update()
	{
		if (!pauseRoom)
		{
			player.update(roomRocks);
			checkIfPlayerOnItem();
			updateDoorStatus();
			checkIfPlayerAtDoor();
			checkProjectileCollision();
			checkIfPlayerDead();
		}
		else
		{
			// Resume the game when ridout finishes his cutscene
			if (roomType == RoomType.BOSS)
			{
				for (Enemy e : enemies)
				{
					if (e instanceof Ridout)
					{
						Ridout ridout = (Ridout) e;
						if (ridout.resumeGame())
						{
							pauseRoom = false;
						}
					}
				}
			}
		}
	}

	/**
	 * Checks if the player is standing over an item and picks it up if so. If
	 * the item is a coin, bomb or key those values for the player are updated
	 * and the item is instantly consumed. If the item is anything else, it is
	 * held by the player for later use
	 */
	private void checkIfPlayerOnItem()
	{
		int itemsRemoved = 0;
		for (int i = 0; i < items.size(); ++i)
		{
			Item item = items.get(i - itemsRemoved);

			// If there is an item, give it to the player if they have no other
			// item
			// If the item is a key, coin or bomb, increment the player counts
			// for those items instead
			if (player.getHitBox().intersects(item.getHitBox())
					&& !player.hasItem())
			{
				String itemName = item.getName();
				if (itemName.equals("silvercoin"))
				{
					player.addCoins(5);
				}
				else if (itemName.equals("goldcoin"))
				{
					player.addCoins(10);
				}
				else if (itemName.equals("bomb"))
				{
					player.addBombs(1);
				}
				else if (itemName.equals("key"))
				{
					player.setNumKeys(player.getNumKeys() + 1);
				}
				else if (player.getCurrentItem() == null)
				{
					player.setItem(item);
				}
				items.remove(i - itemsRemoved);
				++itemsRemoved;
			}
		}
	}

	/**
	 * Sets flags to open unlocked doors when there are no enemies in the room
	 * or open locked doors when the player has keys. When there are enemies in
	 * the room, sets the flags to close all doors
	 */
	public void updateDoorStatus()
	{
		// Keep all doors closed if there are enemies
		// TODO: uncomment
		/*
		 * if (hasEnemies()) { northOpen = false; southOpen = false; eastOpen =
		 * false; westOpen = false; }
		 */

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
			if (north != null)
				if (!north.isLocked() || (north.isLocked() && player.hasKeys()))
					northOpen = true;
			if (south != null)
				if (!south.isLocked() || (south.isLocked() && player.hasKeys()))
					southOpen = true;
			if (east != null)
				if (!east.isLocked() || (east.isLocked() && player.hasKeys()))
					eastOpen = true;
			if (west != null)
				if (!west.isLocked() || (west.isLocked() && player.hasKeys()))
					westOpen = true;
		}
	}

	/**
	 * Checks if a player is at one of the doors and sets booleans if it is
	 */
	private void checkIfPlayerAtDoor()
	{
		// Check if player is at a door
		double x = player.getX();
		double y = player.getY();
		// System.out.println("X " + x + " Y " + y);
		atNorthDoor = false;
		atSouthDoor = false;
		atWestDoor = false;
		atEastDoor = false;

		// North door
		if (north != null && northOpen && x > 412 && x < 518 && y < 230)
		{
			atNorthDoor = true;
		}
		else if (south != null && southOpen && x > 412 && x < 518 && y > 600)
		{
			atSouthDoor = true;
		}
		else if (east != null && eastOpen && x > 845 && y > 340 && y < 405)
		{
			atEastDoor = true;
		}
		else if (west != null && westOpen && x < 90 && y > 340 && y < 405)
		{
			atWestDoor = true;
		}

		// Check if at trapDoor
		if (showTrapDoor)
		{
			 if (x > ROOM_CENTRE_X - 30 && x < ROOM_CENTRE_X + 30
	                    && y > ROOM_CENTRE_Y - 60 && y < ROOM_CENTRE_Y)
			{
				endRoom();
				LevelManager.advanceLevel();
			}
		}
	}

	/**
	 * Checks collisions between all the projectiles and the enemies in this
	 * room
	 */
	private void checkProjectileCollision()
	{
		// Get the latest update on the projectiles
		player.updateProjectiles();
		ArrayList<Projectile> projectiles = player.getAllPlayerProjectiles();

		// Go through all projectiles
		for (int p = 0; p < projectiles.size(); p++)
		{
			Projectile pj = projectiles.get(p);
			// Go through all enemies
			for (int e = 0; e < enemies.size(); e++)
			{
				Enemy en = enemies.get(e);
				// Projectile has hit an enemy
				if (pj.getHitBox().intersects(en.getHitBox()))
				{
					en.takeDamage(pj.getDamage());
					pj.killProjectile();
				}
			}
			// Go through all rock objects
			for (int z = 0; z < roomRocks.size(); z++)
			{
				// Projectile has hit a rock
				if (pj.getShadowHitbox().intersects(
						roomRocks.get(z).getRockHitBox()))
					pj.killProjectile();
			}
		}
	}

	/**
	 * Checks if the player is dead and if so, changes to the dead state
	 */
	private void checkIfPlayerDead()
	{
		if (player.getCurrentHP() <= 0)
		{
			endRoom();
			GameStateManager.setState(State.DEAD);
		}
	}

	/**
	 * Returns if the player is at the north door. If the player is at a locked
	 * door with keys, the player has one key removed and the door is unlocked.
	 *
	 * @return if the player is at the north door
	 */
	public boolean isPlayerAtNorthDoor()
	{
		if (atNorthDoor && player.isMovingNorth())
		{
			if (north.isLocked() && player.getNumKeys() > 0)
			{
				player.setNumKeys(player.getNumKeys() - 1);
				north.setLocked(false);
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns if the player is at the south door. If the player is at a locked
	 * door with keys, the player has one key removed and the door is unlocked.
	 *
	 * @return if the player is at the south door
	 */
	public boolean isPlayerAtSouthDoor()
	{
		if (atSouthDoor && player.isMovingSouth())
		{
			if (south.isLocked() && player.getNumKeys() > 0)
			{
				player.setNumKeys(player.getNumKeys() - 1);
				south.setLocked(false);
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns if the player is at the east door. If the player is at a locked
	 * door with keys, the player has one key removed and the door is unlocked.
	 *
	 * @return if the player is at the east door
	 */
	public boolean isPlayerAtEastDoor()
	{
		if (atEastDoor && player.isMovingEast())
		{
			if (east.isLocked() && player.getNumKeys() > 0)
			{
				player.setNumKeys(player.getNumKeys() - 1);
				east.setLocked(false);
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns if the player is at the west door. If the player is at a locked
	 * door with keys, the player has one key removed and the door is unlocked.
	 *
	 * @return if the player is at the west door
	 */
	public boolean isPlayerAtWestDoor()
	{
		if (atWestDoor && player.isMovingWest())
		{
			if (west.isLocked() && player.getNumKeys() > 0)
			{
				player.setNumKeys(player.getNumKeys() - 1);
				west.setLocked(false);
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * Resets all the player at door flags to false
	 */
	public void resetAllDoors()
	{
		atNorthDoor = false;
		atSouthDoor = false;
		atEastDoor = false;
		atWestDoor = false;
	}

	/**
	 * Returns a reference to the player in this room
	 *
	 * @return a reference to the player in this room
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Sets the player in this room
	 *
	 * @param p the player to set for this room
	 */
	public void setPlayer(Player p)
	{
		this.player = p;
	}

	/**
	 * Sets the player in this room to null
	 */
	public void removePlayer()
	{
		this.player = null;
	}

	/**
	 * Returns if this room has a player
	 *
	 * @return if this room has a player
	 */
	public boolean hasPlayer()
	{
		if (player == null)
			return false;
		return true;
	}

	/**
	 * Returns the list of enemies in this room
	 *
	 * @return the list of enemies in this room
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}

	/**
	 * Sets the enemies of this room to the given list of enemies
	 *
	 * @param e the list of enemies to set for this room
	 */
	public void setEnemies(ArrayList<Enemy> e)
	{
		enemies = e;
	}

	/**
	 * Removes the enemies from this room
	 */
	public void removeEnemies()
	{
		enemies = null;
	}

	/**
	 * Returns if this room has enemies
	 *
	 * @return if this room has enemies
	 */
	public boolean hasEnemies()
	{
		if (enemies == null || enemies.size() == 0)
			return false;
		return true;
	}

	/**
	 * Returns the list of items in this room
	 *
	 * @return the list of items in this room
	 */
	public ArrayList<Item> getItems()
	{
		return items;
	}

	/**
	 * Sets the list of items in this room to the given list of items
	 *
	 * @param i the list of items to set this room's list of items to
	 */
	public void setItems(ArrayList<Item> i)
	{
		items = i;
	}

	/**
	 * Removes all items from this room
	 */
	public void removeItems()
	{
		items = null;
	}

	/**
	 * Returns if this room has items
	 *
	 * @return if this room has items
	 */
	public boolean hasItems()
	{
		if (items == null)
			return false;
		return true;
	}

	/**
	 * Returns the list of game objects in this room
	 *
	 * @return the list of game objects in this room
	 */
	public ArrayList<GameObject> getRoomObjects()
	{
		return roomObjects;
	}

	/**
	 * Sets the list of game objects in this room to the given list of items
	 *
	 * @param go the list of game objects to set this room's list of items to
	 */
	public void setRoomObjects(ArrayList<GameObject> go)
	{
		roomObjects = go;
	}

	/**
	 * Removes all the game objects in this room
	 */
	public void removeRoomObjects()
	{
		roomObjects = null;
	}

	/**
	 * Returns if this room has any game objects
	 *
	 * @return if this room has any game objects
	 */
	public boolean hasRoomObjects()
	{
		return roomObjects != null;
	}

	/**
	 * Sets the north room to the given room if possible
	 *
	 * @param r the room to set the north room to
	 * @return if it is possible to set the north room to the given room
	 */
	public boolean setNorth(Room r)
	{
		if (north != null)
			return false;
		north = r;
		return true;
	}

	/**
	 * Sets the south room to the given room if possible
	 *
	 * @param r the room to set the south room to
	 * @return if it is possible to set the south room to the given room
	 */
	public boolean setSouth(Room r)
	{
		if (south != null)
			return false;
		south = r;
		return true;
	}

	/**
	 * Sets the west room to the given room if possible
	 *
	 * @param r the room to set the west room to
	 * @return if it is possible to set the west room to the given room
	 */
	public boolean setWest(Room r)
	{
		if (west != null)
			return false;
		west = r;
		return true;
	}

	/**
	 * Sets the east room to the given room if possible
	 *
	 * @param r the room to set the east room to
	 * @return if it is possible to set the east room to the given room
	 */
	public boolean setEast(Room r)
	{
		if (east != null)
			return false;
		east = r;
		return true;
	}

	/**
	 * Returns the north room to this room
	 *
	 * @return the north room to this room
	 */
	public Room getNorth()
	{
		return north;
	}

	/**
	 * Returns the south room to this room
	 *
	 * @return the south room to this room
	 */
	public Room getSouth()
	{
		return south;
	}

	/**
	 * Returns the west room to this room
	 *
	 * @return the west room to this room
	 */
	public Room getWest()
	{
		return west;
	}

	/**
	 * Returns the east room to this room
	 *
	 * @return the east room to this room
	 */
	public Room getEast()
	{
		return east;
	}

	/**
	 * Returns if this room's north, east, west and south rooms are set or not
	 *
	 * @return if this room's north, east, west and south rooms are set or not
	 */
	public boolean isFull()
	{
		if (north != null && south != null && west != null && east != null)
			return true;
		return false;
	}

	/**
	 * Returns if this room is locked
	 *
	 * @return if this room is locked
	 */
	public boolean isLocked()
	{
		return isLocked;
	}

	/**
	 * Sets whether or not this room is locked
	 *
	 * @param locked whether or not this room is locked
	 */
	public void setLocked(boolean locked)
	{
		isLocked = locked;
	}

	/**
	 * Returns the type of room this room is
	 *
	 * @return the type of room this room is
	 */
	public RoomType getRoomType()
	{
		return roomType;
	}

	/**
	 * Initializes variables and starts threads required by the room
	 */
	public void startRoom()
	{
		updateHitboxes();
		resetAllDoors();
		inRoom = true;
		System.out.println("STARTING ROOM");
		player.takeDamage(0);

		moveEnemies = new Thread(new EnemyMovementThread());
		moveEnemies.start();

		playerEnemyCollision = new Thread(new EnemyPlayerCollisionThread());
		playerEnemyCollision.start();
		//
		// enemyProjectile = new Thread (new EnemyProjectileThread());
		// enemyProjectile.start();
		found = true;
	}

	/**
	 * Releases any resources and ends threads used by the room
	 */
	public void endRoom()
	{
		System.out.println("ENDING ROOM");
		inRoom = false;
		player.clearBombs();
		// Clear all projectiles
		player.clearProjectiles();
	}

	/**
	 * Update the rectangular representation of all the hitboxes in the room to
	 * their new coordinates
	 */
	public void updateHitboxes()
	{
		// Remove all current hitboxes
		hitboxes.clear();

		// Add all enemy hitboxes
		for (int e = 0; e < enemies.size(); e++)
			hitboxes.add(enemies.get(e).getHitBox());

		// Add all item hitboxes
		for (int i = 0; i < items.size(); i++)
			hitboxes.add(items.get(i).getHitBox());

		// Add all rock hitboxes
		for (int r = 0; r < roomRocks.size(); r++)
			hitboxes.add(roomRocks.get(r).getRockHitBox());

		// Update movement hitboxes
		movementHitboxes.clear();
		for (int m = 0; m < enemies.size(); m++)
			movementHitboxes.add(enemies.get(m).getShadowHitbox());
	}

	/**
	 * Returns if the enemy specified by the index intersects with the given
	 * hitbox
	 *
	 * @param enemyIndex the index of the enemy to check
	 * @param enemyHitbox the hitbox to check the enemy against
	 * @return if the enemy and the hitbox collide
	 */
	public boolean enemyCollision(int enemyIndex,
			Rectangle enemyHitbox)
	{
		for (int n = 0; n < hitboxes.size(); n++)
		{

			// System.out.println("HITBOXES: " + hitboxes.size()
			// + " CURRENT INDEX: " + n);
			if (enemyHitbox.intersects(hitboxes.get(n)) && n != enemyIndex)
				return true;

		}
		return false;
	}

	/**
	 * Sorts all objects in the game based on their y position so that shadows
	 * appear normally
	 */
	public void sortAllGameObjects()
	{
		// roomObjects.addAll(enemies);
		// roomObjects.addAll(items);

		// Go through all items, starting at the second
		for (int n = 1; n < enemies.size(); n++)
		{
			// Keep track of the current number
			int currentIndex = n;

			// Traverse backwards until spot is found
			while (currentIndex > 0
					&& enemies.get(n - 1).getY() > enemies.get(n).getY())
			{
				// Swap current with its previous element
				Enemy placeholder = enemies.get(n);
				enemies.set(n, enemies.get(n - 1));
				enemies.set(n - 1, placeholder);

				currentIndex--;
			}
		}
	}

	/**
	 * Draws the room, HUD background, enemes, items, the player and the room's
	 * rock pattern
	 *
	 * @param g the graphics to draw to
	 */
	public void draw(Graphics g)
	{
		if (!pauseRoom)
		{
			g.drawImage(hud, 0, 0, null);
			g.drawImage(background, 0, 198, null);
			g.setColor(Color.RED);

			// g.drawRect(130, 325, 720, 250);

			g.setColor(Color.BLACK);
			// g.drawRect(LOWER_X_BOUND, LOWER_Y_BOUND, UPPER_X_BOUND -
			// LOWER_X_BOUND,
			// UPPER_Y_BOUND - LOWER_Y_BOUND);

			roomPattern.draw(g);

			drawDoors(g);
			sortAllGameObjects();
			for (int e = 0; e < enemies.size(); e++)
			{
				Enemy currentEnemy = enemies.get(e);
				// Rectangle r = currentEnemy.getHitBox();
				g.drawImage(currentEnemy.getImage(), (int) currentEnemy.getX(),
						(int) currentEnemy.getY(), null);
				// g.drawRect((int) r.getX(), (int) r.getY(), (int)
				// r.getWidth(),
				// (int) r.getHeight());

				currentEnemy.draw(g);
			}

			// Collision hitbox
			// g.setColor(Color.RED);
			// for (int h = 0; h < hitboxes.size(); h++)
			// g.drawRect((int) hitboxes.get(h).getX(), (int)
			// hitboxes.get(h).getY(), (int) hitboxes.get(h).getWidth(), (int)
			// hitboxes.get(h).getHeight());
			//
			// // Movement hitbox
			// g.setColor(Color.GREEN);
			// for (int h = 0; h < movementHitboxes.size(); h++)
			// g.drawRect((int) movementHitboxes.get(h).getX(), (int)
			// movementHitboxes.get(h).getY(), (int)
			// movementHitboxes.get(h).getWidth(), (int)
			// movementHitboxes.get(h).getHeight());

			for (int i = 0; i < items.size(); i++)
			{
				items.get(i).draw(g);
			}

			// for (GameObject currentRoomObject : roomObjects)
			// {
			// g.drawImage(currentRoomObject.getImage(),
			// (int) currentRoomObject.getX(),
			// (int) currentRoomObject.getY(), null);
			// }
			//

			player.draw(g);
		}
		else
		{
			// Draw the ridout cutscenes
			for (Enemy e : enemies)
			{
				if (e instanceof Ridout)
				{
					Ridout ridout = (Ridout) e;
					ridout.draw(g);
				}
			}
		}
	}

	/**
	 * Draws the doors of the room to the given graphics
	 *
	 * @param g the graphics to draw to
	 */
	private void drawDoors(Graphics g)
	{
		// Draw doors
		if (north != null)
		{
			if (north.getRoomType() == RoomType.BOSS)
				if (northOpen)
					g.drawImage(northOpenBossDoor, NORTH_DOOR_X, NORTH_DOOR_Y,
							null);
				else
					g.drawImage(northClosedBossDoor, NORTH_DOOR_X,
							NORTH_DOOR_Y, null);
			else if (northOpen && !north.isLocked())
				g.drawImage(northOpenDoor, NORTH_DOOR_X, NORTH_DOOR_Y - 100,
						null);
			else if (north.isLocked())
				g.drawImage(northLockedDoor, NORTH_DOOR_X, NORTH_DOOR_Y - 100,
						null);
			else
				g.drawImage(northClosedDoor, NORTH_DOOR_X, NORTH_DOOR_Y - 100,
						null);
		}
		if (south != null)
		{
			if (south.getRoomType() == RoomType.BOSS)
				if (southOpen)
					g.drawImage(southOpenBossDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y,
							null);
				else
					g.drawImage(southClosedBossDoor, SOUTH_DOOR_X,
							SOUTH_DOOR_Y, null);
			else if (southOpen && !south.isLocked())
				g.drawImage(southOpenDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y, null);
			else if (south.isLocked())
				g.drawImage(southLockedDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y, null);
			else
				g.drawImage(southClosedDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y, null);
		}
		if (east != null)
		{
			if (east.getRoomType() == RoomType.BOSS)
				if (eastOpen)
					g.drawImage(eastOpenBossDoor, EAST_DOOR_X, EAST_DOOR_Y,
							null);
				else
					g.drawImage(eastClosedBossDoor, EAST_DOOR_X, EAST_DOOR_Y,
							null);
			else if (eastOpen && !east.isLocked())
				g.drawImage(eastOpenDoor, EAST_DOOR_X, EAST_DOOR_Y, null);
			else if (east.isLocked())
				g.drawImage(eastLockedDoor, EAST_DOOR_X, EAST_DOOR_Y, null);
			else
				g.drawImage(eastClosedDoor, EAST_DOOR_X, EAST_DOOR_Y, null);
		}
		if (west != null)
		{
			if (west.getRoomType() == RoomType.BOSS)
				if (westOpen)
					g.drawImage(westOpenBossDoor, WEST_DOOR_X - 100,
							WEST_DOOR_Y, null);
				else
					g.drawImage(westClosedBossDoor, WEST_DOOR_X - 100,
							WEST_DOOR_Y, null);
			else if (westOpen && !west.isLocked())
				g.drawImage(westOpenDoor, WEST_DOOR_X - 100, WEST_DOOR_Y, null);
			else if (west.isLocked())
				g.drawImage(westLockedDoor, WEST_DOOR_X - 100, WEST_DOOR_Y,
						null);
			else
				g.drawImage(westClosedDoor, WEST_DOOR_X - 100, WEST_DOOR_Y,
						null);
		}

		if (showTrapDoor)
		{
			g.drawImage(trapDoor, ROOM_CENTRE_X, ROOM_CENTRE_Y, null);
		}
	}

	/**
	 * Returns if this room was visited by the player
	 *
	 * @return if the room was visited by the player
	 */
	public boolean isVisited()
	{
		return visited;
	}

	/**
	 * Sets if this room was visited by the player
	 *
	 * @param visited sets if this room was visited by the player
	 */
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}

	/**
	 * Removes all dead enemies from the room. If the enemy was a boss, a trap
	 * door is opened and the boss drops an item
	 */
	public void killDeadEnemies()
	{
		int enemiesRemoved = 0;
		for (int n = 0; n < enemies.size(); n++)
		{
			Enemy e = enemies.get(n - enemiesRemoved);
			if (!e.isAlive())
			{
				if (e instanceof Ridout)
				{
					Ridout ridout = (Ridout) e;
					synchronized (ridout)
					{
						if (!ridout.hasUpgraded())
						{
							ridout.loseLife();
							pauseRoom = true;
						}
						else
						{
							enemies.remove(n - enemiesRemoved);
							++enemiesRemoved;
							showTrapDoor = true;
							System.out.println("removed enemy");
						}
					}
				}
				else
				{
					enemies.remove(n - enemiesRemoved);
					if (roomType == RoomType.BOSS && !hasEnemies())
					{
						showTrapDoor = true;
						items.add(bossItem);
					}
					enemiesRemoved++;
					System.out.println("removed enemy");
				}
			}
		}
	}

	/**
	 * A thread that deals with the player collisions that an enemy has
	 *
	 * @author Matthew Sun
	 */
	private class EnemyPlayerCollisionThread implements Runnable
	{
		public void run()
		{
			while (inRoom)
			{
				if (!pauseRoom)
				{
					killDeadEnemies();
					synchronized (enemies)
					{
						for (int n = 0; n < enemies.size(); n++)
						{
							if (enemies.get(n).getShadowHitbox()
									.intersects(player.getHitBox()))
								player.takeDamage(enemies.get(n).getDamage());
						}
					}
				}
			}
		}
	}

	private class EnemyProjectileThread implements Runnable
	{
		public void run()
		{
			while (inRoom)
			{
				if (!pauseRoom)
				{
					for (int n = 0; n < enemies.size(); n++)
					{
						Enemy currentEnemy = enemies.get(n);

					}
				}
			}
		}
	}

	/**
	 * A thread that deals with moving the enemies properly.
	 */
	private class EnemyMovementThread implements Runnable
	{
		public void run()
		{
			while (inRoom)
			{
				if (!pauseRoom)
				{
					killDeadEnemies();
					synchronized (enemies)
					{
						for (int n = 0; n < enemies.size(); n++)
						{
							// Each enemy can go four directions (N,E,S,W)
							// Enemies decide on which direction to take on a
							// random
							// basis
							// Enemies want to (more likely) to continue moving
							// in a
							// direction, until they hit another game object
							// After hitting another game object they will
							// decide
							// another direction on a random basis
							// When enemies are within aggro-range follow
							// players
							// Enemies cannot overlap other game objects
							Enemy currentEnemy = enemies.get(n);
							// if (!currentEnemy.isAlive())
							// {
							// if (roomType == RoomType.BOSS)
							// {
							// showTrapDoor = true;
							// }
							// enemies.remove(n);
							// }
							// Only move moveable enemies
							if (currentEnemy.canMove())
							{
								Random r = new Random();

								// No initial direction
								if (currentEnemy.getDirection() == ' ')
									currentEnemy.setRandomDirection();
								// Already moving in a direction
								else
								{
									// Set a 5% chance to change direction
									if (r.nextInt(100) >= 95)
										currentEnemy.setRandomDirection();
								}

								// Keep track of old coordinates
								int oldX = (int) currentEnemy.getX();
								int oldY = (int) currentEnemy.getY();
								// Enemy is of the aggresive type
								if (currentEnemy.isAngry())
								{
									int enemyWidth = (int) currentEnemy
											.getSize()
											.getWidth();
									int enemyHeight = (int) currentEnemy
											.getSize()
											.getHeight();
									// Make an aggro rectangle for range
									// Aggro range is in a 3 * enemy width by 3
									// *
									// enemy
									// height box with the enemy in the centre
									Rectangle aggroBox = new Rectangle(oldX
											- enemyWidth, oldY - enemyHeight,
											enemyWidth * 3, enemyHeight * 3);
									// Player is within aggro range
									if (aggroBox.intersects(player.getHitBox()))
									{
										// Change direction to run at the player
										// AGGRESIVELY
										currentEnemy.setSpeed(300);
										if (player.getX() < currentEnemy.getX() + 50
												&& player.getX() > currentEnemy
														.getX())
										{
											if (player.getY() > currentEnemy
													.getY())
												currentEnemy.setDirection('S');
											else
												currentEnemy.setDirection('N');
										}
										else if (player.getX() > currentEnemy
												.getX())
											currentEnemy.setDirection('E');
										else
											currentEnemy.setDirection('W');
									}
									else
										currentEnemy.setSpeed(200);
								}
								// Move the enemy in its direction
								currentEnemy.moveInDirection();
								// There is a collision with another ENEMY or
								// ITEM
								// (not
								// player) so move back and change direction
								updateHitboxes();
								if (enemyCollision(n,
										currentEnemy.getShadowHitbox()))
								{
									currentEnemy.move(oldX, oldY);
									currentEnemy.setRandomDirection();
									currentEnemy.moveInDirection();

									// Still collision
									updateHitboxes();
									if (enemyCollision(n,
											currentEnemy.getShadowHitbox()))
									{
										currentEnemy.move(oldX, oldY);
										currentEnemy.setRandomDirection();
										currentEnemy.moveInDirection();

										// Still collision
										updateHitboxes();
										if (enemyCollision(n,
												currentEnemy.getShadowHitbox()))
											currentEnemy.move(oldX, oldY);
									}
								}

								// Gissing projectile behaviour
								if (currentEnemy instanceof Gissing)
								{
									// Gissing is a beast so he shoots all
									// directions
									currentEnemy.shootAllDirections();
								}
								// Student enemy
								else
								{
									// Normal students shoot one direction
									currentEnemy.shootProjectile(currentEnemy
											.getDirection());
								}

								// Get the latest update of projectiles
								currentEnemy.updateProjectiles();
								ArrayList<Projectile> currentP = currentEnemy
										.getAllProjectiles();
								// Go through all projectiles of the current
								// enemy
								for (int p = 0; p < currentP.size(); p++)
								{
									// Hits a player
									if (currentP.get(p).getHitBox()
											.intersects(player.getHitBox()))
										player.takeDamage(currentEnemy
												.getDamage());
									// Hits a rock
									else
										for (int z = 0; z < roomRocks.size(); z++)
										{
											if (currentP
													.get(p)
													.getHitBox()
													.intersects(
															roomRocks
																	.get(z)
																	.getRockHitBox()))
												currentP.get(p)
														.killProjectile();
										}

								}

								// Check enemy collision with MIIIIICHAEL BAY
								if (player.hasMichaelBay())
								{
									if (currentEnemy.getHitBox().intersects(
											player.getMichaelBay()
													.getRockHitBox()))
										currentEnemy.takeDamage(10);
								}
							}

						}
					}

					try
					{
						Thread.sleep(20);
					}
					catch (Exception exc)
					{
					}
				}
			}
		}

	}
}