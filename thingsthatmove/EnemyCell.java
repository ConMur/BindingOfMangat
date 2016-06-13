package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyCell extends Enemy
{
	public EnemyCell () throws IOException
	{
		super (new Enemy(1, 20, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/cellfront.png")),
                new Dimension(67, 38), 20, true, true, new Dimension(51, 11), 7, 67));
	}
}
