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
 * @author Eric Coolman
 *
 */
public class ComparatorFilter implements Filter {
	/**
	 * Subject to filter against.
	 */
	private Object lvalue;
	
	/**
	 * Comparator to use for filtering
	 */
	private Comparator comparator;
	
	/**
	 * Construct a string comparator with the default style (EXACT match).
	 * 
	 * @see #EXACT
	 */
	public ComparatorFilter(Comparator c, Object lvalue) {
		this.comparator = c;
		this.lvalue = lvalue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.util.Filter#matches(java.lang.Object)
	 */
	public boolean matches(Object subject) {
		if (subject == null) {
			return lvalue == null;
		}
		return comparator.compare(lvalue, subject) == 0;
	}
}
