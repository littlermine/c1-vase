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
package ca.coolman.auth.oauth1;

import java.util.Hashtable;

import ca.coolman.components.ObservableWebBrowser;

import com.codename1.components.WebBrowser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.processing.Result;

/**
 * The OAuth1 class implements the OAuth flow, as documented here, for example:
 * 
 * <pre>
 * https://dev.twitter.com/docs/auth/implementing-sign-twitter
 * </pre>
 * 
 * @author Eric Coolman
 * 
 */
public class OAuth1 {
	private final static String URL_AUTHENTICATE = Request.BASEURL
			+ "authenticate";

	private static Hashtable credentials;
	private static SigningImplementation _signerImplementation = null;
	private Signer signer;
	private String callback;

	public OAuth1(String callback) {
		if (_signerImplementation == null) {
			throw new IllegalStateException(
					"Signer implementation has not been initialized!");
		}
		if (credentials == null) {
			throw new IllegalStateException(
				"Consumer credentials has not been initialized!");
		}
		Result result = Result.fromContent(credentials);
		String appId = callback.replace('/','_');
		String consumerKey = result.getAsString('/' + appId + "/key");
		String consumerSecret = result.getAsString('/' + appId + "/secret");
		if ((consumerKey == null) || (consumerSecret == null)) {
			throw new IllegalStateException(
					"Consumer credentials has not been initialized for callback!");
		}
		this.signer = new Signer(_signerImplementation, consumerKey,
				consumerSecret);
		this.callback = callback;
	}

	public void signRequest(ConnectionRequest request, AccessToken token) {
		signer.sign(request, token);
	}

	public static void register(String callback, final String consumerKey,
			final String consumerSecret) {
		if (credentials == null) {
			credentials = new Hashtable();
		}
		Hashtable credential = new Hashtable();
		credential.put("key", consumerKey);
		credential.put("secret", consumerSecret);
		credentials.put(callback.replace('/', '_'), credential);
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
