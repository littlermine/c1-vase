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

public class QuickSorter extends Sorter {
	/**
	 * Partition the objects from a given array of objects according to a given
	 * range.
	 * 
	 * @param array Array of objects.
	 * @param start Start index of the range.
	 * @param end End index of the range.
	 * @return Start index of the range.
	 */
	private int partition(Object array, int start, int end, Comparator comparator) {
		int i = start - 1;
		int j = end;
		Object v = getElement(array, end);
		for ( ; ;) {
			while (comparator.compare(getElement(array, ++i), v) > 0) {
				//
			}
			while (comparator.compare(v, getElement(array, --j)) > 0 && j > start) {
				//
			}
			if (i >= j) {
				break;
			}
			swap(array, i, j);
		}
		swap(array, i, end);
		return i;
	}

	/**
	 * Sorting an array of objects.
	 * 
	 * @param array Objects to be sorted.
	 * @param start Start index.
	 * @param end End index.
	 */
	private void _sort(Object array, int start, int end, Comparator comparator) {
		if (end <= start) {
			return;
		} else {
			int i = partition(array, start, end, comparator);
			_sort(array, start, i - 1, comparator);
			_sort(array, i + 1, end, comparator);
		}
	}

	public void sort(Object[] array, Comparator comparator) {
		_sort(array, 0, array.length - 1, comparator);
	}

	public void sort(Vector array, Comparator comparator) {
		_sort(array, 0, array.size() - 1, comparator);
	}
}