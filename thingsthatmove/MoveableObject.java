package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

public class MoveableObject extends GameObject
{
	private final int LOWER_X_BOUND = 0;
	private final int UPPER_X_BOUND = 0;
	private final int LOWER_Y_BOUND = 0;
	private final int UPPER_Y_BOUND = 0;
	private int dmg, hp, speed, maxHP;
	
	public MoveableObject (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP)
	{
		super(xPos, yPos, image, size);
		this.dmg = dmg;
		this.hp = hp;
		this.maxHP = maxHP;
		this.speed = speed;
	}
	
	public int getDamage ()
	{
		return dmg;
	}
	
	public void setDamage (int newDmg)
	{
		dmg = newDmg;
	}
	
	public int getCurrentHP ()
	{
		return hp;
	}
	
	public int getMaxHP ()
	{
		return maxHP;
	}
	
	public void setMaxHP (int newMaxHP)
	{
		maxHP = newMaxHP;
	}
	
	public void takeDamage (int amount)
	{
		if (amount >= 0)
			hp -= amount;
	}
	
	public void heal (int amount)
	{
		if (amount >= 0)
			if (amount > maxHP)
				hp = maxHP;
			else
				hp += amount;
	}
	
	public int getSpeed ()
	{
		return speed;
	}
	
	public void setSpeed (int speed)
	{
		this.speed = speed;
	}
	
	public void move (int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public boolean isAlive ()
	{
		if (hp <=0)
			return false;
		return true;
	}
	
	public void moveEast ()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND)
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
		if ((getY() + getSize().getHeight()) < UPPER_Y_BOUND)
			setY(getY() + getSpeed());
	}
	
	
}
