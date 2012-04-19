/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.ui.util;

import java.io.IOException;

/**
 * An interface for a State container.  Implementations of this
 * interface do the actual saving, persisting, restoring of the
 * component data to a back end.  Implementations can be made
 * for example for, the CodenameOne storage class, a remote
 * webservice, a JMS queue, etc.
 * 
 * @author Eric Coolman
 *
 */
public interface StateContainer {
	/**
	 * Read the state of component for given key as a string from short term persistence.
	 * 
	 * @param key
	 * @return the value stored for given key.
	 * @throws IOException
	 */
	public String readString(String key) throws IOException;

	/**
	 * Read the state of component for given key as an integer from short term persistence.  
	 * 
	 * @param key
	 * @return the value stored for given key.
	 * @throws IOException
	 */
	public int readInt(String key) throws IOException;

	/**
	 * Read the state of component for given key as a boolean from short term persistence. 
	 * 
	 * @param key
	 * @return the value stored for given key.
	 * @throws IOException
	 */
	public boolean readBoolean(String key) throws IOException;

	/**
	 * Read the state of component for given key as a byte array from short term persistence.  
	 * 
	 * @param key
	 * @return the value stored for given key.
	 * @throws IOException
	 */
	public byte[] readBytes(String key) throws IOException;

	/**
	 * Write the state of component for given key as a string to short term persistence.
	 * 
	 * @param key
	 * @throws IOException on error writing.
	 */
	public void write(String key, String value) throws IOException;

	/**
	 * Write the state of component for given key as an integer to short term persistence.
	 * 
	 * @param key
	 * @throws IOException on error writing.
	 */
	public void write(String key, int value) throws IOException;

	/**
	 * Write the state of component for given key as a boolean to short term persistence.
	 * 
	 * @param key
	 * @throws IOException on error writing.
	 */
	public void write(String key, boolean value) throws IOException;

	/**
	 * Write the state of component for given key as a byte array to short term persistence.
	 * 
	 * @param key
	 * @throws IOException on error writing.
	 */
	public void write(String key, byte value[]) throws IOException;
	
	/**
	 * Save the current state to back end persistence implementation.
	 * 
	 * @throws IOException on error writing to persistent storage.
	 */
	public void save() throws IOException;
	
	/**
	 * Restore the state from back end persistence implementation.
	 * 
	 * @throws IOException on error reading from persistent storage.
	 */
	public void load() throws IOException;
}
