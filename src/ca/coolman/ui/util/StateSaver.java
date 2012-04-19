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
