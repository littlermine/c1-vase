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

import java.util.Vector;

/**
 * A searcher abstract, and a factory to retrieve searcher implementations.
 * 
 * @author Eric Coolman
 */
public abstract class Searcher {
	/**
	 * Value returned with needle not found in haystack.
	 */
	public final static int NOT_FOUND = -1; 
	/**
	 * ID for Binary Search algorithm
	 */
	public final static int BINARY = 0;
	/**
	 * ID for Binary Search algorithm
	 */
	public final static int LINEAR = 1;
	
	/**
	 * Search an array for a given value using a given comparator.
	 * 
	 * @param haystack array of objects to search against 
	 * @param needle value to search for within the haystack.
	 * @param comparator for comparing needle to haystack elements.
	 * @return index index within haystack where needle is found, or -1 if not found.
	 * @see StringComparator
	 * @see NumberComparator
	 * @see SoundexComparator
	 * @see #NOT_FOUND
	 */
	public abstract int search(Object[] haystack, Object needle, Comparator comparator);

	/**
	 * Search an array for a given value using a given comparator.
	 * 
	 * @param haystack array of objects to search against 
	 * @param needle value to search for within the haystack.
	 * @param comparator for comparing needle to haystack elements.
	 * @return index index within haystack where needle is found, or -1 if not found.
	 * @see StringComparator
	 * @see NumberComparator
	 * @see SoundexComparator
	 * @see #NOT_FOUND
	 */
	public abstract int search(Vector haystack, Object needle, Comparator comparator);

	/**
	 * Determine where in array a given value should be inserted, using a given comparator.  This
	 * method must always return a value between 0 and <array-size>.
	 * 
	 * @param haystack array of objects to search against 
	 * @param needle value to search for within the haystack.
	 * @param comparator for comparing needle to haystack elements.
	 * @return index index within haystack where needle should be inserted
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public abstract int getInsertionPoint(Vector haystack, Object needle, Comparator comparator);

	/**
	 * Determine where in array a given value should be inserted, using a given comparator.  This
	 * method must always return a value between 0 and <array-size>.
	 * 
	 * @param haystack array of objects to search against 
	 * @param needle value to search for within the haystack.
	 * @param comparator for comparing needle to haystack elements.
	 * @return index index within haystack where needle should be inserted
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public abstract int getInsertionPoint(Object haystack[], Object needle, Comparator comparator);
	
	/**
	 * Get a default searcher instance.  By default, a binary search implementation will 
	 * be returned.
	 * 
	 * @return default searcher instance.
	 */
	public static Searcher getInstance() {
		return Searcher.getInstance(Searcher.BINARY);
	}
	
	/**
	 * Get a searcher instance for requested algorithm. 
	 * 
	 * @return searcher instance.
	 * @see #BINARY
	 */
	public static Searcher getInstance(int alg) {
		switch (alg) {
			case BINARY:
			default:
				return new BinarySearcher();
		}
	}
}
