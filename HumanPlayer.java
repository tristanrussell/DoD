import java.io.*;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
public class HumanPlayer {

    /**
     * Reads player's input from the console.
     * <p>
     * return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String command = input.readLine();

                if (command == null) {
                    System.exit(0);

                }

                if (command.length() > 0) {
                    return command.toUpperCase();
                }

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }

        return null;
    }

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction(GameLogic logic, String command) {
        String[] commandSplit = command.split(" ");
        command = commandSplit[0];
        switch (command) {
            case "HELLO":
                return "Gold to win: " + logic.hello() + "\n";

            case "GOLD":
                return "Gold owned: " + logic.gold() + "\n";

            case "MOVE":
                if (commandSplit[1].length() == 1) {
                    return logic.move(commandSplit[1].charAt(0)) + "\n";

                } else {
                    return "Invalid direction entered\n";

                }

            case "PICKUP":
                break;

            case "LOOK":
                return logic.look();

            case "QUIT":
                break;

            default:
                break;

        }
        return null;

    }




}