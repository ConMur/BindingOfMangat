package states;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The state the program goes into when the player dies. Allows the player to return to the menu or play again
 *
 * @author Connor Murphy
 */
public class DeadState implements GameState {
    private BufferedImage bg;

    @Override
    public void init() {
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/images/deathscreen.png"));
        } catch (IOException ioe) {
            System.err.println("Error loading death screen background");
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

    }

    @Override
    public void keyReleased(int key) {

    }

    @Override
    public void mousePressed(int x, int y) {
        //play again button
        if (x > 150 && y > 628 && x < 473 && y < 725) {
            GameStateManager.setState(State.PLAY);
        }
        // main menu button
        else if (x > 515 && y > 625 && x < 837 && y < 726) {
            GameStateManager.setState(State.MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {

    }
}
