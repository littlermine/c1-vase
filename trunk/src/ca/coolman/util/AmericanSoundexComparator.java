/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.util;

/**
 * An implementation of the American Soundex encoding variation 
 * described at:
 * 
 * http://en.wikipedia.org/wiki/Soundex
 * 
 * This encoding will work well for english text and names, but
 * for other locales, will produce mixed results.
 * 
 * @author Eric Coolman
 * 
 */
public class AmericanSoundexComparator extends SoundexComparator {

	/**
	 * Construct an american soundex comparator.
	 */
	public AmericanSoundexComparator() {
		super();
	}

	/*
	 * Internal method to get a soundex encoding for a single character.
	 */
	protected int getSoundex(char ch) {
		final String tokens[] = {"BFPV", "CGJKQSXZ", "DT", "L", "MN", "R",
				"AEIOUY"};
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].indexOf(ch) != -1) {
				return i + 1;
			}
		}
		return 0;
	}

	/**
	 * Internal method to strip all the h,w from the original string. The vowels
	 * are stripped later, needed for the recurrence algorithm, see rule 3 at:
	 * 
	 * See http://en.wikipedia.org/wiki/Soundex
	 */
	protected char[] getBaseSoundex(Object text) {
		String strip = "HW"; // "AEIOUYHW";
		char src[] = ((String) text).toUpperCase().toCharArray();
		String dest = "" + src[0];
		for (int i = 0; i < src.length; i++) {
			if (strip.indexOf(src[i]) == -1) {
				dest += src[i];
			}
		}
		return dest.toCharArray();
	}

	/**
	 * Strip the vowels from the soundex string.
	 * 
	 * @param src
	 * @return
	 */
	protected String stripVowels(String src) {
		char s[] = src.toCharArray();
		String dest = "" + s[0];
		for (int i = 1; i < s.length; i++) {
			if (s[i] != '7') {
				dest += s[i];
			}
		}
		return dest;
	}
	/**
	 * Return a 4-character soundex encoding for a given string.
	 * 
	 * See http://en.wikipedia.org/wiki/Soundex
	 * 
	 * @param text
	 * @return soundex value.
	 */
	protected String getSoundex(Object text) {
		if (text == null) {
			return "0000";
		}
		char[] s = getBaseSoundex(text);
		String result = "" + s[0];
		int last = getSoundex(s[0]);
		for (int i = 1; i < s.length; i++) {
			int ch = getSoundex(s[i]);
			if (ch != last) {
				last = ch;
				result += ch;
			}
		}
		return (stripVowels(result) + "000").substring(0, 4);
	}

	/**
	 * A method to test the names described on the wikipedia article.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String test[] = {"Robert", "Rupert", "Rubin", "Ashcraft", "Ashcroft",
				"Tymczak", "Pfister"};
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i] + " = "
					+ new AmericanSoundexComparator().getSoundex(test[i]));
		}
	}

}
