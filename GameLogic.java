import java.io.*;
import java.util.ArrayList;
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
    protected void changeMap(String location) throws FileNotFoundException, IllegalMapException {
        map = new Map(location);
    }

    /**
     * Creates a new bot and adds it to the bot list.
     */
    protected void addBot(int number) {
        for (int i = 0; i < number; i++) {
            BotPlayer newBot = new BotPlayer();
            botList.add(newBot);

        }
    }

    protected void loadStartPositions() {
        info.loadStartPosition(map.getRandomPosition());

        int[] playerStartPosition = info.getPlayerPosition();
        int[] botStartPosition;
        int testDistance;
        boolean taken;

        for (int i = 0; i < botList.size(); i++) {
            taken = false;
            do {
                botStartPosition = map.getRandomPosition();
                int distY = Math.abs(playerStartPosition[0] - botStartPosition[0]);
                int distX = Math.abs(playerStartPosition[1] - botStartPosition[1]);
                testDistance = Math.max(distY, distX);
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

    protected void moveBots() {
        for (BotPlayer bot : botList) {
            bot.takeTurn(map, info);

        }
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
                return "FAIL";

        }
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() {
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

    /**
     * Checks if the player has been caught.
     */
    private void checkCaught() {
        for (BotPlayer bot : botList) {
            boolean sameY = info.getPlayerPosition()[0] == bot.getBotPosition()[0];
            boolean sameX = info.getPlayerPosition()[1] == bot.getBotPosition()[1];
            if (sameY && sameX) {
                System.out.println("You have been caught\nLOSE");
                System.exit(0);

            }
        }
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
        } catch(IllegalMapException e) {
            System.out.println("Error in map, loading default map...");

        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error in file name, loading default map...");

        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }

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
                    System.out.println("Too many bots, loading " + maxBots + " bots...");
                    logic.addBot(maxBots);
                    logic.loadStartPositions();

                } else {
                    System.out.println("Loading " + Math.max(numOfBots, 0) + " bots...");
                    logic.addBot(numOfBots);
                    logic.loadStartPositions();

                }

            } catch(NumberFormatException e) {
                System.out.println("Invalid input, loading 1 bot...");
                logic.addBot(1);
                logic.loadStartPositions();

            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);

            }
        }

        System.out.println("Map Name: " + logic.map.getMapName());

        // DEBUGGING
        /*
        char[][] newMap = logic.map.getMap();
        int[] playerStartPosition = logic.info.getPlayerPosition();

        for (int y = 0; y < newMap.length; y++) {
            if (y == playerStartPosition[0] || y == botStartPosition[0]) {
                for (int x = 0; x < newMap[y].length; x++) {
                    if (x == playerStartPosition[1] && y == playerStartPosition[0]) {
                        System.out.print('P');

                    } else if (x == botStartPosition[1] && y == botStartPosition[0]) {
                        System.out.print('B');

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

        */
        System.out.println(Arrays.toString(logic.info.getPlayerPosition()));
        //END OF DEBUGGING

        HumanPlayer player = new HumanPlayer();
        logic.setGameRunning(true);
        while (logic.gameRunning()) {
            /* Human Player turn */
            String command = player.getInputFromConsole();
            String toPrint = player.getNextAction(logic, command);
            System.out.println(toPrint);

            logic.checkCaught();

            /* Bot Player turn */
            logic.moveBots();
            //logic.bot.takeTurn(logic.map, logic.info);

            logic.checkCaught();
        }
    }

}