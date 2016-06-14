package levels;

import item.Item;

import java.util.ArrayList;
import java.util.Random;

/**
 * A collection of game objects that that player can walk over and "purchace" using coins
 * @deprecated bc we will never use this class rip idea from a week ago
 * @author Connor Murphy
 *
 */
public class Shop
{
	private ArrayList<Item> items;
	private ArrayList<Integer> prices;
	
	private final int PRICE_VARIATION = 10;
	private final int MIN_PRICE = 1;
	
	private Random rand;
	
	public Shop(int noItems, int priceRange)
	{
		rand = new Random();
		ArrayList<Item> itemList = new ArrayList<>(LevelManager.getItems());
		for(int i = 0; i < noItems; ++i)
		{
			items.add(itemList.remove(rand.nextInt(itemList.size() - 1)));
		}
	}
	

}
