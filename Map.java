import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reads and contains in memory the map of the game.
 *
 */
class Map {

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
    Map() {
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
     * @throws IllegalMapException : If the map contains an error.
     */
    Map(String fileName) throws FileNotFoundException, IllegalMapException {
        readMap(fileName);
        checkMap();

    }

    /**
     * @return : Gold required to exit the current map.
     */
    int getGoldRequired() {
        return goldRequired;
    }

    /**
     * @return : The name of the current map.
     */
    String getMapName() {
        return mapName;
    }

    /**
     * Reads the map from a file.
     *
     * @param fileName : The location of the map file.
     * @throws FileNotFoundException : If the file is not found.
     */
    private void readMap(String fileName) throws FileNotFoundException {
        FileReader newMap = new FileReader(fileName);
        String newLine;
        ArrayList<char[]> mapArrayList = new ArrayList<>();

        try {
            BufferedReader line = new BufferedReader(newMap);

            // Iterate through each line and check its contents
            while ((newLine = line.readLine()) != null) {
                String[] lineSplit = newLine.split(" ");
                if (lineSplit.length != 0) {
                    if (lineSplit[0].toLowerCase().equals("name")) {
                        mapName = newLine.substring(5);

                    } else if (lineSplit[0].toLowerCase().equals("win")) {
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
     * Checks if the map is a valid map.
     *
     * @throws IllegalMapException : If there is an error in the map.
     */
    private void checkMap() throws IllegalMapException {
        int tileCount = 0;
        int goldCount = 0;
        int exitCount = 0;

        for (char[] line : map) {for (char c : line) {
                switch (c) {
                    case '.':
                        tileCount++;
                        break;

                    case 'G':
                        tileCount++;
                        goldCount++;
                        break;

                    case 'E':
                        tileCount++;
                        exitCount++;
                        break;

                    case '#':
                        break;

                    default:
                        throw new IllegalMapException();

                }
            }
        }

        // Throw exception if there isn't enough gold to beat the game
        if (exitCount < 1 || goldCount < goldRequired) {
            throw new IllegalMapException();

        }

        // Calculate the maximum number of bot depending on the map
        if (tileCount < 25 || tileCount - 9 <= goldCount) {
            maxBots = 0;

        } else if (tileCount < 50 || tileCount - 10 <= goldCount) {
            maxBots = 1;

        } else if (tileCount < 100 || tileCount - 11 <= goldCount) {
            maxBots = 2;

        }
    }

    /**
     * @return : The maximum number of bots the map can contain.
     */
    int getMaxBots() {
        return maxBots;

    }

    /**
     * @return : A random available location on the map.
     */
    int[] getRandomPosition() {
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
     * Checks if there is a wall or the edge of the map at the specified location.
     *
     * @param posY : Y coordinate of specified location.
     * @param posX : X coordinate of specified location.
     * @return : If there is not a wall at the location.
     */
    boolean notWall(int posY, int posX) {
        boolean yInRange = (posY >= 0 && posY < map.length);
        boolean xInRange = false;
        if (yInRange) {
            xInRange = (posX >= 0 && posX < map[posY].length);

        }
        return (yInRange && xInRange && map[posY][posX] != '#');

    }

    /**
     * Constructs a 5 x 5 grid around the specified location.
     *
     * @param centreY : The y coordinate of the centre point.
     * @param centreX : The x coordinate of the centre point.
     * @return : The constructed grid.
     */
    char[][] getLocalMap(int centreY, int centreX) {
        int[] start = {centreY - 2, centreX - 2};
        int[] end = {centreY + 2, centreX + 2};
        ArrayList<char[]> localArrayList = new ArrayList<>(5);

        for (int y = start[0]; y <= end[0]; y++) {
            char[] row = new char[5];
            // If index is out of bounds place a wall
            if (y < 0 || y > map.length - 1) {
                for (int x = 0; x < 5; x++) {
                    row[x] = '#';

                }
            } else {
                for (int x = 0; x + start[1] <= end[1]; x++) {
                    // If index is out of bounds place a wall
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

    /**
     * Removes gold from the map if the player is standing on the gold.
     *
     * @param playerY : Y coordinate of the player.
     * @param playerX : X coordinate of the player.
     * @return : If the player successfully picked up gold.
     */
    String removeGold(int playerY, int playerX) {
        if (map[playerY][playerX] == 'G') {
            map[playerY][playerX] = '.';
            return "SUCCESS";

        } else {
            return "FAIL";

        }
    }

    /**
     * Tests whether the player is stood on an exit tile.
     *
     * @param playerY : Y coordinate of the player.
     * @param playerX : X coordinate of the player.
     * @return : If the player is stood on an exit.
     */
    boolean onExit(int playerY, int playerX) {
        return map[playerY][playerX] == 'E';

    }

}
