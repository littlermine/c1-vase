/**
 * 
 */
package ca.coolman.util;

/**
 * @author Eric Coolman
 *
 */
public class TestAmericanSoundexComparator {

		/**
		 * A method to test the names described on the wikipedia article.
		 * 
		 * @param args
		 */
	public static void main(String[] args) {
			String test[] = {"Robert", "Rupert", "Rubin", "Ashcraft", "Ashcroft",
					"Tymczak", "Pfister"};
			for (int i = 0; i < test.length; i++) {
				System.out.println(test[i] + " = "
						+ new AmericanSoundexComparator().getSoundex(test[i]));
			}
		}


}
