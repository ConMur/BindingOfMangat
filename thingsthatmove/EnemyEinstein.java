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
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/einsteinfront.png")),
                new Dimension(135, 85), 2, true, true, new Dimension(60, 15), 36, 116));
	}
}
