import java.util.Arrays;
import java.util.Random;

public class BotPlayer {

    /* Coordinates of the bot as {y, x} */
    private int[] botPosition;

    /* Local map around bot */
    private char[][] mapMemory;

    /* The position of the bot relative to the map in mapMemory */
    private int[] savedPosition = new int[2];

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
     * @return : The next move to make.
     */
    protected String nextMove() {
        if (savedPosition[0] == 0 || savedPosition[0] == 4 || savedPosition[1] == 0 || savedPosition[1] == 4) {
            return "LOOK";

        }
        return "MOVE";

    }

    /**
     * Performs the next move in the bot's rotation of moves.
     *
     * @param map : The current map object being used.
     */
    protected void takeTurn(Map map) {
        boolean result;
        switch (this.nextMove()) {
            case "LOOK":
                savedPosition[0] = 2;
                savedPosition[1] = 2;
                mapMemory = map.getLocalMap(botPosition[0], botPosition[1]);
                break;

            case "MOVE":
                do {
                    result = move(map, randDirection());
                } while(!result);
                break;

            default:
                break;
        }

        // DEBUGGING
        System.out.println(Arrays.toString(botPosition));
    }

    /**
     * @return : A random direction for the bot to move in.
     */
    protected char randDirection() {
        char[] directions = {'N', 'E', 'S', 'W'};
        Random r = new Random();
        int randDirection = r.nextInt(4);
        return directions[randDirection];

    }

    /**
     * Moves the bot.
     *
     * @param map : The map that the bot is moving on.
     * @param direction : The direction that the bot should move.
     */
    protected boolean move(Map map, char direction) {
        switch (direction) {
            case 'N':
                if (mapMemory[savedPosition[0] - 1][savedPosition[1]] == '#') {
                    return false;

                }
                if (!map.isWall((botPosition[0] - 1), botPosition[1])) {
                    botPosition[0]--;
                    savedPosition[0]--;

                }
                return true;

            case 'E':
                if (mapMemory[savedPosition[0]][savedPosition[1] + 1] == '#') {
                    return false;


                }
                if (!map.isWall(botPosition[0], (botPosition[1] + 1))) {
                    botPosition[1]++;
                    savedPosition[1]++;

                }
                return true;

            case 'S':
                if (mapMemory[savedPosition[0] + 1][savedPosition[1]] == '#') {
                    return false;

                }
                if (!map.isWall((botPosition[0] + 1), botPosition[1])) {
                    botPosition[0]++;
                    savedPosition[0]++;

                }
                return true;

            case 'W':
                if (mapMemory[savedPosition[0]][savedPosition[1] - 1] == '#') {
                    return false;

                }
                if (!map.isWall(botPosition[0], (botPosition[1] - 1))) {
                    botPosition[1]--;
                    savedPosition[1]--;

                }
                return true;

            default:
                return false;

        }
    }

}
