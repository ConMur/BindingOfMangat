package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyBullshit extends Enemy
{
	public EnemyBullshit () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/bullshitfront.png")),
                new Dimension(107, 62), 2, true, true, new Dimension(60, 12), 23, 113));
	}
}
