/**
 * 
 */
package ca.coolman.auth.oauth1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import com.codename1.io.Util;

/**
 * @author ecoolman
 * 
 */
public abstract class RequestTokenRequest extends Request {
	private final static String API_METHOD = "request_token";
	private String callback;
	
	/**
	 * 
	 */
	public RequestTokenRequest(Signer signer, String callback) {
		super(signer);
		setPost(true);
		setUrl(BASEURL + API_METHOD);
		this.callback = callback;
		signRequest(new RequestToken(callback));
	}

	protected void readResponse(InputStream input) throws IOException {
		byte b[] = new byte[getContentLength()];
		Util.readAll(input, b);
		Hashtable response = parseQuery(new String(b));
		RequestToken token = new RequestToken(callback);
		token.read(response);
		onAuthenticate(token);
	}

	public abstract void onAuthenticate(RequestToken token);
}
