package thingsthatmove;

import util.Util;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Projectile extends GameObject {
    private int speed, dmg;
    private Image image;
    private char direction;
    private boolean visible;
    private int lifeSpan;

    private boolean isDead;

    // The room bounds
    private final int LOWER_X_BOUND = 90;
    private final int UPPER_X_BOUND = 960;
    private final int LOWER_Y_BOUND = 200;
    private final int UPPER_Y_BOUND = 672;


    public Projectile(int projectileNum, char direction) {
        super(0, 0, null, null);
        visible = true;
        this.direction = direction;
        // Basic projectile
        if (projectileNum == 1) {
            image = new ImageIcon("images/projectiles/player/projectile1.png").getImage();
            this.setImage(image);
            this.setSize(new Dimension(50, 50));
            this.speed = 300;
            this.dmg = 5;
            lifeSpan = 500;
        }
        // Broken pencils (greater speed + dmg)
        else if (projectileNum == 2) {
            image = new ImageIcon("images/projectiles/player/projectile2.png").getImage();
            this.setImage(image);
            this.setSize(new Dimension(50, 50));
            this.speed = 10;
            this.dmg = 400;
            lifeSpan = 500;
        }
        // Bombs (slower travel speed, explosion on impact)
        else if (projectileNum == 3) {
            image = new ImageIcon("images/projectiles/player/projectile3.png").getImage();
            this.setImage(image);
            this.setSize(new Dimension(50, 50));
            this.speed = 2;
            this.dmg = 500;
            lifeSpan = 500;
        }
        // Mario's fire flower (greater speed, dmg, fire rate)
        else if (projectileNum == 4) {
            image = new ImageIcon("images/projectiles/player/projectile4.png").getImage();
            this.setImage(image);
            this.setSize(new Dimension(50, 50));
            this.speed = 600;
            this.dmg = 10;
            lifeSpan = 500;
        }
        // Reflective Sunglasses (instant speed, low dmg)
        else if (projectileNum == 5) {
            image = new ImageIcon("images/projectiles/player/projectile5.png").getImage();
            this.setImage(image);
            this.setSize(new Dimension(50, 50));
            this.speed = 1000;
            this.dmg = 3;
            lifeSpan = 1500;
        }
        isDead = false;
    }

    /**
     * @param speed     the speed of the projectile in pixels/sec
     * @param dmg       the damage the projectile does
     * @param image     the image for the projectile
     * @param startX    the x coordinate where the projectile starts
     * @param startY    the y coordinate where the projectile starts
     * @param size      the size of the projectile
     * @param direction the direction the projectile is headed in
     * @param lifeSpan  the number of pixels the projectile should travel before expiring
     */
    public Projectile(int speed, int dmg, Image image, int startX, int startY, Dimension size, char direction, int lifeSpan) {
        super(startX, startY, image, size);
        this.speed = speed;
        this.dmg = dmg;
        this.direction = direction;
        visible = true;
        isDead = false;
        this.lifeSpan = lifeSpan;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char c) {
        direction = c;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisibility(boolean b) {
        visible = b;
    }

    public int getDamage()
    {
        //Make sure that there is no damage if the projectile is dead
        if(isDead)
            return 0;
        return dmg;
    }

    public void killProjectile()
    {
        isDead = true;
    }

    public void update() {
        if (direction == 'N') {
            moveNorth();
        } else if (direction == 'E') {
            moveEast();
        } else if (direction == 'W') {
            moveWest();
        } else if (direction == 'S') {
            moveSouth();
        } else if (direction == '1') {
            moveNorthEast();
        } else if (direction == '2') {
            moveSouthEast();
        } else if (direction == '3') {
            moveSouthWest();
        } else if (direction == '4') {
            moveNorthWest();
        }

        //Subtract the amount travelled from the life span
        lifeSpan -= speed * Util.getDeltaTime();

        //Check if off screen
        double x = getX();
        double y = getY();
        if (x < LOWER_X_BOUND || y < LOWER_Y_BOUND || x > UPPER_X_BOUND || y > UPPER_Y_BOUND) {
            isDead = true;
        }

        //Check if life span is over
        if (lifeSpan <= 0) {
            isDead = true;
        }
    }

    /**
     * Returns if this projectile is dead and should be removed
     *
     * @return if this projectile is dead and should be removed
     */
    public boolean isDeadProjectile() {
        return isDead;
    }

    public void moveNorthWest() {
        setX(getX() - (speed * Util.getDeltaTime()));
        setY(getY() - (speed * Util.getDeltaTime()));
        if (getY() < LOWER_Y_BOUND || getX() < LOWER_Y_BOUND)
            visible = false;
    }

    public void moveNorth() {
        setY(getY() - speed * Util.getDeltaTime());
        if (getY() < LOWER_Y_BOUND)
            visible = false;
    }

    public void moveNorthEast() {
        setY(getY() - (speed * Util.getDeltaTime()));
        setX(getX() + (speed * Util.getDeltaTime()));
        if (getY() < LOWER_Y_BOUND || getX() > UPPER_X_BOUND)
            visible = false;
    }

    public void moveEast() {
        setX(getX() + speed * Util.getDeltaTime());
        if (getX() > UPPER_X_BOUND)
            visible = false;
    }

    public void moveSouthEast() {
        setX(getX() + (speed * Util.getDeltaTime()));
        setY(getY() + (speed * Util.getDeltaTime()));
        if (getX() > UPPER_X_BOUND || getY() > UPPER_Y_BOUND)
            visible = false;
    }

    public void moveSouth() {
        setY(getY() + speed * Util.getDeltaTime());
        if (getY() > UPPER_Y_BOUND)
            visible = false;
    }

    public void moveSouthWest() {
        setY(getY() + (speed * Util.getDeltaTime()));
        setX(getX() - (speed * Util.getDeltaTime()));
        if (getY() > UPPER_Y_BOUND || getX() < LOWER_Y_BOUND)
            visible = false;
    }

    public void moveWest() {
        setX(getX() - speed * Util.getDeltaTime());
        if (getX() < LOWER_Y_BOUND)
            visible = false;
    }

}
