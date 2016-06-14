package thingsthatmove;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import util.Util;

/**
 * The super class for all enemies in the program. An enemy can move and shoot projectiles.
 *
 * @author Matthew Sun, Connor Murphy
 */
public class Enemy extends MoveableObject {
    private ArrayList<Projectile> currentProjectiles;
    private int projectile;
    private long lastFireTime;
    private int fireRate;

    private final int LOWER_X_BOUND = 130;
    private final int UPPER_X_BOUND = 850;
    private final int LOWER_Y_BOUND = 330;
    private final int UPPER_Y_BOUND = 575;

    private final int PROJECTILE_ONE_RATE = 500;
    private final int PROJECTILE_TWO_RATE = 300;
    private final int PROJECTILE_THREE_RATE = 30;
    private final int PROJECTILE_FOUR_RATE = 30;
    private final int PROJECTILE_FIVE_RATE = 3;
    private Dimension movementHitbox;

    private char currentDirection = ' ';
    private boolean shouldMove;
    private boolean anger;

    /**
     * Creates an enemy with the given values
     *
     * @param dmg        the damage the enemy does to the player
     * @param hp         the hp the enemy starts with
     * @param speed      the speed that the enemy moves at
     * @param xPos       the x position the enemy starts at
     * @param yPos       the y position the enemy starts at
     * @param image      the visual representation of the enemy
     * @param size       the size of the enemy
     * @param maxHP      the maximum hp the enemy can have
     * @param shouldMove if the enemy should move or not
     * @param anger      if he enemy can aggro when the player comes near it
     * @param shadowSize the size of the enemy's shadow
     * @param xShadow    the x coordinate of the enemy's shadow
     * @param yShadow    the y coordinate of the enemy's shadow
     */
    public Enemy(int dmg, int hp, int speed, int xPos, int yPos, Image image,
                 Dimension size, int maxHP, boolean shouldMove, boolean anger,
                 Dimension shadowSize, int xShadow, int yShadow) {
        super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shadowSize,
                xShadow, yShadow);
        this.shouldMove = shouldMove;
        this.anger = anger;
        fireRate = PROJECTILE_ONE_RATE;
        projectile = 6;
        currentProjectiles = new ArrayList<>();
    }

    /**
     * Creates a copy of the given enemy
     *
     * @param enemy the enemy to copy from
     */
    public Enemy(Enemy enemy) {
        super(enemy.getDamage(), enemy.getCurrentHP(),
                enemy.getSpeed(), (int) enemy.getX(),
                (int) enemy.getY(), enemy.getImage(),
                enemy.getSize(), enemy.getMaxHP(),
                enemy.getShadowSize(), enemy.getXShadow(),
                enemy.getYShadow());
        this.shouldMove = enemy.canMove();
        this.anger = enemy.isAngry();
//		this.movementHitbox = enemy.getMovementSize();
        fireRate = PROJECTILE_ONE_RATE;
        projectile = enemy.getProjectile();
        currentProjectiles = new ArrayList<>();
    }

//	public void setMovementHitbox(Dimension d)
//	{
//		movementHitbox = d;
//	}
//
//	public Dimension getMovementSize()
//	{
//		return movementHitbox;
//	}
//
//	public Rectangle getMovementHitbox()
//	{
//		return new Rectangle((int) (getX() + getSize().getWidth() * 0.2),
//				(int) (getY() + getSize().getHeight() * 1.2),
//				(int) movementHitbox.getWidth(),
//				(int) movementHitbox.getHeight());
//	}

    /**
     * Draws the enemy, its projecties and hp bar
     *
     * @param g the graphics to draw to
     */
    public void draw(Graphics g) {
        // Draw their set of projectiles
        for (int n = 0; n < currentProjectiles.size(); n++) {
            Projectile p = currentProjectiles.get(n);
            g.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), null);
        }

        // Draw the hp bar
        g.setColor(Color.GREEN);

        int green = (int) ((double) getCurrentHP() / getMaxHP() * getSize().getWidth());
        g.fillRect((int) getX(), (int) getY() - 10, green, 10);

        g.setColor(Color.RED);
        g.fillRect((int) getX() + green, (int) getY() - 10, (int) getSize().getWidth() - green, 10);

    }

    /**
     * Returns all projectiles the enemy is firing
     *
     * @return all projectiles the enemy is firing
     */
    public ArrayList<Projectile> getAllProjectiles() {
        return currentProjectiles;
    }

    /**
     * Sets the current projectiles to the given list
     *
     * @param p the list of projectiles to set for this enemy
     */
    public void setCurrentProjectiles(ArrayList<Projectile> p) {
        currentProjectiles = p;
    }

    /**
     * Sets the type of projectile the enemy shoots
     *
     * @param projectileNumber the numerical representation of the type of projectile this enemy shoots
     */
    public void setProjectile(int projectileNumber) {
        projectile = projectileNumber;
    }

    /**
     * Shoots a projectile in the given direction. A projectile can only be shot every so often specified
     * by the fire rate of teh enemy
     *
     * @param direction the direction in which to shoot the projectile
     */
    public void shootProjectile(char direction) {
        if (System.currentTimeMillis() - lastFireTime > fireRate) {
            Projectile p = new Projectile(getProjectile(), direction);
            p.setX(getX() + 20);
            p.setY(getY() + 25);
            currentProjectiles.add(p);
            lastFireTime = System.currentTimeMillis();
        }
    }

    /**
     * Shoots three projectiles from the enemy. One in front of the enemy, one on the left diagonal and
     * one on the right diagonal
     */
    public void shootTripleProjectiles() {
        if (System.currentTimeMillis() - lastFireTime > fireRate) {
            Projectile p = new Projectile(getProjectile(), currentDirection);
            p.setX(getX() + 20);
            p.setY(getY() + 25);
            currentProjectiles.add(p);

            Projectile p2 = new Projectile(getProjectile(), getTopDirection());
            p2.setX(getX() + 20);
            p2.setY(getY() + 25);
            currentProjectiles.add(p2);

            Projectile p3 = new Projectile(getProjectile(), getBotDirection());
            p3.setX(getX() + 20);
            p3.setY(getY() + 25);
            currentProjectiles.add(p3);

            lastFireTime = System.currentTimeMillis();
        }
    }

    /**
     * Shoots projectiles in all 8 directions
     */
    public void shootAllDirections() {
        if (System.currentTimeMillis() - lastFireTime > fireRate) {
            Projectile p = new Projectile(getProjectile(), 'N');
            p.setX(getX() + 20);
            p.setY(getY() + 25);
            currentProjectiles.add(p);

            Projectile p2 = new Projectile(getProjectile(), 'S');
            p2.setX(getX() + 20);
            p2.setY(getY() + 25);
            currentProjectiles.add(p2);

            Projectile p3 = new Projectile(getProjectile(), 'W');
            p3.setX(getX() + 20);
            p3.setY(getY() + 25);
            currentProjectiles.add(p3);

            Projectile p4 = new Projectile(getProjectile(), 'E');
            p4.setX(getX() + 20);
            p4.setY(getY() + 25);
            currentProjectiles.add(p4);

            Projectile p5 = new Projectile(getProjectile(), '1');
            p5.setX(getX() + 20);
            p5.setY(getY() + 25);
            currentProjectiles.add(p5);

            Projectile p6 = new Projectile(getProjectile(), '2');
            p6.setX(getX() + 20);
            p6.setY(getY() + 25);
            currentProjectiles.add(p6);

            Projectile p7 = new Projectile(getProjectile(), '3');
            p7.setX(getX() + 20);
            p7.setY(getY() + 25);
            currentProjectiles.add(p7);

            Projectile p8 = new Projectile(getProjectile(), '4');
            p8.setX(getX() + 20);
            p8.setY(getY() + 25);
            currentProjectiles.add(p8);

            lastFireTime = System.currentTimeMillis();

        }
    }

    /**
     * Sets the fire rate of this enemy
     *
     * @param n the number of milliseconds between each projectile firing
     */
    public void setFireRate(int n) {
        fireRate = n;
    }

    /**
     * Returns the type of projectile this enemy fires
     *
     * @return the type of projectile this enemy fires
     */
    public int getProjectile() {
        return projectile;
    }

    /**
     * Clears all projectiles that this enemy has fired
     */
    public void clearProjectiles() {
        currentProjectiles.clear();
    }

    /**
     * Moves all projectiles this enemy has fired and removes them if they have traveled more than their range
     */
    public void updateProjectiles() {
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
     * Returns if this enemy is aggroed against the player
     *
     * @return if this enemy is aggroed against the player
     */
    public boolean isAngry() {
        return anger;
    }

    /**
     * Sets the aggro status of this enemy
     *
     * @param b the aggro status of this enemy
     */
    public void setAnger(boolean b) {
        anger = b;
    }

    /**
     * Returns if the enemy is able to move
     *
     * @return if the enemy is able to move
     */
    public boolean canMove() {
        return shouldMove;
    }

    /**
     * Sets if the enemy can move or not
     *
     * @param b if the enemy can move or not
     */
    public void setMoveState(boolean b) {
        shouldMove = b;
    }

    /**
     * Moves the enemy in the current direction that it is moving in
     */
    public void moveInDirection() {
        if (currentDirection == 'N')
            moveNorth();
        else if (currentDirection == 'E')
            moveEast();
        else if (currentDirection == 'S')
            moveSouth();
        else if (currentDirection == 'W')
            moveWest();
        else if (currentDirection == '1')
            moveNorthEast();
        else if (currentDirection == '2')
            moveSouthEast();
        else if (currentDirection == '3')
            moveSouthWest();
        else if (currentDirection == '4')
            moveNorthWest();
    }

    /**
     * Returns the current direction that the enemy is facing
     *
     * @return the current direction that the enemy is facing
     */
    public char getDirection() {
        return currentDirection;
    }

    /**
     * Sets the direction this enemy is moving in
     *
     * @param c the direction this enemy is moving in
     */
    public void setDirection(char c) {
        currentDirection = c;
    }

    /**
     * Returns the direction that this enemy is facing
     *
     * @return the direction that this enemy is facing
     */
    public char getTopDirection() {
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
     * Returns the position behind the enemy
     *
     * @return the position behind the enemy
     */
    public char getBotDirection() {
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
     * Changes the direction this enemy is moving in to a random direction
     */
    public void setRandomDirection() {
        Random r = new Random();
        int direction = r.nextInt(8);
        if (direction == 0)
            currentDirection = 'N';
        else if (direction == 1)
            currentDirection = 'E';
        else if (direction == 2)
            currentDirection = 'S';
        else if (direction == 3)
            currentDirection = 'W';
        else if (direction == 4)
            currentDirection = '1';
        else if (direction == 5)
            currentDirection = '2';
        else if (direction == 6)
            currentDirection = '3';
        else if (direction == 7)
            currentDirection = '4';
    }

    /**
     * Moves this enemy east
     */
    public void moveEast() {
        if ((getX() + getSize().getWidth()) < UPPER_X_BOUND)
            setX(getX() + getSpeed() * Util.getDeltaTime());
    }

    /**
     * Moves this enemy west
     */
    public void moveWest() {
        if (getX() > LOWER_X_BOUND)
            setX(getX() - getSpeed() * Util.getDeltaTime());
    }

    /**
     * Moves this enemy north west
     */
    public void moveNorthWest() {
        if (getX() > LOWER_X_BOUND && getY() > LOWER_Y_BOUND) {
            setX(getX() - (getSpeed() * Util.getDeltaTime()));
            setY(getY() - (getSpeed() * Util.getDeltaTime()));
        }
    }

    /**
     * Moves this enemy north east
     */
    public void moveNorthEast() {
        if ((getX() + getSize().getWidth()) < UPPER_X_BOUND
                && getY() > LOWER_Y_BOUND) {
            setX(getX() + (getSpeed() * Util.getDeltaTime()));
            setY(getY() - (getSpeed() * Util.getDeltaTime()));
        }
    }

    /**
     * Moves this enemy north
     */
    public void moveNorth() {
        if (getY() > LOWER_Y_BOUND)
            setY(getY() - getSpeed() * Util.getDeltaTime());
    }

    /**
     * Moves this enemy south
     */
    public void moveSouth() {
        if ((getY() + getSize().getHeight()) < UPPER_Y_BOUND)
            setY(getY() + getSpeed() * Util.getDeltaTime());
    }

    /**
     * Moves this enemy south east
     */
    public void moveSouthEast() {
        if ((getX() + getSize().getWidth()) < UPPER_X_BOUND
                && (getY() + getSize().getHeight()) < UPPER_Y_BOUND) {
            setX(getX() + (getSpeed() * Util.getDeltaTime()));
            setY(getY() + (getSpeed() * Util.getDeltaTime()));
        }
    }

    /**
     * Moves this enemy south west
     */
    public void moveSouthWest() {
        if (getX() > LOWER_X_BOUND
                && (getY() + getSize().getHeight()) < UPPER_Y_BOUND) {
            setX(getX() - (getSpeed() * Util.getDeltaTime()));
            setY(getY() + (getSpeed() * Util.getDeltaTime()));
        }
    }
}
