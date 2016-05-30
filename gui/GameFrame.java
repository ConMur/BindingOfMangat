package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * The entry point for the program. Creates a frame and displays it
 * @author Connor Murphy
 *
 */
public class GameFrame extends JFrame
{
	/**
	 * A generated serialVersionUID so eclipse does not yell at me
	 */
	private static final long serialVersionUID = -891368853245353471L;

	/**
	 * The entry point to the program
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args)
	{
		// Ensure not using command line args
		if (args.length > 0)
		{
			System.err.println("USAGE: no command line arguments");
			System.exit(0);
		}
		new GameFrame();
	}

	public GameFrame()
	{
		super("Binding of Mangat");
		// Initiate frame and panel

		final GamePanel panel = new GamePanel();
		add(panel);
		pack();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Add a listener to close resources before closing the window
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				panel.stop();
				dispose();
			}
		});

		setVisible(true);
		panel.go();
	}

}
