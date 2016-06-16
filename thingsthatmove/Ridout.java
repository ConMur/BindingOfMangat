package thingsthatmove;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The final boss of the game. Ridout has two forms, an initial easy from and a secondary harder form.
 */
public class Ridout extends Enemy
{
	private int numLives;
	private boolean upgraded;

	private boolean playCutScene, playedFirstCutscene;
	private boolean doneCutscene;
	private BufferedImage firstCutScene, secondCutScene;
	private long lastCutsceneTime;

	private final int CUTSCENE_DISPLAY_LENGTH = 2000;
	
	public Ridout (int dmg, int hp, int speed, int xPos, int yPos, Image image, Dimension size, int maxHP, int numLives,boolean shouldMove, boolean anger)
	{
		super (dmg, hp, speed, xPos ,yPos, image, size, maxHP, shouldMove, anger, new Dimension(82, 16),23, 145);
		this.numLives = numLives;
		upgraded = false;
		playCutScene = false;
		playedFirstCutscene = false;
		doneCutscene = false;

		try {
			firstCutScene = ImageIO.read(getClass().getResourceAsStream("/images/ridoutdead1.png"));
			secondCutScene = ImageIO.read(getClass().getResourceAsStream("/images/ridoutdead2.png"));
		}
		catch(IOException ioe)
		{
			System.err.println("Error loading ridout cutscenes");
		}
	}

	public boolean resumeGame()
	{
		return doneCutscene;
	}

	/**
	 * Makes ridout lose a life
	 */
	public void loseLife ()
	{
		numLives --;
		if (!this.lifeRemaining() && !upgraded) {
			this.secondLifeUpgrade();
			upgraded = true;
			playCutScene = true;
			lastCutsceneTime = System.currentTimeMillis();
		}
	}

	/**
	 * Returns if Ridout has upgraded to his second form or not
	 * @return if Ridout has upgraded to his second form or not
     */
	public boolean hasUpgraded()
	{
		return upgraded;
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
		this.setCurrentHP(this.getMaxHP());
		this.setSpeed(this.getSpeed() * 4);
	}

	@Override
	public void draw(Graphics g)
	{
		if(!playCutScene)
		{
			super.draw(g);
		}
		else
		{
			//NOTE: i do not want to do this in the draw method but it is the only consistent update an enemy has
			if(!playedFirstCutscene) {
				g.drawImage(firstCutScene, 0, 0, null);
				if(System.currentTimeMillis() - lastCutsceneTime > CUTSCENE_DISPLAY_LENGTH)
				{
					playedFirstCutscene = true;
					lastCutsceneTime = System.currentTimeMillis();
				}
			}
			else
			{
				g.drawImage(secondCutScene, 0, 0, null);
				if(System.currentTimeMillis() - lastCutsceneTime > CUTSCENE_DISPLAY_LENGTH)
				{
					doneCutscene = true;
					playCutScene = false;
				}
			}
		}
	}
}
