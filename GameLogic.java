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
     *
     * @param command
     */
    protected void processCommand(String command) {
        switch (command) {
            case "HELLO":
                System.out.println("Gold to win: " + map.getGoldRequired());
                break;

            case "GOLD":
                System.out.println("Gold owned: " + gold.getGold());
                break;

            case "MOVE":
                break;

            case "PICKUP":
                break;

            case "LOOK":
                break;

            case "QUIT":
                break;

            default:
                break;

        }
    }

    /**
	 * Returns the gold required to win.
	 *
     * @return : Gold required to win.
     */
    protected String hello() {
        return null;
    }
	
	/**
	 * Returns the gold currently owned by the player.
	 *
     * @return : Gold currently owned.
     */
    protected String gold() {
        return null;
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

    }
}