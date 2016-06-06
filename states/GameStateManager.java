package states;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Manages the updating, drawing and switching of game states in the game
 *
 * @author Connor Murphy
 */
public final class GameStateManager {
    private static ArrayList<GameState> states;
    private static State currentStateValue;
    private static GameState currentState;

    /**
     * Prevent instantiation of this class
     */
    private GameStateManager() {
    }

    /**
     * Initializes any resources used by this object
     */
    public static void init() {
        states = new ArrayList<>();
        states.add(new PlayState());
        // states.add(new MenuState());
        // states.add(new CreditsState());
        // states.add(new InstructionsState());

        //TODO: remove when have a menu
        currentState = states.get(State.PLAY.ordinal());
    }

    /**
     * Sets the current state to the given state
     *
     * @param state the state to change to
     */
    public static void setState(State state) {
        // Ensure that the state is not the one currently in use
        if (state == currentStateValue)
            return;

        // Clean up the old state
        states.get(currentStateValue.ordinal()).cleanUp();

        // TODO: consider a loading screen

        // Load in the new state
        currentState = states.get(state.ordinal());
        currentState.init();
        currentStateValue = state;
    }

    /**
     * Updates the current state
     *
     */
    public static void update() {
        if (currentState != null)
            currentState.update();
    }

    /**
     * Draws the current state to the screen
     *
     * @param g the graphics to draw to
     */
    public static void draw(Graphics g) {
        if (currentState != null)
            currentState.draw(g);
    }

    /**
     * Sends the key press to the current state
     *
     * @param key the key code of the key pressed
     */
    public static void keyPressed(int key) {
        if (currentState != null)
            currentState.keyPressed(key);
    }

    /**
     * Sends the key release to the current state
     *
     * @param key the key code of the key released
     */
    public static void keyReleased(int key) {
        if (currentState != null)
            currentState.keyReleased(key);
    }

    /**
     * Sends the mouse press to the current state
     *
     * @param x the x coordinate of the mouse press
     * @param y the y coordinate of the mouse press
     */
    public static void mousePressed(int x, int y) {
        if (currentState != null)
            currentState.mousePressed(x, y);
    }

    /**
     * Sends the mouse move to the current state
     *
     * @param x the x coordinate of the mouse move
     * @param y the y coordinate of the mouse move
     */
    public static void mouseMoved(int x, int y) {
        if (currentState != null)
            currentState.mouseMoved(x, y);
    }

}
