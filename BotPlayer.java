public class BotPlayer {

    /* Action to perform in the bot's rotation of moves */
    private int turnInRotation = 1;

    /* Coordinates of the bot as {y, x} */
    private int[] botPosition;

    /**
     * Default constructor.
     */
    public BotPlayer() {

    }

    /**
     * Sets the position of the bot.
     *
     * @param startPosition : The position to place the bot.
     */
    protected void loadStartPosition(int[] startPosition) {
        botPosition = startPosition;
    }

    /**
     * @return coordinates of the bot.
     */
    protected int[] getBotPosition () {
        return botPosition;
    }

    /**
     * Performs the next move in the bot's rotation of moves.
     *
     * @param map : The current map object being used.
     */
    protected void takeTurn(Map map) {
        switch (turnInRotation++) {
            case 1:
                map.getLocalMap(botPosition[0], botPosition[1]);
                break;

            case 2:
                break;

            case 3:
                break;

            default:
                break;
        }
    }

}
