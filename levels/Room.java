package levels;

import item.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import thingsthatmove.GameObject;
import thingsthatmove.Gissing;
import thingsthatmove.Player;
import thingsthatmove.Projectile;

public class Room {
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<GameObject> roomObjects = new ArrayList<GameObject>();
    private Thread moveEnemies, playerEnemyCollision, enemyProjectile;
    private Player player;
    private Image background, hud;
    private Image northClosedDoor, southClosedDoor, eastClosedDoor,
            westClosedDoor;
    private Image northOpenDoor, southOpenDoor, eastOpenDoor, westOpenDoor;
    private BufferedImage northLockedDoor, southLockedDoor, eastLockedDoor,
            westLockedDoor;
    private Room north, east, south, west;
    private boolean northOpen, southOpen, eastOpen, westOpen;
    private boolean atNorthDoor, atSouthDoor, atEastDoor, atWestDoor;

    private boolean inRoom;
    private boolean takenDamage;
    private boolean visited;
    private boolean isLocked;

    // The trap door shown when the player defeats the boss
    private boolean showTrapDoor;
    private BufferedImage trapDoor;

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

    private final int ROOM_CENTRE_X = 400;
    private final int ROOM_CENTRE_Y = 250;

    public enum RoomType {
        NORMAL, BOSS, SHOP
    }

    private RoomType roomType;

    public Room(ArrayList<Enemy> e, ArrayList<Item> i,
                ArrayList<GameObject> go, Player p, boolean locked, RoomType type,
                Room north, Room east,
                Room south, Room west) {
        this.enemies = e;
        this.items = i;
        this.roomObjects = go;
        this.player = p;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;

        try {
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
            // TODO: add trap door image
            trapDoor = ImageIO.read(getClass().getResourceAsStream(
                    "/images/doors/opendoornorth.png"));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        atNorthDoor = false;
        atSouthDoor = false;
        atEastDoor = false;
        atWestDoor = false;

        takenDamage = false;
        visited = false;
        isLocked = locked;

        showTrapDoor = false;

        this.roomType = type;
    }

    public Room(ArrayList<Enemy> e, ArrayList<Item> i,
                ArrayList<GameObject> go, Player p, boolean locked, RoomType type) {
        this(e, i, go, p, locked, type, null, null, null, null);
    }

    public Room(boolean locked, RoomType type, Room north, Room east,
                Room south, Room west) {
        this(null, null, null, null, locked, type, north, east, south, west);
    }

    public boolean isNorthOpen() {
        return northOpen;
    }

    public boolean isSouthOpen() {
        return southOpen;
    }

    public boolean isEastOpen() {
        return eastOpen;
    }

    public boolean isWestOpen() {
        return westOpen;
    }

    public void updateDoorStatus() {
        // Keep all doors closed if there are enemies
        if (hasEnemies()) {
            northOpen = false;
            southOpen = false;
            eastOpen = false;
            westOpen = false;
        }
        // No enemies, open all doors
        else {
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

    public void update() {
        updateDoorStatus();
        checkIfPlayerAtDoor();
        checkProjectileCollision();
    }

    /**
     * Checks collisions between all the projectiles and the enemies in this
     * room
     */
    private void checkProjectileCollision() {
        ArrayList<Projectile> projectiles = player.getAllPlayerProjectiles();
        for (int p = 0; p < projectiles.size(); p++) {
            Projectile pj = projectiles.get(p);
            for (int e = 0; e < enemies.size(); e++) {
                Enemy en = enemies.get(e);
                if (pj.getHitBox().intersects(en.getHitBox())) {
                    en.takeDamage(pj.getDamage());
                    pj.killProjectile();
                }
            }
        }
    }

    /**
     * Checks if a player is at one of the doors and sets booleans if it is
     */
    private void checkIfPlayerAtDoor() {
        // Check if player is at a door
        double x = player.getX();
        double y = player.getY();
        // System.out.println("X " + x + " Y " + y);
        atNorthDoor = false;
        atSouthDoor = false;
        atWestDoor = false;
        atEastDoor = false;

        // North door
        if (north != null && northOpen && x > 412 && x < 518 && y < 250) {
            atNorthDoor = true;
        } else if (south != null && southOpen && x > 412 && x < 518 && y > 565) {
            atSouthDoor = true;
        } else if (east != null && eastOpen && x > 845 && y > 340 && y < 405) {
            atEastDoor = true;
        } else if (west != null && westOpen && x < 90 && y > 340 && y < 405) {
            atWestDoor = true;
        }

        // Check if at trapDoor
        if (showTrapDoor) {
            if (x > ROOM_CENTRE_X && x < ROOM_CENTRE_X + 50
                    && y > ROOM_CENTRE_Y && y < ROOM_CENTRE_X + 50) {
                LevelManager.advanceLevel();
            }
        }
    }

    public boolean isPlayerAtNorthDoor() {
        if (atNorthDoor && player.isMovingNorth()) {
            if (north.isLocked() && player.getNumKeys() > 0) {
                player.setNumKeys(player.getNumKeys() - 1);
                north.setLocked(false);
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean isPlayerAtSouthDoor() {
        if (atSouthDoor && player.isMovingSouth()) {
            if (south.isLocked() && player.getNumKeys() > 0) {
                player.setNumKeys(player.getNumKeys() - 1);
                south.setLocked(false);
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean isPlayerAtEastDoor() {
        if (atEastDoor && player.isMovingEast()) {
            if (east.isLocked() && player.getNumKeys() > 0) {
                player.setNumKeys(player.getNumKeys() - 1);
                east.setLocked(false);
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean isPlayerAtWestDoor() {
        if (atWestDoor && player.isMovingWest()) {
            if (west.isLocked() && player.getNumKeys() > 0) {
                player.setNumKeys(player.getNumKeys() - 1);
                west.setLocked(false);
                return true;
            }
            return true;
        }
        return false;
    }

    public void resetAllDoors() {
        atNorthDoor = false;
        atSouthDoor = false;
        atEastDoor = false;
        atWestDoor = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public void removePlayer() {
        this.player = null;
    }

    public boolean hasPlayer() {
        if (player == null)
            return false;
        return true;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemies(ArrayList<Enemy> e) {
        enemies = e;
    }

    public void removeEnemies() {
        enemies = null;
    }

    public boolean hasEnemies() {
        if (enemies == null || enemies.size() == 0)
            return false;
        return true;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItems(ArrayList<Item> i) {
        items = i;
    }

    public void removeItems() {
        items = null;
    }

    public boolean hasItems() {
        if (items == null)
            return false;
        return true;
    }

    public ArrayList<GameObject> getRoomObjects() {
        return roomObjects;
    }

    public void addRoomObjects(ArrayList<GameObject> go) {
        roomObjects = go;
    }

    public void removeRoomObjects() {
        roomObjects = null;
    }

    public boolean hasRoomObjects() {
        if (roomObjects == null)
            return false;
        return true;
    }

    public boolean setNorth(Room r) {
        if (north != null)
            return false;
        north = r;
        return true;
    }

    public boolean setSouth(Room r) {
        if (south != null)
            return false;
        south = r;
        return true;
    }

    public boolean setWest(Room r) {
        if (west != null)
            return false;
        west = r;
        return true;
    }

    public boolean setEast(Room r) {
        if (east != null)
            return false;
        east = r;
        return true;
    }

    public Room getNorth() {
        return north;
    }

    public Room getSouth() {
        return south;
    }

    public Room getWest() {
        return west;
    }

    public Room getEast() {
        return east;
    }

    public boolean isFull() {
        if (north != null && south != null && west != null && east != null)
            return true;
        return false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void startRoom() {
        updateHitboxes();
        resetAllDoors();
        inRoom = true;
        System.out.println("STARTING ROOM");
        moveEnemies = new Thread(new EnemyMovementThread());
        moveEnemies.start();

        playerEnemyCollision = new Thread(new EnemyPlayerCollisionThread());
        playerEnemyCollision.start();
        //
        // enemyProjectile = new Thread (new EnemyProjectileThread());
        // enemyProjectile.start();

    }

    public void endRoom() {
        System.out.println("ENDING ROOM");
        inRoom = false;
        // Clear all projectiles
        player.clearProjectiles();
    }

    public void updateHitboxes() {
        // Remove all current hitboxes
        hitboxes.clear();

        // Add all enemy hitboxes
        for (int e = 0; e < enemies.size(); e++)
            hitboxes.add(enemies.get(e).getHitBox());
        // Add all item hitboxes
        for (int i = 0; i < items.size(); i++)
            hitboxes.add(items.get(i).getHitBox());
    }

    public boolean enemyCollision(int enemyIndex,
                                  Rectangle enemyHitbox) {
        for (int n = 0; n < hitboxes.size(); n++) {

            // System.out.println("HITBOXES: " + hitboxes.size()
            // + " CURRENT INDEX: " + n);
            if (enemyHitbox.intersects(hitboxes.get(n)) && n != enemyIndex)
                return true;
        }
        return false;
    }

    public void sortAllGameObjects() {
        // roomObjects.addAll(enemies);
        // roomObjects.addAll(items);

        // Go through all items, starting at the second
        for (int n = 1; n < enemies.size(); n++) {
            // Keep track of the current number
            int currentIndex = n;

            // Traverse backwards until spot is found
            while (currentIndex > 0
                    && enemies.get(n - 1).getY() > enemies.get(n).getY()) {
                // Swap current with its previous element
                Enemy placeholder = enemies.get(n);
                enemies.set(n, enemies.get(n - 1));
                enemies.set(n - 1, placeholder);

                currentIndex--;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(hud, 0, 0, null);
        g.drawImage(background, 0, 198, null);
        g.setColor(Color.RED);

        g.drawRect(130, 325, 720, 250);

        g.setColor(Color.BLACK);
        g.drawRect(LOWER_X_BOUND, LOWER_Y_BOUND, UPPER_X_BOUND - LOWER_X_BOUND,
                UPPER_Y_BOUND - LOWER_Y_BOUND);

        drawDoors(g);
        sortAllGameObjects();
        for (int e = 0; e < enemies.size(); e++) {
            Enemy currentEnemy = enemies.get(e);
            Rectangle r = currentEnemy.getHitBox();
            g.drawImage(currentEnemy.getImage(), (int) currentEnemy.getX(),
                    (int) currentEnemy.getY(), null);
            g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(),
                    (int) r.getHeight());

            currentEnemy.draw(g);
        }

        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(g);
        }

        // for (GameObject currentRoomObject : roomObjects)
        // {
        // g.drawImage(currentRoomObject.getImage(),
        // (int) currentRoomObject.getX(),
        // (int) currentRoomObject.getY(), null);
        // }
        //

    }

    /**
     * Draws the doors of the room to the given graphics
     *
     * @param g the graphics to draw to
     */
    private void drawDoors(Graphics g) {
        // Draw doors
        if (north != null) {
            if (northOpen && !north.isLocked())
                g.drawImage(northOpenDoor, NORTH_DOOR_X, NORTH_DOOR_Y - 100,
                        null);
            else if (north.isLocked())
                g.drawImage(northLockedDoor, NORTH_DOOR_X, NORTH_DOOR_Y - 100,
                        null);
            else
                g.drawImage(northClosedDoor, NORTH_DOOR_X, NORTH_DOOR_Y - 100,
                        null);
        }
        if (south != null) {
            if (southOpen && !south.isLocked())
                g.drawImage(southOpenDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y, null);
            else if (south.isLocked())
                g.drawImage(southLockedDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y, null);
            else
                g.drawImage(southClosedDoor, SOUTH_DOOR_X, SOUTH_DOOR_Y, null);
        }
        if (east != null) {
            if (eastOpen && !east.isLocked())
                g.drawImage(eastOpenDoor, EAST_DOOR_X, EAST_DOOR_Y, null);
            else if (east.isLocked())
                g.drawImage(eastLockedDoor, EAST_DOOR_X, EAST_DOOR_Y, null);
            else
                g.drawImage(eastClosedDoor, EAST_DOOR_X, EAST_DOOR_Y, null);
        }
        if (west != null) {
            if (westOpen && !west.isLocked())
                g.drawImage(westOpenDoor, WEST_DOOR_X - 100, WEST_DOOR_Y, null);
            else if (west.isLocked())
                g.drawImage(westLockedDoor, WEST_DOOR_X - 100, WEST_DOOR_Y,
                        null);
            else
                g.drawImage(westClosedDoor, WEST_DOOR_X - 100, WEST_DOOR_Y,
                        null);
        }

        if (showTrapDoor) {
            g.drawImage(trapDoor, ROOM_CENTRE_X, ROOM_CENTRE_Y, null);
        }
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void killDeadEnemies() {
        for (int n = 0; n < enemies.size(); n++) {
            if (!enemies.get(n).isAlive()) {
                if (roomType == RoomType.BOSS)
                    showTrapDoor = true;
                enemies.remove(n);

            }
        }
    }

    private class EnemyPlayerCollisionThread implements Runnable {
        public void run() {
            while (inRoom) {
                killDeadEnemies();
                synchronized (enemies) {
                    for (int n = 0; n < enemies.size(); n++) {
                        if (enemies.get(n).getHitBox()
                                .intersects(player.getHitBox()))
                            player.takeDamage(enemies.get(n).getDamage());
                    }
                }
            }
        }
    }

    private class EnemyProjectileThread implements Runnable {
        public void run() {
            while (inRoom) {
                for (int n = 0; n < enemies.size(); n++) {
                    Enemy currentEnemy = enemies.get(n);

                }
            }
        }
    }

    private class EnemyMovementThread implements Runnable {
        public void run() {
            while (inRoom) {
                killDeadEnemies();
                synchronized (enemies) {
                    for (int n = 0; n < enemies.size(); n++) {
                        // Each enemy can go four directions (N,E,S,W)
                        // Enemies decide on which direction to take on a random
                        // basis
                        // Enemies want to (more likely) to continue moving in a
                        // direction, until they hit another game object
                        // After hitting another game object they will decide
                        // another direction on a random basis
                        // When enemies are within aggro-range follow players
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
                        if (currentEnemy.canMove()) {
                            Random r = new Random();

                            // No initial direction
                            if (currentEnemy.getDirection() == ' ')
                                currentEnemy.setRandomDirection();
                                // Already moving in a direction
                            else {
                                // Set a 5% chance to change direction
                                if (r.nextInt(100) >= 95)
                                    currentEnemy.setRandomDirection();
                            }

                            // Keep track of old coordinates
                            int oldX = (int) currentEnemy.getX();
                            int oldY = (int) currentEnemy.getY();
                            // Enemy is of the aggresive type
                            if (currentEnemy.isAngry()) {
                                int enemyWidth = (int) currentEnemy.getSize()
                                        .getWidth();
                                int enemyHeight = (int) currentEnemy.getSize()
                                        .getHeight();
                                // Make an aggro rectangle for range
                                // Aggro range is in a 3 * enemy width by 3 *
                                // enemy
                                // height box with the enemy in the centre
                                Rectangle aggroBox = new Rectangle(oldX
                                        - enemyWidth, oldY - enemyHeight,
                                        enemyWidth * 3, enemyHeight * 3);
                                // Player is within aggro range
                                if (aggroBox.intersects(player.getHitBox())) {
                                    // Change direction to run at the player
                                    // AGGRESIVELY
                                    currentEnemy.setSpeed(300);
                                    if (player.getX() < currentEnemy.getX() + 50
                                            && player.getX() > currentEnemy
                                            .getX()) {
                                        if (player.getY() > currentEnemy.getY())
                                            currentEnemy.setDirection('S');
                                        else
                                            currentEnemy.setDirection('N');
                                    } else if (player.getX() > currentEnemy
                                            .getX())
                                        currentEnemy.setDirection('E');
                                    else
                                        currentEnemy.setDirection('W');
                                } else
                                    currentEnemy.setSpeed(200);
                            }
                            // Move the enemy in its direction
                            currentEnemy.moveInDirection();
                            // There is a collision with another ENEMY or ITEM
                            // (not
                            // player) so move back and change direction
                            updateHitboxes();
                            if (enemyCollision(n, currentEnemy.getHitBox())) {
                                currentEnemy.move(oldX, oldY);
                                currentEnemy.setRandomDirection();
                                currentEnemy.moveInDirection();

                                // Still collision
                                updateHitboxes();
                                if (enemyCollision(n, currentEnemy.getHitBox())) {
                                    currentEnemy.move(oldX, oldY);
                                    currentEnemy.setRandomDirection();
                                    currentEnemy.moveInDirection();

                                    // Still collision
                                    updateHitboxes();
                                    if (enemyCollision(n,
                                            currentEnemy.getHitBox()))
                                        currentEnemy.move(oldX, oldY);
                                }
                            }
                            // Gissing projectile behaviour
                            if (currentEnemy instanceof Gissing) {
                                currentEnemy.shootMultipleProjectiles(3,
                                        currentEnemy.getDirection(),
                                        currentEnemy.getBotDirection(),
                                        currentEnemy.getTopDirection());

                                ArrayList<Projectile> currentP = currentEnemy
                                        .getAllProjectiles();
                                for (int p = 0; p < currentP.size(); p++) {
                                    if (currentP.get(p).getHitBox()
                                            .intersects(player.getHitBox()))
                                        player.takeDamage(currentEnemy
                                                .getDamage());
                                }
                            }
                            // Student enemy
                            else {
                                currentEnemy.shootProjectile(currentEnemy
                                        .getDirection());
                                ArrayList<Projectile> currentP = currentEnemy
                                        .getAllProjectiles();
                                for (int p = 0; p < currentP.size(); p++) {
                                    if (currentP.get(p).getHitBox()
                                            .intersects(player.getHitBox()))
                                        player.takeDamage(currentEnemy
                                                .getDamage());
                                }
                            }
                        }

                    }
                }

                try {
                    Thread.sleep(20);
                } catch (Exception exc) {
                }
            }
        }

    }
}
