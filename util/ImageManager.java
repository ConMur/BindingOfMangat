package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Connor Murphy
 */
public final class ImageManager {
    private static LinkedHashMap<String, BufferedImage> images;
    private static LinkedHashMap<String, BufferedImage> originalImages;

    private ImageManager() {
    }

    public static void init() {
        images = new LinkedHashMap<>();
        originalImages = new LinkedHashMap<>();
    }

    public static void resizeImages(int width, int height) {
        Iterator<Map.Entry<String, BufferedImage>> it = originalImages.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, BufferedImage> data = it.next();
            Util.resizeImage(data.getKey(), data.getValue(), width, height);
        }
    }

    public static void addImage(String key, BufferedImage i) {
        images.put(key, i);
        originalImages.put(key,i);
    }

    public static void addNewRescaledImage(String key, BufferedImage i)
    {
        images.put(key, i);
    }

    public static void removeImage(String key) {
        images.remove(key);
        originalImages.remove(key);
    }

    public static void removeOldRescaledImage(String key)
    {
        images.remove(key);
    }

    public static BufferedImage getImage(String key) {
        return images.get(key);
    }


}
