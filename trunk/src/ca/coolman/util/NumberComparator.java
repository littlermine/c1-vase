/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
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
