package levels;

import item.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import states.GameStateManager;
import states.State;
import thingsthatmove.*;

import javax.imageio.ImageIO;


/**
 * Creates, updates and draws the levels in the game
 *
 * @author Connor Murphy, Matthew Sun
 */
public final class LevelManager {
    private static final int EXTRA_ROOMS_PER_LEVEL = 2;
    private static final int NUMBER_OF_LEVELS = 5;

    // The percent chance that there will be a shop this level
    private static final int SHOP_ROOM_CHANCE = 50;

    // The percent chance that there will be an item in the room
    private static final int ITEM_CHANCE = 10;
    private static final int LOCKED_ROOM_ITEM_BONUS = -(100 - ITEM_CHANCE);

    //The percent chance that a room will be locked
    private static final int LOCKED_CHANCE = 100;

    // The minimum amount of enemies in a level
    private static final int MIN_ENEMIES = 3;

    // The number of enemies more than the minimum that there can be in the
    // level
    private static final int ADDITIONAL_ENEMIES = 3;

    //The coordinates of the text that displays the current level
    private static final int LEVEL_TEXT_X = 15;
    private static final int LEVEL_TEXT_Y = 760;

    private static Font levelTextFont = new Font("LetterOMatic!", Font.PLAIN, 26);

    private static ArrayList<Level> levels;
    private static Level currentLevel;

    private static int levelNumber;
    private static boolean createdLockedRoom = false;
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

    /**
     * private constructor so that no one can access it
     */
    private LevelManager() {
    }

    /**
     * Loads all the resources and initializes all the variables needed by the program
     *
     * @param p a reference to the player
     */
    public static void init(Player p) {
        levels = new ArrayList<>();
        rand = new Random();
        player = p;

        //Initialize the specific items for different levels
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

        //Create the level text font
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, LevelManager.class.getResourceAsStream("/fonts/ltromatic.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Error loading level display font");
            e.printStackTrace();
        }


        generateLevels(NUMBER_OF_LEVELS - 1);

        // Set the current level to the first level
        currentLevel = levels.get(0);
        currentLevel.start();
    }

    /**
     * Adds enemies to the given lists of enemies
     * @param science the enemies for the science level
     * @param english the enemies for the english level
     * @param math the enemies for the math level
     * @param history the enemies for the history level
     */
    private static void populateEnemyLists(
            ArrayList<Enemy> science, ArrayList<Enemy> english,
            ArrayList<Enemy> math, ArrayList<Enemy> history) {
        // TODO: update as more enemies are added
        // Add the science enemies
        try {
            science.add(new EnemyEclipse());
            science.add(new EnemyStudent());
            science.add(new EnemyStudentCluster());

            science.add(new EnemyEinstein());
            science.add(new EnemyCell());
            science.add(new EnemyChem());
            science.add(new EnemyMolecule());
        } catch (IOException ioe) {
            System.err.println("Unable to load science enemies");
            ioe.printStackTrace();
        }
        // Add the english enemies
        try {
            english.add(new EnemyEclipse());
            english.add(new EnemyStudent());
            english.add(new EnemyStudentCluster());

            english.add(new EnemyBullshit());

        } catch (IOException ioe) {
            System.err.println("Unable to load english enemies");
            ioe.printStackTrace();
        }
        // Add the math enemies
        try {
            math.add(new EnemyEclipse());
            math.add(new EnemyStudent());
            math.add(new EnemyStudentCluster());

            math.add(new EnemyTextbook());
            math.add(new EnemyPi());
            math.add(new EnemyCalculator());
            math.add(new EnemyFunction());
        } catch (IOException ioe) {
            System.err.println("Unable to load math enemies");
            ioe.printStackTrace();
        }
        // Add the history enemies
        try {
            history.add(new EnemyEclipse());
            history.add(new EnemyStudent());
            history.add(new EnemyStudentCluster());
            
            history.add(new EnemyHat());
            history.add(new EnemyFlag());
            
        } catch (IOException ioe) {
            System.err.println("Unable to load history enemies");
            ioe.printStackTrace();
        }
    }

    /**
     * Adds items to the given list of items
     * @param items the list to add items to
     */
    private static void populateItemList(ArrayList<Item> items) {
        // TODO: update as more items are added
        // Add the items
        try {
            items.add(new Item("ankh", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/ankh.png")), new Dimension(39, 32), true));
            items.add(new Item("blockofwood", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/blockofwood.png")), new Dimension(60, 14), true));
            items.add(new Item("bombguidebook", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/bombguidebook.png")), new Dimension(39, 37), true));
            items.add(new Item("brokenpencil", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/brokenpencil.png")), new Dimension(60, 30), true));
            items.add(new Item("c++", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/c++.png")), new Dimension(58, 31), true));
            items.add(new Item("caffood", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/caffood.png")), new Dimension(69, 24), true));
            items.add(new Item("compscisweater", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/compscisweater.png")), new Dimension(87, 45), true));
            items.add(new Item("fireflower", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/fireflower.png")), new Dimension(81, 45), true));
//            items.add(new Item("glasses", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/glasses.png")), new Dimension(69, 21), true));
//            items.add(new Item("goldenmushroom", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/goldenmushroom.png")), new Dimension(80, 47), true));
            items.add(new Item("halo", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/halo.png")), new Dimension(72, 26), true));
//            items.add(new Item("icecube", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/icecube.png")), new Dimension(90, 59), true));
//            items.add(new Item("lightning", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/lightning.png")), new Dimension(54, 48), true));
            items.add(new Item("lotteryticket", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/lotteryticket.png")), new Dimension(88, 30), true));
//            items.add(new Item("mariomushroom", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/mariomushroom.png")), new Dimension(82, 41), true));
            items.add(new Item("masterkey", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/masterkey.png")), new Dimension(32, 42), true));
//            items.add(new Item("projector", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/projector.png")), new Dimension(88, 37), true));
            items.add(new Item("usb", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/usb.png")), new Dimension(83, 36), true));
        
            items.add(new Item("socksandsandals", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/socksandsandals.png")), new Dimension(69,40), true));
            items.add(new Item("wheyprotein", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/wheyprotein.png")), new Dimension(72,39), true));
            items.add(new Item("steak", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/steak.png")), new Dimension(81,34), true));
            items.add(new Item("duedateextension", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/duedateextension.png")), new Dimension(67,43), true));

            
            items.add(new Item("key", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/key.png")), new Dimension(40, 32), true));
            items.add(new Item("bomb", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/items/bomb.png")), new Dimension(58, 38), true));
            items.add(new Item("silvercoin", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/silvercoin.png")), new Dimension(53, 20), true));
            items.add(new Item("goldcoin", 450, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/goldcoin.png")), new Dimension(53, 20), true));

        } catch (IOException ioe) {
            System.err.println("Error loading items");
            ioe.printStackTrace();
        }
    }

    /**
     * Returns the items in the game
     * @return the items in the game
     */
    public static ArrayList<Item> getItems() {
        return itemList;
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

        for (int i = 1; i < numLevels + 1; ++i) {
            createdLockedRoom = false;

            // Create the rooms list
            ArrayList<Room> rooms = new ArrayList<>();

            // Create a copy of the list of enemies for this level
            ArrayList<Enemy> levelEnemies = null;
            if (i == 1) {
                levelEnemies = new ArrayList<>(scienceEnemyList);
            } else if (i == 3) {
                levelEnemies = new ArrayList<>(englishEnemyList);
            } else if (i == 2) {
                levelEnemies = new ArrayList<>(mathEnemyList);
            } else if (i == 4) {
                levelEnemies = new ArrayList<>(historyEnemyList);
            }
            else {
                System.err.println("Invalid level value: " + i);
                levelEnemies = null;
            }

            // Create a copy of the list of items for this level
            ArrayList<Item> levelItems = new ArrayList<>(itemList);

            //Make the first room have no enemies
            rooms.add(createInitialRoom(levelItems));
            // Create general rooms (4 rooms on 1st level increasing by
            // EXTRA_ROOMS_PER_LEVEL each time
            for (int j = 1; j < 3 + (i * EXTRA_ROOMS_PER_LEVEL); ++j) {
                Room r = createRoom(levelEnemies, levelItems);
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
            rooms.add(createBossRoom(i, levelItems));

            Level level = new Level(rooms);
            levels.add(level);
        }

        //Create the final boss level (an empty room and a boss room)
        ArrayList<Item> levelItems = new ArrayList<>(itemList);
        Room startRoom = createInitialRoom(levelItems);
        Room boss = createBossRoom(5, levelItems);
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(startRoom);
        rooms.add(boss);
        levels.add(new Level(rooms));
    }

    /**
     * Creates the boss room for the level
     *
     * @param levelNumber the level number to generate a boss for
     * @return the boss room for the level
     */
    private static Room createBossRoom(int levelNumber, ArrayList<Item> items) {
        ArrayList<Enemy> enemyList = new ArrayList<>();

        //Find the level and create the boss for that level
        if (levelNumber == 1) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/gissingfront.png"));
                enemyList.add(new Gissing(1, 100, 250, 500, 500, bossImage, new Dimension(87, 60), 100, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading gissing image file");
                ioe.printStackTrace();
            }
        } else if (levelNumber == 3) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/pomakofront.png"));
                enemyList.add(new Pomakov(1, 150, 250, 500, 500, bossImage, new Dimension(75, 64), 150, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading gissing image file");
                ioe.printStackTrace();
            }
        } else if (levelNumber == 2) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/marsellafront.png"));
                enemyList.add(new Marsella(1, 100, 250, 500, 500, bossImage, new Dimension(93, 67), 100, true, false));
            } catch (IOException ioe) {
                System.err.println("Error loading marsella image file");
                ioe.printStackTrace();
            }

            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/shimfront.png"));
                enemyList.add(new Shim(1, 100, 250, 500, 500, bossImage, new Dimension(89, 51), 100, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading shim image file");
                ioe.printStackTrace();
            }
        } else if (levelNumber == 4) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/macfront.png"));
                enemyList.add(new Mack(1, 400, 250, 500, 500, bossImage, new Dimension(85, 53), 400, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading mack image file");
                ioe.printStackTrace();
            }
        } else if (levelNumber == 5) {
            try {
                BufferedImage bossImage = ImageIO.read(LevelManager.class.getResourceAsStream("/images/bosses/ridoutfront.png"));
                enemyList.add(new Ridout(1, 200, 250, 500, 500, bossImage, new Dimension(128, 79), 200, 1, true, true));
            } catch (IOException ioe) {
                System.err.println("Error loading ridout image file");
                ioe.printStackTrace();
            }
        } else {
            System.err.println("Invalid level value: " + levelNumber);
        }

        RockPattern rp;
        boolean emptyRockPattern = rand.nextBoolean();
        if (emptyRockPattern) {
            rp = new RockPattern(0);
        } else {
            rp = new RockPattern(5);
        }

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(items.get(rand.nextInt(items.size())));

        Room room = new Room(enemyList, itemList, new ArrayList<>(), player, false, Room.RoomType.BOSS, rp);
        return room;
    }

    /**
     * Creates the initial room which is a room with no enemies
     * @param items the items that the room can have
     * @return the initial room
     */
    private static Room createInitialRoom(ArrayList<Item> items) {
        // Have a chance to have an item in the room
        ArrayList<Item> itemList = new ArrayList<>();
        int itemChance = rand.nextInt(100);
        if (itemChance < ITEM_CHANCE) {
            //TODO: uncomment when have enough items
            //itemList.add(items.remove(rand.nextInt(items.size())));
            itemList.add(items.get(rand.nextInt(items.size())));
        }
        RockPattern rp = new RockPattern(0);
        return new Room(new ArrayList<>(), itemList, new ArrayList<>(), player, false, Room.RoomType.NORMAL, rp);
    }

    /**
     * Creates a general room with enemies, items, a rock pattern and a chance to be locked
     * @param enemies the list of enemies this level has
     * @param items the list of items this level has
     * @return the room created
     */
    private static Room createRoom(ArrayList<Enemy> enemies,
                                   ArrayList<Item> items) {
        boolean thisRoomLocked = false;

        //Potentially make this room locked
        if (!createdLockedRoom) {
            int chance = rand.nextInt(100);
            if (chance < LOCKED_CHANCE) {
                createdLockedRoom = true;
                thisRoomLocked = true;
            }
        }

        //Set the rock pattern for this room
        RockPattern rp;
        if (thisRoomLocked) {
            rp = new RockPattern(1);
        } else {
            int pattern = rand.nextInt(RockPattern.getNumRockPatterns());
            rp = new RockPattern(pattern);
        }

        ArrayList<GameObject> rocks = rp.getRocks();
        ArrayList<Enemy> enemyList = new ArrayList<>();
        ArrayList<Point> spawnLocations = rp.getSpawnLocations();

        if (!thisRoomLocked) {
            // Choose the enemy for this room
            //TODO: uncomment when have enough enemies
            //Enemy e = enemies.remove(rand.nextInt(enemies.size()));
            Enemy e = enemies.get(rand.nextInt(enemies.size()));

            // Create a number of enemies
            int numEnemies = MIN_ENEMIES + rand.nextInt(ADDITIONAL_ENEMIES);
            for (int enemy = 0; enemy < numEnemies; ++enemy) {
                Enemy j = new Enemy(e);
                Point spawnPoint = spawnLocations.remove(rand.nextInt(spawnLocations.size()));
                j.setX(spawnPoint.getX());
                j.setY(spawnPoint.getY());
                enemyList.add(j);
            }
        }

        // Have a chance to have an item in the room
        ArrayList<Item> itemList = new ArrayList<>();
        int itemChance = rand.nextInt(100);

        // Spawn item randomly and locked rooms always have items
        if (thisRoomLocked || itemChance < ITEM_CHANCE) {
            itemList.add(items.get(rand.nextInt(items.size())));
        }
        Room room;
        if (thisRoomLocked) {
            room = new Room(enemyList, itemList, new ArrayList<>(), player, true, Room.RoomType.NORMAL, rp);
        } else {
            room = new Room(enemyList, itemList, new ArrayList<>(), player, false, Room.RoomType.NORMAL, rp);
        }
        return room;
    }

    /**
     * Advances the level to the next one. If the level advanced to is more than the total levels, it notifies the
     * player that they won
     */
    public static void advanceLevel() {
        currentLevel.stopAllRooms();
        ++levelNumber;

        System.out.println("level number " + levelNumber + " number of levels "  + NUMBER_OF_LEVELS);
        //If there are no more levels, the player won
        if (levelNumber > NUMBER_OF_LEVELS) {
            //Player won
            GameStateManager.setState(State.WIN);
            return;
        }
        currentLevel = levels.get(levelNumber - 1);
        currentLevel.start();
    }

    /**
     * Updates the current level
     */
    public static void update() {
        currentLevel.update();
    }

    /**
     * Draws the current level and text in the bottom left corner that displays the current level's name
     * @param g the graphics to draw to
     */
    public static void draw(Graphics g) {
        currentLevel.draw(g);


        g.setColor(Color.BLACK);
        g.fillRect(0, 720, 400, 300);
        g.setFont(levelTextFont);
        g.setColor(Color.WHITE);
        if (levelNumber == 1)
            g.drawString("Science Department", LEVEL_TEXT_X, LEVEL_TEXT_Y);
        else if (levelNumber == 2)
            g.drawString("Math Department", LEVEL_TEXT_X, LEVEL_TEXT_Y);
        else if (levelNumber == 3)
            g.drawString("English Department", LEVEL_TEXT_X, LEVEL_TEXT_Y);
        else if (levelNumber == 4)
            g.drawString("History Department", LEVEL_TEXT_X, LEVEL_TEXT_Y);
        else if (levelNumber == 5)
            g.drawString("Ridout's Office", LEVEL_TEXT_X, LEVEL_TEXT_Y);
    }

}