package thingsthatmove;
import item.Item;
import java.awt.Dimension;
import java.awt.Image;

public class Enemy extends MoveableObject
{
	private int dmg, hp, speed;
	private int projectile; 
	private Item currentItem;
	private int xPos, yPos;
	private Image image;
	private Dimension size;
	
	public Enemy (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size);
	}
	
	public boolean isAlive ()
	{
		if (hp <=0)
			return false;
		return true;
	}
}
