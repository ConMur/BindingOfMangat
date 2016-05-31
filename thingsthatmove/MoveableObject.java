package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

public class MoveableObject extends GameObject
{
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
	
	
}
