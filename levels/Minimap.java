package levels;

import java.awt.*;
import java.util.ArrayList;
import levels.Room.RoomType;

/**
 * The minimap for the level that displays rooms in the level. It only shows
 * rooms that the player has visited. Green rooms are locked rooms, red rooms
 * are boss rooms, black rooms are regular rooms and the white room is the one
 * the player is in
 * @author Connor Murphy
 */
public class Minimap
{
	private ArrayList<Room> rooms;
	private ArrayList<MinimapRoom> minimapRooms;

	private int playerRoomX, playerRoomY;

	/**
	 * Creates the minimap with the given rooms
	 * @param rooms the rooms to add to the minimap
	 */
	public Minimap(ArrayList<Room> rooms)
	{
		this.rooms = rooms;
		minimapRooms = new ArrayList<>();

		Room rootRoom = rooms.get(0);
		playerRoomX = 0;
		playerRoomY = 0;
		setUpRooms(rootRoom, 0, 0);
	}

	/**
	 * Creates an empty minimap
	 */
	public Minimap()
	{
		playerRoomX = 0;
		playerRoomY = 0;
		rooms = new ArrayList<>();
		minimapRooms = new ArrayList<>();
	}

	/**
	 * Adds a room to the minimap
	 * @param r the room to add
	 */
	public void addRoom(Room r)
	{
		rooms.add(r);
	}

	/**
	 * Starts the recursive method to connect the rooms in the minimap
	 */
	public void setUpRooms()
	{
		Room rootRoom = rooms.get(0);
		setUpRooms(rootRoom, 0, 0);
	}

	/**
	 * Goes through each room and finds the relative x and y coordinates from
	 * the initial room that the other, unvisited rooms are
	 * @param room the room to check
	 * @param x the x coordinate of the given room
	 * @param y the y coordinate of the given room
	 */
	private void setUpRooms(Room room, int x, int y)
	{
		room.setVisited(true);
		minimapRooms.add(new MinimapRoom(room, x, y));

		if (room.getNorth() != null && !room.getNorth().isVisited())
		{
			minimapRooms.add(new MinimapRoom(room.getNorth(), x, y - 1));
			setUpRooms(room.getNorth(), x, y - 1);
		}
		if (room.getSouth() != null && !room.getSouth().isVisited())
		{
			minimapRooms.add(new MinimapRoom(room.getSouth(), x, y + 1));
			setUpRooms(room.getSouth(), x, y + 1);
		}
		if (room.getEast() != null && !room.getEast().isVisited())
		{
			minimapRooms.add(new MinimapRoom(room.getEast(), x + 1, y));
			setUpRooms(room.getEast(), x + 1, y);
		}
		if (room.getWest() != null && !room.getWest().isVisited())
		{
			minimapRooms.add(new MinimapRoom(room.getWest(), x - 1, y));
			setUpRooms(room.getWest(), x - 1, y);
		}
	}

	/**
	 * Draws the minimap to the screen. Draws the room white if the player is in
	 * it, black if it is a regular room, green if it is a locked room and red
	 * if it is a boss room
	 * @param g the graphics to draw to
	 */
	public void draw(Graphics g)
	{
		for (MinimapRoom r : minimapRooms)
		{
			if (r.getRoom().isFound())
			{
				RoomType type = r.getRoom().getRoomType();
				if (r.getX() == playerRoomX && r.getY() == playerRoomY)
				{
					g.setColor(Color.WHITE);
				}
				else if (r.getRoom().isLocked())
				{
					g.setColor(Color.GREEN);
				}
				else if (type == RoomType.NORMAL)
				{
					g.setColor(Color.BLACK);
				}
				else if (type == RoomType.BOSS)
				{
					g.setColor(Color.RED);
				}
				else if (type == RoomType.SHOP)
				{
					g.setColor(Color.YELLOW);
				}
				g.fillRect(r.getX() * 11 + 150, r.getY() * 11 + 75, 10, 10);
			}
		}
	}

	/**
	 * Returns the x coordinate of the room the player is in
	 * @return the x coordinate of the room the player is in
	 */
	public int getPlayerRoomX()
	{
		return playerRoomX;
	}

	/**
	 * Sets the x coordinate of the room the player is in to the given value
	 * @param playerRoomX the x coordinate of the room the player is in
	 */
	public void setPlayerRoomX(int playerRoomX)
	{
		this.playerRoomX = playerRoomX;
	}

	/**
	 * Returns the y coordinate of the room the player is in
	 * @return the y coordinate of the room the player is in
	 */
	public int getPlayerRoomY()
	{
		return playerRoomY;
	}

	/**
	 * Sets the y coordinate of the room the player is in to the given value
	 * @param playerRoomY the y coordinate of the room the player is in
	 */
	public void setPlayerRoomY(int playerRoomY)
	{
		this.playerRoomY = playerRoomY;
	}

	/**
	 * A class that represents a room in the minimap. It is a regular room with
	 * x and y coordinates
	 * @author Connor Murphy
	 *
	 */
	class MinimapRoom
	{
		private int x, y;
		private Room room;
		
		/**
		 * Creates a minimap room with the given values
		 * @param r the room associated with this minimap room
		 * @param x the x coordinate of this minimap room
		 * @param y the y coordinate of this minimap room
		 */
		public MinimapRoom(Room r, int x, int y)
		{
			this.room = r;
			this.x = x;
			this.y = y;
		}

		/**
		 * Returns the x coordinate of this minimap room
		 * @return the x coordinate of this minimap room
		 */
		public int getX()
		{
			return x;
		}

		/**
		 * Sets the x position of this minimap room
		 * @param x the x position of this minimap room
		 */
		public void setX(int x)
		{
			this.x = x;
		}

		/**
		 * Returns the y coordinate of this minimap room
		 * @return the y coordinate of this minimap room
		 */
		public int getY()
		{
			return y;
		}

		/**
		 * Sets the y position of this minimap room
		 * @param y the y position of this minimap room
		 */
		public void setY(int y)
		{
			this.y = y;
		}

		/**
		 * Returns the room associated with this room
		 * @return the room associated with this room
		 */
		public Room getRoom()
		{
			return room;
		}

		/**
		 * Sets the room associated with this room
		 * @param room the room associated with this room
		 */
		public void setRoom(Room room)
		{
			this.room = room;
		}
	}
}
