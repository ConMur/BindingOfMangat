package item;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import thingsthatmove.GameObject;
import thingsthatmove.Player;

public class Item extends GameObject
{
	private String name;
	private boolean onGround;
	Random r = new Random();
	
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
		// Blocks one fatal instance of damage
		if (name.equals("ankh"))
		{
			
			
			return true;
		}
		// Does nothing
		else if(name.equals("blockofwood"))
		{
			//Do nothing
			return true;
		}
		else if (name.equals("bombguidebook"))
		{
			return true;
		}
		else if (name.equals("brokenpencil"))
		{
			player.setProjectile(2);
			return true;
		}
		else if(name.equals("c++"))
		{
			player.setSpeed(player.getSpeed() + 50);
			return true;
		}
		
		else if(name.equals("caffood"))
		{
			player.takeDamage(1);
			return true;
		}
		else if(name.equals("compscisweater"))
		{
			player.setMaxHP(player.getMaxHP() + 1);
			return true;
		}
		else if (name.equals("fireflower"))
		{
			return true;
		}
		else if (name.equals("glasses"))
		{
			return true;
		}
		else if (name.equals("goldenmushroom"))
		{
			return true;
		}
		else if(name.equals("halo"))
		{
			player.setDamage(player.getDamage() + 1);
			player.setSpeed(player.getSpeed() + 50);
			player.setMaxHP(player.getMaxHP() + 1);
			return true;
		}
		else if (name.equals("icecube"))
		{
			return true;
		}
		else if (name.equals("lightning"))
		{
			return true;
		}
		else if (name.equals("lotteryticket"))
		{

			if (r.nextInt(2) == 0)
				player.setNumCoin(99);
			else
				player.takeDamage(2);
			return true;
		}
		else if (name.equals("mariomushroom"))
		{
			return true;
		}
		else if(name.equals("masterkey"))
		{
			//This item makes keys unnessecary so just give the player a whole bunch of keys
			player.setNumKeys(99);
			player.heal(1);
			return true;
		}
		else if (name.equals("projector"))
		{
			return true;
		}
		else if (name.equals("usb"))
		{
			if (r.nextInt(4) == 3)
			{
				player.heal(1);
				player.addCoins(5);
			}
			else
				player.takeDamage(1);
			return true;
		}
		return false;
	}
}
