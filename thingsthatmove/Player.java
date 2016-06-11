package thingsthatmove;

import item.Item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player extends MoveableObject
{
	private int projectile;
	private Item currentItem;
	private boolean movingNorth;
	private boolean movingWest;
	private boolean movingEast;
	private boolean movingSouth;
	private ArrayList<Projectile> currentProjectiles;
	private Thread pThread;
	private boolean isShooting;
	private boolean takenDMG;
	private Dimension movementHitbox;

	// Items
	private int numKeys;

	// Projectiles
	private long lastFireTime, lastDmgTime;
	private int fireRate, invincibleTime;
	// Miliseconds between firing
	private final int PROJECTILE_ONE_RATE = 500;
	private final int PROJECTILE_TWO_RATE = 300;
	private final int PROJECTILE_THREE_RATE = 30;
	private final int PROJECTILE_FOUR_RATE = 30;
	private final int PROJECTILE_FIVE_RATE = 3;

	private BufferedImage mangatFront, mangatBack, mangatLeft, mangatRight;
	private BufferedImage mangatHurtFront, mangatHurtBack, mangatHurtLeft,
			mangatHurtRight;
	private BufferedImage fullHeart, emptyHeart;

	public Player(int dmg, int hp, int speed, int x, int y, Image i,
			Dimension s, int maxHP, int projectile, Item item,
			Dimension shadowSize, int xShadow, int yShadow)
	{
		super(dmg, hp, speed, x, y, i, s, maxHP, shadowSize, xShadow, yShadow);
		currentProjectiles = new ArrayList<Projectile>();
		this.projectile = projectile;
		this.currentItem = item;
		fireRate = PROJECTILE_ONE_RATE;
		invincibleTime = 1500;

		boolean movingNorth = false;
		boolean movingWest = false;
		boolean movingEast = false;
		boolean movingSouth = false;
		takenDMG = false;

		numKeys = 0;

		try
		{
			mangatFront = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/mangatfront.png"));
			mangatBack = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/mangatback.png"));
			mangatLeft = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/mangatleft.png"));
			mangatRight = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/mangatright.png"));
			mangatHurtFront = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/hurt/mangatfront.png"));
			mangatHurtBack = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/hurt/mangatback.png"));
			mangatHurtLeft = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/hurt/mangatleft.png"));
			mangatHurtRight = ImageIO.read(getClass().getResourceAsStream(
					"/images/mangat/hurt/mangatright.png"));
			fullHeart = ImageIO.read(getClass().getResourceAsStream(
					"/images/fullheart.png"));
			emptyHeart = ImageIO.read(getClass().getResourceAsStream(
					"/images/emptyheart.png"));
			setImage(mangatFront);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void takeDamage(int amount)
	{
		if (System.currentTimeMillis() - lastDmgTime > invincibleTime)
		{
			super.takeDamage(amount);
			lastDmgTime = System.currentTimeMillis();
			takenDMG = true;

			if (getImage() == mangatFront)
			{
				setImage(mangatHurtFront);
			}
			else if (getImage() == mangatBack)
			{
				setImage(mangatHurtBack);
			}
			else if (getImage() == mangatLeft)
			{
				setImage(mangatHurtLeft);
			}
			else if (getImage() == mangatRight)
			{
				setImage(mangatHurtRight);
			}
		}

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
//		return new Rectangle((int) getX() + 10, (int) (getY() + 70),
//				(int) movementHitbox.getWidth(),
//				(int) movementHitbox.getHeight());
//	}

	public ArrayList<Projectile> getAllPlayerProjectiles()
	{
		return currentProjectiles;
	}

	public void setCurrentProjectiles(ArrayList<Projectile> p)
	{
		currentProjectiles = p;
	}

	/**
	 * Sets the projectile number of the player
	 *
	 * @param projectileNumber the projectile number
	 */
	public void setProjectile(int projectileNumber)
	{
		projectile = projectileNumber;
	}

	public void shootProjectile(char direction)
	{
		if (System.currentTimeMillis() - lastFireTime > fireRate)
		{
			Projectile p = new Projectile(getProjectile(), direction);

			// When facing back, put the projectile near the top of the head
			if (getImage() == mangatBack || getImage() == mangatHurtBack)
			{
				p.setY(getY());
			}
			else
			{
				// For any other direction, have the projectile start in the
				// centre of the player's head
				p.setY(getY() + 25);
			}
			p.setX(getX() + 20);

			currentProjectiles.add(p);
			lastFireTime = System.currentTimeMillis();
		}
	}

	/**
	 * Returns the projectile type
	 *
	 * @return the projectile number
	 */
	public int getProjectile()
	{
		return projectile;
	}

	/**
	 * @return the current item in possession
	 */
	public Item getCurrentItem()
	{
		return currentItem;
	}

	public String getCurrentItemName()
	{
		return currentItem.getName();
	}

	public void setItem(Item i)
	{
		currentItem = i;
	}

	public void useItem()
	{
		String itemName = currentItem.getName();
		this.setItem(null);

		// FINISH ITEMS
	}

	public int getNumKeys()
	{
		return numKeys;
	}

	public void setNumKeys(int numKeys)
	{
		this.numKeys = numKeys;
	}

	public boolean hasKeys()
	{
		return numKeys > 0;
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

	public void update(ArrayList<GameObject> rocks)
	{
		if (movingNorth && !collidesWithRocks(rocks))
		{
			moveNorth();
			if (collidesWithRocks(rocks))
			{
				moveSouth();
			}
		}
		if (movingSouth)
		{
			moveSouth();
			if (collidesWithRocks(rocks))
			{
				moveNorth();
			}
		}
		if (movingWest)
		{
			moveWest();
			if (collidesWithRocks(rocks))
			{
				moveEast();
			}
		}
		if (movingEast)
		{
			moveEast();
			if (collidesWithRocks(rocks))
			{
				moveWest();
			}
		}

		// Update projectiles
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

		if (isShooting)
		{
			if (movingNorth && movingEast)
				shootProjectile('1');
			else if (movingSouth && movingEast)
				shootProjectile('2');
			else if (movingSouth && movingWest)
				shootProjectile('3');
			else if (movingNorth && movingWest)
				shootProjectile('4');
			else if (getImage() == mangatFront || getImage() == mangatHurtFront)
				shootProjectile('S');
			else if (getImage() == mangatBack || getImage() == mangatHurtBack)
				shootProjectile('N');
			else if (getImage() == mangatLeft || getImage() == mangatHurtLeft)
				shootProjectile('W');
			else if (getImage() == mangatRight || getImage() == mangatHurtRight)
				shootProjectile('E');
			else
				System.err.println("no projectile added");
		}

		// Update invincibility if havent already
		if (takenDMG)
		{
			if (System.currentTimeMillis() - lastDmgTime > invincibleTime)
			{
				takenDMG = false;
				if (getImage() == mangatHurtFront)
				{
					setImage(mangatFront);
				}
				else if (getImage() == mangatHurtBack)
				{
					setImage(mangatBack);
				}
				else if (getImage() == mangatHurtLeft)
				{
					setImage(mangatLeft);
				}
				else if (getImage() == mangatHurtRight)
				{
					setImage(mangatRight);
				}
			}
		}
	}

	/**
	 * Returns if the player collides with a rock
	 * @param rocks the potential rocks to collide with
	 * @return if the player collides with a rock
	 */
	private boolean collidesWithRocks(ArrayList<GameObject> rocks)
	{
		for (GameObject rock : rocks)
		{
			if (getShadowHitbox().intersects(rock.getRockHitBox()))
			{
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g)
	{
		// Draw player
		g.drawImage(getImage(), (int) getX(), (int) getY(), null);

		// Movement hitbox
		g.setColor(Color.GREEN);
		g.drawRect((int) getShadowHitbox().getX(), (int) getShadowHitbox().getY(), (int)getShadowHitbox().getWidth(), (int) getShadowHitbox().getHeight());

		// Projectile collision hitbox
		g.setColor(Color.red);
		Rectangle r = getHitBox();
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(),
				(int) r.getHeight());

		// Draw HP level in the HUD
		for (int n = 0; n < this.getMaxHP(); n++)
		{
			if (n < this.getCurrentHP())
				g.drawImage(fullHeart, 820 + 30 * n, 70 + (n / 5) * 30, null);
			else
				g.drawImage(emptyHeart, 820 + 30 * n, 70, null);
		}

		// Draw all projectiles currently on the screen
		for (Projectile p : currentProjectiles)
		{
			p.draw(g);
		}
	}

	public void keyPressed(int key)
	{
		if (key == KeyEvent.VK_W)
		{
			if (!movingNorth)
			{
				if (!takenDMG)
					setImage(mangatBack);
				else
					setImage(mangatHurtBack);
			}
			movingNorth = true;
		}
		if (key == KeyEvent.VK_A)
		{
			if (!movingWest)
			{
				if (!takenDMG)
					setImage(mangatLeft);
				else
					setImage(mangatHurtLeft);
			}
			movingWest = true;
		}
		if (key == KeyEvent.VK_S)
		{
			if (!movingSouth)
			{
				if (!takenDMG)
					setImage(mangatFront);
				else
					setImage(mangatHurtFront);
			}
			movingSouth = true;
		}
		if (key == KeyEvent.VK_D)
		{
			if (!movingEast)
			{
				if (!takenDMG)
					setImage(mangatRight);
				else
					setImage(mangatHurtRight);
			}
			movingEast = true;
		}

		if (key == KeyEvent.VK_SPACE)
		{
			isShooting = true;
		}
	}

	public void keyReleased(int key)
	{
		if (key == KeyEvent.VK_W)
		{
			movingNorth = false;
			setPositionFacing();
		}
		if (key == KeyEvent.VK_A)
		{
			movingWest = false;
			setPositionFacing();
		}
		if (key == KeyEvent.VK_S)
		{
			movingSouth = false;
			setPositionFacing();
		}
		if (key == KeyEvent.VK_D)
		{
			movingEast = false;
			setPositionFacing();
		}
		if (key == KeyEvent.VK_SPACE)
			isShooting = false;
	}

	/**
	 * Sets the direction that mangat is facing based on the direction he is
	 * moving in
	 */
	private void setPositionFacing()
	{
		if (movingNorth)
		{
			if (!takenDMG)
				setImage(mangatBack);
			else
				setImage(mangatHurtBack);
		}
		else if (movingSouth)
		{
			if (!takenDMG)
				setImage(mangatFront);
			else
				setImage(mangatHurtFront);
		}
		else if (movingEast)
		{
			if (!takenDMG)
				setImage(mangatRight);
			else
				setImage(mangatHurtRight);
		}
		else if (movingWest)
		{
			if (!takenDMG)
				setImage(mangatLeft);
			else
				setImage(mangatHurtLeft);
		}
	}

	public void clearProjectiles()
	{
		currentProjectiles.clear();
	}
}
