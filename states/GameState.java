package states;

import java.awt.Graphics;

/**
 * An interface for each game state
 * @author Connor Murphy
 *
 */
public interface GameState
{
	// Initialize the game state
	public void init();
	//Updates the game simulation
	public void update(double deltaTime);
	// Draws this state to given graphics
	public void draw(Graphics g);
	//Frees any resources used by this state
	public void cleanUp();
	//Called when a key is pressed
	public void keyPressed(int key);
	//Called when a key is released
	public void keyReleased(int key);
	//Called when the mouse is pressed
	public void mousePressed(int x, int y);
	//Called when the mouse is moved
	public void mouseMoved(int x, int y);
}
