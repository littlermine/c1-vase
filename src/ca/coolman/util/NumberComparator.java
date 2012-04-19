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
 * A Comparator implementation for comparing numbers.  Supports 
 * integers, longs, shorts, floats. 
 * 
 * @author Eric Coolman
 *
 */
public class NumberComparator implements Comparator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.util.Comparator#compare(java.lang.Object,
	 * java.lang.Object)
	 */
	public int compare(Object lvalue, Object rvalue) {
		if (lvalue != null && lvalue.equals(rvalue)) {
			return 0;
		}
		if (lvalue instanceof Integer) {
			return compare((Integer)lvalue, (Integer)rvalue);
		} else if (lvalue instanceof Long) {
			return compare((Long)lvalue, (Long)rvalue);
		} else if (lvalue instanceof Float) {
			return compare((Float)lvalue, (Float)rvalue);
		} else if (lvalue instanceof Short) {
			return compare((Short)lvalue, (Short)rvalue);
		} else if (lvalue == null) {
			if (rvalue == null) {
				return 0;
			}
			return -1;
		}
		throw new ClassCastException("Could not compare values: " + lvalue.getClass().getName());
	}

	public int compare(Integer lvalue, Integer rvalue) {
		if (lvalue.equals(rvalue)) {
			return 0;
		}
		return lvalue.intValue() - rvalue.intValue();
	}

	public int compare(Long lvalue, Long rvalue) {
		if (lvalue.equals(rvalue)) {
			return 0;
		}
		return ((lvalue.longValue() - rvalue.longValue()) < 0L) ? -1 : 1;
	}

	public int compare(Float lvalue, Float rvalue) {
		if (lvalue.equals(rvalue)) {
			return 0;
		}
		return ((lvalue.floatValue() - rvalue.floatValue()) < 0f) ? -1 : 1;
	}
}
