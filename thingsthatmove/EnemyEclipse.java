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
                new Dimension(64, 35), 2, true, false,  new Dimension(43, 9), 7, 63));
	}
}