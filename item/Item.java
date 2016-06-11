package item;
import java.awt.*;

import thingsthatmove.GameObject;
import thingsthatmove.Player;

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

	/**
	 * Applies the effects the item has to the player
	 * @param player the player to apply the effects to
	 * @return if an effect was applied
     */
	public boolean applyEffects(Player player)
	{
		if(name.equals("c++"))
		{
			player.setSpeed(player.getSpeed() + 50);
			return true;
		}
		else if(name.equals("halo"))
		{
			player.setDamage(player.getDamage() + 1);
			player.setSpeed(player.getSpeed() + 50);
			player.setMaxHP(player.getMaxHP() + 1);
			return true;
		}
		else if(name.equals("compscisweater"))
		{
			player.setMaxHP(player.getMaxHP() + 1);
			return true;
		}
		else if(name.equals("caffood"))
		{
			player.takeDamage(1);
			return true;
		}
		else if(name.equals("blockofwood"))
		{
			//Do nothing
			return true;
		}
		else if(name.equals("masterkey"))
		{
			//This item makes keys unnessecary so just give the player a whole bunch of keys
			player.setNumKeys(20000);
			player.heal(1);
			return true;
		}
		return false;
	}
}
