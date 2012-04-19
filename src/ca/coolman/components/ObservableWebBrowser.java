/**
 * 
 */
package ca.coolman.components;

import java.util.Enumeration;
import java.util.Vector;

import com.codename1.components.WebBrowser;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 * @author ecoolman
 *
 */
public class ObservableWebBrowser extends WebBrowser {
	private Vector loadListeners = new Vector();
	private Vector errorListeners = new Vector();
	private Vector startListeners = new Vector();
	
	public void addErrorListener(ActionListener l) {
		errorListeners.addElement(l);
	}

	public void removeErrorListener(ActionListener l) {
		errorListeners.removeElement(l);
	}

	public void addLoadListener(ActionListener l) {
		loadListeners.addElement(l);
	}

	public void removeLoadListener(ActionListener l) {
		loadListeners.removeElement(l);
	}

	public void addStartListener(ActionListener l) {
		startListeners.addElement(l);
	}

	public void removeStartListener(ActionListener l) {
		startListeners.removeElement(l);
	}

	/* (non-Javadoc)
	 * @see com.codename1.components.WebBrowser#onError(java.lang.String, int)
	 */
	public void onError(String message, int errorCode) {
		super.onError(message, errorCode);
		for (Enumeration e = errorListeners.elements(); e.hasMoreElements(); ) {
			((ActionListener)e.nextElement()).actionPerformed(new ActionEvent(message, errorCode));
		}
	}

	/* (non-Javadoc)
	 * @see com.codename1.components.WebBrowser#onLoad(java.lang.String)
	 */
	public void onLoad(String url) {
		super.onLoad(url);
		for (Enumeration e = loadListeners.elements(); e.hasMoreElements(); ) {
			((ActionListener)e.nextElement()).actionPerformed(new ActionEvent(url));
		}
	}

	/* (non-Javadoc)
	 * @see com.codename1.components.WebBrowser#onStart(java.lang.String)
	 */
	public void onStart(String url) {
		super.onStart(url);
		for (Enumeration e = startListeners.elements(); e.hasMoreElements(); ) {
			((ActionListener)e.nextElement()).actionPerformed(new ActionEvent(url));
		}
	}

}
