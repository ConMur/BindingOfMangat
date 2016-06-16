package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyFlag extends Enemy
{
	public EnemyFlag () throws IOException
	{
		super (new Enemy(1, 50, 100, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/canadafront.png")),
                new Dimension(98, 39), 50, true, true, new Dimension(69, 14), 13, 64));
	}
}
