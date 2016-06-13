package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyTextbook extends Enemy
{
	public EnemyTextbook () throws IOException
	{
		super (new Enemy(1, 20, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/textbookfront.png")),
                new Dimension(65, 47), 20, true, false, new Dimension(48, 10), 9, 84));
	}
}
