/**
 * Stores the gold for the player and has methods to access and
 * modify the amount.
 *
 */
public class PlayerGold {

    /* Current gold that the human player has */
    private int gold;

    /**
     *
     */
    public PlayerGold () {
        gold = 0;
    }

    /**
     *
     *
     * @return
     */
    public int getGold () {
        return gold;
    }


    public int incrementGold () {
        gold++;
        return gold;
    }
}
