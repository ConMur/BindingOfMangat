package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyEinstein extends Enemy
{
	public EnemyEinstein () throws IOException
	{
		super (new Enemy(1, 10, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/einsteinfront.png")),
                new Dimension(72, 34), 10, true, true, new Dimension(33, 7), 18, 63));
	}
}
