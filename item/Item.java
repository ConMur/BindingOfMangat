package item;
import java.awt.Dimension;
import java.awt.Image;
import thingsthatmove.GameObject;

public class Item extends GameObject
{
	private String name;
	private boolean onGround;
	
	public Item (String name, int x, int y, Image image, Dimension size, boolean oG)
	{
		super (x, y, image, size);
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
}
