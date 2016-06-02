package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

public class Projectile extends GameObject
{
	private int speed, dmg;
	private int xPos, yPos;
	private Image image;
	private Dimension size;
	
	public Projectile (int speed, int dmg, Image image, int xPos, int yPos, Dimension size)
	{
		super(xPos, yPos, image, size);
		this.speed = speed;
		this.dmg = dmg;
	}
	
	
}
