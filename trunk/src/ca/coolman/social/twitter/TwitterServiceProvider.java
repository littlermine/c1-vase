/**
 * 
 */
package ca.coolman.social.twitter;

import ca.coolman.auth.oauth1.HmacSha1;
import ca.coolman.auth.oauth1.ServiceProvider;

/**
 * @author Eric Coolman
 * 
 */
public class TwitterServiceProvider extends ServiceProvider {
	static final String BASEURL = "://api.twitter.com/oauth/";

	public TwitterServiceProvider(String id, boolean debug) {
		this(id, "http");
	}

	public TwitterServiceProvider(String id) {
		this(id, "https");
	}

	public TwitterServiceProvider(boolean debug) {
		this(null, "http");
	}

	public TwitterServiceProvider() {
		this(null, "https");
	}

	private TwitterServiceProvider(String id, String protocol) {
		super((id == null) ? "twitter" : id, protocol + BASEURL
				+ "request_token", protocol + BASEURL + "access_token",
				protocol + BASEURL + "authenticate", new HmacSha1());
	}
}
