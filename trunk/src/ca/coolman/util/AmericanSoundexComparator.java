/*
 * Copyright (c) 2012, Eric Coolman, 1815750 Ontario Inc. and/or its 
 * affiliates. All rights reserved.
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  1815750 Ontario Inc designates 
 * this  * particular file as subject to the "Classpath" exception as provided
 * in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact 1815750 Ontario Inc. through http://www.coolman.ca/ if you 
 * need additional information or have any questions.
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

}
