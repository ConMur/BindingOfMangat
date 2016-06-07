package levels;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Level
{
	private ArrayList<Room> rooms;
	private Random rand;
	private Room currentRoom;

	private Minimap minimap;
	
	private final int PLAYER_SOUTH_X = 465;
	private final int PLAYER_SOUTH_Y = 565;
	
	private final int PLAYER_NORTH_X = 465;
	private final int PLAYER_NORTH_Y = 250;
	
	private final int PLAYER_EAST_X = 845;
	private final int PLAYER_EAST_Y = 380;
	
	private final int PLAYER_WEST_X = 75;
	private final int PLAYER_WEST_Y = 380;
	

	
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
		minimap = new Minimap();
		setRooms(rooms);
	}

	public void setRooms(ArrayList<Room> roomList)
	{
		rooms = roomList;
		
		currentRoom = rooms.remove(0);
		// Arrange the rooms
		Room workingRoom = currentRoom;
		minimap.addRoom(workingRoom);

		//Remove the boss room from the general room connecting
		Room bossRoom = rooms.remove(rooms.size() - 1);

		while (rooms.size() > 0)
		{
			if (!workingRoom.isFull())
			{
				// Always connect to one room
				Room room = rooms.remove(rand.nextInt(rooms.size()));
				addRoom(workingRoom, room);
				minimap.addRoom(room);
				// Have a chance to add additional rooms if the room is not
				// already full and there are rooms to add
				if (!workingRoom.isFull() && rooms.size() > 0)
				{
					int chanceNumber = rand.nextInt(TOTAL_CHANCES);
					if (chanceNumber <= ADDITIONAL_ROOM_CHANCE)
					{
						Room additionalRoom = rooms.remove(rand.nextInt(rooms.size()));
						addRoom(workingRoom, additionalRoom);
						minimap.addRoom(additionalRoom);
					}
				}
				
				//Reassign the working room
				workingRoom = room;
			}
		}

		//Add the boss room to the last room added
		addRoom(workingRoom, bossRoom);
		minimap.addRoom(bossRoom);

		minimap.setUpRooms();
		
	}
	
	public void start ()
	{
		currentRoom.startRoom();
	}

	public void update()
	{
		currentRoom.update();

		checkPlayerAtDoor();
	}

	/**
	 * Checks if the player is at a door and moves them to the next room if so
	 */
	private void checkPlayerAtDoor() {
		if(currentRoom.isPlayerAtNorthDoor())
		{
			minimap.setPlayerRoomY(minimap.getPlayerRoomY() - 1);
			currentRoom.getPlayer().setY(PLAYER_SOUTH_Y);
			System.out.println("AT NORTH");
			currentRoom.endRoom();
			currentRoom = currentRoom.getNorth();
			currentRoom.startRoom();
		}
		else if(currentRoom.isPlayerAtSouthDoor())
		{
			minimap.setPlayerRoomY(minimap.getPlayerRoomY() + 1);
			currentRoom.getPlayer().setY(PLAYER_NORTH_Y);
			System.out.println("AT SOUTH");
			currentRoom.endRoom();
			currentRoom = currentRoom.getSouth();
			currentRoom.startRoom();
		}
		else if(currentRoom.isPlayerAtEastDoor())
		{
			minimap.setPlayerRoomX(minimap.getPlayerRoomX() + 1);
			currentRoom.getPlayer().setX(PLAYER_WEST_X);
			System.out.println("AT EAST");
			currentRoom.endRoom();
			currentRoom = currentRoom.getEast();
			currentRoom.startRoom();
		}
		else if(currentRoom.isPlayerAtWestDoor())
		{
			minimap.setPlayerRoomX(minimap.getPlayerRoomX() - 1);
			currentRoom.getPlayer().setX(PLAYER_EAST_X);
			System.out.println("AT WEST");
			currentRoom.endRoom();
			currentRoom = currentRoom.getWest();
			currentRoom.startRoom();
		}
	}

	public void draw(Graphics g)
	{
		currentRoom.draw(g);
		minimap.draw(g);
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
				if(sucessful)
				{
					if(room.getSouth() != null)
					{
						System.err.println("South ALREADY SEt!");
					}
					room.setSouth(workingRoom);
				}
			}
			else if (dir == 1)
			{
				sucessful = workingRoom.setEast(room);
				if(sucessful)
				{
					if(room.getWest() != null)
					{
						System.err.println("WEST ALREADY SEt!");
					}
					room.setWest(workingRoom);
				}
			}
			else if (dir == 2)
			{
				sucessful = workingRoom.setSouth(room);
				if(sucessful)
				{
					if(room.getNorth() != null)
					{
						System.err.println("NORTH ALREADY SEt!");
					}
					room.setNorth(workingRoom);
				}
			}
			else if (dir == 3)
			{
				sucessful = workingRoom.setWest(room);
				if(sucessful)
				{
					if(room.getEast() != null)
					{
						System.err.println("EAST ALREADY SEt!");
					}
					room.setEast(workingRoom);
				}
			}
		}
	}

}
