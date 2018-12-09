public class BotPlayer {

    private int turnInRotation = 1;

    /* Coordinates of the bot as {y, x} */
    private int[] botPosition;

    public BotPlayer() {

    }

    protected void loadStartPosition(int[] startPosition) {
        botPosition = startPosition;
    }

    /**
     * @return coordinates of the bot.
     */
    protected int[] getBotPosition () {
        return botPosition;
    }

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
