import java.io.IOException;
import java.util.Random;

/**
 * A class representing a player in the game of Zilch.
 */
public abstract class Player {

    private int gameScore = 0;
    private int zilchCount = 0;
    private int rollCount = 0;
    private int diceAvailable;
    private int turnScore;
    private String name;
    private static int numInstantiations = 0;	// Used only to change the seed value below
    private Random generator = new Random(System.currentTimeMillis() + numInstantiations);

    /**
     * The constructor for the Player class.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        numInstantiations++;
    } // end Constructor

    /**
     * Plays a single turn for the player.  A turn consists of one or more
     * dice rolls.
     */
    // The method uses the Scoring class to score a dice roll.  Calling
    // Scoring.scoreThrow essentially resets all the attributes stored in Scoring,
    // which can be obtained through accessors.
    public void takeTurn() {
        boolean turnOver = false;
        diceAvailable = 6;
        turnScore = 0;
        int rollScore;
        char playerChoice;
        while (!turnOver) {
            System.out.print("\n" + name + " Rolling " + diceAvailable +
                    " dice (roll " + (rollCount + 1) + "): (Press <enter>)");
            @SuppressWarnings("unused")
            char dummy = getChar();
            rollScore = Scoring.scoreThrow(rollDice(diceAvailable));
            turnScore += rollScore;
            diceAvailable = Scoring.getDiceLeft();
            System.out.println(Scoring.getScoreDescription() + " " + rollScore + " points.");
            if (rollScore == 0) {
                zilchCount++;
                turnScore = 0;
                turnOver = true;
                if (zilchCount == 3) {
                    gameScore -= 500;
                    zilchCount = 0;
                }
            } // end zilch check
            else if (Scoring.getRollAgain() || diceAvailable == 0) {
                System.out.println("You get a free roll!");
                diceAvailable = 6;
            } // end free roll check
            else if (turnScore < 300)
                System.out.println("Turn score: " + turnScore + " is less than 300. " + name + " must roll again.");
            else if (turnScore >= 300 && diceAvailable > 0) {
                System.out.println("Turn score is: " + turnScore + " and " + name + " can roll " +
                        diceAvailable + " dice.");
                playerChoice = makePlayChoice();
                System.out.print(name + " choosing to ");
                if (playerChoice == 'b' || playerChoice == 'B') {
                    // Bank turn sum.
                    System.out.println("bank score.");
                    zilchCount = 0;
                    gameScore += turnScore;
                    turnScore = 0;
                    turnOver = true;
                } // end bank
                else
                    System.out.println("roll again.");
            } // end bank or roll check
        } // end while
        showStatus();
    } // end takeTurn

    /**
     * The player chooses to roll or bank their turn sum.
     * @return A character indicating the player's choice: r or R for roll, b or
     * B for bank, and q or Q to quit the game early.
     */
    public abstract char makePlayChoice();

    //
    /**
     * Obtains and returns a single character as provided by the user. If the user enters
     * more than one character the extra characters are ignored. If he or she does not
     * provide any characters then the null character is returned.
     * @return A single character as provided by the user.
     */
    public static char getChar() {
        byte[] buffer = new byte[100];
        int numRead = 0;
        try {
            numRead = System.in.read(buffer);
        } catch (IOException e) {
        }
        if (numRead > 0)
            return (char)buffer[0];
        return '\0';
    } // end getChar

    // Returns an array of numDice random numbers between 1 and 6
    private int[] rollDice(int numDice) {
        int[] dice = new int[numDice];
        for (int i = 0; i < numDice; i++)
            dice[i] = generator.nextInt(6) + 1;
        rollCount++;
        return dice;
    } // end rollDice

    // Displays the status of the player
    private void showStatus() {
        System.out.println(name + "\'s Bank: " + gameScore + " Zilch count: " + zilchCount);
    } // end showStatus

    /**
     * Returns the player's current banked score.
     * @return The player's current banked score.
     */
    public int getScore() {
        return gameScore;
    } // end getScore

    /**
     * Returns the player's cumulative dice roll count.
     * @return The player's dice roll count.
     */
    public int getRollCount() {
        return rollCount;
    } // end getRollCount

    /**
     * Returns the player's name.
     * @return The player's name.
     */
    public String getName() {
        return name;
    } // end getName

    /**
     * Returns the number of dice available to roll.
     * @return The current number of dice available to roll.
     */
    public int getDiceAvailable() {
        return diceAvailable;
    } // end getDiceAvailable

    /**
     * Returns the current turn score.
     * @return The current turn score.
     */
    public int getTurnScore() {
        return turnScore;
    } // end getTurnScore

} // end Player class
