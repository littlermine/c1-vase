/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
 */
package ca.coolman.ui.util;

import java.io.IOException;

import com.codename1.io.Log;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;

/**
 * An implementation of a visitor for restoring the state of a form.
 * 
 * @author Eric Coolman
 *
 */
public class StateRestorer extends AbstractUIVisitor {

	/**
	 * Construct a StateRestorer for a given container.
	 * 
	 * @param root
	 */
	public StateRestorer(Container root) {
		super(root);
	}

	/**
	 * Restore the state of the container, optionally from back end storage.
	 * 
	 * @param fromStorage true to restore from long-term storage, otherwise from short term storage.
	 */
	public void restore(boolean fromStorage) {
		if (fromStorage == true) {
			try {
				getState().load();
			} catch (IOException ioe) {
				Log.e(ioe);
			}
		}
		visit(getRoot());
	}
	
	/**
	 * Restore the state of a checkbox.
	 */
	public void visit(CheckBox cb) {
		boolean selected;
		try {
			selected = getState().readBoolean(cb.getName());
			cb.setSelected(selected);
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Restore the state of a radio button.
	 */
	public void visit(RadioButton b) {
		try {
			boolean selected = getState().readBoolean(b.getName());
			b.setSelected(selected);
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Restore the state of a combobox.
	 */
	public void visit(ComboBox cb) {
		try {
			int selected = getState().readInt(cb.getName());
			cb.setSelectedIndex(selected);
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Restore the state of a textfield.
	 */
	public void visit(TextField tf) {
		try {
			String value = (String) getState().readString(tf.getName());
			if (value != null) {
				tf.setText(value);
			}
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Restore the state of a textarea.
	 */
	public void visit(TextArea ta) {
		try {
			String value = (String) getState().readString(ta.getName());
			if (value != null) {
				ta.setText(value);
				ta.repaint();
			}
		} catch (IOException e) {
			error(null, e);
		}
	}

}
