package util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class that provides utility methods
 * @author Connor Murphy
 */
public final class Util {
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
}
