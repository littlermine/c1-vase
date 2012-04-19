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
 * An sorter abstract, and a factory to retrieve sorter implementations.
 * 
 * @author Eric Coolman
 * 
 */
public abstract class Sorter {
	/**
	 * ID for Bubble Sort algorithm.
	 */
	public final static int BUBBLE = 0;
	/**
	 * ID for Quick Sort algorithm.
	 */
	public final static int QUICK = 1;

	/**
	 * Sort an array of objects with a given element comparator.
	 * 
	 * @param array array to sort.
	 * @param comparator element comparator implementation/
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public abstract void sort(Object[] array, Comparator comparator);

	/**
	 * Sort an array (Vector) of objects with a given element comparator.
	 * 
	 * @param array vector to sort.
	 * @param comparator element comparator implementation/
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public abstract void sort(Vector array, Comparator comparator);

	/**
	 * Get an element from an array given the index
	 * 
	 * @param array Array of objects.
	 * @return i index of object to be retrived.
	 */
	protected Object getElement(Object array, int i) {
		if (array instanceof Vector) {
			return ((Vector)array).elementAt(i);
		} else {
			return ((Object[])array)[i];
		}
	}
	
	/**
	 * Swap two objects for given indexes.
	 * 
	 * @param array Array of objects.
	 * @param l Index of element to swap.
	 * @param r Index of element to swap.
	 */
	protected void swap(Object array, int l, int r) {
		if (array instanceof Vector) {
			swap((Vector)array, l, r);
		} else {
			swap((Object[])array, l, r);
		}
	}

	/**
	 * Swap two objects for given indexes.
	 * 
	 * @param array Array of objects.
	 * @param l Index of element to swap.
	 * @param r Index of element to swap.
	 */
	protected void swap(Object[] array, int l, int r) {
		Object tmp = array[l];
		array[l] = array[r];
		array[r] = tmp;
	}

	/**
	 * Swap two objects for given indexes.
	 * 
	 * @param array Array of objects.
	 * @param l Index of element to swap.
	 * @param r Index of element to swap.
	 */
	protected void swap(Vector array, int l, int r) {
		Object tmp = array.elementAt(l);
		array.setElementAt(array.elementAt(r), l);
		array.setElementAt(tmp, r);
	}
	
	protected int getSize(Object array) {
		if (array instanceof Vector) {
			return ((Vector)array).size();
		} else if (array instanceof Object[]) {
			return ((Object[])array).length;
		}
		return 0;
	}
	
	/**
	 * Get a default sorter implementation. By default a basic bubble sorter is
	 * returned.
	 * 
	 * @return default sorter implementation.
	 */
	public static Sorter getInstance() {
		return Sorter.getInstance(Sorter.BUBBLE);
	}

	/**
	 * Get a sorter implementation for requested algorithm.
	 * 
	 * @return sorter implementation for requested algorithm.
	 * @see #BUBBLE
	 */
	public static Sorter getInstance(int alg) {
		switch (alg) {
			case BUBBLE :
			default :
				return new BubbleSorter();
		}
	}
}
