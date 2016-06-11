package item;
import java.awt.*;

import thingsthatmove.GameObject;

public class Item extends GameObject
{
	private String name;
	private boolean onGround;
	
	public Item (String name, int x, int y, Image image, Dimension size, boolean oG)
	{
		super (x, y, image, size, null, 0, 0);
		this.name = name;
		this.onGround = oG;
	}
	
	public String getName ()
	{
		return name;
	}
	
	public boolean isTheItemOnTheGround ()
	{
		return onGround;
	}
	public void draw(Graphics g)
	{
		g.drawImage(getImage(), (int)getX(), (int)getY(), null);
	}
}
