package levels;

import java.awt.Graphics;
import java.util.ArrayList;

import thingsthatmove.Player;

public final class LevelManager
{
	private static final int EXTRA_ROOMS_PER_LEVEL = 1;
	private static final int NUMBER_OF_LEVELS = 4;
	
	private static ArrayList<Level> levels;
	private static Level currentLevel;
	
	private LevelManager()	{}
	
	public static void init()
	{
		levels = new ArrayList<Level>();
		generateLevels(NUMBER_OF_LEVELS);
		
		//Start on the first level
		currentLevel = levels.get(0);
	}

	private static void generateLevels(int numLevels)
	{
		/*
		 * maybe shop
		 * always key door
		 * always boss room
		 * always other rooms
		 */
		
		for(int i = 0; i < numLevels; ++i)
		{
			//Create the rooms list
			ArrayList<Room> rooms = new ArrayList<Room>();
			
			Level level = new Level();
		}
	}
	
	public void update()
	{
		//Check if the player is at a door
		int x = Player.getX();
		int y = Player.getY();
		
		//if(x > && x < && y > && y <)
	}
	
	public void draw(Graphics g)
	{
		currentLevel.draw(g);
	}
	
	
}
