/**
 * Plays the game of Zilch with a human and an AI player.
 * @author Alan McLeod
 * @version 1.0
 */
public class ZilchGame {

    private int gameLimit;
    private HumanPlayer human;
    private AIPlayer bot;

    /**
     * Constructor accepts the two game players - a human and an AI as well as the
     * game limit value.
     * @param human Represents the human player.
     * @param bot Represents the AI player.
     * @param gameLimit Once a player gets over this limit they have won.
     * @throws ZilchException If either player object is null, or the gameLimit is illegal.
     */
    public ZilchGame (HumanPlayer human, AIPlayer bot, int gameLimit) throws ZilchException {
        if (human == null)
            throw new ZilchException("Human player not defined!");
        if (bot == null)
            throw new ZilchException("AI player not defined!");
        if (gameLimit < 300 || gameLimit > 100000)
            throw new ZilchException("Game limit: " + gameLimit + " not legal (Between 300 and 100000).");
        this.gameLimit = gameLimit;
        this.human = human;
        this.bot = bot;
    } // end constructor

    // Displays instructions as to how to interact with the game.
    private void displayIntro() {
        // A Text block would be useful here!
        String out = "This program plays the game of Zilch.";
        out += "\nYou will roll against an AI player who banks or rolls at random.";
        out += "\n\nPossible responses at a prompt are \"r\" to roll again, \"b\" to";
        out += "\nbank your points, just <enter> and \"q\" to quit the game early. Otherwise";
        out += "\nthe session will run until one player wins.\n";
        System.out.println(out);
    } // end displayIntro

    /**
     * Plays the game of Zilch.  A random choice is made to see which player goes
     * first.
     */
    public void playGame() {
        displayIntro();
        boolean gameOver = false;
        if (Math.random() > 0.5) {
            System.out.println("Human goes first!");
            while (!gameOver) {
                displayScores();
                human.takeTurn();
                if (human.getScore() < gameLimit) {
                    bot.takeTurn();
                    if (bot.getScore() >= gameLimit)
                        gameOver = true;
                } else
                    gameOver = true;
            } // end while
        } else {
            System.out.println("AI goes first!");
            while (!gameOver) {
                displayScores();
                bot.takeTurn();
                if (bot.getScore() < gameLimit) {
                    human.takeTurn();
                    if (human.getScore() >= gameLimit)
                        gameOver = true;
                } else
                    gameOver = true;
            } // end while
        } // end else
        if (human.getScore() >= gameLimit)
            System.out.println("\n" + human.getName() + " wins with " + human.getScore() +
                    " points, after " + human.getRollCount() + " dice rolls!");
        else
            System.out.println("\n" + bot.getName() + " wins with " + bot.getScore() +
                    " points, after " + bot.getRollCount() + " dice rolls!");
    } // end playGame

    // Displays the scores of both players.
    private void displayScores() {
        System.out.println("\n" + human.getName() + " has " + human.getScore() + " points, " +
                bot.getName() + " has " + bot.getScore() + " points.");
    } // end displayScores

} // end ZilchGame
