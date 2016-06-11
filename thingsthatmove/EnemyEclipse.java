package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyEclipse extends Enemy
{
	public EnemyEclipse () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
                new Dimension(77, 55), 2, true, false,  new Dimension(60, 15), 10, 77));
	}
}
