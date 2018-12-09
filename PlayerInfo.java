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

    /**
     * Sets the position of the player.
     *
     * @param startPosition : The position to place the player.
     */
    protected void loadStartPosition (int[] startPosition) {
        playerPosition = startPosition;
    }

    /**
     * Moves the player.
     *
     * @param axis : The axis on which the player is moving.
     * @param direction : The direction that the player is moving on the axis.
     * @return : If the player successfully moved or not.
     */
    protected String movePlayer (char axis, int direction) {
        switch (axis) {
            case 'Y':
                playerPosition[0] += direction;
                return "SUCCESS";

            case 'X':
                playerPosition[1] += direction;
                return "SUCCESS";

            default:
                return "FAIL";
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
