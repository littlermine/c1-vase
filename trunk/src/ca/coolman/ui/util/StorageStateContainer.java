/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.ui.util;

import java.io.IOException;
import java.util.Hashtable;

import com.codename1.io.Storage;
import com.codename1.ui.Component;

/**
 * A basic implementation of a StateContainer.  This implementation 
 * uses in-memory for saving state, and the Codename One Storage
 * object for persisting state.  You can use this as a basis
 * for other types of state containers, for example a WebserviceStateContainer.
 * 
 * @author Eric Coolman
 *
 */
public class StorageStateContainer implements StateContainer {
	/**
	 * The pool of states for all components.
	 */
	private static Hashtable _internalState;
	/**
	 * Ths name of the top level component.
	 */
	private String _rootName;

	public StorageStateContainer(Component root) {
		this._rootName = root.getName();
	}

	/**
	 * Internal method - do not use.  Get the pool of all known states.
	 * 
	 * @return
	 */
	private static Hashtable getStatePool() {
		if (_internalState == null) {
			_internalState = new Hashtable();
		}
		return _internalState;
	}

	/**
	 * Get the internal state of the component contained by thie container.
	 * 
	 * @return State of component.
	 */
	public Hashtable getState() {
		if (_rootName == null) {
			return getStatePool();
		}
		if (getStatePool().containsKey(_rootName) == false) {
			Hashtable formState = new Hashtable();
			getStatePool().put(_rootName, formState);
		}
		return (Hashtable) getStatePool().get(_rootName);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#readString(java.lang.String)
	 */
	public String readString(String key) throws IOException {
		return (String) getState().get(key);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#readInt(java.lang.String)
	 */
	public int readInt(String key) throws IOException {
		Object o = getState().get(key);
		if (o == null || (o instanceof Integer) == false) {
			return 0;
		}
		return ((Integer) o).intValue();
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#readBoolean(java.lang.String)
	 */
	public boolean readBoolean(String key) throws IOException {
		Object o = getState().get(key);
		if (o == null || (o instanceof Boolean) == false) {
			return false;
		}
		return ((Boolean) o).booleanValue();
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#readBytes(java.lang.String)
	 */
	public byte[] readBytes(String key) throws IOException {
		Object o = getState().get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof byte[]) {
			return (byte[]) o;
		}
		return o.toString().getBytes();
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#write(java.lang.String, java.lang.String)
	 */
	public void write(String key, String value) throws IOException {
		getState().put(key, value);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#write(java.lang.String, int)
	 */
	public void write(String key, int value) throws IOException {
		getState().put(key, new Integer(value));
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#write(java.lang.String, boolean)
	 */
	public void write(String key, boolean value) throws IOException {
		getState().put(key, new Boolean(value));
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#write(java.lang.String, byte[])
	 */
	public void write(String key, byte[] value) throws IOException {
		getState().put(key, value);
	}

	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#load()
	 */
	public void load() throws IOException {
        Hashtable h = (Hashtable)Storage.getInstance().readObject(_rootName);
        if (h == null) {
        	return;
        }
       	getStatePool().put(_rootName, h);
	}
	
	/* (non-Javadoc)
	 * @see ca.coolman.c1.ui.StateContainer#save()
	 */
	public void save() throws IOException {
        Storage.getInstance().writeObject(_rootName, getState());
	}
}
