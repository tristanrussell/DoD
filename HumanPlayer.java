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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String command = reader.readLine();

                if (command == null) {
                    System.exit(0);

                }

                return this.getNextAction(command);

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
    protected String getNextAction(String command) {
        return null;
    }




}