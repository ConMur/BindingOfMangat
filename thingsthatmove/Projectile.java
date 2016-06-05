package thingsthatmove;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Projectile extends GameObject
{
	private int speed, dmg;
	private Image image;
	private char direction;
	private boolean visible;
	
	private final int LOWER_X_BOUND = 0;
	private final int UPPER_X_BOUND = 0;
	private final int LOWER_Y_BOUND = 0;
	private final int UPPER_Y_BOUND = 0;
	private final double ROOT_TWO = Math.sqrt(2);
	
	public Projectile (int projectileNum, char direction)
	{
		super (0, 0, null, null);
		visible = true;
		this.direction = direction;
		// Basic projectile
		if (projectileNum == 1)
		{
			image = new ImageIcon("images/projectiles/player/projectile1.png").getImage();
			this.setImage(image);
			this.setSize(new Dimension(50, 50));
			this.speed = 5;
			this.dmg = 5;
		}
		// Broken pencils (greater speed + dmg)
		else if (projectileNum == 2)
		{
			image = new ImageIcon("images/projectiles/player/projectile2.png").getImage();
			this.setImage(image);
			this.setSize(new Dimension(50, 50));
			this.speed = 10;
			this.dmg = 8;
		}
		// Bombs (slower travel speed, explosion on impact)
		else if (projectileNum == 3)
		{
			image = new ImageIcon("images/projectiles/player/projectile3.png").getImage();
			this.setImage(image);
			this.setSize(new Dimension(50, 50));
			this.speed = 2;
			this.dmg = 15;
		}
		// Mario's fire flower (greater speed, dmg, fire rate)
		else if (projectileNum == 4)
		{
			image = new ImageIcon("images/projectiles/player/projectile4.png").getImage();
			this.setImage(image);
			this.setSize(new Dimension(50, 50));
			this.speed = 15;
			this.dmg = 10;
		}
		// Reflective Sunglasses (instant speed, low dmg)
		else if (projectileNum == 5)
		{
			image = new ImageIcon("images/projectiles/player/projectile5.png").getImage();
			this.setImage(image);
			this.setSize(new Dimension(50, 50));
			this.speed = 25;
			this.dmg = 3;
		}
	}
	
	public Projectile (int speed, int dmg, Image image, int startX, int startY, Dimension size)
	{
		super(startX, startY, image, size);
		this.speed = speed;
		this.dmg = dmg;
		visible = true;
	}
	
	public char getDirection ()
	{
		return direction;
	}
	
	public void setDirection (char c)
	{
		direction = c;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setVisibility(boolean b)
	{
		visible = b;
	}
	
	public void moveInDirection()
	{
		if (direction == 'N')
			moveNorth();
		else if (direction == 'E')
			moveEast();
		else if (direction == 'W')
			moveWest();
		else if (direction == 'S')
			moveSouth();
		else if (direction == '1')
			moveNorthEast();
		else if (direction == '2')
			moveSouthEast();
		else if (direction == '3')
			moveSouthWest();
		else if (direction == '4')
			moveNorthWest();
	}
	
	public void moveNorthWest()
	{
		setX(getX() - (int)(speed/ROOT_TWO));
		setY(getY() - (int)(speed/ROOT_TWO));
		if (getY() < LOWER_Y_BOUND || getX() < LOWER_Y_BOUND)
			visible = false;
	}
	
	public void moveNorth ()
	{
		setY(getY() - speed);
		if (getY() < LOWER_Y_BOUND)
			visible = false;
	}
	
	public void moveNorthEast ()
	{
		setY(getY() - (int)(speed/ROOT_TWO));
		setX(getX() + (int)(speed/ROOT_TWO));
		if (getY() < LOWER_Y_BOUND || getX() > UPPER_X_BOUND)
			visible = false;
	}
	
	public void moveEast ()
	{
		setX(getX() + speed);
		if (getX() > UPPER_X_BOUND)
			visible = false;
	}
	
	public void moveSouthEast()
	{
		setX(getX() + (int)(speed/ROOT_TWO));
		setY(getY() + (int)(speed/ROOT_TWO));
		if (getX() > UPPER_X_BOUND || getY() > UPPER_Y_BOUND)
			visible = false;
	}
	
	public void moveSouth ()
	{
		setY(getY() + speed);
		if (getY() > UPPER_Y_BOUND)
			visible = false;
	}
	
	public void moveSouthWest()
	{
		setY(getY() + (int)(speed/ROOT_TWO));
		setX(getX() - (int)(speed/ROOT_TWO));
		if (getY() > UPPER_Y_BOUND || getX() < LOWER_Y_BOUND)
			visible = false;
	}
	
	public void moveWest ()
	{
		setX(getX() - speed);
		if (getX() < LOWER_Y_BOUND)
			visible = false;
	}
	
}
