package thingsthatmove;

import java.awt.*;

/**
 * The level 1 boss, Mr. Gissing
 * @author Connor Murphy, Matthew Sun
 */
public class Gissing extends Enemy {

    public Gissing(int dmg, int hp, int speed, int xPos, int yPos, Image image,
                 Dimension size, int maxHP, boolean shouldMove, boolean anger)
    {
        super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shouldMove, anger,  new Dimension(72, 15), 9, 104);
        setFireRate(2500);
        setProjectile(7);
    }
    
}
