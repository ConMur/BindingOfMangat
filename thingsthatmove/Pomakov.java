package thingsthatmove;

import java.awt.*;

/**
 * The level 3 boss, Mr. Pomakov
 * @author Connor Murphy
 */
public class Pomakov extends Enemy {

    public Pomakov(int dmg, int hp, int speed, int xPos, int yPos, Image image,
                 Dimension size, int maxHP, boolean shouldMove, boolean anger)
    {
        super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shouldMove, anger,  new Dimension(66, 14), 4, 115);
    }
    
}
