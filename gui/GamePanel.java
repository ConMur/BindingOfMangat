package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

import states.GameState;
import states.GameStateManager;
import util.ImageManager;
import util.Util;

/**
 * The panel that displays what is happening in the program
 * @author Connor Murphy
 *
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
	/**
	 * A generated serialVersionUID so eclipse does not yell at me
	 */
	private static final long serialVersionUID = -7906909062119976256L;

	private boolean running;

	private long lastTime;

	private JLabel fpsLabel;

	private int oldWidth, oldHeight;

	private int frames;

	public GamePanel()
	{
		super();
		running = true;
		
		setPreferredSize(new Dimension(1024, 768));

		lastTime = System.nanoTime();

		fpsLabel = new JLabel();
		add(fpsLabel);

		oldWidth = getWidth();
		oldHeight = getHeight();

		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Initializes any resources and starts the program
	 */
	public void go()
	{
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int ticks = 0;
		frames = 0;
		long timer = System.currentTimeMillis();

		ImageManager.init();
		GameStateManager.init();

		long lastFrameTime = System.nanoTime();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				draw();
				ticks++;
				delta--;

				double deltaFrameTime = (System.nanoTime() - lastFrameTime)/1000000000.0;
				Util.setDeltaTime(deltaFrameTime);
				lastFrameTime = System.nanoTime();
				fpsLabel.setText("FPS: " + (int)Util.calcFPS(deltaFrameTime));
			}
			frames++;


			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				ticks = 0;
			}


		}
		/*
		//Initialize static classes
		ImageManager.init();
		GameStateManager.init();

		//Program loop
		while (running)
		{
			// Calculate the time between frames
			long currentTime = System.nanoTime();
			double deltaTime = (currentTime - lastTime) / 1000000000.0;
			Util.setDeltaTime(deltaTime);
			// Update the simulation
			update();

			//Check if the window was resized

			int height = getHeight();
			int width = getWidth();
			if(width != oldWidth || height != oldHeight )
			{
				ImageManager.resizeImages(width, height);
				oldWidth = width;
				oldHeight = height;
			}
			// Draw the updates
			draw();

			lastTime = currentTime;
			fpsLabel.setText("fps: " + (int)Util.calcFPS(deltaTime));
			try
			{
				Thread.sleep(16);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * Updates the program
	 */
	private void update()
	{
		GameStateManager.update();
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		GameStateManager.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		GameStateManager.keyReleased(e.getKeyCode());
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		GameStateManager.mousePressed(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		GameStateManager.mousePressed(e.getX(), e.getY());
	}
}
