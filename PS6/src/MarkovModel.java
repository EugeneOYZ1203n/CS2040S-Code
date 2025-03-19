import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();
	private int order;

	private Map<String, Map<Character, Integer>> markov_table;

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		markov_table = new HashMap<>();

		// Build the Markov model here
		for (int i = 0; i < text.length() - order; i++) {
            String currentKgram = text.substring(i, i + order);

			char nextChar = text.charAt(i+order);

			markov_table.putIfAbsent(currentKgram, new HashMap<>());
			Map<Character, Integer> nextCharTable = markov_table.get(currentKgram);
			nextCharTable.put(
				nextChar, 
				nextCharTable.getOrDefault(nextChar, 0) + 1);
        }
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		Map<Character, Integer> table = this.markov_table.getOrDefault(kgram, new HashMap<>());
		int sum = 0;
		for (Integer freq: table.values()) {
			sum += freq;
		}
		return sum;
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		return markov_table.getOrDefault(kgram, new HashMap<>()).getOrDefault(c, 0);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.

		int totalFreq = getFrequency(kgram);
		int index = generator.nextInt(totalFreq);

		if (totalFreq == 0) {
			return NOCHARACTER;
		}

		for (int i = 0; i < 256; i++) {
			index -= getFrequency(kgram, (char) i);
			if (index < 0) {
				return (char) i;
			}
		}

		return ' ';
	}
}
