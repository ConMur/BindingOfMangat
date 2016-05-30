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
	public static void main(String[] args)
	{
		// Initiate panel here:
		final JFrame frame = new JFrame("Binding of Mangat");
		final GamePanel panel = new GamePanel();
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				panel.stop();
				frame.dispose();
			}
		});
		
		frame.setVisible(true);
		panel.go();

	}
}
