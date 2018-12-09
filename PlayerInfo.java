/**
 * Stores the gold for the player and has methods to access and
 * modify the amount.
 *
 */
public class PlayerInfo {

    /* Current gold that the player has */
    private int gold;

    /* Coordinates of the player as {y, x} */
    private int[] playerPosition;

    /**
     * @return coordinates of the player.
     */
    protected int[] getPlayerPosition () {
        return playerPosition;
    }

    protected void loadStartPosition (int[] startPosition) {
        playerPosition = startPosition;
    }

    protected String movePlayer (char axis, int direction) {
        switch (axis) {
            case 'Y':
                playerPosition[0] += direction;
                return "SUCCESS";

            case 'X':
                playerPosition[1] += direction;
                return "SUCCESS";

            default:
                return null;
        }
    }

    /**
     * Default constructor.
     */
    public PlayerInfo() {
        gold = 0;
    }

    /**
     * @return : The player's gold.
     */
    public int getGold () {
        return gold;
    }

    /**
     * Increment the player's gold by 1.
     *
     * @return : The player's gold after incrementing.
     */
    public void incrementGold () {
        gold++;
    }

}
