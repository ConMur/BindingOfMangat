package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

public class MoveableObject extends GameObject
{
	private int dmg, hp, speed;
	private int xPos, yPos;
	private Image image;
	private Dimension size;
	
	public MoveableObject (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size)
	{
		super(xPos, yPos, image, size);
		this.dmg = dmg;
		this.hp = hp;
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
	
	public void takeDamage (int amount)
	{
		if (amount >= 0)
			hp -= amount;
	}
	
	public void heal (int amount)
	{
		if (amount >= 0)
			hp += amount;
	}
	
	public void setSpeed (int speed)
	{
		this.speed = speed;
	}
	
	public void move (int x, int y)
	{
		xPos = x;
		yPos = y;
	}
	
	
}
