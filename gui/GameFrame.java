package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * The entry point for the program. Creates a frame and displays it
 *
 * @author Connor Murphy
 */
public class GameFrame extends JFrame {
    /**
     * A generated serialVersionUID so eclipse does not yell at me
     */
    private static final long serialVersionUID = -891368853245353471L;

    /**
     * Creates the frame that displays the panel which displays the game
     */

    public GameFrame() {
        super();

        setTitle("Binding of Mangat");

        // Initialize frame and panel
        final GamePanel panel = new GamePanel();
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Add a listener to close resources before closing the window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel.stop();
                dispose();
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        panel.go();
    }
}
