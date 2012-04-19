/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
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
