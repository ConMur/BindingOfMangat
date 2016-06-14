package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

/**
 * The final boss of the game. Ridout has two forms, an initial easy from and a secondary harder form.
 */
public class Ridout extends Enemy
{
	private int numLives;
	private boolean upgraded;
	
	public Ridout (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP, int numLives,boolean shouldMove, boolean anger)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size, maxHP, shouldMove, anger, new Dimension(82, 16),23, 145);
		this.numLives = numLives;
		upgraded = false;
	}

	/**
	 * Makes ridout lose a life
	 */
	public void loseLife ()
	{
		numLives --;
		if (this.lifeRemaining()) {
			this.secondLifeUpgrade();
			upgraded = true;
		}
	}

	/**
	 * Returns if Ridout still has lives remaining
	 * @return if Ridout still has lives remaining
     */
	public boolean lifeRemaining()
	{
		if (numLives > 0)
			return true;
		return false;
	}

	/**
	 * Sets ridouts stats to his harder form
	 */
	private void secondLifeUpgrade ()
	{
		System.out.println("Oh NO! It seems Ridout has started recursively calling massive stat gains");
		this.setDamage(this.getDamage() * 4);
		this.setMaxHP(this.getMaxHP() * 4);
		this.heal(this.getMaxHP());
		this.setSpeed(this.getSpeed() * 4);
	}
}
