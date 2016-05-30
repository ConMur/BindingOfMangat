package gui;

import java.awt.Graphics;

import javax.swing.JPanel;

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

	public GamePanel()
	{
		super();
		running = true;

		lastTime = System.nanoTime();
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
			double deltaTime = (lastTime - currentTime) / 1000000000.0;

			// Update the simulation
			update(deltaTime);
			// Draw the updates
			draw();

			lastTime = currentTime;
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
		GameStateManager.draw(g);
	}

	/**
	 * Closes any resources used by the program
	 */
	public void stop()
	{
		running = false;
	}
}
