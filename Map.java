import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

    /* Representation of the map */
    private char[][] map;

    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

    /* Number of bots allowed in the game */
    private int maxBots = 3;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
    public Map() {
        mapName = "Very small Labyrinth of Doom";
        goldRequired = 2;
        map = new char[][]{
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };
    }

    /**
     * Constructor that accepts a map to read in from.
     *
     * @param fileName : The filename of the map file.
     * @throws FileNotFoundException : If the file is not found.
     */
    public Map(String fileName) throws FileNotFoundException, IllegalMapException {
        readMap(fileName);
        checkMap();

    }

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }


    /**
     * @return : The name of the current map.
     */
    protected String getMapName() {
        return mapName;
    }


    /**
     * Reads the map from file.
     *
     * @param fileName : The filename of the map file.
     * @throws FileNotFoundException : If the file is not found.
     */
    protected void readMap(String fileName) throws FileNotFoundException {
        FileReader newMap = new FileReader(fileName);
        String newLine = null;
        ArrayList<char[]> mapArrayList = new ArrayList<>();

        try {
            BufferedReader line = new BufferedReader(newMap);

            while ((newLine = line.readLine()) != null) {
                String[] lineSplit = newLine.split(" ");
                if (lineSplit.length != 0) {
                    if (lineSplit[0].equals("name")) {
                        mapName = newLine.substring(5);

                    } else if (lineSplit[0].equals("win")) {
                        goldRequired = Integer.parseInt(newLine.substring(4));

                    } else {
                        mapArrayList.add(newLine.toCharArray());

                    }

                }

            }

        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }

        map = mapArrayList.toArray(new char[mapArrayList.size()][]);

    }

    /**
     * Checks the map is a valid map.
     *
     * @throws IllegalMapException : If there is an error in the map.
     */
    protected void checkMap() throws IllegalMapException {
        int maxLineLength = 0;
        int spaceCount = 0;
        int goldCount = 0;
        int exitCount = 0;

        if (map.length < 5) {
            throw new IllegalMapException();

        }

        for (char[] line : map) {
            if (line.length > maxLineLength) {
                maxLineLength = line.length;

            }

            for (char c : line) {
                switch (c) {
                    case '.':
                        spaceCount++;
                        break;

                    case 'G':
                        spaceCount++;
                        goldCount++;
                        break;

                    case 'E':
                        spaceCount++;
                        exitCount++;
                        break;

                    case '#':
                        break;

                    default:
                        throw new IllegalMapException();

                }
            }
        }

        if ((exitCount < 1) || (goldCount < goldRequired) || (maxLineLength < 5) || (goldCount > spaceCount - 12)) {
            throw new IllegalMapException();

        }

        if (spaceCount < 50) {
            maxBots = 1;

        } else if (spaceCount < 100) {
            maxBots = 2;

        }


    }

    /**
     * @return : The maximum number of bots the max can contain.
     */
    protected int getMaxBots() {
        return maxBots;
    }

    /**
     * @return : A random available location on the map.
     */
    protected int[] getRandomPosition() {
        Random r = new Random();

        while (true) {
            int randIndex1 = r.nextInt(map.length);
            int randIndex2 = r.nextInt(map[randIndex1].length);
            int[] test = new int[2];

            if ((map[randIndex1][randIndex2] == '.') || (map[randIndex1][randIndex2] == 'E')) {
                test[0] = randIndex1;
                test[1] = randIndex2;
                return test;

            }
        }
    }

    /**
     * Checks if there is a wall at the specified location.
     *
     * @param posY : Y coordinate of specified location.
     * @param posX : X coordinate of specified location.
     * @return : If there is a wall at the location.
     */
    protected boolean notWall(int posY, int posX) {
        boolean yInRange = (posY >= 0 && posY < map.length);
        boolean xInRange = false;
        if (yInRange) {
            xInRange = (posX >= 0 && posX < map[posY].length);
        }
        return (yInRange && xInRange && map[posY][posX] != '#');
    }

    /**
     * @param CentreY : The y coordinate of the centre point.
     * @param CentreX : The x coordinate of the centre point.
     * @return : 5 x 5 map centred around the specified point.
     */
    protected char[][] getLocalMap(int CentreY, int CentreX) {
        int[] start = {CentreY - 2, CentreX - 2};
        int[] end = {CentreY + 2, CentreX + 2};
        ArrayList<char[]> localArrayList = new ArrayList<>(5);
        for (int y = start[0]; y <= end[0]; y++) {
            char[] row = new char[5];
            if (y < 0 || y > map.length - 1) {
                for (int x = 0; x < 5; x++) {
                    row[x] = '#';

                }
            } else {
                for (int x = 0; x + start[1] <= end[1]; x++) {
                    if (x + start[1] < 0 || x + start[1] > map[y].length - 1) {
                        row[x] = '#';

                    } else {
                        row[x] = map[y][x + start[1]];

                    }
                }
            }
            localArrayList.add(row);

        }
        return localArrayList.toArray(new char[5][5]);

    }

    protected String removeGold(int playerY, int playerX) {
        if (map[playerY][playerX] == 'G') {
            map[playerY][playerX] = '.';
            return "SUCCESS";

        } else {
            return "FAIL";

        }
    }

    protected boolean onExit(int playerY, int playerX) {
        return map[playerY][playerX] == 'E';
    }

}
