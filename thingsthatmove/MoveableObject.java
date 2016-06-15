package thingsthatmove;

import util.Util;

import java.awt.Dimension;
import java.awt.Image;

/**
 * The superclass of any object in the game that moves
 * @author Matthew Sun, Connor Murphy
 */
public class MoveableObject extends GameObject
{
	private final int LOWER_X_BOUND = 90;
	private final int UPPER_X_BOUND = 940;
	private final int LOWER_Y_BOUND = 220;
	private final int UPPER_Y_BOUND = 660;
	private int dmg, hp, speed, maxHP;

	/**
	 * Creates a moveable object with the given values
	 * @param dmg the damage that this moveable object does
	 * @param hp the hp that this moveable object has
	 * @param speed the speed that this moveable object moves at
	 * @param xPos the x position of this moveable object
	 * @param yPos the y position of this moveable object
	 * @param image the visual representation of this moveable object
	 * @param size the size of this moveable object
	 * @param maxHP the maximum hp of this moveable object
	 * @param shadowSize the size of this moveable object's shadow
     * @param xShadow the x position of this moveable object's shadow
     * @param yShadow the y position of this moveable object's shadow
     */
	public MoveableObject (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP, Dimension shadowSize, int xShadow, int yShadow)
	{
		super(xPos, yPos, image, size, shadowSize, xShadow, yShadow);
		this.dmg = dmg;
		this.hp = hp;
		this.maxHP = maxHP;
		this.speed = speed;
	}

	/**
	 * Returns the damage this moveable object does
	 * @return the damage this moveable object does
     */
	public int getDamage ()
	{
		return dmg;
	}

	/**
	 * Sets the damage this moveable object does
	 * @param newDmg the new damage for this moveable object
     */
	public void setDamage (int newDmg)
	{
		dmg = newDmg;
	}

	/**
	 * Returns the current hp of this moveable object
	 * @return the current hp of this moveable object
     */
	public int getCurrentHP ()
	{
		return hp;
	}

	/**
	 * Returns the max hp of this moveable object
	 * @return the max hp of this moveable object
     */
	public int getMaxHP ()
	{
		return maxHP;
	}

	/**
	 * Sets the maximum hp of this movable object to the given value
	 * @param newMaxHP the new max hp of this movable object
     */
	public void setMaxHP (int newMaxHP)
	{
		maxHP = newMaxHP;
	}

	/**
	 * Decreases this movable object's hp by the given amount
	 * @param amount the amount to decrease this movable object's hp by
     */
	public void takeDamage (int amount)
	{
		if (amount >= 0)
			hp -= amount;
	}

	/**
	 * Increases this movable object's hp by the given amount
	 * @param amount the amount to increase this movable object's hp by
     */
	public void heal (int amount)
	{
		if (amount >= 0)
			if (amount > maxHP)
				hp = maxHP;
			else
				hp += amount;
	}

	/**
	 * Returns the speed of this movable object
	 * @return the speed of this movable object
     */
	public int getSpeed ()
	{
		return speed;
	}

	/**
	 * Sets the speed of this moveable object
	 * @param speed the new speed for this moveable object
     */
	public void setSpeed (int speed)
	{
		this.speed = speed;
	}

	/**
	 * Moves this moveable object to the given x and y coordinates
	 * @param x the x coordinate to move to
	 * @param y the y coordinate to move to
     */
	public void move (int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Returns if this moveable object is alive
	 * @return if this moveable object is alive
     */
	public boolean isAlive ()
	{
		if (hp <=0)
			return false;
		return true;
	}

	/**
	 * Moves this moveable object east
	 */
	public void moveEast ()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND)
			setX(getX() + getSpeed() * Util.getDeltaTime());
	}

	/**
	 * Moves this moveable object west
	 */
	public void moveWest()
	{
		if (getX() > LOWER_X_BOUND)
			setX(getX() - getSpeed() * Util.getDeltaTime());
	}

	/**
	 * Moves this moveable object north west
	 */
	public void moveNorthWest()
	{
		if (getX() > LOWER_X_BOUND && getY() > LOWER_Y_BOUND)
		{
			setX(getX() - (getSpeed() * Util.getDeltaTime()));
			setY(getY() - (getSpeed() * Util.getDeltaTime()));
		}
	}

	/**
	 * Moves this moveable object north east
	 */
	public void moveNorthEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND && getY() > LOWER_Y_BOUND)
		{
			setX(getX() + (getSpeed() * Util.getDeltaTime()));
			setY(getY() - (getSpeed() * Util.getDeltaTime()));
		}
	}

	/**
	 * Moves this moveable object north
	 */
	public void moveNorth()
	{
		if (getY() > LOWER_Y_BOUND)
			setY(getY() - getSpeed() * Util.getDeltaTime());
	}

	/**
	 * Moves this moveable object south
	 */
	public void moveSouth()
	{
		if ((getY() + getSize().getHeight()) < UPPER_Y_BOUND)
			setY(getY() + getSpeed() * Util.getDeltaTime());
	}

	/**
	 * Moves this moveable object south east
	 */
	public void moveSouthEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND && (getY() + getSize().getHeight()) < UPPER_Y_BOUND)
		{
			setX(getX() + (getSpeed() * Util.getDeltaTime()));
			setY(getY() + (getSpeed() * Util.getDeltaTime()));
		}
	}

	/**
	 * Moves this moveable object south west
	 */
	public void moveSouthWest()
	{
		if (getX() > LOWER_X_BOUND && (getY() + getSize().getHeight()) < UPPER_Y_BOUND)
		{
			setX(getX() - (getSpeed() * Util.getDeltaTime()));
			setY(getY() + (getSpeed() * Util.getDeltaTime()));
		}
	}
}
