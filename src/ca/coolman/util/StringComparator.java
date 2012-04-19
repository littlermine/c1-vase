/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.util;

/**
 * @author ecoolman
 * 
 */
public class StringComparator implements Comparator {
	/**
	 * Perform an exact match comparison.
	 */
	public final static int EXACT = 0;
	/**
	 * Perform a contains comparison (lvalue contains rvalue).
	 */
	public final static int CONTAINS = 1;
	/**
	 * Perform a starts-with comparison (lvalue contains rvalue).
	 */
	public final static int STARTS_WITH = 2;
	/**
	 * Perform a case-insensitive comparison.
	 */
	public final static int CASE_SENSITIVE = 4;

	/**
	 * Bitfield of comparison preferences.
	 */
	private int style;

	/**
	 * Construct a string comparator with the default style (EXACT match).
	 * 
	 * @see #EXACT
	 */
	public StringComparator() {
		this(EXACT);
	}

	/**
	 * Construct a string comparator with the requested style
	 * 
	 * @param style a bitfield containing preferences for the comparison.
	 * @see #EXACT
	 * @see #CONTAINS
	 * @see #CASE_SENSITIVE
	 */
	public StringComparator(int style) {
		this.style = style;
	}

	/**
	 * Determine if comparison should be exact match.
	 * 
	 * @return true for exact match preference.
	 */
	protected boolean isExactMatch() {
		return (isContains() == false);
	}

	/**
	 * Determine if comparison should be where lvalue contains rvalue. 
	 * 
	 * @return true for a contains search.
	 */
	protected boolean isContains() {
		return (style & CONTAINS) == CONTAINS;
	}

	/**
	 * Determine if comparison should be where lvalue contains rvalue. 
	 * 
	 * @return true for a contains search.
	 */
	protected boolean isStartsWith() {
		return (style & STARTS_WITH) == STARTS_WITH;
	}

	/**
	 * Determine if comparison should be case sensitive. 
	 * 
	 * @return true if comparison should be case sensitive.
	 */
	protected boolean isCaseSensitive() {
		return (style & CASE_SENSITIVE) == CASE_SENSITIVE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.util.Comparator#compare(java.lang.Object,
	 * java.lang.Object)
	 */
	public int compare(Object lvalue, Object rvalue) {
		if ((lvalue == null) || (rvalue == null)) {
			if (lvalue == rvalue) {
				return 0;
			}
			if (lvalue == null) {
				return -1;
			}
			return 1;
		}
		if ((lvalue instanceof String) == false) {
			lvalue = lvalue.toString();
		}
		if ((rvalue instanceof String) == false) {
			rvalue = rvalue.toString();
		}
		if (isCaseSensitive() == false) {
			lvalue = ((String) lvalue).toUpperCase();
			rvalue = ((String) rvalue).toUpperCase();
		}
		if (isExactMatch()) {
			return ((String) lvalue).compareTo((String) rvalue);
		} else if (isContains()) {
			return (((String) lvalue).indexOf((String) rvalue) < 0) ? -1 : 0;
		} else if (isStartsWith()) {
			return ((String) lvalue).startsWith((String) rvalue) ? -1 : 0;
		}
		return -1;
	}
}
