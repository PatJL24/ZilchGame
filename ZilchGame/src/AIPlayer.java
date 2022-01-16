
/**
 * A class representing an AI player in the game of Zilch.
 */
public class AIPlayer extends Player {

    /**
     * The constructor for the AIPlayer class.
     * @param name The name of the AI player.
     */
    public AIPlayer(String name) {
        super(name);
    } // end Constructor

    // Use the Javadoc comment from Player
    public char makePlayChoice() {
        if ((int)(6 * Math.random()) + 1 > getDiceAvailable() || getTurnScore() > 1000)
            return 'b';
        else
            return 'r';
    } // end makePlayChoice

} // end AIPlayer class
