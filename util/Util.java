package util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class that provides utility methods
 * @author Connor Murphy
 */
public final class Util {
    //Fps calculation
    private static double oldFPS = 0;

    private Util(){}

    public static BufferedImage createCompatibleImage(Image image)
    {
        //Get current GraphicsConfiguration
        GraphicsConfiguration graphicsConfiguration
                = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        //Create a Compatible BufferedImage
        BufferedImage bufferedImage
                = graphicsConfiguration.createCompatibleImage(
                image.getWidth(null),
                image.getHeight(null));
        //Copy from original Image to new Compatible BufferedImage
        Graphics tempGraphics = bufferedImage.getGraphics();
        tempGraphics.drawImage(image, 0, 0, null);
        tempGraphics.dispose();

        return bufferedImage;
    }

    /**
     * Calculates the average fps using the delta time
     * @param dt the delta time (time elapsed between frames)
     * @return
     */
    public static double calcFPS(double dt)
    {
        //Change the fps by 10% of the new fps for a smoother display
        oldFPS = oldFPS * 0.9 + (1/dt) * 0.1;
        return oldFPS;
    }

}
