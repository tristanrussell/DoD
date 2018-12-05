import java.io.*;

/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {
	
	private Map map;

	private PlayerGold gold;

	private boolean playerTurn = false;
	
	/**
	 * Default constructor
	 */
	public GameLogic() {
		map = new Map();
		gold = new PlayerGold();
	}

    /**
	 * Checks if the game is running
	 *
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        return false;
    }

    /**
     * Checks if it is the players turn
     *
     * @return if it is the players turn.
     */
    protected boolean acceptInput() {
        return playerTurn;
    }

    /**
     * Checks for a map file at the specified location and changes the map.
     *
     * @param location : The location of the new map file
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
        return gold.getGold();
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return : Protocol if success or not.
     */
    protected String move(char direction) {
        return null;
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() {
        return null;
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        return null;
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {

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

        System.out.println(logic.map.getMapName());

        for (int i = 0; i < logic.map.getMap().length; i++) {
            /*
            for (int j = 0; j < logic.map.getMap()[i].length; j++) {
                System.out.println(logic.map.getMap()[i][j]);

            }
            */
            System.out.println(logic.map.getMap()[i]);

        }
    }
}