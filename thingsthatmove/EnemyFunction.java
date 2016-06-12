package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyFunction extends Enemy
{
	public EnemyFunction () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/functionfront.png")),
                new Dimension(51, 25), 2, true, true, new Dimension(44, 9), 6, 43));
	}
}
