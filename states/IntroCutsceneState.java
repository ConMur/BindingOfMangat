package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Displays the opening cutscene for the game. The player can press any key or the mouse button to skip
 * @author Connor Murphy
 *
 */
public class IntroCutsceneState implements GameState
{
	private ArrayList<BufferedImage> cutscenes;
	private BufferedImage currentImage;
	private int currentImageIndex;
	
	private final int NUMBER_SCENES = 23;
	
	private long lastSceneTime;
	private final int CUTSCENE_TIME = 3000;

	@Override
	public void init()
	{
		cutscenes = new ArrayList<>();

		//Load the images
		try
		{
			for (int scene = 1; scene <= NUMBER_SCENES; scene++)
			{
				cutscenes.add(ImageIO.read(getClass().getResourceAsStream("/images/intro/" + scene + ".png")));
			}
		}
		catch (IOException ioe)
		{
			System.err.println("Error loading intro cutscenes");
			ioe.printStackTrace();
		}
		
		currentImageIndex = 0;
		currentImage = cutscenes.get(currentImageIndex);
		lastSceneTime = System.currentTimeMillis();
	}

	@Override
	public void update()
	{
		if(System.currentTimeMillis() - lastSceneTime > CUTSCENE_TIME)
		{
			currentImageIndex++;
			
			//If there are no more cutscenes, go to the next state
			if(currentImageIndex >= NUMBER_SCENES)
			{
				GameStateManager.setState(State.PLAY);
				return;
			}
			else
			{
				currentImage = cutscenes.get(currentImageIndex);
				lastSceneTime = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void draw(Graphics g)
	{
		g.drawImage(currentImage, 0, 0,null);
	}

	@Override
	public void cleanUp()
	{
		cutscenes.clear();
		cutscenes = null;
	}

	@Override
	public void keyPressed(int key)
	{
		GameStateManager.setState(State.PLAY);
	}

	@Override
	public void keyReleased(int key)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int x, int y)
	{
		GameStateManager.setState(State.PLAY);
	}

	@Override
	public void mouseMoved(int x, int y)
	{
		// TODO Auto-generated method stub

	}

}
