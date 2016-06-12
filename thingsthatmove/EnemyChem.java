package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyChem extends Enemy
{
	public EnemyChem () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/chemfront.png")),
                new Dimension(57, 43), 2, true, false, new Dimension(48, 10), 3, 78));
	}
}
