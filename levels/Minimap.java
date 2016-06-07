package levels;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Connor Murphy
 */
public class Minimap {
    private ArrayList<Room> rooms;
    private ArrayList<MinimapRoom> minimapRooms;

    private int playerRoomX, playerRoomY;

    public Minimap(ArrayList<Room> rooms) {
        this.rooms = rooms;
        minimapRooms = new ArrayList<>();

        Room rootRoom = rooms.get(0);
        playerRoomX = 0;
        playerRoomY = 0;
        setUpRooms(rootRoom, 0, 0);
    }

    public void setUpRooms(Room room, int x, int y) {
        room.setVisited(true);
        minimapRooms.add(new MinimapRoom(room,x,y));

        if (room.getNorth() != null && !room.getNorth().isVisited()) {
            minimapRooms.add(new MinimapRoom(room.getNorth(), x, y - 1));
            setUpRooms(room.getNorth(), x, y -1);
        }
        if (room.getSouth() != null && !room.getSouth().isVisited()) {
            minimapRooms.add(new MinimapRoom(room.getSouth(), x, y + 1));
            setUpRooms(room.getSouth(), x, y + 1);
        }
        if (room.getEast() != null && !room.getEast().isVisited()) {
            minimapRooms.add(new MinimapRoom(room.getEast(), x + 1, y));
            setUpRooms(room.getEast(), x + 1, y);
        }
        if (room.getWest() != null && !room.getWest().isVisited()) {
            minimapRooms.add(new MinimapRoom(room.getWest(), x - 1, y));
            setUpRooms(room.getWest(), x - 1, y);
        }
    }

    public void draw(Graphics g) {
        for(MinimapRoom r: minimapRooms)
        {
            if(r.getX() == playerRoomX && r.getY() == playerRoomY)
            {
                g.setColor(Color.WHITE);
            }
            else
            {
                g.setColor(Color.BLACK);
            }
            g.fillRect(r.getX() * 11 + 200, r.getY() * 11 + 200, 10,10);
        }
    }

    public int getPlayerRoomX() {
        return playerRoomX;
    }

    public void setPlayerRoomX(int playerRoomX) {
        this.playerRoomX = playerRoomX;
    }

    public int getPlayerRoomY() {
        return playerRoomY;
    }

    public void setPlayerRoomY(int playerRoomY) {
        this.playerRoomY = playerRoomY;
    }

    class MinimapRoom {
        private int x, y;
        private Room room;

        public MinimapRoom(Room r, int x, int y) {
            this.room = r;
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Room getRoom() {
            return room;
        }

        public void setRoom(Room room) {
            this.room = room;
        }
    }
}

