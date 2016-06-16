package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyHat extends Enemy
{
	public EnemyHat () throws IOException
	{
		super (new Enemy(1, 50, 50, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/hatfront.png")),
                new Dimension(86, 36), 50, true, true, new Dimension(44, 10), 20, 65));
	}
}
