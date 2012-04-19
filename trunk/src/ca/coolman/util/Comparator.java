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
 * A Comparator interface used for searching and sorting.
 * 
 * @author Eric Coolman
 *
 */
public interface Comparator {
	/**
	 * Compares its two arguments for order. Returns a negative integer, zero,
	 * or a positive integer as the first argument is less than, equal to, or
	 * greater than the second.
	 * 
	 * The implementor must ensure that sgn(compare(x, y)) == -sgn(compare(y,
	 * x)) for all x and y. (This implies that compare(x, y) must throw an
	 * exception if and only if compare(y, x) throws an exception.)
	 * 
	 * The implementor must also ensure that the relation is transitive:
	 * ((compare(x, y)>0) && (compare(y, z)>0)) implies compare(x, z)>0.
	 * 
	 * Finally, the implementer must ensure that compare(x, y)==0 implies that
	 * sgn(compare(x, z))==sgn(compare(y, z)) for all z.
	 * 
	 * It is generally the case, but not strictly required that (compare(x,
	 * y)==0) == (x.equals(y)). Generally speaking, any comparator that violates
	 * this condition should clearly indicate this fact. The recommended
	 * language is
	 * "Note: this comparator imposes orderings that are inconsistent with equals."
	 * 
	 * @param lvalue the first object to be compared.
	 * @param rvalue - the second object to be compared.
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 * @throws ClassCastException - if the arguments' types prevent them from
	 *             being compared by this Comparator.
	 */
	public int compare(Object lvalue, Object rvalue);

}
