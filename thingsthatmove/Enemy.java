package thingsthatmove;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Enemy extends MoveableObject
{
	private final int LOWER_X_BOUND = 0;
	private final int UPPER_X_BOUND = 0;
	private final int LOWER_Y_BOUND = 0;
	private final int UPPER_Y_BOUND = 0;
	private char currentDirection = ' ';
	private boolean shouldMove;
	private boolean anger;
	
	public Enemy (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP, boolean shouldMove, boolean anger)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size, maxHP);
		this.shouldMove = shouldMove;
		this.anger = anger;
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
	
	
	public void moveEast ()
	{
		if (getX() < UPPER_X_BOUND)
			setX(getX() + getSpeed());
	}
	
	public void moveWest()
	{
		if (getX() > LOWER_X_BOUND)
			setX(getX() - getSpeed());
	}
	
	public void moveNorth()
	{
		if (getY() > LOWER_Y_BOUND)
			setY(getY() - getSpeed());
	}
	
	public void moveSouth()
	{
		if (getY() < UPPER_Y_BOUND)
			setY(getY() + getSpeed());
	}
	
	public void moveInDirection()
	{
		if (currentDirection == 0)
			moveNorth();
		else if (currentDirection == 1)
			moveEast();
		else if (currentDirection == 2)
			moveSouth();
		else
			moveWest();
	}
	
	public void setDirection(char c)
	{
		currentDirection = c;
	}
	
	public char getDirection()
	{
		return currentDirection;
	}
}
