package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import levels.LevelManager;

public class EnemyCalculator extends Enemy
{
	public EnemyCalculator () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/calculatorfront.png")),
                new Dimension(74, 40), 2, true, true, new Dimension(67, 12), 4, 69));
	}
}
