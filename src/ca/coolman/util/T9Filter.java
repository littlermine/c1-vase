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
 * 
 * @author Eric Coolman
 *
 */
public class T9Filter implements Filter {
	private static final String map = "22233344455566677778889999";
	private String subject;

	/**
	 * Set the subject to filter against.
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Test of word matches subject.
	 * 
	 * @return true of word matches (starts with) subject, otherwise false.
	 */
	public boolean matches(Object word) {
		String encoded = getWordValue((String)word);
		return encoded.startsWith(subject);
	}

	/**
	 * Map the characters of a word to the 1-9 keys of a mobile keypad.
	 * 
	 * @param word
	 * @return
	 */
	public String getWordValue(String word) {
		word = word.toLowerCase();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			if (ch >= 'a' && ch <= 'z') {
				int index = ch - 'a';
				buf.append(map.charAt(index));
			}
		}
		return buf.toString();
	}
}
