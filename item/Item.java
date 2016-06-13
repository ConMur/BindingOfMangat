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
	 * Applies the effects the item has to the player. If the item affects the player,
	 * the item is consumed. If the item does no affect the player, the method returns false
	 * and effectively does nothing
	 * @param player the player to apply the effects to
	 * @return if an effect was applied
     */
	public boolean applyEffects(Player player)
	{
		boolean appliedEffectsToPlayer = false;
		// Gives you an extra life if triggered upon death, if consumed gives +2 Max HP and +1 heal
		if (name.equals("ankh"))
		{
			player.setMaxHP(player.getMaxHP() + 2);
			player.heal(1);
			appliedEffectsToPlayer = true;
		}
		// Does nothing
		else if(name.equals("blockofwood"))
		{
			//Do nothing
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("bombguidebook"))
		{
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("brokenpencil"))
		{
			player.setProjectile(2);
			appliedEffectsToPlayer = true;
		}
		else if(name.equals("c++"))
		{
			player.setSpeed(player.getSpeed() + 50);
			appliedEffectsToPlayer = true;
		}
		
		else if(name.equals("caffood"))
		{
			player.takeDamage(1);
			appliedEffectsToPlayer = true;
		}
		else if(name.equals("compscisweater"))
		{
			player.setMaxHP(player.getMaxHP() + 1);
			player.heal(10);
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("fireflower"))
		{
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("glasses"))
		{
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("goldenmushroom"))
		{
			appliedEffectsToPlayer = true;
		}
		else if(name.equals("halo"))
		{
			player.setSpeed(player.getSpeed() + 50);
			player.setMaxHP(player.getMaxHP() + 1);
			player.heal(10);
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("icecube"))
		{
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("lightning"))
		{
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("lotteryticket"))
		{

			if (r.nextInt(2) == 0)
			{
				player.heal(10);
				player.setNumCoin(99);
			}
			else
				player.takeDamage(2);
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("mariomushroom"))
		{
			appliedEffectsToPlayer = true;
		}
		else if(name.equals("masterkey"))
		{
			//This item makes keys unnessecary so just give the player a whole bunch of keys
			player.setNumKeys(99);
			player.heal(10);
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("projector"))
		{
			appliedEffectsToPlayer = true;
		}
		else if (name.equals("usb"))
		{
			if (r.nextInt(4) == 3)
			{
				player.heal(10);
				player.addCoins(5);
			}
			else
				player.takeDamage(1);
			appliedEffectsToPlayer = true;
		}

		//Item was used, remove it from the player's inventory
		if(appliedEffectsToPlayer)
		{
			player.setItem(null);
		}
		return appliedEffectsToPlayer;
	}
}
