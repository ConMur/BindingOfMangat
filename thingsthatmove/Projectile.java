package thingsthatmove;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Util;

/**
 * A ranged attack that has a limited range. A Projectile can move and draw itself
 * @author Connor Murphy, Matthew Sun
 */
public class Projectile extends GameObject
{
	private int speed, dmg;
	private BufferedImage image;
	private char direction;
	private boolean visible;
	private int lifeSpan;

	private boolean isDead;

	// The room bounds
	private final int LOWER_X_BOUND = 90;
	private final int UPPER_X_BOUND = 960;
	private final int LOWER_Y_BOUND = 200;
	private final int UPPER_Y_BOUND = 672;

	/**
	 * Creates a new projectile of the type specified and travelling in the direction given
	 * @param projectileNum the type of projectile this projectile is
	 * @param direction the direction this projectile will move in
     */
	public Projectile(int projectileNum, char direction)
	{
		super(0, 0, null, null, null, 0, 0);
		visible = true;
		this.direction = direction;
		// Basic projectile
		if (projectileNum == 1)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/player/projectile1.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 1");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(30, 25));
			this.setShadowSize(new Dimension (25, 5));
			this.setXShadow(4);
			this.setYShadow(24);
			

			//TODO: reset damage to 10
			this.speed = 300;
			this.dmg = 1000;
			lifeSpan = 500;
		}
		// Broken pencils (triple shot, faster with less dmg)
		else if (projectileNum == 2)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/player/projectile1.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 2");
				ioe.printStackTrace();
			}

			this.setImage(image);
			this.setSize(new Dimension(30, 25));
			this.setShadowSize(new Dimension (25, 5));
			this.setXShadow(4);
			this.setYShadow(24);
			this.speed = 500;
			this.dmg = 5;
			lifeSpan = 500;
		}
		// Bombs (slower travel speed, explosion on impact)
		else if (projectileNum == 3)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/player/projectile3.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 3");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(30, 25));
			this.speed = 2;
			this.dmg = 500;
			lifeSpan = 500;
		}
		// Mario's fire flower (greater speed, dmg, fire rate)
		else if (projectileNum == 4)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/player/projectile4.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 4");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(30, 25));
			this.speed = 600;
			this.dmg = 10;
			lifeSpan = 500;
		}
		// Reflective Sunglasses (instant speed, low dmg)
		else if (projectileNum == 5)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/player/projectile5.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 5");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(30, 25));
			
			
			this.speed = 1000;
			this.dmg = 3;
			lifeSpan = 1500;
		}
		
		// ENEMY PROJECTILES
		// BASIC ENEMY PROJECTILE 
		else if (projectileNum == 6)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/enemy/projectile1.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 1");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(30, 25));
			this.setShadowSize(new Dimension (25, 5));
			this.setXShadow(4);
			this.setYShadow(24);
			
			this.speed = 300;
			this.dmg = 1;
			lifeSpan = 1500;
		}
		// GISSING PROJECTILE
		else if (projectileNum == 7)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/enemy/frictionlesscart.png"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 1");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(46,25));
			this.setShadowSize(new Dimension(43,8));
			this.setXShadow(3);
			this.setYShadow(20);
			
			this.speed = 600;
			this.dmg = 1;
			lifeSpan = 1500;
		}
		// NULL POINTER PROJECTILE
		else if (projectileNum == 8)
		{
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream(
						"/images/projectiles/enemy/projectile2.PNG"));
			}
			catch (IOException ioe)
			{
				System.err.println("Error loading projectile 1");
				ioe.printStackTrace();
			}
			this.setImage(image);
			this.setSize(new Dimension(224,24));
			this.setShadowSize(new Dimension(222,3));
			this.setXShadow(1);
			this.setYShadow(17);
			
			this.speed = 50;
			this.dmg = 1;
			lifeSpan = 500;
		}
		isDead = false;
	}

	/**
	 * @param speed the speed of the projectile in pixels/sec
	 * @param dmg the damage the projectile does
	 * @param image the image for the projectile
	 * @param startX the x coordinate where the projectile starts
	 * @param startY the y coordinate where the projectile starts
	 * @param size the size of the projectile
	 * @param direction the direction the projectile is headed in
	 * @param lifeSpan the number of pixels the projectile should travel before
	 *            expiring
	 */
	public Projectile(int speed, int dmg, Image image, int startX, int startY,
			Dimension size, char direction, int lifeSpan, Dimension shadowSize, int xShadow, int yShadow)
	{
		super(startX, startY, image, size, shadowSize, xShadow, yShadow);
		this.speed = speed;
		this.dmg = dmg;
		this.direction = direction;
		visible = true;
		isDead = false;
		this.lifeSpan = lifeSpan;
	}

	/**
	 * Returns the direction that this projectile is moving in
	 * @return the direction that this projectile is moving in
     */
	public char getDirection()
	{
		return direction;
	}

	/**
	 * Sets the direction that this projectile is moving in
	 * @param c the direction that this projectile will move in
	 */
	public void setDirection(char c)
	{
		direction = c;
	}

	/**
	 * Returns if the projectile is visible or not
	 * @return if the projectile is visible or not
     */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * Sets the visibility of this projectile
	 * @param b whether the projectile is visible or not
     */
	public void setVisibility(boolean b)
	{
		visible = b;
	}

	/**
	 * Returns the damage of this projectile if it is still moveing
	 * @return the damage of this projectile if it is still moveing
     */
	public int getDamage()
	{
		// Make sure that there is no damage if the projectile is dead
		if (isDead)
			return 0;
		return dmg;
	}

	/**
	 * Notifies the projectile that is should be removed from the simulation
	 */
	public void killProjectile()
	{
		isDead = true;
	}

	/**
	 * Moves the particle and removes it if it has traveled more than its range or if it goes out of the room's bounds
	 */
	public void update()
	{
		if (direction == 'N')
		{
			moveNorth();
		}
		else if (direction == 'E')
		{
			moveEast();
		}
		else if (direction == 'W')
		{
			moveWest();
		}
		else if (direction == 'S')
		{
			moveSouth();
		}
		else if (direction == '1')
		{
			moveNorthEast();
		}
		else if (direction == '2')
		{
			moveSouthEast();
		}
		else if (direction == '3')
		{
			moveSouthWest();
		}
		else if (direction == '4')
		{
			moveNorthWest();
		}

		// Subtract the amount travelled from the life span
		lifeSpan -= speed * Util.getDeltaTime();

		// Check if off screen
		double x = getX();
		double y = getY();
		if (x < LOWER_X_BOUND || y < LOWER_Y_BOUND || x > UPPER_X_BOUND
				|| y > UPPER_Y_BOUND)
			isDead = true;

		// Check if life span is over
		if (lifeSpan <= 0)
			isDead = true;
	}

	/**
	 * Draws the projectile
	 * @param g the graphics to draw to
     */
	public void draw(Graphics g)
	{
		g.drawImage(image, (int) getX(), (int) getY(), null);
//		g.drawRect((int)getX(),(int)getY(),(int)getSize().getWidth(), (int)getSize().getHeight());
	}

	/**
	 * Returns if this projectile is dead and should be removed
	 *
	 * @return if this projectile is dead and should be removed
	 */
	public boolean isDeadProjectile()
	{
		return isDead;
	}

	/**
	 * Moves the projectile north west
	 */
	public void moveNorthWest()
	{
		setX(getX() - (speed * Util.getDeltaTime()));
		setY(getY() - (speed * Util.getDeltaTime()));
		if (getY() < LOWER_Y_BOUND || getX() < LOWER_Y_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile north
	 */
	public void moveNorth()
	{
		setY(getY() - speed * Util.getDeltaTime());
		if (getY() < LOWER_Y_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile north east
	 */
	public void moveNorthEast()
	{
		setY(getY() - (speed * Util.getDeltaTime()));
		setX(getX() + (speed * Util.getDeltaTime()));
		if (getY() < LOWER_Y_BOUND || getX() > UPPER_X_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile east
	 */
	public void moveEast()
	{
		setX(getX() + speed * Util.getDeltaTime());
		if (getX() > UPPER_X_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile south east
	 */
	public void moveSouthEast()
	{
		setX(getX() + (speed * Util.getDeltaTime()));
		setY(getY() + (speed * Util.getDeltaTime()));
		if (getX() > UPPER_X_BOUND || getY() > UPPER_Y_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile south
	 */
	public void moveSouth()
	{
		setY(getY() + speed * Util.getDeltaTime());
		if (getY() > UPPER_Y_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile south west
	 */
	public void moveSouthWest()
	{
		setY(getY() + (speed * Util.getDeltaTime()));
		setX(getX() - (speed * Util.getDeltaTime()));
		if (getY() > UPPER_Y_BOUND || getX() < LOWER_Y_BOUND)
			visible = false;
	}

	/**
	 * Moves the projectile west
	 */
	public void moveWest()
	{
		setX(getX() - speed * Util.getDeltaTime());
		if (getX() < LOWER_Y_BOUND)
			visible = false;
	}

}
