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
