package thingsthatmove;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import thingsthatmove.Enemy;
import levels.LevelManager;

public class EnemyMolecule extends Enemy
{
	public EnemyMolecule () throws IOException
	{
		super (new Enemy(1, 2, 200, 400, 400, ImageIO.read(LevelManager.class.getResourceAsStream("/images/enemies/moleculefront.png")),
                new Dimension(94, 80), 2, true, false, new Dimension(60, 15), 16, 92));
	}
}
