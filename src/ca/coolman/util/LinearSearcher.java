/**
 * 
 */
package ca.coolman.util;

import java.util.Vector;

/**
 * @author ecoolman
 *
 */
public class LinearSearcher extends Searcher {
	/* (non-Javadoc)
	 * @see ca.coolman.util.Searcher#search(java.lang.Object[], java.lang.Object, ca.coolman.util.Comparator)
	 */
	public int search(Object[] haystack, Object needle, Comparator comparator) {
		return _search(haystack, needle, 0, comparator);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.util.Searcher#search(java.util.Vector, java.lang.Object, ca.coolman.util.Comparator)
	 */
	public int search(Vector haystack, Object needle, Comparator comparator) {
		return _search(haystack, needle, 0, comparator);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.util.Searcher#getInsertionPoint(java.util.Vector, java.lang.Object, ca.coolman.util.Comparator)
	 */
	public int getInsertionPoint(Vector haystack, Object needle,
			Comparator comparator) {
		if (haystack == null) {
			return 0;
		}
		return haystack.size();
	}

	/* (non-Javadoc)
	 * @see ca.coolman.util.Searcher#getInsertionPoint(java.lang.Object[], java.lang.Object, ca.coolman.util.Comparator)
	 */
	public int getInsertionPoint(Object[] haystack, Object needle,
			Comparator comparator) {
		if (haystack == null) {
			return 0;
		}
		return haystack.length;
	}
	
	/**
	 * Internal method - do not use.  Called by all other search methods. 
	 * 
	 * @param array
	 * @param searchFor
	 * @param comparator
	 * @param insertion
	 * @return
	 */
	private int _search(Object haystack, Object needle, int startAt, Comparator comparator) {
		for (int i = 0; i < _getSize(haystack); i++) {
			if (comparator.compare(_getElement(haystack, i), needle) == 0) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
	/**
	 * Internal method - do not use.  Called by _search() to determine the size of the haystack. 
	 * 
	 * @param array
	 * @param searchFor
	 * @param comparator
	 * @param insertion
	 * @return
	 */
	private int _getSize(Object src) {
		if (src instanceof Vector) {
			return ((Vector)src).size();
		} else if (src instanceof Object[]) {
			return ((Object[])src).length;
		}
		return 0;
	}
	
	/**
	 * Internal method - do not use.  Called by _search() to retrieve an element from the haystack. 
	 * 
	 * @param array
	 * @param searchFor
	 * @param comparator
	 * @param insertion
	 * @return
	 */
	private Object _getElement(Object src, int index) {
		if (src instanceof Vector) {
			return ((Vector)src).elementAt(index);
		} else if (src instanceof Object[]) {
			return ((Object[])src)[index];
		} else if (src instanceof int[]) {
			return new Integer(((int[])src)[index]);
		} else if (src instanceof float[]) {
			return new Float(((float[])src)[index]);
		} else if (src instanceof long[]) {
			return new Long(((long[])src)[index]);
		} else if (src instanceof short[]) {
			return new Short(((short[])src)[index]);
		}
		return null;
	}
}
