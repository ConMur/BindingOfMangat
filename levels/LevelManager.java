package levels;

import item.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import thingsthatmove.*;

import javax.imageio.ImageIO;

//TODO: add a way to make obstacles (rocks, walls, etc) in the rooms

/**
 * Creates, updates and draws the levels in the game
 *
 * @author Connor Murphy
 */
public final class LevelManager {
    private static final int EXTRA_ROOMS_PER_LEVEL = 2;
    private static final int NUMBER_OF_LEVELS = 4;

    // The percent chance that there will be a shop this level
    private static final int SHOP_ROOM_CHANCE = 50;

    // The percent chance that there will be an item in the room
    private static final int ITEM_CHANCE = 20;

    //The percent chance that a room will be locked
    private static final int LOCKED_CHANCE = 10;

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
    private final static int SPAWN_X_WIDTH = 720;
    private final static int SPAWN_Y_HEIGHT = 250;

    // Lists of things that can be added to a level
    private static ArrayList<Enemy> scienceEnemyList, englishEnemyList,
            mathEnemyList, historyEnemyList;
    private static ArrayList<Item> itemList;

    // A reference to the player
    private static Player player;

    private LevelManager() {
    }

    public static void init(Player p) {
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
            ArrayList<Enemy> math, ArrayList<Enemy> history) {
        // TODO: update as more enemies are added
        // Add the science enemies
        try {
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
                    new Dimension(75, 43), 2, true, false));
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/einsteinfront.png")),
                    new Dimension(130, 65), 2, true, true));
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/cellfront.png")),
                    new Dimension(75, 50), 2, true, true));
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/chemfront.png")),
                    new Dimension(70, 60), 2, true, false));
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/moleculefront.png")),
                    new Dimension(90, 62), 2, true, false));
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/studentfront.png")),
                    new Dimension(75, 50), 2, true, false));
            science.add(new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/studentclusterfront.png")),
                    new Dimension(115, 65), 2, true, false));
        } catch (IOException ioe) {
            System.err.println("Unable to load science enemies");
            ioe.printStackTrace();
        }
        // Add the english enemies
        try {
            english.add(new Enemy(2, 2, 1, 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
                    new Dimension(100, 100), 2, true, false));
        } catch (IOException ioe) {
            System.err.println("Unable to load english enemies");
            ioe.printStackTrace();
        }
        // Add the math enemies
        try {
            math.add(new Enemy(2, 2, 1, 400, 600, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
                    new Dimension(100, 100), 2, true, false));
        } catch (IOException ioe) {
            System.err.println("Unable to load math enemies");
            ioe.printStackTrace();
        }
        // Add the history enemies
        try {
            history.add(new Enemy(2, 2, 1, 200, 200, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
                    new Dimension(100, 100), 2, true, false));
        } catch (IOException ioe) {
            System.err.println("Unable to load history enemies");
            ioe.printStackTrace();
        }
    }

    private static void populateItemList(ArrayList<Item> items) {
        // TODO: update as more items are added
        // Add the items
        try {
            items.add(new Item("ankh", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/ankh.png")), new Dimension(100, 100), true));
            items.add(new Item("blockofwood", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/blockofwood.png")), new Dimension(100, 100), true));
            items.add(new Item("bomb", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/bomb.png")), new Dimension(100, 100), true));
            items.add(new Item("bombguidebook", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/bombguidebook.png")), new Dimension(100, 100), true));
            items.add(new Item("brokenpencil", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/brokenpencil.png")), new Dimension(100, 100), true));
            items.add(new Item("c++", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/c++.png")), new Dimension(100, 100), true));
            items.add(new Item("caffood", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/caffood.png")), new Dimension(100, 100), true));
            items.add(new Item("compscisweater", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/compscisweater.png")), new Dimension(100, 100), true));
            items.add(new Item("fireflower", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/fireflower.png")), new Dimension(100, 100), true));
            items.add(new Item("glasses", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/glasses.png")), new Dimension(100, 100), true));
            items.add(new Item("goldenmushroom", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/goldenmushroom.png")), new Dimension(100, 100), true));
            items.add(new Item("halo", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/halo.png")), new Dimension(100, 100), true));
            items.add(new Item("icecube", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/icecube.png")), new Dimension(100, 100), true));
            items.add(new Item("lightning", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/lightning.png")), new Dimension(100, 100), true));
            items.add(new Item("lotteryticket", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/lotteryticket.png")), new Dimension(100, 100), true));
            items.add(new Item("mariomushroom", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/mariomushroom.png")), new Dimension(100, 100), true));
            items.add(new Item("masterkey", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/masterkey.png")), new Dimension(100, 100), true));
            items.add(new Item("projector", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/projector.png")), new Dimension(100, 100), true));
            items.add(new Item("usb", 500, 500, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/usb.png")), new Dimension(100, 100), true));

        } catch (IOException ioe) {
            System.err.println("Error loading items");
            ioe.printStackTrace();
        }
    }

    /**
     * Creates the given number of levels
     *
     * @param numLevels the number of levels to create
     */
    private static void generateLevels(int numLevels) {
        /*
		 * maybe shop always key door always boss room always other rooms
		 */

        for (int i = 0; i < numLevels; ++i) {
            boolean createdLockedRoom = false;

            // Create the rooms list
            ArrayList<Room> rooms = new ArrayList<>();

            // Create a copy of the list of enemies for this level
            ArrayList<Enemy> levelEnemies;
            if (levelNumber == 1) {
                levelEnemies = new ArrayList<>(scienceEnemyList);
            } else if (levelNumber == 2) {
                levelEnemies = new ArrayList<>(englishEnemyList);
            } else if (levelNumber == 3) {
                levelEnemies = new ArrayList<>(mathEnemyList);
            } else if (levelNumber == 4) {
                levelEnemies = new ArrayList<>(historyEnemyList);
            } else {
                System.err.println("Invalid level value: " + levelNumber);
                levelEnemies = null;
            }

            // Create a copy of the list of items for this level
            ArrayList<Item> levelItems = new ArrayList<>(itemList);

            //Make the first room have no enemies
            rooms.add(createInitialRoom(levelItems));
            // Create general rooms (4 rooms on 1st level increasing by
            // EXTRA_ROOMS_PER_LEVEL each time
            for (int j = 1; j < 3 + (levelNumber * EXTRA_ROOMS_PER_LEVEL); ++j) {
                Room r = createRoom(levelEnemies, levelItems);

                //Potentially make this room locked
                if (!createdLockedRoom) {
                    int chance = rand.nextInt(100);
                    if (chance < LOCKED_CHANCE) {
                        createdLockedRoom = true;
                        r.setLocked(true);
                    }
                }
                rooms.add(r);
            }
            // Roll for a shop and add it if successful or just add another
            // general room
            int roll = rand.nextInt(100);
            if (roll < SHOP_ROOM_CHANCE) {
                // TODO add shop (room with game object with a GameObject that
                // the player can interact with)
            } else {
                rooms.add(createRoom(levelEnemies, levelItems));
            }

            //Boss room
            rooms.add(createBossRoom());

            Level level = new Level(rooms);
            levels.add(level);
        }
    }

    /**
     * Creates the boss room for the level
     * @return the boss room for the level
     */
    private static Room createBossRoom() {
        ArrayList<Enemy> enemyList = new ArrayList<>();

        //Find the level and create the boss for that level
        if (levelNumber == 1) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/gissingfront.png"));
                enemyList.add(new Gissing(2, 5, 250, 500, 500, bossImage, new Dimension(87, 100), 5, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading gissing image file");
                ioe.printStackTrace();
            }
        } else if (levelNumber == 2) {
            //TODO add mr pomakov
        } else if (levelNumber == 3) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/marsellafront.png"));
                enemyList.add(new Marsella(2, 5, 250, 500, 500, bossImage, new Dimension(87, 100), 5, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading marsella image file");
                ioe.printStackTrace();
            }

            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/shimfront.png"));
                enemyList.add(new Shim(2, 5, 250, 500, 500, bossImage, new Dimension(87, 100), 5, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading shim image file");
                ioe.printStackTrace();
            }
        }
        else if(levelNumber == 4)
        {
            //TODO: add mr mack
        } else if (levelNumber == 5) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/ridoutfront.png"));
                enemyList.add(new Ridout(2, 5, 250, 500, 500, bossImage, new Dimension(87, 100), 5, 2, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading ridout image file");
                ioe.printStackTrace();
            }
        } else {
            System.err.println("Invalid level value: " + levelNumber);
        }

        Room room = new Room(enemyList, new ArrayList<>(), new ArrayList<>(), player, false, Room.RoomType.BOSS);
        return room;
    }

    private static Room createInitialRoom(ArrayList<Item> items) {
        // Have a chance to have an item in the room
        ArrayList<Item> itemList = new ArrayList<>();
        int itemChance = rand.nextInt(100);
        if (itemChance < ITEM_CHANCE) {
            //TODO: uncomment when have enough items
            //itemList.add(items.remove(rand.nextInt(items.size())));
            itemList.add(items.get(rand.nextInt(items.size())));
        }
        return new Room(new ArrayList<>(), itemList, new ArrayList<>(), player, false, Room.RoomType.NORMAL);
    }

    private static Room createRoom(ArrayList<Enemy> enemies,
                                   ArrayList<Item> items) {
        // Choose the enemy for this room
        //TODO: uncomment when have enough enemies
        //Enemy e = enemies.remove(rand.nextInt(enemies.size()));
        Enemy e = enemies.get(rand.nextInt(enemies.size()));

        // Create a number of enemies
        ArrayList<Enemy> enemyList = new ArrayList<>();
        int numEnemies = MIN_ENEMIES + rand.nextInt(ADDITIONAL_ENEMIES);
        for (int enemy = 0; enemy < numEnemies; ++enemy) {
            Enemy j = new Enemy(e);
            boolean overlap = true;
            // Make sure enemies don't spawn on top of each other
            while (overlap) {
                overlap = false;
                j.setX(rand.nextInt(SPAWN_X_WIDTH) + SPAWN_X_LOWER);
                j.setY(rand.nextInt(SPAWN_Y_HEIGHT) + SPAWN_Y_LOWER);
                // Go through all enemies
                for (int n = 0; n < enemy; n++) {
                    // Overlap found
                    if (j.getHitBox().intersects(enemyList.get(n).getHitBox())) {
                        overlap = true;
                        break;
                    }
                }
            }
            enemyList.add(j);
        }

        // Have a chance to have an item in the room
        ArrayList<Item> itemList = new ArrayList<>();
        int itemChance = rand.nextInt(100);
        if (itemChance < ITEM_CHANCE) {
            //TODO: uncomment when have enough items
            //itemList.add(items.remove(rand.nextInt(items.size())));
            itemList.add(items.get(rand.nextInt(items.size())));
        }

        Room room = new Room(enemyList, itemList, new ArrayList<>(), player, false, Room.RoomType.NORMAL);
        return room;
    }

    public static void advanceLevel()
    {
    	currentLevel = levels.get(++levelNumber - 1);
    }
    
    public static void update() {
        currentLevel.update();
    }

    public static void draw(Graphics g) {
        currentLevel.draw(g);
    }
    
}