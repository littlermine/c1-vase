/**
 * 
 */
package ca.coolman.auth.oauth1;

import ca.coolman.components.ObservableWebBrowser;

import com.codename1.components.WebBrowser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.Util;

/**
 * @author ecoolman
 * 
 */
public class OAuth1 {
	private final static String URL_AUTHENTICATE = Request.BASEURL
			+ "authenticate";

	private static SigningImplementation _signerImplementation = null;
	private Signer signer;
	private String callback;

	public OAuth1(String consumerKey, String consumerSecret, String callback) {
		if (_signerImplementation == null) {
			throw new IllegalStateException(
					"Signer implementation has not been initialized!");
		}
		this.signer = new Signer(_signerImplementation, consumerKey,
				consumerSecret);
		this.callback = callback;
	}

	public static void initialize(SigningImplementation impl) {
		_signerImplementation = impl;
		Util.register(AccessToken.OBJECT_ID, AccessToken.class);
	}

	public void authenticate() {
		if (onLoadAccessToken() == true) {
			return;
		}
		RequestTokenRequest rtr = new RequestTokenRequest(signer, callback) {
			public void onAuthenticate(RequestToken requestToken) {
				final AccessTokenRequest atr = new AccessTokenRequest(signer,
						requestToken) {
					public void onAccessToken(AccessToken accessToken) {
						onReceiveAccessToken(accessToken);
					}
				};

				final ObservableWebBrowser wb = new ObservableWebBrowser();
				wb.addLoadListener(atr);
				String url = URL_AUTHENTICATE + "?force_login=true&"
						+ RequestToken.TOKEN + '=' + requestToken.getToken();
				wb.setURL(url);
				onDisplayLogin(wb);
			}
		};
		NetworkManager.getInstance().addToQueue(rtr);
	}

	/**
	 * Handle placement of the web browser, ie. in a form or dialog.
	 * 
	 * @param webBrowser
	 */
	public void onDisplayLogin(WebBrowser webBrowser) {
		// todo: default implementation here
	}

	/**
	 * Handle a newly received access token, use this to handle one-time
	 * operations such as persistence, etc.
	 * 
	 * @param token
	 */
	public void onReceiveAccessToken(AccessToken token) {
		Storage.getInstance().writeObject("oauth1-" + AccessToken.OBJECT_ID,
				token);
	}

	/**
	 * Handle a request made to retrieve a previously stored access token. On
	 * successful retrieval, implementors of this method should chain to
	 * onAccessToken() to elevate privileges.
	 * 
	 * @param token
	 */
	public boolean onLoadAccessToken() {
		AccessToken token = (AccessToken) Storage.getInstance().readObject(
				"oauth1-" + AccessToken.OBJECT_ID);
		if (token != null) {
			onAccessToken(token);
			return true;
		}
		return false;
	}

	/**
	 * Handle elevating access privileges.
	 * 
	 * @param token
	 */
	public void onAccessToken(AccessToken token) {
		System.out
				.println("User fully authenticated: " + token.getScreenName());
	}

	/**
	 * User denied, continue with limited access.
	 */
	public void onDeniedAccess() {
	}
}
