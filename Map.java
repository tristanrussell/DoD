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

    /* */
    private int playerPosition[];

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

        playerPosition = loadRandomPosition();
    }

    /**
     * Constructor that accepts a map to read in from.
     *
     * @param : The filename of the map file.
     */
    public Map(String fileName) throws FileNotFoundException {
        readMap(fileName);
        playerPosition = loadRandomPosition();

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
     * @param : Name of the map's file.
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
     * @return : A random available location on the map
     */
    protected int[] loadRandomPosition() {
        Random r = new Random();

        while (true) {
            int randIndex1 = r.nextInt(map.length);
            int randIndex2 = r.nextInt(map[randIndex1].length);
            int[] test = new int[2];

            if (map[randIndex1][randIndex2] == '.') {
                test[0] = randIndex1;
                test[1] = randIndex2;
                return test;
            }
        }
    }

    /**
     *
     * @return
     */
    protected int[] getPlayerPosition () {
        return playerPosition;
    }
}
