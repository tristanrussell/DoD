/**
 * Stores the gold for the player and has methods to access and
 * modify the amount.
 *
 */
class PlayerInfo {

    /* Current gold that the player has */
    private int gold;

    /* Coordinates of the player as {y, x} */
    private int[] playerPosition;

    /**
     * Default constructor.
     */
    PlayerInfo() {
        gold = 0;
    }

    /**
     * @return coordinates of the player.
     */
    int[] getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Sets the position of the player.
     *
     * @param startPosition : The position to place the player.
     */
    void loadStartPosition(int[] startPosition) {
        playerPosition = startPosition;
    }

    /**
     * Moves the player.
     *
     * @param axis : The axis on which the player is moving.
     * @param direction : The direction that the player is moving on the axis.
     * @return : If the player successfully moved or not.
     */
    String movePlayer(char axis, int direction) {
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
     * @return : The player's gold.
     */
    int getGold() {
        return gold;
    }

    /**
     * Increment the player's gold by 1.
     */
    void incrementGold() {
        gold++;
    }

}
