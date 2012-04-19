/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.ui.util;

import com.codename1.io.Log;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.util.EmbeddedContainer;

/**
 * An abstract for UI visitors to implement.  Implementations can be made to do other things against
 * components besides saving state. 
 * 
 * @author Eric Coolman
 *
 */
public abstract class AbstractUIVisitor implements UIVisitor {
	private Container root;
	private StateContainer state;
	
	/**
	 * Construct a visitor for a given container.
	 * 
	 * @param root
	 */
	public AbstractUIVisitor(Container root) {
		this.root = root;
		setState(new StorageStateContainer(root));
	}
	
	/**
	 * On error, log a message and/or stacktrace.
	 * 
	 * @param message
	 * @param t
	 */
	protected void error(String message, Throwable t) {
		if (message != null) {
			Log.p(message, Log.ERROR);
		}
		if (t != null) {
			Log.e(t);
		}
	}

	/**
	 * Set the state of the component.
	 * 
	 * @param state
	 */
	protected void setState(StateContainer state) {
		this.state = state;
	}

	/**
	 * Get the state of the component.
	 * 
	 * @param state
	 */
	protected StateContainer getState() {
		return this.state;
	}
	
	/**
	 * Get the top level container.
	 * 
	 * @param state
	 */
	public Container getRoot() {
		return root;
	}
	
	public abstract void visit(CheckBox cb);
	
	public abstract void visit(RadioButton b);
	
	public abstract void visit(TextField tf);

	public abstract void visit(TextArea tb);

	public abstract void visit(ComboBox tb);
	
	public void visit(Container c) {
		int count = c.getComponentCount();
		for (int i = 0; i < count; i++) {
			Component component = c.getComponentAt(i);
			Log.p(getClass().getName() + " visit[" + i + "] \""+ component.getName() 
					+ "\" (" + component.getClass().getName() + ")", Log.DEBUG);
			if (component instanceof CheckBox) {
				visit((CheckBox)component);
			} else if (component instanceof ComboBox) {
				visit((ComboBox)component);
			} else if (component instanceof RadioButton) {
				visit((RadioButton)component);
			} else if (component instanceof TextField) {
				visit((TextField)component);
			} else if (component instanceof TextArea) {
				visit((TextArea)component);
			} else if (component instanceof ComponentGroup) {
				visit((Container)component);
			} else if (component instanceof EmbeddedContainer) {
				visit((Container)component);
			} else if (component instanceof Container) {
				visit((Container)component);
			}
		}
	}

	public String getObjectId() {
		return getRoot().getName();
	}
}
