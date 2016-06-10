package levels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import thingsthatmove.GameObject;

public class RockPatterns
{
	private final int ROCK_X = 55;
	private final int ROCK_Y = 60;

	private BufferedImage rock;
	private ArrayList<GameObject> rocksInPattern;
	private Dimension rockDimension;

	public RockPatterns(int patternNum)
	{
		try
		{
			rock = ImageIO.read(getClass().getResourceAsStream(
					"/images/rock.png"));
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		rockDimension = new Dimension(51, 58);
		rocksInPattern = new ArrayList<GameObject>();

		setNewPattern(patternNum);
	}

	public ArrayList<GameObject> getRocks()
	{
		return rocksInPattern;
	}

	public void setNewPattern(int newPattern)
	{
		rocksInPattern.clear();

		// Middle square pattern (KEY ROOM PATTERN)
		if (newPattern == 1)
		{
			// Bottom right rocks
			rocksInPattern.add(new GameObject(545, 580, rock, rockDimension));
			rocksInPattern.add(new GameObject(600, 580, rock, rockDimension));
			rocksInPattern.add(new GameObject(600, 520, rock, rockDimension));

			// Bottom left rocks
			rocksInPattern.add(new GameObject(340, 580, rock, rockDimension));
			rocksInPattern.add(new GameObject(400, 580, rock, rockDimension));
			rocksInPattern.add(new GameObject(340, 520, rock, rockDimension));

			// Top right rocks
			rocksInPattern.add(new GameObject(545, 340, rock, rockDimension));
			rocksInPattern.add(new GameObject(600, 400, rock, rockDimension));
			rocksInPattern.add(new GameObject(600, 340, rock, rockDimension));

			// Top left rocks
			rocksInPattern.add(new GameObject(340, 340, rock, rockDimension));
			rocksInPattern.add(new GameObject(400, 340, rock, rockDimension));
			rocksInPattern.add(new GameObject(340, 400, rock, rockDimension));
		}
		// Row pattern
		else if (newPattern == 2)
		{
			// 3 rows and 13 rocks per row
			for (int row = 0; row < 3; row++)
				for (int col = 0; col < 13; col++)
					rocksInPattern.add(new GameObject(160 + ROCK_X * col, 340
							+ 120 * row , rock, rockDimension));
		}
		// Column pattern
		else if (newPattern == 3)
		{
			// 6 columns, 5 rocks per column
			for (int row = 0; row < 5; row++)
				for (int col = 0; col < 6; col++)
					rocksInPattern.add(new GameObject(165 + 130 * col, 340
							+ ROCK_Y * row , rock, rockDimension));
		}
		// Dotted pattern
		else if (newPattern == 4)
		{
			// 3 rows, 6 columns
			for (int row = 0; row < 3; row++)
				for (int col = 0; col < 6; col++)
					rocksInPattern.add(new GameObject(165 + 130 * col, 340
							+ 120 * row , rock, rockDimension));
		}
		// Open box pattern
		else if (newPattern == 5)
		{
			// Draw rows
			for (int row = 0 ; row < 2 ; row ++)
				for (int col = 1 ; col < 12 ; col ++)
					// Leave gap to move into box
					if (col < 5 || col > 7)
						rocksInPattern.add(new GameObject(160 + ROCK_X * col, 320
							+ 270 * row , rock, rockDimension));

			// Columns
			for (int col = 0 ; col < 2 ; col ++)
				for (int row = 0 ; row < 4 ; row ++)
					rocksInPattern.add(new GameObject(160 + 660 * col, 367
							+ ROCK_Y * row , rock, rockDimension));
		}			
		// Vertical zigzag pattern
		else if (newPattern == 6)
		{
			// 6 columns, 5 rocks per column
			for (int row = 0; row < 3; row++)
			{
				for (int topCol = 0; topCol < 3; topCol++)
					rocksInPattern.add(new GameObject(175 + 240 * topCol, 300
						+ ROCK_Y * row , rock, rockDimension));
				for (int botCol = 0 ; botCol < 3 ;botCol ++)
					rocksInPattern.add(new GameObject(280 + 240 * botCol, 620
							 - ROCK_Y * row , rock, rockDimension));
			}
		}
		// Smile pattern
		else if (newPattern == 7)
		{
			// Eyes
			rocksInPattern.add(new GameObject(415, 400, rock, rockDimension));
			rocksInPattern.add(new GameObject(525, 400, rock, rockDimension));
			
			// Smile
			rocksInPattern.add(new GameObject(360, 500, rock, rockDimension));
			rocksInPattern.add(new GameObject(415, 520, rock, rockDimension));
			rocksInPattern.add(new GameObject(470, 540, rock, rockDimension));
			rocksInPattern.add(new GameObject(525, 520, rock, rockDimension));
			rocksInPattern.add(new GameObject(580, 500, rock, rockDimension));
		}
		// Inner room pattern
		else if (newPattern == 8)
		{
			
		}
		

	}

	public void draw(Graphics g)
	{
		// Draw all rocks
		for (int n = 0; n < rocksInPattern.size(); n++)
		{
			GameObject currentRock = rocksInPattern.get(n);
			g.drawImage(rock, (int) currentRock.getX(),
					(int) currentRock.getY(), null);
		}
	}
}
