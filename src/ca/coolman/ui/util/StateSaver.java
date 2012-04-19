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
 * An implementation of a visitor for saving the state of a form.
 * 
 * @author Eric Coolman
 *
 */
public class StateSaver extends AbstractUIVisitor {

	/**
	 * Construct a StateRestorer for a given container.
	 * 
	 * @param root
	 */
	public StateSaver(Container root) {
		super(root);
	}
	
	/**
	 * Save the state of the container, optionally to back end storage.
	 * 
	 * @param fromStorage true to save to long-term storage, otherwise from short term storage.
	 */
	public void save(boolean toStorage) {
		visit(getRoot());
		if (toStorage) {
			try {
				getState().save();
			} catch (IOException e) {
				Log.e(e);
			}
		}
	}
	
	/**
	 * Save the state of a checkbox.
	 */
	public void visit(CheckBox cb) {
		try {
			getState().write(cb.getName(), cb.isSelected());
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Save the state of a RadioButton.
	 */
	public void visit(RadioButton b) {
		try {
			getState().write(b.getName(), b.isSelected());
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Save the state of a TextField.
	 */
	public void visit(TextField tf) {
		try {
			getState().write(tf.getName(), tf.getText());
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Save the state of a TextArea.
	 */
	public void visit(TextArea ta) {
		try {
			getState().write(ta.getName(), ta.getText());
		} catch (IOException e) {
			error(null, e);
		}
	}

	/**
	 * Save the state of a ComboBox.
	 */
	public void visit(ComboBox cb) {
		try {
			getState().write(cb.getName(), cb.getSelectedIndex());
		} catch (IOException e) {
			error(null, e);
		}
	}
}
