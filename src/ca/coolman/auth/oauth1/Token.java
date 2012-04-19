package ca.coolman.auth.oauth1;

import java.util.Hashtable;

interface Token {
	static final String TOKEN = "oauth_token";
	static final String TOKEN_SECRET = TOKEN + "_secret";

	/**
	 * @return the token
	 */
	public abstract String getToken();

	/**
	 * @return the secret
	 */
	public abstract String getSecret();

	/**
	 * Apply token specific parameters to the given target.
	 * 
	 * @param target
	 */
	public abstract void applyParameters(Hashtable target);
	
	/**
	 * Read state from given hashtable.
	 * 
	 * @param h
	 */
	public abstract void read(Hashtable h);
}