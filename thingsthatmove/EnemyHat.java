package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyHat extends Enemy
{
	public EnemyHat () throws IOException
	{
		super (new Enemy(1, 50, 100, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/hatfront.png")),
                new Dimension(80, 20), 50, true, true, new Dimension(66, 13), 4, 29));
	}
}
