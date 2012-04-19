package ca.coolman.util;

import java.util.Vector;

public class QuickSorter extends Sorter {
	/**
	 * Partition the objects from a given array of objects according to a given
	 * range.
	 * 
	 * @param array Array of objects.
	 * @param start Start index of the range.
	 * @param end End index of the range.
	 * @return Start index of the range.
	 */
	private int partition(Object array, int start, int end, Comparator comparator) {
		int i = start - 1;
		int j = end;
		Object v = getElement(array, end);
		for ( ; ;) {
			while (comparator.compare(getElement(array, ++i), v) > 0) {
				//
			}
			while (comparator.compare(v, getElement(array, --j)) > 0 && j > start) {
				//
			}
			if (i >= j) {
				break;
			}
			swap(array, i, j);
		}
		swap(array, i, end);
		return i;
	}

	/**
	 * Sorting an array of objects.
	 * 
	 * @param array Objects to be sorted.
	 * @param start Start index.
	 * @param end End index.
	 */
	private void _sort(Object array, int start, int end, Comparator comparator) {
		if (end <= start) {
			return;
		} else {
			int i = partition(array, start, end, comparator);
			_sort(array, start, i - 1, comparator);
			_sort(array, i + 1, end, comparator);
		}
	}

	public void sort(Object[] array, Comparator comparator) {
		_sort(array, 0, array.length - 1, comparator);
	}

	public void sort(Vector array, Comparator comparator) {
		_sort(array, 0, array.size() - 1, comparator);
	}
}