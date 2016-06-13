package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyEclipse extends Enemy
{
	public EnemyEclipse () throws IOException
	{
		super (new Enemy(1, 20, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/eclipsefront.png")),
                new Dimension(64, 35), 20, true, false,  new Dimension(43, 9), 7, 63));
		
		this.setProjectile(8);
	}
}
