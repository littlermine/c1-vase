/**
 * 
 */
package ca.coolman.auth.oauth1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 * @author ecoolman
 * 
 */
public abstract class AccessTokenRequest extends Request implements ActionListener {
	private final static String API_METHOD = "access_token";

	private RequestToken requestToken;

	/**
	 * @param consumerSecret
	 * @param consumerKey
	 */
	public AccessTokenRequest(Signer signer, RequestToken requestToken) {
		super(signer);
		this.requestToken = requestToken;
		setUrl(BASEURL + API_METHOD);
		setPost(true);
	}

	public void actionPerformed(ActionEvent e) {
		String url = (String)e.getSource();	
		Log.p("actionPerformed URL:" + e.getSource(), Log.DEBUG);
		if (url.startsWith(requestToken.getCallback())) {
			Hashtable params = getUrlParameters(url);
			requestToken.read(params);
			if (requestToken.isDenied()) {
				onDenied(requestToken);
			} else if (requestToken.getVerifier() != null) {
				onVerified(requestToken);
			}
		}
	}

	public void onDenied(RequestToken token) {
		Log.p("Denied", Log.DEBUG);
		// user triggered, so by default do nothing, just continue with limited
		// privileges. Override this to handle otherwise.
	}

	public void onVerified(RequestToken token) {
		Log.p("Verified: " + token.getVerifier(), Log.DEBUG);
		signRequest(token);
		NetworkManager.getInstance().addToQueue(this);
	}

	protected void readResponse(InputStream input) throws IOException {
		byte b[] = new byte[getContentLength()];
		Util.readAll(input, b);
		Hashtable response = parseQuery(new String(b));
		AccessToken token = new AccessToken();
		token.read(response);
		onAccessToken(token);
	}

	public abstract void onAccessToken(AccessToken token);
}
