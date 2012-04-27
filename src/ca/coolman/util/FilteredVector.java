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

import java.util.Enumeration;
import java.util.Vector;

/**
 * An filtered vector implementation. Elements from a source vector are filtered
 * by calling the filter() method, and unfiltered by passing null to the filter
 * method.
 * 
 * @author Eric Coolman
 */
public class FilteredVector extends Vector {
	/**
	 * The filter used for filtering the list
	 */
	private Filter filter;
	/**
	 * The source vector to be filtered
	 */
	private Vector source;
	/**
	 * The indexes of each source element that matches the filter
	 */
	private Vector indexes;

	/**
	 * Construct a FilteredVector with a given comparator
	 * 
	 * @param f a comparator for filtering value
	 * @see ComparatorFilter
	 * @see Filter
	 */
	public FilteredVector(Filter f) {
		source = this;
		this.filter = f;
	}

	/**
	 * Construct a sorted vector with a given source vector and element
	 * comparator. The source vector will be sorted on construction.
	 * 
	 * @param src a source vector.
	 * @param f filter implementation for filtering elements on insertion.
	 * @see ComparatorFilter
	 * @see Filter
	 */
	public FilteredVector(Vector src, Filter f) {
		this.source = src;
		this.filter(f);
	}

	/**
	 * Construct a sorted vector with a given source vector and element
	 * comparator. The source vector will be sorted on construction.
	 * 
	 * @param src a source vector.
	 * @param f filter implementation for filtering elements on insertion.
	 * @see ComparatorFilter
	 * @see Filter
	 */
	public FilteredVector(Object src[], Filter f) {
		this(f);
		if (src != null) {
			for (int i = 0; i < src.length; i++) {
				addElement(src[i]);
			}
		}
	}

	/**
	 * Apply a filter against the source vector, or clear the filter by passing
	 * null.
	 * 
	 * When null is passed, this vector will behave the same as if we were
	 * operating against the source vector.
	 * 
	 * @param f filter to be applied against source vector.
	 */
	public void filter(Filter f) {
		indexes = null;
		if (f == null) {
			return;
		}
		this.filter = f;
		Vector tmp = new Vector();
		for (int i = 0; i < source.size(); i++) {
			if (f.matches(source.elementAt(i))) {
				tmp.addElement(new Integer(i));
			}
		}
		this.indexes = tmp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#elementAt(int)
	 */
	public Object elementAt(int index) {
		// return the element within the filtered vector
		if (indexes != null) {
			index = ((Integer) indexes.elementAt(index)).intValue();
			// fall through
		}
		if (source == this) {
			return super.elementAt(index);
		} else {
			return source.elementAt(index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#size()
	 */
	public int size() {
		if (indexes != null) {
			return indexes.size();
		}
		if (source == this) {
			return super.size();
		} else {
			return source.size();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#addElement(java.lang.Object)
	 */
	public void addElement(Object obj) {
		if (source == this) {
			super.addElement(obj);
		} else {
			source.addElement(obj);
		}
		// push the element to the source vector, and if it passes the
		// filter, also add it to the filter index
		if (indexes != null) {
			if (filter.matches(obj)) {
				int size;
				if (source == this) {
					size = super.size();
				} else {
					size = source.size();
				}
				indexes.addElement(new Integer(size - 1));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#capacity()
	 */
	public int capacity() {
		if (source == this) {
			return super.capacity();
		}
		return source.capacity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#contains(java.lang.Object)
	 */
	public boolean contains(Object elem) {
		// return if contains in filtered vector
		if (indexes != null) {
			return indexOf(elem) != -1;
		}
		if (source == this) {
			return super.contains(elem);
		}
		return source.contains(elem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#copyInto(java.lang.Object[])
	 */
	public void copyInto(Object[] anArray) {
		// only copy the filtered values to the target array
		if (indexes != null) {
			for (int i = 0; i < indexes.size(); i++) {
				int index = ((Integer) indexes.elementAt(i)).intValue();
				if (source == this) {
					anArray[i] = super.elementAt(index);
				} else {
					anArray[i] = source.elementAt(index);
				}
			}
			return;
		}
		if (source == this) {
			super.copyInto(anArray);
		} else {
			source.copyInto(anArray);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#elements()
	 */
	public Enumeration elements() {
		// return an enumeration of only filtered elements
		if (indexes != null) {
			return new Enumeration() {
				int i = 0;
				public boolean hasMoreElements() {
					return i < indexes.size();
				}

				public Object nextElement() {
					int index = ((Integer) indexes.elementAt(i++)).intValue();
					if (source == FilteredVector.this) {
						return FilteredVector.super.elementAt(index);
					}
					return source.elementAt(index);
				}

			};
		}
		if (source == this) {
			return super.elements();
		}
		return source.elements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#ensureCapacity(int)
	 */
	public void ensureCapacity(int minCapacity) {
		if (source == this) {
			super.ensureCapacity(minCapacity);
		} else {
			source.ensureCapacity(minCapacity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#firstElement()
	 */
	public Object firstElement() {
		// return the first filtered element
		if (indexes != null) {
			int index = ((Integer) indexes.firstElement()).intValue();
			if (source == this) {
				return super.elementAt(index);
			}
			return source.elementAt(index);
		}
		if (source == this) {
			return super.firstElement();
		}
		return source.firstElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#indexOf(java.lang.Object)
	 */
	public int indexOf(Object elem) {
		return indexOf(elem, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#indexOf(java.lang.Object, int)
	 */
	public int indexOf(Object elem, int startingAt) {
		// find the element within the filtered values
		if (indexes != null) {
			for (int i = startingAt; i < indexes.size(); i++) {
				int index = ((Integer) indexes.elementAt(i)).intValue();
				Object rvalue;
				if (source == this) {
					rvalue = super.elementAt(index);
				} else {
					rvalue = source.elementAt(index);
				}
				if (elem.equals(rvalue)) {
					return i;
				}
			}
			return -1;
		}
		if (source == this) {
			return super.indexOf(elem, startingAt);
		}
		return source.indexOf(elem, startingAt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#insertElementAt(java.lang.Object, int)
	 */
	public void insertElementAt(Object obj, int index) {
		if (source == this) {
			super.insertElementAt(obj, index);
		} else {
			source.insertElementAt(obj, index);
		}
		// push the element to the source, and if it matches the filter,
		// then add it to the filter index too.
		if (indexes != null) {
			if (filter.matches(obj)) {
				int size;
				if (source == this) {
					size = super.size();
				} else {
					size = source.size();
				}
				indexes.addElement(new Integer(size - 1));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#isEmpty()
	 */
	public boolean isEmpty() {
		if (indexes != null) {
			return indexes.isEmpty();
		}
		if (source == this) {
			return super.isEmpty();
		}
		return source.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#lastElement()
	 */
	public Object lastElement() {
		// return the last filtered element
		if (indexes != null) {
			if (indexes.isEmpty()) {
				return null;
			}
			int index = ((Integer) indexes.lastElement()).intValue();
			if (source == this) {
				return super.elementAt(index);
			}
			return source.elementAt(index);
		}
		if (source == this) {
			return super.lastElement();
		}
		return source.lastElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object elem) {
		return this.lastIndexOf(elem, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#lastIndexOf(java.lang.Object, int)
	 */
	public int lastIndexOf(Object elem, int startingAt) {
		// return the last occurrance within the filtered vector
		if (indexes != null) {
			int last = startingAt - 1;
			int index;
			while ((index = indexOf(elem, last + 1)) != -1) {
				last = index;
			}
			if (last < startingAt) {
				return -1;
			}
			return last;
		}
		if (source == this) {
			return super.lastIndexOf(elem, startingAt);
		}
		return source.lastIndexOf(elem, startingAt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#removeAllElements()
	 */
	public void removeAllElements() {
		// remove only the filtered elements from the source vector
		if (indexes != null) {
			for (Enumeration e = indexes.elements(); e.hasMoreElements();) {
				int index = ((Integer) e.nextElement()).intValue();
				Object element = source.elementAt(index);
				source.removeElement(element);
			}
			indexes.removeAllElements();
			return;
		} 
		if (source == this) {
			super.removeAllElements();
		} else {
			source.removeAllElements();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#removeElement(java.lang.Object)
	 */
	public boolean removeElement(Object obj) {
		// remove the object from the source, and if it's within
		// filtered values, remove it from there too.
		if (indexes != null) {
			int index;
			if (source == this) {
				index = super.indexOf(obj);
			} else {
				index = source.indexOf(obj);
			}
			if (index == -1) {
				return false;
			}
			int indexindex = indexes.indexOf(new Integer(index));
			if (indexindex != -1) {
				indexes.removeElementAt(indexindex);
			}
			if (source == this) {
				super.removeElementAt(index);
			} else {
				source.removeElementAt(index);
			}
			return true;
		}
		if (source == this) {
			return super.removeElement(obj);
		}
		return source.removeElement(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#removeElementAt(int)
	 */
	public void removeElementAt(int at) {
		// remove the element from the source vector, indexed by the filtered
		// vector.
		if (indexes != null) {
			int index = ((Integer) indexes.elementAt(at)).intValue();
			indexes.removeElementAt(at);
			at = index;
			// fall through
		}
		if (source == this) {
			super.removeElementAt(at);
		} else {
			source.removeElementAt(at);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#setElementAt(java.lang.Object, int)
	 */
	public void setElementAt(Object obj, int at) {
		// push the new element to the source, indexed by the filter,
		// but if the new value doesn't match the filter, remove it
		// from the filter index.
		if (indexes != null) {
			int index = ((Integer) indexes.elementAt(at)).intValue();
			if (filter.matches(obj) == false) {
				indexes.removeElementAt(index);
			}
			at = index;
			// fall through
		}
		if (source == this) {
			super.setElementAt(obj, at);
		} else {
			source.setElementAt(obj, at);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#setSize(int)
	 */
	public void setSize(int newSize) {
		// not implemented for filter for now.
		if (source == this) {
			super.setSize(newSize);
		} else {
			source.setSize(newSize);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#toString()
	 */
	public String toString() {
		// not implemented for filter for now.
		if (source == this) {
			return super.toString();
		}
		return source.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#trimToSize()
	 */
	public void trimToSize() {
		// not implemented for filter for now.
		if (source == this) {
			super.trimToSize();
		}
		source.trimToSize();
	}
}
