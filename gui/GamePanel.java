package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

import states.GameStateManager;

/**
 * The panel that displays what is happening in the program
 * @author Connor Murphy
 *
 */
public class GamePanel extends JPanel
{
	/**
	 * A generated serialVersionUID so eclipse does not yell at me
	 */
	private static final long serialVersionUID = -7906909062119976256L;

	private boolean running;

	private long lastTime;

	private JLabel fpsLabel;

	public GamePanel()
	{
		super();
		running = true;
		
		setPreferredSize(new Dimension(1280, 720));

		lastTime = System.nanoTime();

		fpsLabel = new JLabel();
		add(fpsLabel);
	}

	/**
	 * Initializes any resources and starts the program
	 */
	public void go()
	{
		GameStateManager.init();
		while (running)
		{
			// Calculate the time between frames
			long currentTime = System.nanoTime();
			double deltaTime = (currentTime - lastTime) / 1000000000.0;

			// Update the simulation
			update(deltaTime);
			// Draw the updates
			draw();

			lastTime = currentTime;
			fpsLabel.setText("fps: " + Integer.toString((int)(1 / deltaTime)));
		}
	}

	/**
	 * Updates the program
	 * @param deltaTime the time between this frame and the last
	 */
	private void update(double deltaTime)
	{
		GameStateManager.update(deltaTime);
	}

	/**
	 * Draws the program to the panel
	 */
	private void draw()
	{
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		try
		{
			GameStateManager.draw(g);
		}
		catch (NullPointerException npe)
		{
			// Do nothing as trying to repaint before the game state manager has something to paint
		}
	}

	/**
	 * Closes any resources used by the program
	 */
	public void stop()
	{
		running = false;
	}
}
