// All this class has is a main method.  It obtains parameters for the Zilch game
// from the user and starts up the game.
public class Main {

    public static void main(String[] args) {
        String humanName = IOHelper.getString("Enter the human player\'s name: ");
        String botName = IOHelper.getString("Enter the AI player\'s name: ");
        int gameLimit = IOHelper.getInt(300, "Enter the game limit: ", 100000);
        HumanPlayer human = new HumanPlayer(humanName);
        AIPlayer bot = new AIPlayer(botName);
        try {
            ZilchGame game = new ZilchGame(human, bot, gameLimit);
            game.playGame();
        } catch (ZilchException e) {
            System.err.println("Cannot play game: " + e.getMessage());
        } // end try/catch
    } // end main method

} // end Main class
