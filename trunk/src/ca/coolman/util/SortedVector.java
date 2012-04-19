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

import java.util.Enumeration;
import java.util.Vector;

/**
 * An auto-sorting vector implementation.  Elements are sorted
 * on the fly as they are inserted into the vector.
 * 
 * @author Eric Coolman
 */
public class SortedVector extends Vector {
	private Comparator comparator;
	
	/**
	 * Construct a sorted vector with a given element comparator.
	 * 
	 * @param c comparator implementation for sorting elements on insertion.
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public SortedVector(Comparator c) {
		super();
		this.comparator = c;
	}

	/**
	 * Construct a sorted vector with a given source vector and element comparator.  The
	 * source vector will be sorted on construction.
	 * 
	 * @param src a source vector.
	 * @param c comparator implementation for sorting elements on insertion.
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public SortedVector(Vector src, Comparator c) {
		this(c);
		if (src != null) {
			for (Enumeration e = src.elements(); e.hasMoreElements(); ) {
				addElement(e.nextElement());
			}
		}
	}

	/**
	 * Construct a sorted vector with a given source vector and element comparator.  The
	 * source vector will be sorted on construction.
	 * 
	 * @param src a source vector.
	 * @param c comparator implementation for sorting elements on insertion.
	 * @see StringComparator
	 * @see NumberComparator
	 */
	public SortedVector(Object src[], Comparator c) {
		this(c);
		if (src != null) {
			for (int i = 0; i < src.length; i++) {
				addElement(src[i]);
			}
		}
	}

	/**
	 * Get the comparator used in sorting.
	 * 
	 * @return the comparator used in sorting.
	 */
	public Comparator getComparator() {
		return comparator;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Vector#addElement(java.lang.Object)
	 */
	public void addElement(Object obj) {
		int index = Searcher.getInstance().getInsertionPoint(this, obj, comparator);
		insertElementAt(obj, index);
	}

}
