package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyPi extends Enemy
{
	public EnemyPi () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/pifront.png")),
                new Dimension(86, 36), 2, true, false, new Dimension(44, 10), 20, 65));
	}
}
