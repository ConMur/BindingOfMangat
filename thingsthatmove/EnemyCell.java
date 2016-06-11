package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyCell extends Enemy
{
	public EnemyCell () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/cellfront.png")),
                new Dimension(79, 70), 2, true, true, new Dimension(60, 15), 10, 78));
	}
}
