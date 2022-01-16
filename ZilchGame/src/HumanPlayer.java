
/**
 * A class representing a human player in the game of Zilch.
 */
public class HumanPlayer extends Player {

    /**
     * The constructor for the HumanPlayer class.
     * @param name The name of the human player.
     */
    public HumanPlayer(String name) {
        super(name);
    } // end Constructor

    // Use the Javadoc comment from Player
    public char makePlayChoice() {
        char input = playerPrompt("Do you want to (r)oll or (b)ank your turn sum? ");
        if (input == 'q' || input == 'Q') {
            System.out.println("\nQuitting early!!\n");
            System.exit(0);;
        }
        return input;
    } // end makePlayChoice

    // Returns true if the supplied target is in the supplied array, false otherwise.
    private static boolean inArray(char[] array, char target) {
        for (char elem : array)
            if (elem == target)
                return true;
        return false;
    } // end inArray

    // Prompts for, obtains and returns a single character from the user. If the
    // character is not legal, the user is prompted again.
    private static char playerPrompt(String prompt) {
        char response = '?';
        char[] legalResponses = {'r', 'R', 'b', 'B', 'q', 'Q'};
        while (true) {
            System.out.print(prompt);
            response = getChar();
            if (inArray(legalResponses, response))
                return response;
            else
                System.out.print("Illegal entry, please try again. ");
        }
    } // end playerPrompt

} // end HumanPlayer class
