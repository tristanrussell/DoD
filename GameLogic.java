import java.io.*;
import java.util.Arrays;

/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {

    /* New Map object */
	private Map map;

	/* New PlayerGold object */
	private PlayerInfo info;

	/* Status of the game */
	private boolean running = false;

	/* Status of the human player's turn */
	private boolean playerTurn = false;
	
	/**
	 * Default constructor.
	 */
	public GameLogic() {
		map = new Map();
		info = new PlayerInfo();
	}

    /**
	 * Checks if the game is running.
	 *
     * @return whether the game is running.
     */
    protected boolean gameRunning() {
        return running;
    }

    /**
     * Sets the game status.
     */
    protected void setGameRunning(boolean status) {
        running = status;
    }

    /**
     * Checks if it is the players turn.
     *
     * @return whether it is the players turn.
     */
    protected boolean acceptInput() {
        return playerTurn;
    }

    /**
     * Checks for a map file at the specified location and changes the map.
     *
     * @param location : The location of the new map file.
     */
    protected void changeMap(String location) throws FileNotFoundException {
        map = new Map(location);
    }

    /**
	 * Returns the gold required to win.
	 *
     * @return : Gold required to win.
     */
    protected int hello() {
        return map.getGoldRequired();
    }
	
	/**
	 * Returns the gold currently owned by the player.
	 *
     * @return : Gold currently owned.
     */
    protected int gold() {
        return info.getGold();
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return : If the player successfully moved or not.
     */
    protected String move(char direction) {
        int[] player = info.getPlayerPosition();
        switch (direction) {
            case 'N':
                if (!map.isWall((player[0] - 1), player[1])) {
                    return info.movePlayer('Y', -1);
                }

            case 'E':
                if (!map.isWall(player[0], (player[1] + 1))) {
                    return info.movePlayer('X', 1);
                }

            case 'S':
                if (!map.isWall((player[0] + 1), player[1])) {
                    return info.movePlayer('Y', 1);
                }

            case 'W':
                if (!map.isWall(player[0], (player[1] - 1))) {
                    return info.movePlayer('X', -1);
                }

            default:
                return "FAIL";

        }
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() {
        int[] player = info.getPlayerPosition();
        char[][] localMap = map.getLocalMap(player[0], player[1]);
        localMap[2][2] = 'P';
        String mapToString = "";
        for (char[] chars : localMap) {
            mapToString = mapToString.concat(new String(chars) + "\n");

        }
        return mapToString;

    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        int[] player = info.getPlayerPosition();
        String result = map.removeGold(player[0], player[1]);
        if (result.equals("SUCCESS")) {
            info.incrementGold();

        }
        return result;

    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {
        int[] player = info.getPlayerPosition();
        if (map.onExit(player[0], player[1]) && (info.getGold() == map.getGoldRequired())) {
            System.out.println("WIN");

        } else {
            System.out.println("LOSE");

        }
        System.exit(0);

    }
	
	public static void main(String[] args) {
		GameLogic logic = new GameLogic();

        System.out.println("Enter a file location for your map or leave blank for the default map: ");

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            String location = input.readLine();

            if (location == null) {
                System.exit(0);

            }

            // remove error in the case that the user puts a space after the address
            location = location.split(" ")[0];

            if (location.length() > 0) {
                System.out.println("Loading map from file location...");
                logic.changeMap(location);

            } else {
                System.out.println("Loading default map...");

            }
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error in file name, loading default map...");

        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }

        logic.info.loadStartPosition(logic.map.getRandomPosition());

        // DEBUGGING
        System.out.println(logic.map.getMapName());

        char[][] newMap = logic.map.getMap();
        int[] playerPosition = logic.info.getPlayerPosition();

        for (int y = 0; y < newMap.length; y++) {
            if (y == playerPosition[0]) {
                for (int x = 0; x < newMap[y].length; x++) {
                    if (x == playerPosition[1]) {
                        System.out.print('P');
                    } else {
                        System.out.print(newMap[y][x]);

                    }
                }
            } else {
                for (int x = 0; x < newMap[y].length; x++) {
                    System.out.print(newMap[y][x]);

                }
            }
            System.out.println();

        }

        System.out.println(Arrays.toString(logic.info.getPlayerPosition()));
        //END OF DEBUGGING

        logic.setGameRunning(true);
        HumanPlayer player = new HumanPlayer();
        while (logic.gameRunning()) {
            String command = player.getInputFromConsole();
            String toPrint = player.getNextAction(logic, command);
            System.out.println(toPrint);
        }
    }

}