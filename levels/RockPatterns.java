package levels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import thingsthatmove.GameObject;

public class RockPatterns {
    private final int ROCK_X = 55;
    private final int ROCK_Y = 60;

    //The number of different patterns that there are (including 0)
    private static final int NUM_PATTERNS = 8;

    private BufferedImage rock;
    private ArrayList<GameObject> rocksInPattern;
    private Dimension rockDimension;
    private ArrayList<Point> spawnLocations;

    public RockPatterns(int patternNum) {
        try {
            rock = ImageIO.read(getClass().getResourceAsStream(
                    "/images/rock.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        rockDimension = new Dimension(51, 58);
        rocksInPattern = new ArrayList<>();
        spawnLocations = new ArrayList<>();

        setNewPattern(patternNum);
    }

    public ArrayList<GameObject> getRocks() {
        return rocksInPattern;
    }

    public void setNewPattern(int newPattern) {
        rocksInPattern.clear();

        //Empty pattern
        if (newPattern == 0) {
            //Spawn positions
            spawnLocations.add(new Point(130, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(850,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(490,450));
            spawnLocations.add(new Point(400,450));
            spawnLocations.add(new Point(580,450));
            spawnLocations.add(new Point(400,388));
            spawnLocations.add(new Point(400,513));
        }
        // Middle square pattern (KEY ROOM PATTERN)
        else if (newPattern == 1) {
            // Bottom right rocks
            rocksInPattern.add(new GameObject(545, 580, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(600, 580, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(600, 520, rock, rockDimension, null, 0, 0));

            // Bottom left rocks
            rocksInPattern.add(new GameObject(340, 580, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(400, 580, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(340, 520, rock, rockDimension, null, 0, 0));

            // Top right rocks
            rocksInPattern.add(new GameObject(545, 340, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(600, 400, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(600, 340, rock, rockDimension, null, 0, 0));

            // Top left rocks
            rocksInPattern.add(new GameObject(340, 340, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(400, 340, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(340, 400, rock, rockDimension, null, 0, 0));

            //Spawn locations
            spawnLocations.add(new Point(130, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(850,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(490,450));
            spawnLocations.add(new Point(400,388));
            spawnLocations.add(new Point(440,513));
        }
        // Row pattern
        else if (newPattern == 2) {
            // 3 rows and 13 rocks per row
            for (int row = 0; row < 3; row++)
                for (int col = 0; col < 13; col++)
                    rocksInPattern.add(new GameObject(160 + ROCK_X * col, 340
                            + 120 * row, rock, rockDimension, null, 0, 0));

            //Spawn locations
            spawnLocations.add(new Point(130, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(490,325));
            spawnLocations.add(new Point(490,575));
            spawnLocations.add(new Point(850,325));
            spawnLocations.add(new Point(850,575));
        }
        // Column pattern
        else if (newPattern == 3) {
            // 6 columns, 5 rocks per column
            for (int row = 0; row < 5; row++)
                for (int col = 0; col < 6; col++)
                    rocksInPattern.add(new GameObject(165 + 130 * col, 340
                            + ROCK_Y * row, rock, rockDimension, null, 0, 0));

            //Spawn locations
            spawnLocations.add(new Point(200, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(730,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(470,450));
            spawnLocations.add(new Point(340,450));
            spawnLocations.add(new Point(600,450));
        }
        // Dotted pattern
        else if (newPattern == 4) {
            // 3 rows, 6 columns
            for (int row = 0; row < 3; row++)
                for (int col = 0; col < 6; col++)
                    rocksInPattern.add(new GameObject(165 + 130 * col, 340
                            + 120 * row, rock, rockDimension, null, 0, 0));

            //Spawn locations
            spawnLocations.add(new Point(130, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(850,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(470,450));
            spawnLocations.add(new Point(340,450));
            spawnLocations.add(new Point(600,450));
        }
        // Open box pattern
        else if (newPattern == 5) {
            // Draw rows
            for (int row = 0; row < 2; row++)
                for (int col = 1; col < 12; col++)
                    // Leave gap to move into box
                    if (col < 5 || col > 7)
                        rocksInPattern.add(new GameObject(160 + ROCK_X * col, 320
                                + 270 * row, rock, rockDimension, null, 0, 0));

            // Columns
            for (int col = 0; col < 2; col++)
                for (int row = 0; row < 4; row++)
                    rocksInPattern.add(new GameObject(160 + 660 * col, 367
                            + ROCK_Y * row, rock, rockDimension, null, 0, 0));

            //Spawn locations
            spawnLocations.add(new Point(200, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(730,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(400,450));
            spawnLocations.add(new Point(600,450));
        }
        // Vertical zigzag pattern
        else if (newPattern == 6) {
            // 6 columns, 5 rocks per column
            for (int row = 0; row < 3; row++) {
                for (int topCol = 0; topCol < 3; topCol++)
                    rocksInPattern.add(new GameObject(175 + 240 * topCol, 300
                            + ROCK_Y * row, rock, rockDimension, null, 0, 0));
                for (int botCol = 0; botCol < 3; botCol++)
                    rocksInPattern.add(new GameObject(280 + 240 * botCol, 620
                            - ROCK_Y * row, rock, rockDimension, null, 0, 0));
            }

            //Spawn locations
            spawnLocations.add(new Point(230, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(850,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(400,450));
            spawnLocations.add(new Point(600,450));
        }
        // Smile pattern
        else if (newPattern == 7) {
            // Eyes
            rocksInPattern.add(new GameObject(415, 400, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(525, 400, rock, rockDimension, null, 0, 0));

            // Smile
            rocksInPattern.add(new GameObject(360, 500, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(415, 520, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(470, 540, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(525, 520, rock, rockDimension, null, 0, 0));
            rocksInPattern.add(new GameObject(580, 500, rock, rockDimension, null, 0, 0));

            //Spawn locations
            spawnLocations.add(new Point(230, 325));
            spawnLocations.add(new Point(130,575));
            spawnLocations.add(new Point(850,325));
            spawnLocations.add(new Point(850,575));
            spawnLocations.add(new Point(270,450));
            spawnLocations.add(new Point(650,450));
        }
        // Inner room pattern
        else if (newPattern == 8) {

        } else {
            System.err.println("Invalid rock pattern: " + newPattern);
        }
    }

    /**
     * Returns the spawn locations for enemies
     * @return
     */
    public ArrayList<Point> getSpawnLocations()
    {
        return spawnLocations;
    }

    /**
     * Returns the number of different rock patterns that there are (including 0)
     * @return the number of different rock patterns that there are (including 0)
     */
    public static int getNumRockPatterns() {
        return NUM_PATTERNS;
    }

    public void draw(Graphics g) {
        // Draw all rocks
        for (int n = 0; n < rocksInPattern.size(); n++) {
            GameObject currentRock = rocksInPattern.get(n);
            g.drawImage(rock, (int) currentRock.getX(),
                    (int) currentRock.getY(), null);
        }
    }
}
