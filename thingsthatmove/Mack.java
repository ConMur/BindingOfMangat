package thingsthatmove;

import java.awt.*;

/**
 * The level 4 boss, Mr. Mac
 * @author Connor Murphy
 */
public class Mack extends Enemy {

    public Mack(int dmg, int hp, int speed, int xPos, int yPos, Image image,
                 Dimension size, int maxHP, boolean shouldMove, boolean anger)
    {
        super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shouldMove, anger, new Dimension(70, 15), 7, 96);
        setFireRate(1000);
    }
    
}
