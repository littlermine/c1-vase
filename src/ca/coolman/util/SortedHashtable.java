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
import java.util.Hashtable;

/**
 * At this time, this implementation does not handle nested Hashtables.
 * 
 * @author Eric Coolman
 *
 */
public class SortedHashtable extends Hashtable {
	private SortedVector keys;
	
	/**
	 * 
	 */
	public SortedHashtable() {
		super();
		keys = new SortedVector(new StringComparator());
	}

	/**
	 * @param initialCapacity
	 */
	public SortedHashtable(int initialCapacity) {
		super(initialCapacity);
		keys = new SortedVector(new StringComparator());
	}

	/* (non-Javadoc)
	 * @see java.util.Hashtable#elements()
	 */
	public Enumeration elements() {
		return new Enumeration() {
			private Enumeration cursor = keys.elements();
			
			public boolean hasMoreElements() {
				return cursor.hasMoreElements();
			}

			public Object nextElement() {
				return get(cursor.nextElement());
			}
		};
	}

	
	/* (non-Javadoc)
	 * @see java.util.Hashtable#keys()
	 */
	public Enumeration keys() {
		return keys.elements();
	}

	/* (non-Javadoc)
	 * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		Object prev;
		if (value instanceof Hashtable) {
			// TODO: Implement this constructor
			//return new put(key, SortedHashtable((Hashtable)value));
			
			prev = super.put(key, value);
		} else {
			prev = super.put(key, value);
		}
		if (prev == null) {
			keys.addElement(key);
		}
		return prev;
	}

}
