package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Random;

import util.Util;

public class Enemy extends MoveableObject
{
	private final int LOWER_X_BOUND = 130;
	private final int UPPER_X_BOUND = 850;
	private final int LOWER_Y_BOUND = 330;
	private final int UPPER_Y_BOUND = 575;

	
	private char currentDirection = ' ';
	private boolean shouldMove;
	private boolean anger;

	public Enemy(int dmg, int hp, int speed, int xPos, int yPos, Image image,
			Dimension size, int maxHP, boolean shouldMove, boolean anger)
	{
		super(dmg, hp, speed, xPos, yPos, image, size, maxHP);
		this.shouldMove = shouldMove;
		this.anger = anger;
	}

	public Enemy(Enemy connorBeingLazy)
	{
		super(connorBeingLazy.getDamage(), connorBeingLazy.getCurrentHP(),
				connorBeingLazy.getSpeed(), (int)connorBeingLazy.getX(),
				(int)connorBeingLazy.getY(), connorBeingLazy.getImage(),
				connorBeingLazy.getSize(), connorBeingLazy.getMaxHP());
		this.shouldMove = connorBeingLazy.canMove();
		this.anger = connorBeingLazy.isAngry();
	}

	public boolean isAngry()
	{
		return anger;
	}

	public void setAnger(boolean b)
	{
		anger = b;
	}

	public boolean canMove()
	{
		return shouldMove;
	}

	public void setMoveState(boolean b)
	{
		shouldMove = b;
	}

	public void moveInDirection()
	{
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

	public void setDirection(char c)
	{
		currentDirection = c;
	}

	public char getDirection()
	{
		return currentDirection;
	}
	
	public void setRandomDirection()
	{
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
	
	public void moveEast ()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND)
			setX(getX() + getSpeed() * Util.getDeltaTime());
	}
	
	public void moveWest()
	{
		if (getX() > LOWER_X_BOUND)
			setX(getX() - getSpeed() * Util.getDeltaTime());
	}
	
	public void moveNorthWest()
	{
		if (getX() > LOWER_X_BOUND && getY() > LOWER_Y_BOUND)
		{
			setX(getX() - (getSpeed() * Util.getDeltaTime()));
			setY(getY() - (getSpeed() * Util.getDeltaTime()));
		}
	}
	
	public void moveNorthEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND && getY() > LOWER_Y_BOUND)
		{
			setX(getX() + (getSpeed() * Util.getDeltaTime()));
			setY(getY() - (getSpeed() * Util.getDeltaTime()));
		}
	}
	
	public void moveNorth()
	{
		if (getY() > LOWER_Y_BOUND)
			setY(getY() - getSpeed() * Util.getDeltaTime());
	}
	
	public void moveSouth()
	{
		if ((getY() + getSize().getHeight()) < UPPER_Y_BOUND)
			setY(getY() + getSpeed() * Util.getDeltaTime());
	}
	
	public void moveSouthEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND && (getY() + getSize().getHeight()) < UPPER_Y_BOUND)
		{
			setX(getX() + (getSpeed() * Util.getDeltaTime()));
			setY(getY() + (getSpeed() * Util.getDeltaTime()));
		}
	}
	
	public void moveSouthWest()
	{
		if (getX() > LOWER_X_BOUND && (getY() + getSize().getHeight()) < UPPER_Y_BOUND)
		{
			setX(getX() - (getSpeed() * Util.getDeltaTime()));
			setY(getY() + (getSpeed() * Util.getDeltaTime()));
		}
	}
}
