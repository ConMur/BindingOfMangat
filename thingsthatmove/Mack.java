package thingsthatmove;

import java.awt.*;

/**
 * @author Connor Murphy
 */
public class Mack extends Enemy {

    public Mack(int dmg, int hp, int speed, int xPos, int yPos, Image image,
                 Dimension size, int maxHP, boolean shouldMove, boolean anger)
    {
        super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shouldMove, anger);
        setFireRate(1000);
    }
    
}
