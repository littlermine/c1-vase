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
 * An implementation of the classic Binary Search, a fast method of 
 * searching pre-sorted arrays.
 * 
 * @author Eric Coolman
 */
public class BinarySearcher extends Searcher {
	/**
	 * Search an array for a given value using a given comparator.  NOTE: This 
	 * implementation works ONLY on sorted haystacks!
	 * 
	 * @param haystack array of objects to search against 
	 * @param needle value to search for within the haystack.
	 * @param comparator for comparing needle to haystack elements.
	 * @return index index within haystack where needle is found, or -1 if not found.
	 * @see StringComparator
	 * @see NumberComparator
	 * @see #NOT_FOUND
	 * @see Sorter#sort(Object[], Comparator)
	 */
	public int search(Object[] haystack, Object needle, Comparator comparator) {
		return _search(haystack, needle, comparator, false);
	}

	/**
	 * Search an array for a given value using a given comparator.  NOTE: This 
	 * implementation works ONLY on sorted haystacks!
	 * 
	 * @param haystack array of objects to search against 
	 * @param needle value to search for within the haystack.
	 * @param comparator for comparing needle to haystack elements.
	 * @return index index within haystack where needle is found, or -1 if not found.
	 * @see StringComparator
	 * @see NumberComparator
	 * @see #NOT_FOUND
	 * @see Sorter#sort(Vector, Comparator)
	 * @see SortedVector
	 */
	public int search(Vector array, Object searchFor, Comparator comparator) {
		return _search(array, searchFor, comparator, false);
	}

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
	 * @see Sorter#sort(Object[], Comparator)
	 */
	public int getInsertionPoint(Object array[], Object searchFor, Comparator comparator) {
		return _search(array, searchFor, comparator, true);
	}

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
	 * @see Sorter#sort(Vector, Comparator)
	 * @see SortedVector
	 */
	public int getInsertionPoint(Vector array, Object searchFor, Comparator comparator) {
		return _search(array, searchFor, comparator, true);
	}

	/**
	 * Internal method - do not use.  Called by all other search methods. 
	 * 
	 * @param array
	 * @param searchFor
	 * @param comparator
	 * @param insertion
	 * @return
	 */
	private int _search(Object array, Object searchFor, Comparator comparator, boolean insertion) {
		int rc = NOT_FOUND;
		int low = 0;
		int high = _getSize(array) - 1;
		int mid = 0;
		int compare = 0;
		while (low <= high) {
			mid = (int)((float)((low + high) / 2) + 0.5f);
			compare = comparator.compare(_getElement(array, mid), searchFor);
			if (compare < 0) {
				low = mid + 1;
			} else if (compare > 0) {
				high = mid - 1;
			} else {
				rc = mid;
				break;
			}
		}
		if (insertion && rc == NOT_FOUND) {
			if (compare < 0) {
				mid++;
			}
			return mid;
		}
		return rc;
	}
	
	/**
	 * Internal method - do not use.  Called by _search() to determine the size of the haystack. 
	 * 
	 * @param array
	 * @param searchFor
	 * @param comparator
	 * @param insertion
	 * @return
	 */
	private int _getSize(Object src) {
		if (src instanceof Vector) {
			return ((Vector)src).size();
		} else if (src instanceof Object[]) {
			return ((Object[])src).length;
		}
		return 0;
	}
	
	/**
	 * Internal method - do not use.  Called by _search() to retrieve an element from the haystack. 
	 * 
	 * @param array
	 * @param searchFor
	 * @param comparator
	 * @param insertion
	 * @return
	 */
	private Object _getElement(Object src, int index) {
		if (src instanceof Vector) {
			return ((Vector)src).elementAt(index);
		} else if (src instanceof Object[]) {
			return ((Object[])src)[index];
		} else if (src instanceof int[]) {
			return new Integer(((int[])src)[index]);
		} else if (src instanceof float[]) {
			return new Float(((float[])src)[index]);
		} else if (src instanceof long[]) {
			return new Long(((long[])src)[index]);
		} else if (src instanceof short[]) {
			return new Short(((short[])src)[index]);
		}
		return null;
	}
}
