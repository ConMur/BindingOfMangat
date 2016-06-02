package levels;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Level
{
	private ArrayList<Room> rooms;
	private Random rand;
	private Room currentRoom;

	
	//TODO: call start room and end room when entering and exiting a room
	
	// Additional room chance out of TOTAL_CHANCES
	private final int ADDITIONAL_ROOM_CHANCE = 3;
	private final int TOTAL_CHANCES = 10;

	/**
	 * Creates the level with the given rooms
	 * @param rooms the rooms to create the level with. The room at index 0 is
	 *            the starting room
	 */
	public Level(ArrayList<Room> rooms)
	{
		rand = new Random();
		setRooms(rooms);
	}

	public void setRooms(ArrayList<Room> roomList)
	{
		rooms = roomList;
		
		currentRoom = rooms.get(0);
		// Arrange the rooms
		Room workingRoom = currentRoom;
		while (rooms.size() > 0)
		{
			if (!workingRoom.isFull())
			{
				// Always connect to one room
				Room room = rooms.get(rand.nextInt(rooms.size()));
				addRoom(workingRoom, room);

				// Have a chance to add additional rooms if the room is not
				// already full and there are rooms to add
				if (!workingRoom.isFull() && rooms.size() > 0)
				{
					int chanceNumber = rand.nextInt(TOTAL_CHANCES);
					if (chanceNumber <= ADDITIONAL_ROOM_CHANCE)
					{
						Room additionalRoom = rooms.get(rand.nextInt(rooms.size()));
						addRoom(additionalRoom, rooms.get(rand.nextInt(rooms.size())));
					}
				}
				
				//Reassign the working room
				workingRoom = room;
			}
		}
	}
	
	public void draw(Graphics g)
	{
		currentRoom.draw(g);
	}

	/**
	 * Adds a room to a random valid location in a room
	 * @param workingRoom the room to connect the given room to
	 * @param room the room to connect the working room to
	 */
	private void addRoom(Room workingRoom, Room room)
	{
		boolean sucessful = false;
		while (!sucessful)
		{
			int dir = rand.nextInt(4);
			// Try to set to a room
			if (dir == 0)
			{
				sucessful = workingRoom.setNorth(room);
			}
			else if (dir == 1)
			{
				sucessful = workingRoom.setEast(room);
			}
			else if (dir == 2)
			{
				sucessful = workingRoom.setSouth(room);
			}
			else if (dir == 3)
			{
				sucessful = workingRoom.setWest(room);
			}
		}
	}

}
