package thingsthatmove;
import java.awt.Dimension;
import java.awt.Image;

public class Enemy extends MoveableObject
{
	public Enemy (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size, maxHP);
	}
	
	
}
