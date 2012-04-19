/**
 * 
 */
package ca.coolman.auth.oauth1;

import java.util.Hashtable;

/**
 * @author ecoolman
 * 
 */
public class RequestToken implements Token {
	static final String CALLBACK = "oauth_callback";
	static final String CALLBACK_CONFIRMED = CALLBACK + "_confirmed";
	static final String VERIFIER = "oauth_verifier";
	static final String DENIED = "denied";
	private String token;
	private String secret;
	private String callback;
	private boolean callbackVerified;
	private boolean denied;
	private String verifier;

	public RequestToken(String callback) {
		this("","",callback,false);
	}

	/**
	 * @param token
	 * @param secret
	 * @param callback
	 */
	public RequestToken(String token, String secret, String callback,
			boolean callbackVerified) {
		super();
		this.token = token;
		this.secret = secret;
		this.callback = callback;
		this.callbackVerified = callbackVerified;
	}

	public void applyParameters(Hashtable target) {
		if (getVerifier() == null) {
			target.put(CALLBACK, callback);
		} else {
			target.put(TOKEN, getToken());
			target.put(VERIFIER, getVerifier());
		}
	}
	/**
	 * @return the verifier
	 */
	public String getVerifier() {
		return verifier;
	}
	/**
	 * @param verifier the verifier to set
	 */
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.auth.oauth1.Token#getToken()
	 */
	public String getToken() {
		return token;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.auth.oauth1.Token#getSecret()
	 */
	public String getSecret() {
		return secret;
	}
	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}
	/**
	 * @return the callbackVerified
	 */
	public boolean isCallbackVerified() {
		return callbackVerified;
	}

	public void read(Hashtable h) {
		if (h.containsKey(VERIFIER)) {
			// Should prob test equals() here instead.
			token = (String) h.get(TOKEN);
			verifier = (String) h.get(VERIFIER);
		} else if (h.containsKey(DENIED)) {
			denied = true;
		} else {
			token = (String) h.get(TOKEN);
			secret = (String) h.get(TOKEN_SECRET);
			callbackVerified = "true".equals((String) h.get(CALLBACK_CONFIRMED));
		}
	}

	/**
	 * @return the denied
	 */
	public boolean isDenied() {
		return denied;
	}

}
