import java.util.Arrays;
import java.util.Random;

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
     * @return : The next move in the rotation to make.
     */
    protected int nextMove() {
        if (turnInRotation == 3) {
            turnInRotation = 1;
            return 3;

        }
        return turnInRotation++;

    }

    /**
     * Performs the next move in the bot's rotation of moves.
     *
     * @param map : The current map object being used.
     */
    protected void takeTurn(Map map) {
        switch (this.nextMove()) {
            case 1:
                map.getLocalMap(botPosition[0], botPosition[1]);
                break;

            case 2:
                this.moveRandom(map);
                break;

            case 3:
                this.moveRandom(map);
                break;

            default:
                break;
        }

        System.out.println(Arrays.toString(botPosition));
    }

    protected void moveRandom(Map map) {
        char[] directions = {'N', 'E', 'S', 'W'};
        Random r = new Random();
        int randDirection = r.nextInt(4);
        this.move(map, directions[randDirection]);

    }

    protected void move(Map map, char direction) {
        switch (direction) {
            case 'N':
                if (!map.isWall((botPosition[0] - 1), botPosition[1])) {
                    botPosition[0]--;
                }
                break;

            case 'E':
                if (!map.isWall(botPosition[0], (botPosition[1] + 1))) {
                    botPosition[1]++;
                }
                break;

            case 'S':
                if (!map.isWall((botPosition[0] + 1), botPosition[1])) {
                    botPosition[0]++;
                }
                break;

            case 'W':
                if (!map.isWall(botPosition[0], (botPosition[1] - 1))) {
                    botPosition[1]--;
                }
                break;

        }
    }

}
