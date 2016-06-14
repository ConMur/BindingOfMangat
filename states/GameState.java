package states;

import java.awt.Graphics;

/**
 * An interface for each game state
 *
 * @author Connor Murphy
 */
public interface GameState {
    /**
     * Initialize the game state
     */
    void init();

    /**
     * Updates the game simulation
     */
    void update();

    /**
     * Draws this state to given graphics
     */
    void draw(Graphics g);

    /**
     * Frees any resources used by this state
     */
    void cleanUp();

    /**
     * Called when a key is pressed
     */
    void keyPressed(int key);

    /**
     * Called when a key is released
     */
    void keyReleased(int key);

    /**
     * Called when the mouse is pressed
     */
    void mousePressed(int x, int y);

    /**
     * Called when the mouse is moved
     */
    void mouseMoved(int x, int y);
}
