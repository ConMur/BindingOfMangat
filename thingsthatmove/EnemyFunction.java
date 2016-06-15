package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyFunction extends Enemy
{
	public EnemyFunction () throws IOException
	{
		super (new Enemy(1, 30, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/functionfront.png")),
                new Dimension(75, 37), 30, true, true, new Dimension(62, 14), 9, 64));
	}
}
