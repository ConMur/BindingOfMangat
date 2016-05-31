package main;

import gui.GameFrame;

public class Main
{
	private static GameFrame frame;
	public static void main(String[] args)
	{
		// Ensure not using command line args
		if (args.length > 0)
		{
			System.err.println("USAGE: no command line arguments");
			System.exit(0);
		}
		
		//Start the program
		frame = new GameFrame();
	}
	
	public static int getHeight()
	{
		return frame.getHeight();
	}
	
	public static int getWidth()
	{
		return frame.getWidth();
	}
}
