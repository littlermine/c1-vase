/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.util;

import java.util.Vector;

/**
 * An implementation of the classic bubble sort, the most basic
 * of all sorts, with low memory overhead, but certainly not the 
 * fastest in most use cases. 
 * 
 * @author Eric Coolman
 *
 */
public class BubbleSorter extends Sorter {
	
	/* (non-Javadoc)
	 * @see ca.coolman.util.Sorter#sort(java.lang.Object[], ca.coolman.util.Comparator)
	 */
	public void sort(Object[] array, Comparator comparator) {
		_sort(array, comparator);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.util.Sorter#sort(java.util.Vector, ca.coolman.util.Comparator)
	 */
	public void sort(Vector array, Comparator comparator) {
		_sort(array, comparator);
	}
	
	/* (non-Javadoc)
	 * @see ca.coolman.util.Sorter#sort(java.util.Vector, ca.coolman.util.Comparator)
	 */
	public void _sort(Object array, Comparator comparator) {
		boolean unsorted = true;
		int j = getSize(array);
		while (unsorted) {
			unsorted = false;
			j--;
			for (int i = 0; i < j; i++) {
				Object lvalue = getElement(array, i);
				Object rvalue = getElement(array, i+1);
				if (comparator.compare(lvalue, rvalue) < 0) {
					swap(array, i, i+1);
					unsorted = true;
				}
			}
		}
	}
}
