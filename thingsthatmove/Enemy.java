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
	
	public Enemy (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP, boolean shouldMove)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size, maxHP);
		this.shouldMove = shouldMove;
	}
	
	public boolean getMoveState()
	{
		if (shouldMove)
			return true;
		return false;
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
	
	public void setDirection(char c)
	{
		currentDirection = c;
	}
	
	public char getDirection()
	{
		return currentDirection;
	}
}
