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

import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;

/**
 * The interface of the visitor.
 * 
 * @author Eric Coolman
 */
public interface UIVisitor {
	/**
	 * Visit a CheckBox component.
	 * 
	 * @param cb CheckBox instance
	 */
	public void visit(CheckBox cb);
	
	/**
	 * Visit a RadioButton component.
	 * 
	 * @param b RadioButton instance
	 */
	public void visit(RadioButton b);
	
	/**
	 * Visit a TextField component.
	 * 
	 * @param ctf TextField instance.
	 */
	public void visit(TextField tf);

	/**
	 * Visit a TextArea component.
	 * 
	 * @param ta TextArea instance
	 */
	public void visit(TextArea ta);

	/**
	 * Visit a ComboBox component.
	 * 
	 * @param cb ComboBox instance
	 */
	public void visit(ComboBox cb);

	/**
	 * Visit a Container component.  This is called for handling all containers, including Form,
	 * ContainerGroup, EmbeddedContainer, etc. It should do a deep visit of the container and call
	 * the appropriate visit method for each element found.
	 * 
	 * @param c Container
	 */
	public void visit(Container c);
}
