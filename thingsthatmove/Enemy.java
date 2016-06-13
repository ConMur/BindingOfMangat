package thingsthatmove;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import util.Util;

public class Enemy extends MoveableObject
{
	private ArrayList<Projectile> currentProjectiles;
	private int projectile;
	private long lastFireTime;
	private int fireRate;

	private final int LOWER_X_BOUND = 130;
	private final int UPPER_X_BOUND = 850;
	private final int LOWER_Y_BOUND = 330;
	private final int UPPER_Y_BOUND = 575;

	private final int PROJECTILE_ONE_RATE = 500;
	private final int PROJECTILE_TWO_RATE = 300;
	private final int PROJECTILE_THREE_RATE = 30;
	private final int PROJECTILE_FOUR_RATE = 30;
	private final int PROJECTILE_FIVE_RATE = 3;
	private Dimension movementHitbox;

	private char currentDirection = ' ';
	private boolean shouldMove;
	private boolean anger;

	public Enemy(int dmg, int hp, int speed, int xPos, int yPos, Image image,
			Dimension size, int maxHP, boolean shouldMove, boolean anger,
			Dimension shadowSize, int xShadow, int yShadow)
	{
		super(dmg, hp, speed, xPos, yPos, image, size, maxHP, shadowSize,
				xShadow, yShadow);
		this.shouldMove = shouldMove;
		this.anger = anger;
		fireRate = PROJECTILE_ONE_RATE;
		projectile = 6;
		currentProjectiles = new ArrayList<Projectile>();
	}

	public Enemy(Enemy connorBeingLazy)
	{
		super(connorBeingLazy.getDamage(), connorBeingLazy.getCurrentHP(),
				connorBeingLazy.getSpeed(), (int) connorBeingLazy.getX(),
				(int) connorBeingLazy.getY(), connorBeingLazy.getImage(),
				connorBeingLazy.getSize(), connorBeingLazy.getMaxHP(),
				connorBeingLazy.getShadowSize(), connorBeingLazy.getXShadow(),
				connorBeingLazy.getYShadow());
		this.shouldMove = connorBeingLazy.canMove();
		this.anger = connorBeingLazy.isAngry();
//		this.movementHitbox = connorBeingLazy.getMovementSize();
		fireRate = PROJECTILE_ONE_RATE;
		projectile = connorBeingLazy.getProjectile();
		currentProjectiles = new ArrayList<Projectile>();
	}

//	public void setMovementHitbox(Dimension d)
//	{
//		movementHitbox = d;
//	}
//
//	public Dimension getMovementSize()
//	{
//		return movementHitbox;
//	}
//
//	public Rectangle getMovementHitbox()
//	{
//		return new Rectangle((int) (getX() + getSize().getWidth() * 0.2),
//				(int) (getY() + getSize().getHeight() * 1.2),
//				(int) movementHitbox.getWidth(),
//				(int) movementHitbox.getHeight());
//	}

	public void draw(Graphics g)
	{
		// Draw their set of projectiles
		for (int n = 0; n < currentProjectiles.size(); n++)
		{
			Projectile p = currentProjectiles.get(n);
			g.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), null);
		}
		
		// Draw the hp bar
		g.setColor(Color.GREEN);
		
		int green = (int)((double)getCurrentHP()/getMaxHP() * getSize().getWidth());
		g.fillRect((int)getX(), (int)getY() - 10, green, 10);
		
		g.setColor(Color.RED);
		g.fillRect((int)getX() + green, (int)getY() - 10, (int)getSize().getWidth() - green, 10);

	}

	public ArrayList<Projectile> getAllProjectiles()
	{
		return currentProjectiles;
	}

	public void setCurrentProjectiles(ArrayList<Projectile> p)
	{
		currentProjectiles = p;
	}

	public void setProjectile(int projectileNumber)
	{
		projectile = projectileNumber;
	}

	public void shootProjectile(char direction)
	{
		if (System.currentTimeMillis() - lastFireTime > fireRate)
		{
			Projectile p = new Projectile(getProjectile(), direction);
			p.setX(getX() + 20);
			p.setY(getY() + 25);
			currentProjectiles.add(p);
			lastFireTime = System.currentTimeMillis();
		}
	}

	public void shootTripleProjectiles()
	{
		if (System.currentTimeMillis() - lastFireTime > fireRate)
		{
			Projectile p = new Projectile(getProjectile(), currentDirection);
			p.setX(getX() + 20);
			p.setY(getY() + 25);
			currentProjectiles.add(p);

			Projectile p2 = new Projectile(getProjectile(), getTopDirection());
			p2.setX(getX() + 20);
			p2.setY(getY() + 25);
			currentProjectiles.add(p2);

			Projectile p3 = new Projectile(getProjectile(), getBotDirection());
			p3.setX(getX() + 20);
			p3.setY(getY() + 25);
			currentProjectiles.add(p3);

			lastFireTime = System.currentTimeMillis();
		}
	}

	public void shootAllDirections()
	{
		if (System.currentTimeMillis() - lastFireTime > fireRate)
		{
			Projectile p = new Projectile(getProjectile(), 'N');
			p.setX(getX() + 20);
			p.setY(getY() + 25);
			currentProjectiles.add(p);

			Projectile p2 = new Projectile(getProjectile(), 'S');
			p2.setX(getX() + 20);
			p2.setY(getY() + 25);
			currentProjectiles.add(p2);

			Projectile p3 = new Projectile(getProjectile(), 'W');
			p3.setX(getX() + 20);
			p3.setY(getY() + 25);
			currentProjectiles.add(p3);

			Projectile p4 = new Projectile(getProjectile(), 'E');
			p4.setX(getX() + 20);
			p4.setY(getY() + 25);
			currentProjectiles.add(p4);

			Projectile p5 = new Projectile(getProjectile(), '1');
			p5.setX(getX() + 20);
			p5.setY(getY() + 25);
			currentProjectiles.add(p5);

			Projectile p6 = new Projectile(getProjectile(), '2');
			p6.setX(getX() + 20);
			p6.setY(getY() + 25);
			currentProjectiles.add(p6);

			Projectile p7 = new Projectile(getProjectile(), '3');
			p7.setX(getX() + 20);
			p7.setY(getY() + 25);
			currentProjectiles.add(p7);

			Projectile p8 = new Projectile(getProjectile(), '4');
			p8.setX(getX() + 20);
			p8.setY(getY() + 25);
			currentProjectiles.add(p8);

			lastFireTime = System.currentTimeMillis();

		}
	}

	public void setFireRate(int n)
	{
		fireRate = n;
	}

	public int getProjectile()
	{
		return projectile;
	}

	public void clearProjectiles()
	{
		currentProjectiles.clear();
	}

	public void updateProjectiles()
	{
		int removedProjectiles = 0;
		for (int i = 0; i < currentProjectiles.size(); ++i)
		{
			Projectile p = currentProjectiles.get(i - removedProjectiles);
			p.update();
			if (p.isDeadProjectile())
			{
				currentProjectiles.remove(i - removedProjectiles);
				++removedProjectiles;
			}
		}
	}

	public boolean isAngry()
	{
		return anger;
	}

	public void setAnger(boolean b)
	{
		anger = b;
	}

	public boolean canMove()
	{
		return shouldMove;
	}

	public void setMoveState(boolean b)
	{
		shouldMove = b;
	}

	public void moveInDirection()
	{
		if (currentDirection == 'N')
			moveNorth();
		else if (currentDirection == 'E')
			moveEast();
		else if (currentDirection == 'S')
			moveSouth();
		else if (currentDirection == 'W')
			moveWest();
		else if (currentDirection == '1')
			moveNorthEast();
		else if (currentDirection == '2')
			moveSouthEast();
		else if (currentDirection == '3')
			moveSouthWest();
		else if (currentDirection == '4')
			moveNorthWest();
	}

	public void setDirection(char c)
	{
		currentDirection = c;
	}

	public char getTopDirection()
	{
		if (currentDirection == 'N')
			return '1';
		else if (currentDirection == 'E')
			return '1';
		else if (currentDirection == 'S')
			return '2';
		else if (currentDirection == 'W')
			return '4';
		else if (currentDirection == '1')
			return 'N';
		else if (currentDirection == '2')
			return 'E';
		else if (currentDirection == '3')
			return 'W';
		else
			return 'N';
	}

	public char getBotDirection()
	{
		if (currentDirection == 'N')
			return '4';
		else if (currentDirection == 'E')
			return '2';
		else if (currentDirection == 'S')
			return '3';
		else if (currentDirection == 'W')
			return '3';
		else if (currentDirection == '1')
			return 'E';
		else if (currentDirection == '2')
			return 'S';
		else if (currentDirection == '3')
			return 'S';
		else
			return 'W';
	}

	public char getDirection()
	{
		return currentDirection;
	}

	public void setRandomDirection()
	{
		Random r = new Random();
		int direction = r.nextInt(8);
		if (direction == 0)
			currentDirection = 'N';
		else if (direction == 1)
			currentDirection = 'E';
		else if (direction == 2)
			currentDirection = 'S';
		else if (direction == 3)
			currentDirection = 'W';
		else if (direction == 4)
			currentDirection = '1';
		else if (direction == 5)
			currentDirection = '2';
		else if (direction == 6)
			currentDirection = '3';
		else if (direction == 7)
			currentDirection = '4';
	}

	public void moveEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND)
			setX(getX() + getSpeed() * Util.getDeltaTime());
	}

	public void moveWest()
	{
		if (getX() > LOWER_X_BOUND)
			setX(getX() - getSpeed() * Util.getDeltaTime());
	}

	public void moveNorthWest()
	{
		if (getX() > LOWER_X_BOUND && getY() > LOWER_Y_BOUND)
		{
			setX(getX() - (getSpeed() * Util.getDeltaTime()));
			setY(getY() - (getSpeed() * Util.getDeltaTime()));
		}
	}

	public void moveNorthEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND
				&& getY() > LOWER_Y_BOUND)
		{
			setX(getX() + (getSpeed() * Util.getDeltaTime()));
			setY(getY() - (getSpeed() * Util.getDeltaTime()));
		}
	}

	public void moveNorth()
	{
		if (getY() > LOWER_Y_BOUND)
			setY(getY() - getSpeed() * Util.getDeltaTime());
	}

	public void moveSouth()
	{
		if ((getY() + getSize().getHeight()) < UPPER_Y_BOUND)
			setY(getY() + getSpeed() * Util.getDeltaTime());
	}

	public void moveSouthEast()
	{
		if ((getX() + getSize().getWidth()) < UPPER_X_BOUND
				&& (getY() + getSize().getHeight()) < UPPER_Y_BOUND)
		{
			setX(getX() + (getSpeed() * Util.getDeltaTime()));
			setY(getY() + (getSpeed() * Util.getDeltaTime()));
		}
	}

	public void moveSouthWest()
	{
		if (getX() > LOWER_X_BOUND
				&& (getY() + getSize().getHeight()) < UPPER_Y_BOUND)
		{
			setX(getX() - (getSpeed() * Util.getDeltaTime()));
			setY(getY() + (getSpeed() * Util.getDeltaTime()));
		}
	}
}
