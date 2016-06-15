package item;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import thingsthatmove.GameObject;
import thingsthatmove.Player;

/**
 * An item is something in the game that can be picked up by the player. It can have positive or
 * negative effects such as changing hp, giving coins or affecting enemies
 * @author Matthew Sun, Connor Murphy
 */
public class Item extends GameObject
{
	private String name;
	private boolean onGround;
	Random r = new Random();

	/**
	 * Creates the item
	 * @param name the name of the item
	 * @param x the x coordinate of the item
	 * @param y the y coordinate of the item
	 * @param image the image that visually represents the item
	 * @param size the size of the item
     * @param oG if the item is on the ground or not
     */
	public Item (String name, int x, int y, Image image, Dimension size, boolean oG)
	{
		super (x, y, image, size, null, 0, 0);
		this.name = name;
		this.onGround = oG;
	}

	/**
	 * Returns the name of the item
	 * @return the name of the item
     */
	public String getName ()
	{
		return name;
	}

	/**
	 * Returns if the item is on the ground
	 * @return if the item is on the ground
     */
	public boolean isTheItemOnTheGround ()
	{
		return onGround;
	}

	/**
	 * Draws the item
	 * @param g the graphics to draw to
     */
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
			player.addBombs(2);
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
				player.setNumCoin(10);
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
