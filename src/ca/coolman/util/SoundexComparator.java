/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.util;

/**
 * An abstract for Soundex comparator implementations.
 * 
 * @author Eric Coolman
 *
 */
public abstract class SoundexComparator extends StringComparator {

	SoundexComparator() {
		super(EXACT);
	}

	/**
	 * Encode a source string to a soundex encoded string
	 * 
	 * @param text source string
	 * @return soundex encoded string.
	 */
	protected abstract String getSoundex(Object text);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.util.Comparator#compare(java.lang.Object,
	 * java.lang.Object)
	 */
	public int compare(Object lvalue, Object rvalue) {
		return super.compare(getSoundex(lvalue), getSoundex(rvalue));
	}
}