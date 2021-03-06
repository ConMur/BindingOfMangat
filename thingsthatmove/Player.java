package thingsthatmove;

import item.Item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import levels.LevelManager;

/**
 * The player for the game. It can move, shoot projecties and use items
 *
 * @author Matthew Sun, Connor Murphy
 */
public class Player extends MoveableObject {
    private int projectile;
    private Item currentItem;
    private boolean movingNorth;
    private boolean movingWest;
    private boolean movingEast;
    private boolean movingSouth;
    private ArrayList<Projectile> currentProjectiles;
    private Item bomb;
    private long bombPlaceTime;
    private Thread pThread;
    private boolean isShooting;
    private boolean takenDMG;
    private Dimension movementHitbox;
    private int bombX, bombY;

    private GameObject michaelBay;

    private boolean explosionActive;
    private long bombExplosionTime;
    private final long BOMB_TIME = 2000;

    private Font itemTextFont = new Font("LetterOMatic!", Font.PLAIN, 30);

    // Items
    private int numKeys, numBombs, numCoins;

    // Projectiles
    private long lastFireTime, lastDmgTime;
    private int fireRate, invincibleTime;
    // Miliseconds between firing
    private final int PROJECTILE_ONE_RATE = 500;
    private final int PROJECTILE_TWO_RATE = 300;
    private final int PROJECTILE_THREE_RATE = 30;
    private final int PROJECTILE_FOUR_RATE = 30;
    private final int PROJECTILE_FIVE_RATE = 3;

    private BufferedImage mangatFront, mangatBack, mangatLeft, mangatRight;
    private BufferedImage mangatHurtFront, mangatHurtBack, mangatHurtLeft,
            mangatHurtRight, bombImage, michaelBayImage;
    private BufferedImage fullHeart, emptyHeart;
    private BufferedImage p1Image, p2Image, p4Image;

    /**
     * Creates a player with the given values
     *
     * @param dmg        the damage the player can do
     * @param hp         the hp the player starts with
     * @param speed      the speed the player moves at
     * @param x          the x position of the player
     * @param y          the y position of the player
     * @param i          the image for the player
     * @param s          the size of the player
     * @param maxHP      the maximum hp of the player
     * @param projectile the type of projectile the player shoots
     * @param item       the item the player currently has
     * @param shadowSize the size of the player's shadow
     * @param xShadow    the x position of the player's shadow
     * @param yShadow    the y position of the player's shadow
     */
    public Player(int dmg, int hp, int speed, int x, int y, Image i,
                  Dimension s, int maxHP, int projectile, Item item,
                  Dimension shadowSize, int xShadow, int yShadow) {
        super(dmg, hp, speed, x, y, i, s, maxHP, shadowSize, xShadow, yShadow);

        // Create the text font
        try {
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    LevelManager.class
                            .getResourceAsStream("/fonts/ltromatic.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Error loading font");
            e.printStackTrace();
        }

        currentProjectiles = new ArrayList<Projectile>();
        this.projectile = projectile;
        this.currentItem = item;
        fireRate = PROJECTILE_ONE_RATE;
        invincibleTime = 1500;
        explosionActive = false;
        boolean movingNorth = false;
        boolean movingWest = false;
        boolean movingEast = false;
        boolean movingSouth = false;
        takenDMG = false;
        michaelBay = null;

        numKeys = 2;
        numBombs = 1;
        numCoins = 2;

        //Load the images for the player
        try {
            bombImage = ImageIO.read(LevelManager.class
                    .getResourceAsStream("/images/items/bomb.png"));
            michaelBayImage = ImageIO.read(LevelManager.class
                    .getResourceAsStream("/images/michaelbay.png"));
            p1Image = ImageIO.read(getClass().getResourceAsStream(
					"/images/projectiles/player/projectile1.png"));
            p2Image = ImageIO.read(getClass().getResourceAsStream(
					"/images/items/brokenpencil.png"));
            p4Image = ImageIO.read(getClass().getResourceAsStream(
					"/images/items/fireflower.png"));
            mangatFront = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/mangatfront.png"));
            mangatBack = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/mangatback.png"));
            mangatLeft = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/mangatleft.png"));
            mangatRight = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/mangatright.png"));
            mangatHurtFront = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/hurt/mangatfront.png"));
            mangatHurtBack = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/hurt/mangatback.png"));
            mangatHurtLeft = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/hurt/mangatleft.png"));
            mangatHurtRight = ImageIO.read(getClass().getResourceAsStream(
                    "/images/mangat/hurt/mangatright.png"));
            fullHeart = ImageIO.read(getClass().getResourceAsStream(
                    "/images/fullheart.png"));
            emptyHeart = ImageIO.read(getClass().getResourceAsStream(
                    "/images/emptyheart.png"));
            setImage(mangatFront);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the bomb item the player has
     *
     * @return the bomb item the player has
     */
    public Item getBomb() {
        return bomb;
    }

    /**
     * Makes the player go invincible
     */
    public void goInvincible() {
        takenDMG = true;
    }

    /**
     * Decreases the player's hp by the given amount if they are not invincible
     *
     * @param amount the amount to decrease the player's hp by
     */
    public void takeDamage(int amount) {
        if (System.currentTimeMillis() - lastDmgTime > invincibleTime) {
            super.takeDamage(amount);
            lastDmgTime = System.currentTimeMillis();
            takenDMG = true;

            if (getImage() == mangatFront) {
                setImage(mangatHurtFront);
            } else if (getImage() == mangatBack) {
                setImage(mangatHurtBack);
            } else if (getImage() == mangatLeft) {
                setImage(mangatHurtLeft);
            } else if (getImage() == mangatRight) {
                setImage(mangatHurtRight);
            }

            if (currentItem == null) {
            } else if (currentItem.getName() == "ankh"
                    && this.getCurrentHP() == 0) {
                System.out.println("REVIVE");
                super.takeDamage(0);
                this.heal(10);
                currentItem = null;
            }
        }
    }

    /**
     * Returns the hitbox for the player
     *
     * @return the hitbox for the player
     */
    public Rectangle getHitBox() {
        return new Rectangle((int) getX(), (int) getY()
                + (int) (getSize().getHeight() * 0.5), (int) getSize()
                .getWidth(), (int) getSize().getHeight());
    }

    /**
     * Returns a list of projectile that the play has shot
     *
     * @return a list of projectile that the play has shot
     */
    public ArrayList<Projectile> getAllPlayerProjectiles() {
        return currentProjectiles;
    }

    /**
     * Sets the current list of projectiles the player has to the given list
     *
     * @param p the list of projectiles to set the player's to
     */
    public void setCurrentProjectiles(ArrayList<Projectile> p) {
        currentProjectiles = p;
    }

    /**
     * Sets the projectile number of the player
     *
     * @param projectileNumber the projectile number
     */
    public void setProjectile(int projectileNumber) {
        projectile = projectileNumber;
        
        if (projectileNumber == 4)
        	this.setFireRate(50);
        else if (projectileNumber == 1 || projectileNumber == 2)
        	setFireRate(500);
    }
    
    /**
     * Sets the firerate of player
     * @param f the fire rate
     */
    public void setFireRate(int f)
    {
    	fireRate = f;
    }

    /**
     * Shoots a projectile in the given direction
     *
     * @param direction the direction to shoot the projectile in
     */
    public void shootProjectile(char direction) {
        if (projectile == 1) {
            if (System.currentTimeMillis() - lastFireTime > fireRate) {
                Projectile p = new Projectile(getProjectile(), direction);

                // When facing back, put the projectile near the top of the head
                if (getImage() == mangatBack || getImage() == mangatHurtBack) {
                    p.setY(getY());
                } else {
                    // For any other direction, have the projectile start in the
                    // centre of the player's head
                    p.setY(getY() + 50);
                }
                p.setX(getX() + 20);

                currentProjectiles.add(p);
                lastFireTime = System.currentTimeMillis();
            }
        } else if (projectile == 2)
            shootTripleProjectiles(direction);
        else if (projectile == 4)
        {
        	  if (System.currentTimeMillis() - lastFireTime > fireRate) {
                  Projectile p = new Projectile(getProjectile(), direction);

                  // When facing back, put the projectile near the top of the head
                  if (getImage() == mangatBack || getImage() == mangatHurtBack) {
                      p.setY(getY());
                  } else {
                      // For any other direction, have the projectile start in the
                      // centre of the player's head
                      p.setY(getY() + 50);
                  }
                  p.setX(getX() + 20);

                  currentProjectiles.add(p);
                  lastFireTime = System.currentTimeMillis();
              }
        }
    }

    /**
     * Shoots three projectiles from the player. One in front of the player, one on the left diagonal and
     * one on the right diagonal
     *
     * @param currentDirection the direction the player is facing
     */
    public void shootTripleProjectiles(char currentDirection) {
        if (System.currentTimeMillis() - lastFireTime > fireRate) {
            Projectile p = new Projectile(getProjectile(), currentDirection);
            p.setX(getX() + 20);
            p.setY(getY() + 25);
            currentProjectiles.add(p);

            Projectile p2 = new Projectile(getProjectile(),
                    getTopDirection(currentDirection));
            p2.setX(getX() + 20);
            p2.setY(getY() + 25);
            currentProjectiles.add(p2);

            Projectile p3 = new Projectile(getProjectile(),
                    getBotDirection(currentDirection));
            p3.setX(getX() + 20);
            p3.setY(getY() + 25);
            currentProjectiles.add(p3);

            lastFireTime = System.currentTimeMillis();
        }
    }

    /**
     * Returns the direction that the player is facing
     *
     * @return the direction that the player is facing
     */
    public char getTopDirection(char currentDirection) {
        if (currentDirection == 'N')
            return '1';
        else if (currentDirection == 'E')
            return '1';
        else if (currentDirection == 'S')
            return '2';
        else if (currentDirection == 'W')
            return '4';
        else if (currentDirection == '1')
            return 'N';
        else if (currentDirection == '2')
            return 'E';
        else if (currentDirection == '3')
            return 'W';
        else
            return 'N';
    }

    /**
     * Returns the position behind the player
     *
     * @return the position behind the player
     */
    public char getBotDirection(char currentDirection) {
        if (currentDirection == 'N')
            return '4';
        else if (currentDirection == 'E')
            return '2';
        else if (currentDirection == 'S')
            return '3';
        else if (currentDirection == 'W')
            return '3';
        else if (currentDirection == '1')
            return 'E';
        else if (currentDirection == '2')
            return 'S';
        else if (currentDirection == '3')
            return 'S';
        else
            return 'W';
    }

    /**
     * Returns the projectile type
     *
     * @return the projectile number
     */
    public int getProjectile() {
        return projectile;
    }

    /**
     * @return the current item in possession
     */
    public Item getCurrentItem() {
        return currentItem;
    }

    public String getCurrentItemName() {
        return currentItem.getName();
    }

    public boolean hasItem() {
        if (currentItem != null)
            return true;
        return false;
    }

    /**
     * Sets the players current item to the given item or null if there is no
     * item
     *
     * @param i the item to give to the player
     */
    public void setItem(Item i) {
        // Automatically consumed upon pickup
        if (i == null)
            currentItem = i;
        else if (i.getName() == "key")
            numKeys++;
        else if (i.getName() == "bomb")
            numBombs++;
        else if (i.getName() == "silvercoin")
            numCoins++;
        else if (i.getName() == "goldcoin")
            numCoins += 5;
            // Manually consumed items
        else
            currentItem = i;
    }

    /**
     * Applies affects of the current item to the player
     */
    public void useItem() {
        currentItem.applyEffects(this);
        setItem(null);
        // FINISH ITEMS
    }

    /**
     * Returns the number of keys the player has
     *
     * @return the number of keys the player has
     */
    public int getNumKeys() {
        return numKeys;
    }

    /**
     * Sets the number of keys the player has
     *
     * @param numKeys the number of keys the player will have
     */
    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    /**
     * Adds the given number of keys to the player's total
     *
     * @param keys the amount of keys to add
     */
    public void addKeys(int keys) {
        numKeys += keys;
    }

    /**
     * Returns if the player has keys
     *
     * @return if the player has keys
     */
    public boolean hasKeys() {
        return numKeys > 0;
    }
    
    /**
     * Clears the player bombs
     */
    public void clearBombs () {
    	explosionActive = false;
    	michaelBay = null;
    	bomb = null;
    }

    /**
     * Returns the number of bombs the player has
     *
     * @return the number of bombs the player has
     */
    public int getNumBombs() {
        return numBombs;
    }

    /**
     * Adds the given number of bombs to the player's total
     *
     * @param bombs the number of bombs to add
     */
    public void addBombs(int bombs) {
        numBombs += bombs;
    }

    /**
     * Sets the number of bombs the player has to the given value
     *
     * @param numBombs the number of bombs the player will have
     */
    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

    /**
     * Returns the number of coins the player has
     *
     * @return the number of coins the player has
     */
    public int getNumCoins() {
        return numCoins;
    }

    /**
     * Adds the given number of coins to the player's total
     *
     * @param coins the number of coins to add
     */
    public void addCoins(int coins) {
        numCoins += coins;
    }

    /**
     * Sets the number of coins the player has to the given value
     *
     * @param numCoins the number of coins the player will have
     */
    public void setNumCoin(int numCoins) {
        this.numCoins = numCoins;
    }

    /**
     * Returns if the player is moving north
     *
     * @return if the player is moving north
     */
    public boolean isMovingNorth() {
        return movingNorth;
    }

    /**
     * Returns if the player is moving south
     *
     * @return if the player is moving south
     */
    public boolean isMovingSouth() {
        return movingSouth;
    }

    /**
     * Returns if the player is moving east
     *
     * @return if the player is moving east
     */
    public boolean isMovingEast() {
        return movingEast;
    }

    /**
     * Returns if the player is moving west
     *
     * @return if the player is moving west
     */
    public boolean isMovingWest() {
        return movingWest;
    }

    /**
     * Updates the position of the player's projectiles and removes them if the should be removed
     */
    public void updateProjectiles() {
        // Update projectiles
        int removedProjectiles = 0;
        for (int i = 0; i < currentProjectiles.size(); ++i) {
            Projectile p = currentProjectiles.get(i - removedProjectiles);
            p.update();
            if (p.isDeadProjectile()) {
                currentProjectiles.remove(i - removedProjectiles);
                ++removedProjectiles;
            }
        }
    }

    /**
     * Moves the player, shoots projectiles, takes damage and places bombs
     *
     * @param rocks rocks that the player can collide with
     */
    public void update(ArrayList<GameObject> rocks) {
        if (movingNorth && !collidesWithRocks(rocks)) {
            moveNorth();
            if (collidesWithRocks(rocks)) {
                moveSouth();
            }
        }
        if (movingSouth) {
            moveSouth();
            if (collidesWithRocks(rocks)) {
                moveNorth();
            }
        }
        if (movingWest) {
            moveWest();
            if (collidesWithRocks(rocks)) {
                moveEast();
            }
        }
        if (movingEast) {
            moveEast();
            if (collidesWithRocks(rocks)) {
                moveWest();
            }
        }

        if (isShooting) {
            if (movingNorth && movingEast)
                shootProjectile('1');
            else if (movingSouth && movingEast)
                shootProjectile('2');
            else if (movingSouth && movingWest)
                shootProjectile('3');
            else if (movingNorth && movingWest)
                shootProjectile('4');
            else if (getImage() == mangatFront || getImage() == mangatHurtFront)
                shootProjectile('S');
            else if (getImage() == mangatBack || getImage() == mangatHurtBack)
                shootProjectile('N');
            else if (getImage() == mangatLeft || getImage() == mangatHurtLeft)
                shootProjectile('W');
            else if (getImage() == mangatRight || getImage() == mangatHurtRight)
                shootProjectile('E');
            else
                System.err.println("no projectile added");
        }

        // Update invincibility if havent already
        if (takenDMG) {
            if (System.currentTimeMillis() - lastDmgTime > invincibleTime) {
                takenDMG = false;
                if (getImage() == mangatHurtFront) {
                    setImage(mangatFront);
                } else if (getImage() == mangatHurtBack) {
                    setImage(mangatBack);
                } else if (getImage() == mangatHurtLeft) {
                    setImage(mangatLeft);
                } else if (getImage() == mangatHurtRight) {
                    setImage(mangatRight);
                }
            }
        }

        if (bomb != null) {
            if (System.currentTimeMillis() - bombPlaceTime > BOMB_TIME) {
                System.out.println("BOMB EXPLOSION");
                michaelBay = new GameObject((int) bomb.getX(),
                        (int) bomb.getY(), michaelBayImage, new Dimension(80,
                        80), new Dimension(80, 80), 0, 0);
                bomb = null;
                explosionActive = true;

                bombExplosionTime = System.currentTimeMillis();
            }
        }

        if (explosionActive
                && System.currentTimeMillis() - bombExplosionTime > BOMB_TIME) {
            michaelBay = null;
            explosionActive = false;
        }

    }

    /**
     * Returns if the player collides with a rock
     *
     * @param rocks the potential rocks to collide with
     * @return if the player collides with a rock
     */
    private boolean collidesWithRocks(ArrayList<GameObject> rocks) {
        for (GameObject rock : rocks) {
            if (getShadowHitbox().intersects(rock.getRockHitBox())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses coins to become invincible
     * @return if coins were used to become invincible
     */
    public boolean useCoins() {
        if (numCoins >= 2) {
            numCoins -= 2;
            return true;
        }
        return false;
    }

    /**
     * Places a bomb if there is one to place
     * @return if a bomb was placed
     */
    public boolean useBomb() {
        if (numBombs >= 1 && bomb == null) {
            numBombs--;
            return true;
        }
        return false;
    }

    /**
     * Returns the explosion game object
     * @return the explosion game object
     */
    public GameObject getMichaelBay() {
        if (explosionActive)
            return michaelBay;
        return null;
    }

    /**
     * Returns if there is an explosion active
     * @return if there is an explosion active
     */
    public boolean hasMichaelBay() {
        if (michaelBay == null)
            return false;
        return true;
    }

    /**
     * Draws the player, bomb, explosion, HUD info and projectiles
     * @param g the graphics to draw to
     */
    public void draw(Graphics g) {
        // Draw player
        g.drawImage(getImage(), (int) getX(), (int) getY(), null);

        // Movement hitbox
        g.setColor(Color.GREEN);
        // g.drawRect((int) getShadowHitbox().getX(), (int)
        // getShadowHitbox().getY(), (int)getShadowHitbox().getWidth(), (int)
        // getShadowHitbox().getHeight());

        // Projectile collision hitbox
        g.setColor(Color.red);
        // Rectangle r = getHitBox();
        // g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(),
        // (int) r.getHeight());

        if (bomb != null) {
            g.drawImage(bombImage, (int) bomb.getX(), (int) bomb.getY(), null);
        }

        if (explosionActive)
            g.drawImage(michaelBayImage, (int) michaelBay.getX(), (int) michaelBay.getY(), null);

        // Draw HP level in the HUD
        for (int n = 0; n < this.getMaxHP(); n++) {
            if (n < 5) {
                if (n < this.getCurrentHP())
                    g.drawImage(fullHeart, 820 + (30 * n), 70, null);
                else
                    g.drawImage(emptyHeart, 820 + (30 * n), 70, null);
            } else if (n < 10) {
                if (n < this.getCurrentHP())
                    g.drawImage(fullHeart, 670 + (30 * n), 70 + 30, null);
                else
                    g.drawImage(emptyHeart, 670 + (30 * n), 70 + 30, null);
            } else {
                if (n < this.getCurrentHP())
                    g.drawImage(fullHeart, 520 + (30 * n), 130, null);
                else
                    g.drawImage(emptyHeart, 520 + (30 * n), 130, null);
            }
        }

        // Draw the item in the HUD
        if (hasItem())
        {
        	if (currentItem.getName().equals("fireflower"))
        		g.drawImage(currentItem.getImage(), 700, 75, null);
        	else if (currentItem.getName().equals("ankh"))
        		g.drawImage(currentItem.getImage(), 717, 85, null);
        	else if (currentItem.getName().equals("blockofwood"))
        		g.drawImage(currentItem.getImage(), 705, 105, null);
        	else if (currentItem.getName().equals("bombguidebook"))
        		g.drawImage(currentItem.getImage(), 717, 84, null);
        	else if (currentItem.getName().equals("brokenpencil"))
        		g.drawImage(currentItem.getImage(), 709, 84, null);
        	else if (currentItem.getName().equals("c++"))
        		g.drawImage(currentItem.getImage(), 709, 84, null);
        	else if (currentItem.getName().equals("caffood"))
        		g.drawImage(currentItem.getImage(), 700, 95, null);
        	else if (currentItem.getName().equals("compscisweater"))
        		g.drawImage(currentItem.getImage(), 690, 75, null);
        	else if (currentItem.getName().equals("halo"))
        		g.drawImage(currentItem.getImage(), 700, 95, null);
        	else if (currentItem.getName().equals("lotteryticket"))
        		g.drawImage(currentItem.getImage(), 690, 85, null);
        	else if (currentItem.getName().equals("masterkey"))
        		g.drawImage(currentItem.getImage(), 720, 75, null);
        	else if (currentItem.getName().equals("usb"))
        		g.drawImage(currentItem.getImage(), 690, 84, null);
        	else if (currentItem.getName().equals("socksandsandals"))
        		g.drawImage(currentItem.getImage(), 700, 75, null);
        	else if (currentItem.getName().equals("wheyprotein"))
        		g.drawImage(currentItem.getImage(), 700, 75, null);
        	else if (currentItem.getName().equals("steak"))
        		g.drawImage(currentItem.getImage(), 695, 85, null);
        	else if (currentItem.getName().equals("duedateextension"))
        		g.drawImage(currentItem.getImage(), 705, 75, null);
        }
        
        // Draw the projectile in the HUD
        if (this.getProjectile() == 1)
        	g.drawImage(p1Image, 580, 100, null);
        else if (getProjectile() == 2)
        	g.drawImage(p2Image, 565, 80, null);
        else if (getProjectile() == 4)
        	g.drawImage(p4Image, 555, 70, null);

        // Draw number of keys, bombs, coins
        g.setFont(itemTextFont);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(numCoins), 435, 63);
        g.drawString(Integer.toString(numBombs), 435, 117);
        g.drawString(Integer.toString(numKeys), 435, 173);

        // Draw all projectiles currently on the screen
        for (Projectile p : currentProjectiles) {
            p.draw(g);
        }
    }

    /**
     * Called when a key is pressed. Moves the player, shoots a projectile, places a bomb, goes invincible
     * or uses an item depending on the key pressed
     * @param key the key code of the key that was pressed
     */
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_W) {
            if (!movingNorth) {
                if (!takenDMG)
                    setImage(mangatBack);
                else
                    setImage(mangatHurtBack);
            }
            movingNorth = true;
        }
        if (key == KeyEvent.VK_A) {
            if (!movingWest) {
                if (!takenDMG)
                    setImage(mangatLeft);
                else
                    setImage(mangatHurtLeft);
            }
            movingWest = true;
        }
        if (key == KeyEvent.VK_S) {
            if (!movingSouth) {
                if (!takenDMG)
                    setImage(mangatFront);
                else
                    setImage(mangatHurtFront);
            }
            movingSouth = true;
        }
        if (key == KeyEvent.VK_D) {
            if (!movingEast) {
                if (!takenDMG)
                    setImage(mangatRight);
                else
                    setImage(mangatHurtRight);
            }
            movingEast = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            isShooting = true;
        }

        if (key == KeyEvent.VK_ENTER) {
            if (hasItem())
                useItem();
        }

        if (key == KeyEvent.VK_Q) {
            // There are enough coins to go invincible
            if (useCoins())
                takeDamage(0);
        }

        if (key == KeyEvent.VK_E) {
            if (useBomb() && bomb == null) {
                bomb = new Item("bomb", (int) getX(), (int) getY(), bombImage,
                        new Dimension(58, 38), true);
                bombX = (int) bomb.getX();
                bombY = (int) bomb.getY();
                bombPlaceTime = System.currentTimeMillis();
            }
        }

    }

    /**
     * Called when a key is released. Resets some actions that were caused by a key press
     * @param key the key that was released
     */
    public void keyReleased(int key) {
        if (key == KeyEvent.VK_W) {
            movingNorth = false;
            setPositionFacing();
        }
        if (key == KeyEvent.VK_A) {
            movingWest = false;
            setPositionFacing();
        }
        if (key == KeyEvent.VK_S) {
            movingSouth = false;
            setPositionFacing();
        }
        if (key == KeyEvent.VK_D) {
            movingEast = false;
            setPositionFacing();
        }
        if (key == KeyEvent.VK_SPACE)
            isShooting = false;
    }

    /**
     * Sets the direction that mangat is facing based on the direction he is
     * moving in
     */
    private void setPositionFacing() {
        if (movingNorth) {
            if (!takenDMG)
                setImage(mangatBack);
            else
                setImage(mangatHurtBack);
        } else if (movingSouth) {
            if (!takenDMG)
                setImage(mangatFront);
            else
                setImage(mangatHurtFront);
        } else if (movingEast) {
            if (!takenDMG)
                setImage(mangatRight);
            else
                setImage(mangatHurtRight);
        } else if (movingWest) {
            if (!takenDMG)
                setImage(mangatLeft);
            else
                setImage(mangatHurtLeft);
        }
    }

    /**
     * Clears all the projectiles that the player has
     */
    public void clearProjectiles() {
        currentProjectiles.clear();
    }
}
