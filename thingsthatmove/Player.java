package thingsthatmove;

import item.Item;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player extends MoveableObject {
    private int projectile;
    private Item currentItem;
    private boolean movingNorth;
    private boolean movingWest;
    private boolean movingEast;
    private boolean movingSouth;
    private ArrayList<Projectile> currentProjectiles;
    private Thread pThread;
    private boolean isShooting;

    private BufferedImage mangatFront, mangatBack, mangatLeft, mangatRight;

    public Player(int dmg, int hp, int speed, int x, int y, Image i,
                  Dimension s, int maxHP, int projectile, Item item) {
        super(dmg, hp, speed, x, y, i, s, maxHP);
        currentProjectiles = new ArrayList<Projectile>();
        this.projectile = projectile;
        this.currentItem = item;
        boolean movingNorth = false;
        boolean movingWest = false;
        boolean movingEast = false;
        boolean movingSouth = false;


        try {
            mangatFront = ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatfront.png"));
            mangatBack = ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatback.png"));
            mangatLeft = ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatleft.png"));
            mangatRight = ImageIO.read(getClass().getResourceAsStream("/images/mangat/mangatright.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Projectile> getAllPlayerProjectiles() {
        return currentProjectiles;
    }

    public void setCurrentProjectiles(ArrayList<Projectile> p) {
        currentProjectiles = p;
    }

    /**
     * Sets the projectile number of the player
     *
     * @param projectileNumber the projectile number
     */
    public void setProjectile(int projectileNumber) {
        projectile = projectileNumber;
    }

    public void shootProjectile(char direction) {
        Projectile p = new Projectile(getProjectile(), direction);
        p.setX(getX());
        p.setY(getY());
        currentProjectiles.add(p);
    }

    /**
     * Returns the projectile type
     *
     * @return the projectile number
     */
    public int getProjectile() {
        return projectile;
    }

    /**
     * @return the current item in possession
     */
    public Item getCurrentItem() {
        return currentItem;
    }

    public String getCurrentItemName() {
        return currentItem.getName();
    }

    public void setItem(Item i) {
        currentItem = i;
    }

    public void useItem() {
        String itemName = currentItem.getName();
        this.setItem(null);

        // FINISH ITEMS
    }
    
    public boolean isMovingNorth()
    {
    	return movingNorth;
    }
    
    public boolean isMovingSouth()
    {
    	return movingSouth;
    }
    
    public boolean isMovingEast()
    {
    	return movingEast;
    }
    
    public boolean isMovingWest()
    {
    	return movingWest;
    }

    public void updatePosition() {
        if (movingNorth)
            moveNorth();
        if (movingSouth)
            moveSouth();
        if (movingWest)
            moveWest();
        if (movingEast)
            moveEast();

        int removedProjectiles = 0;
        for (int i = 0; i < currentProjectiles.size(); ++i) {
            Projectile p = currentProjectiles.get(i - removedProjectiles);
            p.update();
            if(p.isDeadProjectile())
            {
                currentProjectiles.remove(i - removedProjectiles);
                ++removedProjectiles;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(getImage(), (int) getX(), (int) getY(), null);

        for (Projectile p : currentProjectiles) {
        	if (p.getImage() == null)
        		System.out.println("NO IMAGE");
            g.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), null);
        }
    }

    public void keyPressed(int key) {
        if (key == KeyEvent.VK_W) {
            if (!movingNorth) {
                setImage(mangatBack);
            }
            movingNorth = true;
        }
        if (key == KeyEvent.VK_A) {
            if (!movingWest) {
                setImage(mangatLeft);
            }
            movingWest = true;
        }
        if (key == KeyEvent.VK_S) {
            if (!movingSouth) {
                setImage(mangatFront);
            }
            movingSouth = true;
        }
        if (key == KeyEvent.VK_D) {
            if (!movingEast) {
                setImage(mangatRight);
            }
            movingEast = true;
        }

        if (key == KeyEvent.VK_SPACE) {
            isShooting = true;
            if (movingNorth && movingEast)
                shootProjectile('1');
            else if (movingSouth && movingEast)
                shootProjectile('2');
            else if (movingSouth && movingWest)
                shootProjectile('3');
            else if (movingNorth && movingWest)
                shootProjectile('4');
            else if (movingNorth)
                shootProjectile('N');
            else if (movingEast)
                shootProjectile('E');
            else if (movingSouth)
                shootProjectile('S');
            else if (movingWest)
                shootProjectile('W');
            else
                System.err.println("no projextile added");
        }
    }

    public void keyReleased(int key) {
        if (key == KeyEvent.VK_W)
            movingNorth = false;
        if (key == KeyEvent.VK_A)
            movingWest = false;
        if (key == KeyEvent.VK_S)
            movingSouth = false;
        if (key == KeyEvent.VK_D)
            movingEast = false;
    }

    public void clearProjectiles() {
        currentProjectiles.clear();
    }
}
