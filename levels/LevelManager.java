package levels;

import item.Item;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import thingsthatmove.Enemy;
import thingsthatmove.Player;

import javax.imageio.ImageIO;

//TODO: add a way to make obstacles (rocks, walls, etc) in the rooms

/**
 * Creates, updates and draws the levels in the game
 * @author Connor Murphy
 *
 */
public final class LevelManager
{
	private static final int EXTRA_ROOMS_PER_LEVEL = 2;
	private static final int NUMBER_OF_LEVELS = 4;

	// The percent chance that there will be a shop this level
	private static final int SHOP_ROOM_CHANCE = 50;

	// The percent chance that there will be an item in the room
	private static final int ITEM_CHANCE = 20;

	// The minimum amount of enemies in a level
	private static final int MIN_ENEMIES = 3;

	// The number of enemies more than the minimum that there can be in the
	// level
	private static final int ADDITIONAL_ENEMIES = 3;

	private static ArrayList<Level> levels;
	private static Level currentLevel;

	private static int levelNumber;

	private static Random rand;
	
	// Enemy spawn boundaries
	private final static int SPAWN_X_LOWER = 130;
	private final static int SPAWN_Y_LOWER = 325;
	private final static int SPAWN_X_WIDTH = 780;
	private final static int SPAWN_Y_HEIGHT = 250;

	// Lists of things that can be added to a level
	private static ArrayList<Enemy> scienceEnemyList, englishEnemyList,
			mathEnemyList, historyEnemyList;
	private static ArrayList<Item> itemList;

	// A reference to the player
	private static Player player;

	private LevelManager()
	{
	}

	public static void init(Player p)
	{
		levels = new ArrayList<Level>();
		rand = new Random();
		player = p;

		scienceEnemyList = new ArrayList<>();
		englishEnemyList = new ArrayList<>();
		mathEnemyList = new ArrayList<>();
		historyEnemyList = new ArrayList<>();
		populateEnemyLists(scienceEnemyList, englishEnemyList, mathEnemyList,
				historyEnemyList);

		itemList = new ArrayList<>();
		populateItemList(itemList);

		// Start on the first level
		levelNumber = 1;

		generateLevels(NUMBER_OF_LEVELS);

		// Set the current level to the first level
		currentLevel = levels.get(0);
		currentLevel.start();
	}

	private static void populateEnemyLists(
			ArrayList<Enemy> science, ArrayList<Enemy> english,
			ArrayList<Enemy> math, ArrayList<Enemy> history)
	{
		// TODO: update as more enemies are added
		// Add the science enemies
		try {
			science.add(new Enemy(2, 2, 250, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
					new Dimension(75, 43), 2, true, false));
		}
		catch(IOException ioe)
		{
			System.err.println("Unable to load science enemies");
			ioe.printStackTrace();
		}
		// Add the english enemies
		try {
			english.add(new Enemy(2, 2, 1, 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
					new Dimension(100, 100), 2, true, false));
		}
		catch(IOException ioe)
		{
			System.err.println("Unable to load english enemies");
			ioe.printStackTrace();
		}
		// Add the math enemies
		try {
			math.add(new Enemy(2, 2, 1, 400, 600, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
					new Dimension(100, 100), 2, true, false));
		}
		catch(IOException ioe)
		{
			System.err.println("Unable to load math enemies");
			ioe.printStackTrace();
		}
		// Add the history enemies
		try {
			history.add(new Enemy(2, 2, 1, 200, 200, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
					new Dimension(100, 100), 2, true, false));
		}
		catch(IOException ioe)
		{
			System.err.println("Unable to load history enemies");
			ioe.printStackTrace();
		}
	}

	private static void populateItemList(ArrayList<Item> items)
	{
		// TODO: update as more items are added
		// Add the items
		try {
			items.add(new Item("Test", 600, 300, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/pifront.png")), new Dimension(100, 100), true));
		}
		catch(IOException ioe)
		{
			System.err.println("Error loading items");
			ioe.printStackTrace();
		}
	}

	/**
	 * Creates the given number of levels
	 * @param numLevels the number of levels to create
	 */
	private static void generateLevels(int numLevels)
	{
		/*
		 * maybe shop always key door always boss room always other rooms
		 */

		for (int i = 0; i < numLevels; ++i)
		{
			// Create the rooms list
			ArrayList<Room> rooms = new ArrayList<>();

			// Create a copy of the list of enemies for this level
			ArrayList<Enemy> levelEnemies;
			if (levelNumber == 1)
			{
				levelEnemies = new ArrayList<>(scienceEnemyList);
			}
			else if (levelNumber == 2)
			{
				levelEnemies = new ArrayList<>(englishEnemyList);
			}
			else if (levelNumber == 3)
			{
				levelEnemies = new ArrayList<>(mathEnemyList);
			}
			else if (levelNumber == 4)
			{
				levelEnemies = new ArrayList<>(historyEnemyList);
			}
			else
			{
				System.err.println("Invalid level value: " + levelNumber);
				levelEnemies = null;
			}

			// Create a copy of the list of items for this level
			ArrayList<Item> levelItems = new ArrayList<>(itemList);

			// Create general rooms (4 rooms on 1st level increasing by
			// EXTRA_ROOMS_PER_LEVEL each time
			for (int j = 0; j < 3 + (levelNumber * EXTRA_ROOMS_PER_LEVEL); ++j)
			{
				rooms.add(createRoom(levelEnemies, levelItems));
			}
			// Roll for a shop and add it if successful or just add another
			// general room
			int roll = rand.nextInt(100);
			if (roll < SHOP_ROOM_CHANCE)
			{
				// TODO add shop (room with game object with a GameObject that
				// the player can interact with)
			}
			else
			{
				rooms.add(createRoom(levelEnemies, levelItems));
			}

			Level level = new Level(rooms);
			levels.add(level);
		}
	}

	private static Room createRoom(ArrayList<Enemy> enemies,
			ArrayList<Item> items)
	{
		// Choose the enemy for this room
		//TODO: uncomment when have enough enemies
		//Enemy e = enemies.remove(rand.nextInt(enemies.size()));
		Enemy e = enemies.get(rand.nextInt(enemies.size()));

		// Create a number of enemies
		ArrayList<Enemy> enemyList = new ArrayList<>();
		int numEnemies = MIN_ENEMIES + rand.nextInt(ADDITIONAL_ENEMIES);
		for (int enemy = 0; enemy < numEnemies; ++enemy)
		{
			Enemy j = new Enemy (e);
			j.setX(rand.nextInt(SPAWN_X_WIDTH) + SPAWN_X_LOWER);
			j.setY(rand.nextInt(SPAWN_Y_HEIGHT) + SPAWN_Y_LOWER);
			enemyList.add(j);

		}

		// Have a chance to have an item in the room
		ArrayList<Item> itemList = new ArrayList<>();
		int itemChance = rand.nextInt(100);
		if (itemChance < ITEM_CHANCE)
		{
			//TODO: uncomment when have enough items
			//itemList.add(items.remove(rand.nextInt(items.size())));
			itemList.add(items.get(rand.nextInt(items.size())));
		}

		Room room = new Room(enemyList, itemList, new ArrayList<>(), player);
		return room;
	}

	public static void update()
	{
		currentLevel.update();
	}

	public static void draw(Graphics g)
	{
		currentLevel.draw(g);
	}

}
