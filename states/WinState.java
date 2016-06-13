package states;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The state that the program goes into when the player beats the game.
 * It allows the player to navigate back to the main menu
 * @author Connor Murphy
 */
public class WinState implements GameState {
    private BufferedImage bg;

    @Override
    public void init() {
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/images/WinScreen.png"));
        } catch (IOException ioe) {
            System.err.println("Error loading win state background");
            ioe.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bg, 0, 0, null);
    }

    @Override
    public void cleanUp() {
        bg = null;
    }

    @Override
    public void keyPressed(int key) {
        //When any key is pressed, go to the main menu
        GameStateManager.setState(State.MENU);
    }

    @Override
    public void keyReleased(int key) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }
}
