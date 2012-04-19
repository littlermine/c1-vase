/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
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
