package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Shows the instructions for the game
 * @author Connor Murphy
 *
 */
public class InstructionsState implements GameState
{
	private BufferedImage bg;

	@Override
	public void init()
	{
		try
		{
			bg = ImageIO.read(getClass().getResourceAsStream(
					"/images/instructionscreen.png"));
		}
		catch (IOException ioe)
		{
			System.err.println("Unable to load instructions screen");
			ioe.printStackTrace();
		}
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g)
	{
		g.drawImage(bg, 0, 0, null);
	}

	@Override
	public void cleanUp()
	{
		bg = null;
	}

	@Override
	public void keyPressed(int key)
	{
		GameStateManager.setState(State.MENU);
	}

	@Override
	public void keyReleased(int key)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int x, int y)
	{
		GameStateManager.setState(State.MENU);
	}

	@Override
	public void mouseMoved(int x, int y)
	{
		// TODO Auto-generated method stub

	}

}
