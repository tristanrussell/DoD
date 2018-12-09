/**
 * Stores the gold for the player and has methods to access and
 * modify the amount.
 *
 */
public class PlayerGold {

    /* Current gold that the player has */
    private int gold;

    /**
     * Default constructor.
     */
    public PlayerGold () {
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
