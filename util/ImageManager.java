package util;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stores references to images and resizes them as the screen size changes
 * @author Connor Murphy
 */
public final class ImageManager {
    private static LinkedHashMap<String, BufferedImage> images;
    private static LinkedHashMap<String, BufferedImage> originalImages;

    /**
     * Prevent construcion of this class
     */
    private ImageManager() {
    }

    /**
     * Initializes variables needed for the image manager
     */
    public static void init() {
        images = new LinkedHashMap<>();
        originalImages = new LinkedHashMap<>();
    }

    /**
     * Resizes each image that this ImageManager manager
     * @param width the new width of the screen
     * @param height the new height of the screen
     */
    public static void resizeImages(int width, int height) {
        Iterator<Map.Entry<String, BufferedImage>> it = originalImages.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, BufferedImage> data = it.next();
            Util.resizeImage(data.getKey(), data.getValue(), width, height);
        }
    }

    /**
     * Adds an image to be managed by this ImageManager
     * @param key the string name for this image
     * @param i the image to store
     */
    public static void addImage(String key, BufferedImage i) {
        images.put(key, i);
        originalImages.put(key,i);
    }

    /**
     * Adds a rescaled image to the image manager
     * @param key the name of the image
     * @param i the image to add
     */
    public static void addNewRescaledImage(String key, BufferedImage i)
    {
        images.put(key, i);
    }

    public static void removeImage(String key) {
        images.remove(key);
        originalImages.remove(key);
    }

    /**
     * Removes the image specified by its name
     * @param key the name of the image to remove
     */
    public static void removeOldRescaledImage(String key)
    {
        images.remove(key);
    }

    /**
     * Returns the image based on the given name
     * @param key the name of the image to get
     * @return the image based on the given name
     */
    public static BufferedImage getImage(String key) {
        return images.get(key);
    }


}
