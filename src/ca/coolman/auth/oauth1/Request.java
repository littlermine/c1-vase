/**
 * 
 */
package ca.coolman.auth.oauth1;

import java.util.Hashtable;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.Util;

/**
 * @author ecoolman
 * 
 */
class Request extends ConnectionRequest {
	static final String BASEURL = "http://api.twitter.com/oauth/";

	private Signer signer;
	
	/**
	 * 
	 */
	public Request(Signer signer) {
		this.signer = signer;
	}



	protected void signRequest(Token token) {
		signer.sign(this, token);
	}

	protected Hashtable getUrlParameters(String url) {
		String query = Util.getURLPath(url);
		int index = query.indexOf("?");
		if (index == -1) {
			return null;
		}
		query = query.substring(index + 1);
		return parseQuery(query);
	}

	protected Hashtable parseQuery(String query) {
		return parseQuery(query, "&");
	}
	
	protected Hashtable parseQuery(String query, String delimiter) {
		String elements[] = Util.split(query, delimiter);
		Hashtable response = new Hashtable();
		for (int i = 0; i < elements.length; i++) {
			String namevalue[] = Util.split(elements[i], "=");
			if (namevalue.length == 2) {
				response.put(namevalue[0], namevalue[1]);
			} else if (namevalue.length == 1) {
				response.put(namevalue[0], "");
			} else {
				Log.p("Error parsing query " + i + ":" + elements[i]);
			}
		}
		return response;
	}

}
