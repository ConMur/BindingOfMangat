package states;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import levels.LevelManager;

public class MenuState implements GameState 
{
	private BufferedImage bg;

	
	
	public MenuState ()
	{
		// Menu image
		try {
			bg = ImageIO.read(getClass().getResource("/images/menu/mainmenu.png"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		
		
	}
	
	
	public void init()
	{
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int key)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int x, int y)
	{

		// Play button
		if (x > 615 && x < 919 && y > 223 && y < 314)
			GameStateManager.setState(State.PLAY);
		// Help button
//		else if (x > 615 && x < 919 && y > 338 && y < 425)

		// Exit button
//		else if (x > 615 && x < 919 && y > 617 && y < 552)
		
	}

	@Override
	public void mouseMoved(int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

}
