import java.io.*;

/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
class HumanPlayer {

    /**
     * Reads player's input from the console.
     *
     * @return : A string containing the input the player entered.
     */
    String getInputFromConsole() {
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
     * Processes the command.
     *
     * @param logic : The GameLogic object to call actions on.
     * @param command : The command that the user typed in.
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    String getNextAction(GameLogic logic, String command) {
        String[] commandSplit = command.split(" ");
        command = commandSplit[0];
        switch (command) {
            case "HELLO":
                return "Gold to win: " + logic.hello() + "\n";

            case "GOLD":
                return "Gold owned: " + logic.gold() + "\n";

            case "MOVE":
                if (commandSplit.length > 1 && commandSplit[1].length() == 1) {
                    return logic.move(commandSplit[1].charAt(0)) + "\n";

                } else {
                    return "Invalid direction entered\n";

                }

            case "PICKUP":
                return logic.pickup() + ". Gold owned: " + logic.gold() + "\n";

            case "LOOK":
                return logic.look();

            case "QUIT":
                logic.quitGame();

            default:
                return "Invalid command";

        }
    }

}