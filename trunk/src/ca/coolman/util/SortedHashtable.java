/**
 * 
 */
package ca.coolman.util;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * At this time, this implementation does not handle nested Hashtables.
 * 
 * @author ecoolman
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
