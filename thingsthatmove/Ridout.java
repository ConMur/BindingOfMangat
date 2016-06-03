package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

public class Ridout extends Enemy
{
	private int numLives;
	
	public Ridout (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP, int numLives,boolean shouldMove, boolean anger)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size, maxHP, shouldMove, anger);
		this.numLives = numLives;
	}
	
	public void loseLife ()
	{
		numLives --;
		if (this.lifeRemaining())
			this.secondLifeUpgrade();
	}
	
	public boolean lifeRemaining()
	{
		if (numLives > 0)
			return true;
		return false;
	}
	
	public void secondLifeUpgrade ()
	{
		this.setDamage(this.getDamage() * 4);
		this.setMaxHP(this.getMaxHP() * 4);
		this.heal(this.getMaxHP());
		this.setSpeed(this.getSpeed() * 4);
	}
}
