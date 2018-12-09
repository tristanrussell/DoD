import java.util.Arrays;
import java.util.Random;

public class BotPlayer {

    /* Coordinates of the bot as {y, x} */
    private int[] botPosition;

    /* Local map around bot */
    private char[][] mapMemory;

    private boolean toUpdate = true;

    /* The position of the bot relative to the map in mapMemory */
    private int[] savedPosition = new int[2];

    private boolean isPlayer = false;

    /* Coordinates of the player if they are found */
    private int[] playerPosition = new int[2];

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
        if (toUpdate) {
            toUpdate = false;
            return "LOOK";

        }
        return "MOVE";

    }

    /**
     * Performs the next move in the bot's rotation of moves.
     *
     * @param map : The current map object being used.
     */
    protected void takeTurn(Map map, PlayerInfo player) {
        boolean hasMoved;
        switch (this.nextMove()) {
            case "LOOK":
                savedPosition[0] = 2;
                savedPosition[1] = 2;
                mapMemory = map.getLocalMap(botPosition[0], botPosition[1]);
                isPlayer = checkForPlayer(player);
                break;

            case "MOVE":
                do {
                    hasMoved = move(map, randDirection());
                } while(!hasMoved);
                break;

            default:
                break;
        }

        // DEBUGGING
        System.out.println(Arrays.toString(botPosition));
    }

    protected boolean checkForPlayer(PlayerInfo player) {
        int[] playerPos = player.getPlayerPosition();
        int testY = Math.abs(playerPos[0] - botPosition[0]);
        int testX = Math.abs(playerPos[1] - botPosition[1]);

        if (testX < 3 && testY < 3) {
            playerPosition[0] = 2 + playerPos[0] - botPosition[0];
            playerPosition[1] = 2 + playerPos[1] - botPosition[1];
            return true;

        }
        return false;

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
     * @param map : The current map object being used.
     * @param direction : The direction that the bot should move.
     */
    protected boolean move(Map map, char direction) {
        if (isPlayer) {
            if(Math.abs(savedPosition[0] - playerPosition[0]) > Math.abs(savedPosition[1] - playerPosition[1])) {
                if (savedPosition[0] < playerPosition[0]) {
                    direction = 'S';


                } else {
                    direction = 'N';

                }
            } else {
                if (savedPosition[1] < playerPosition[1]) {
                    direction = 'E';


                } else {
                    direction = 'W';

                }
            }
        }
        switch (direction) {
            case 'N':
                if (mapMemory[savedPosition[0] - 1][savedPosition[1]] == '#') {
                    return false;

                }
                if (!map.isWall((botPosition[0] - 1), botPosition[1])) {
                    botPosition[0]--;
                    if (--savedPosition[0] == 0) {
                        if (!isPlayer) {
                            toUpdate = true;

                        }
                    }
                }
                break;

            case 'E':
                if (mapMemory[savedPosition[0]][savedPosition[1] + 1] == '#') {
                    return false;

                }
                if (!map.isWall(botPosition[0], (botPosition[1] + 1))) {
                    botPosition[1]++;
                    if (++savedPosition[1] == 4) {
                        if (!isPlayer) {
                            toUpdate = true;

                        }
                    }
                }
                break;

            case 'S':
                if (mapMemory[savedPosition[0] + 1][savedPosition[1]] == '#') {
                    return false;

                }
                if (!map.isWall((botPosition[0] + 1), botPosition[1])) {
                    botPosition[0]++;
                    if (++savedPosition[0] == 4) {
                        if (!isPlayer) {
                            toUpdate = true;

                        }
                    }
                }
                break;

            case 'W':
                if (mapMemory[savedPosition[0]][savedPosition[1] - 1] == '#') {
                    return false;

                }
                if (!map.isWall(botPosition[0], (botPosition[1] - 1))) {
                    botPosition[1]--;
                    if (--savedPosition[1] == 0) {
                        if (!isPlayer) {
                            toUpdate = true;

                        }
                    }
                }
                break;

            default:
                return false;

        }
        if (isPlayer && playerPosition[0] == savedPosition[0] && playerPosition[1] == savedPosition[1]) {
            isPlayer = false;
            toUpdate = true;

        }
        return true;

    }

}
