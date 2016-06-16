package states;

import levels.LevelManager;
import thingsthatmove.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * The state the player is in when they are playing the game. This state updates the level manager and
 * creates the player
 *
 * @author Connor Murphy
 */
public class PlayState implements GameState {
    private Player player;

    @Override
    public void init() {
        //Create the player
        try {
            player = new Player(10, 5, 250, 350, 400, ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatfront.png")),
                    new Dimension(75, 55), 5, 1, null, new Dimension(55, 10), 13, 72);
        } catch (IOException ioe) {
            System.err.println("Unable to read player image file");
            ioe.printStackTrace();
        }
        LevelManager.init(player);
    }

    @Override
    public void update() {
        LevelManager.update();
    }

    @Override
    public void draw(Graphics g) {
        LevelManager.draw(g);
    }

    @Override
    public void cleanUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(int key) {
        if (player != null)
            player.keyPressed(key);
    }

    @Override
    public void keyReleased(int key) {
        if (player != null)
            player.keyReleased(key);
    }

    @Override
    public void mousePressed(int x, int y) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(int x, int y) {
        // TODO Auto-generated method stub

    }

}
