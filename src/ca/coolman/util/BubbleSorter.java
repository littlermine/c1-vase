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
 * An implementation of the classic bubble sort, the most basic
 * of all sorts, with low memory overhead, but certainly not the 
 * fastest in most use cases. 
 * 
 * @author Eric Coolman
 *
 */
public class BubbleSorter extends Sorter {
	
	/* (non-Javadoc)
	 * @see ca.coolman.util.Sorter#sort(java.lang.Object[], ca.coolman.util.Comparator)
	 */
	public void sort(Object[] array, Comparator comparator) {
		_sort(array, comparator);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.util.Sorter#sort(java.util.Vector, ca.coolman.util.Comparator)
	 */
	public void sort(Vector array, Comparator comparator) {
		_sort(array, comparator);
	}
	
	/* (non-Javadoc)
	 * @see ca.coolman.util.Sorter#sort(java.util.Vector, ca.coolman.util.Comparator)
	 */
	public void _sort(Object array, Comparator comparator) {
		boolean unsorted = true;
		int j = getSize(array);
		while (unsorted) {
			unsorted = false;
			j--;
			for (int i = 0; i < j; i++) {
				Object lvalue = getElement(array, i);
				Object rvalue = getElement(array, i+1);
				if (comparator.compare(lvalue, rvalue) < 0) {
					swap(array, i, i+1);
					unsorted = true;
				}
			}
		}
	}
}
