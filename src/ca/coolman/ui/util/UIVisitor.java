/**
 * This code is contributed to the public domain.  Feel free to
 * use it for any purpose, commercial or otherwise. 
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
