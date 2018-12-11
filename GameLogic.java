import java.io.*;
import java.util.ArrayList;

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

	/* List of bots in the game */
    private ArrayList<BotPlayer> botList;
	
	/**
	 * Default constructor.
	 */
	public GameLogic() {
		map = new Map();
		info = new PlayerInfo();
        botList = new ArrayList<>();
	}

    /**
	 * Checks if the game is running.
	 *
     * @return whether the game is running.
     */
    private boolean gameRunning() {
        return running;
    }

    /**
     * Sets the game status.
     */
    private void setGameRunning(boolean status) {
        running = status;

    }

    /**
     * Checks for a map file at the specified location and changes the map.
     *
     * @param location : The location of the new map file.
     * @throws FileNotFoundException : If the file is not found.
     * @throws IllegalMapException : If the map contains an error.
     */
    private void changeMap(String location) throws FileNotFoundException, IllegalMapException {
        map = new Map(location);
    }

    /**
     * Creates new bots and adds them to the bot list.
     *
     * @param number : The number of bots to create.
     */
    private void addBot(int number) {
        for (int i = 0; i < number; i++) {
            BotPlayer newBot = new BotPlayer();
            botList.add(newBot);

        }
    }

    /**
     * Loads the start positions of the player and all the bots.
     */
    private void loadStartPositions() {
        info.loadStartPosition(map.getRandomPosition());

        int[] playerStartPosition = info.getPlayerPosition();
        int[] botStartPosition;
        int testDistance;
        boolean taken;

        // For each is not used as the index of the bot is required inside the loop
        for (int i = 0; i < botList.size(); i++) {
            taken = false;

            // Assign each bot to a tile not within two tiles of the player
            do {
                botStartPosition = map.getRandomPosition();
                int distY = Math.abs(playerStartPosition[0] - botStartPosition[0]);
                int distX = Math.abs(playerStartPosition[1] - botStartPosition[1]);
                testDistance = Math.max(distY, distX);

                // Checks if any of the bots have already taken this tile
                for (int j = 0; j < i; j++) {
                    boolean testY = botStartPosition[0] == botList.get(j).getBotPosition()[0];
                    boolean testX = botStartPosition[1] == botList.get(j).getBotPosition()[1];
                    if (testY && testX) {
                        taken = true;

                    }
                }

            } while (testDistance < 2 && !taken);

            botList.get(i).loadStartPosition(botStartPosition);

        }
    }

    /**
     * Iterates through the bots and lets each one take its next turn.
     */
    private void moveBots() {
        for (BotPlayer bot : botList) {
            bot.takeTurn(map, info);

        }
    }

    /**
	 * Returns the gold required to win.
	 *
     * @return : Gold required to win.
     */
    int hello() {
        return map.getGoldRequired();
    }
	
	/**
	 * Returns the gold currently owned by the player.
	 *
     * @return : Gold currently owned.
     */
    int gold() {
        return info.getGold();
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return : If the player successfully moved or not.
     */
    String move(char direction) {
        int[] player = info.getPlayerPosition();
        switch (direction) {
            case 'N':
                if (map.notWall((player[0] - 1), player[1])) {
                    return info.movePlayer('Y', -1);

                }
                return "FAIL";

            case 'E':
                if (map.notWall(player[0], (player[1] + 1))) {
                    return info.movePlayer('X', 1);

                }
                return "FAIL";

            case 'S':
                if (map.notWall((player[0] + 1), player[1])) {
                    return info.movePlayer('Y', 1);

                }
                return "FAIL";

            case 'W':
                if (map.notWall(player[0], (player[1] - 1))) {
                    return info.movePlayer('X', -1);

                }
                return "FAIL";

            default:
                return "Invalid direction entered";

        }
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    String look() {
        int[] playerPos = info.getPlayerPosition();
        char[][] localMap = map.getLocalMap(playerPos[0], playerPos[1]);
        localMap[2][2] = 'P';

        for (BotPlayer bot : botList) {
            int[] botPos = bot.getBotPosition();
            int testY = Math.abs(playerPos[0] - botPos[0]);
            int testX = Math.abs(playerPos[1] - botPos[1]);

            if (testX < 3 && testY < 3) {
                localMap[2 + (botPos[0] - playerPos[0])][2 + (botPos[1] - playerPos[1])] = 'B';

            }
        }

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
    String pickup() {
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
    void quitGame() {
        setGameRunning(false);
        int[] player = info.getPlayerPosition();
        if (map.onExit(player[0], player[1]) && (info.getGold() >= map.getGoldRequired())) {
            System.out.println("You successfully looted the dungeon.");
            System.out.println("WIN");

        } else {
            System.out.println("You failed to loot the dungeon and escape.");
            System.out.println("LOSE");

        }
        System.exit(0);

    }

    /**
     * Checks if the player has been caught.
     */
    private void checkCaught() {
        for (BotPlayer bot : botList) {
            boolean sameY = info.getPlayerPosition()[0] == bot.getBotPosition()[0];
            boolean sameX = info.getPlayerPosition()[1] == bot.getBotPosition()[1];
            // If both X and Y coordinates are the same then the player has been caught
            if (sameY && sameX) {
                setGameRunning(false);
                System.out.println("You have been caught.");
                System.out.println("LOSE");
                System.exit(0);

            }
        }
    }
	
	public static void main(String[] args) {
        /* SETUP */
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
                System.out.println("\nLoading map from file location...");
                logic.changeMap(location);

            } else {
                System.out.println("Loading default map...");

            }
        } catch(IllegalMapException e) {
            System.out.println("Error in map, loading default map...");

        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error in file name, loading default map...");

        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }

        System.out.println("Map Name: " + logic.map.getMapName() + "\n");

        int maxBots = logic.map.getMaxBots();
        if (maxBots == 0) {
            logic.loadStartPositions();

        } else {
            System.out.print("Enter the number of bots to load (max " + maxBots + "): ");
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String numInput = input.readLine();

                int numOfBots = Integer.parseInt(numInput);
                if (numOfBots > maxBots) {
                    System.out.println("Too many bots, loading " + maxBots + " bots...\n");
                    logic.addBot(maxBots);
                    logic.loadStartPositions();

                } else {
                    System.out.println("Loading " + Math.max(numOfBots, 0) + " bots...\n");
                    // if numOfBots is less than zero then the method works just the same as zero
                    logic.addBot(numOfBots);
                    logic.loadStartPositions();

                }
            } catch(NumberFormatException e) {
                System.out.println("Invalid input, loading 1 bot...\n");
                logic.addBot(1);
                logic.loadStartPositions();

            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);

            }
        }

        HumanPlayer player = new HumanPlayer();
        logic.setGameRunning(true);
        System.out.println("The hunt begins.\n");
        /* GAME */
        while (logic.gameRunning()) {
            /* Human Player turn */
            // Asks for player input
            String command = player.getInputFromConsole();
            // Sends the command to be processed, retrieves a string to be printed
            String toPrint = player.getNextAction(logic, command);
            System.out.println(toPrint);

            logic.checkCaught();

            /* Bot Player turn */
            logic.moveBots();

            logic.checkCaught();
        }
    }

}