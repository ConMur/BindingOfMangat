package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Random;

public class Enemy extends MoveableObject
{
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
		int direction = r.nextInt(4);
		if (direction == 0)
			currentDirection = 'N';
        else if (direction == 1)
        	currentDirection = 'E';
        else if (direction == 2)
        	currentDirection = 'S';
        else
        	currentDirection = 'W';
	}
}
