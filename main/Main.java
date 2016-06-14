package main;

import gui.GameFrame;

import java.io.IOException;

/**
 * This is the entry point for the program. If the program detects that it is running on a unix system,
 * it attempts to relaunch using OpenGL for drawing efficiency.
 *
 * @author Connor Murphy
 */
public class Main {
    private static GameFrame frame;

    public static void main(String[] args) {
        //Set command line args if not already using

        //TODO: uncomment when have a jar file
        /*
        if (args.length == 0) {
            try {
                //Find if running a unix system
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                    // Relaunch the application using opengl to render for performance reasons
                    Runtime.getRuntime().exec(new String[]{"java", "-Dsun.java2d.opengl=True", "-jar", "BindingOfMangat.jar"});
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {*/
        //Start the program
        frame = new GameFrame();
        //}
    }

    /**
     * Returns the width of the frame
     *
     * @return the width of the frame
     */
    public static int getHeight() {
        return frame.getHeight();
    }

    /**
     * Returns the height of the frame
     *
     * @return the height of the frame
     */
    public static int getWidth() {
        return frame.getWidth();
    }
}
