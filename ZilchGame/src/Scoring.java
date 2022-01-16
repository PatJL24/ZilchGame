/**
 * A utility class to be used in the game of Zilch to calculate dice roll scores.
 *
 * The class not only calculates the score but it also returns a description of
 * the scoring combination as a string and calculates how many dice are left to
 * roll assuming that all scoring combinations will be used.  A flag is also set
 * if the user has earned a free roll.
 *
 */

// The class has all static members as it does not require instantiation.  It is
// assumed that the scoreThrow method will be first invoked with a new dice roll,
// and then this method can reset the values of the static attributes.
public class Scoring {

    private static int[] roll;
    private static int diceLeft;
    private static String scoreDescription;
    private static boolean rollAgain = false;

    /**
     * The method returns the maximum score that can be obtained from the dice roll
     * supplied to the method as an array of int.  It also assigns the other attributes
     * including the number of dice left to roll, the string description of the
     * scoring combination and sets the flag that indicates that a free roll has been
     * obtained.
     * @param dice The array of integer values representing the roll of the dice. The
     * size of this array will be the number of dice that were rolled by the player.
     * @return The maximum score of the supplied dice combination.
     */
    // This method also resets the values of the attributes.
    public static int scoreThrow(int[] dice) {
        int score = 0;
        rollAgain = false;
        roll = dice.clone();
        scoreDescription = "";
        int numThrown = dice.length;
        diceLeft = numThrown;
        int[] counts = countDice(dice);
        int[] countOfCounts = countCounts(counts);
        // A straight or three pairs
        if (countOfCounts[1] == 6 || countOfCounts[2] == 3) {
            if (countOfCounts[1] == 6)
                scoreDescription = "*A Straight!*";
            else
                scoreDescription = "*Three pairs!*";
            setFreeRoll();
            return 1500;
        }
        // Two triplets
        if (countOfCounts[3] == 2) {
            scoreDescription = "*Two triplets!*";
            setFreeRoll();
            return scoreDuplicates(3, counts);
        }
        // Six of a kind
        if (countOfCounts[6] == 1) {
            scoreDescription = "*Six of a kind!*";
            setFreeRoll();
            return scoreDuplicates(6, counts);
        }
        // Check 3, 4 or 5 of a kind
        for (int numDups = 3; numDups < 6; numDups++) {
            if (countOfCounts[numDups] == 1) {
                scoreDescription = "*" + getNumberName(numDups) + " of a kind*";
                diceLeft = numThrown - numDups;
                score = scoreDuplicates(numDups, counts);
                score += countLeftovers(counts);
                if (diceLeft == 0 && numThrown == 6) {
                    setFreeRoll();
                }
                else
                    rollAgain = false;
                return score;
            } // end if
        } // end for
        // Ones and Fives Only
        score = countLeftovers(counts);
        // No score with six dice check
        if (numThrown == 6 && score == 0) {
            scoreDescription = "*No score with six dice!*";
            setFreeRoll();
            return 500;
        }
        // Zilch check
        if (score == 0)
            scoreDescription = "*A Zilch!*";
        return score;
    } // end scoreThrow

    // Counts the frequency of each dice value and returns an array of these
    // counts.  The first element in the array is not used, so the index value
    // will be the actual dice number.
    private static int[] countDice(int[] dice) {
        int[] counts = new int[7];
        for (int i = 0; i < dice.length; i++)
            counts[dice[i]]++;
        return counts;
    } // end countDice

    // Counts the frequency of duplicate values and returns an array.  The first
    // element in the array is not used.
    private static int[] countCounts(int[] counts) {
        return countDice(counts);
    } // end countCounts

    // Scores duplicates - triples, four of a kind or five of a kind as supplied
    // as the first argument.  The method can also score two triples at a time,
    // if necessary.
    private static int scoreDuplicates(int numDups, int[] counts) {
        int score = 0;
        int factor = (int)Math.pow(2, numDups - 3);
        // Need to figure out which dice value is duplicated:
        for (int dice = 1; dice < 7; dice++) {
            if (counts[dice] == numDups) {
                if (dice == 1)
                    score += 1000 * factor;
                else
                    score += dice * 100 * factor;
            } // end if
        } // end for
        return score;
    } // end scoreDuplicates

    // Scores leftover ones and fives that are not in duplicates.
    // Also builds the string listing the number of these dice.
    private static int countLeftovers(int[] counts) {
        int score = 0;
        int numOnes = counts[1];
        int numFives = counts[5];
        // Ones
        if (numOnes == 2 || numOnes == 1) {
            scoreDescription += "*" + getNumberName(numOnes);
            if (numOnes == 2)
                scoreDescription += " ones*";
            else
                scoreDescription += " one*";
            score += numOnes * 100;
            diceLeft -= numOnes;
        } // Fives
        if (numFives == 2 || numFives == 1) {
            scoreDescription += "*" + getNumberName(numFives);
            if (numFives == 2)
                scoreDescription += " fives*";
            else
                scoreDescription += " five*";
            score += numFives * 50;
            diceLeft -= numFives;
        }
        return score;
    } // end countLeftovers

    // Called when a free roll is awarded.
    private static void setFreeRoll() {
        diceLeft = 6;
        rollAgain = true;
    } // end setFreeRoll

    // Returns the name of the dice roll as a word.
    private static String getNumberName(int roll) {
        String[] names = {"one", "two", "three", "four", "five", "six"};
        return names[roll - 1];
    } // end getNumberName

    // Builds and returns a string representation of the dice roll using full
    // names of each dice value.
    private static String rollString() {
        String out = "";
        for (int die : roll)
            out += "*" + getNumberName(die) + "*";
        return out;
    } // end rollString

    /**
     * Returns the number of dice left to roll.
     * @return The number of dice left to roll.  It is assumed that all scoring dice
     * will be used and only the non-scoring dice can be re-rolled.
     */
    public static int getDiceLeft() {
        return diceLeft;
    } // end getDiceLeft

    /**
     * Builds a string description of the roll and the scoring combination.
     * @return A string description of the dice roll with a description of all possible
     * scoring combinations in the roll.
     */
    public static String getScoreDescription() {
        return rollString() + "\n" + scoreDescription;
    } // end getScoreDescription

    /**
     * Returns a true if the player has obtained a free roll, false otherwise.
     * @return True if the player has obtained a free roll, false otherwise.
     */
    public static boolean getRollAgain() {
        return rollAgain;
    } // end getRollAgain

} // end Scoring class
