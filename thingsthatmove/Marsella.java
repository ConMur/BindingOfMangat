package thingsthatmove;

import java.awt.*;

/**
 * @author Connor Murphy
 */
public class Marsella extends Enemy {

    public Marsella(int dmg, int hp, int speed, int xPos, int yPos, Image image,
                   Dimension size, int maxHP, boolean shouldMove, boolean anger)
    {
        super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shouldMove, anger, new Dimension(74, 15), 6, 120);
    }
}
