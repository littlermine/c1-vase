/**
 * 
 */
package ca.coolman.auth.oauth1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;

import ca.coolman.util.Encoders;
import ca.coolman.util.SortedHashtable;

/**
 * @author ecoolman
 * 
 */
public class Signer {
	static final String AUTHORIZATION = "Authorization";
	static final String CONSUMER_KEY = "oauth_consumer_key";
	static final String SIGNATURE_METHOD = "oauth_signature_method";
	static final String SIGNATURE = "oauth_signature";
	static final String TIMESTAMP = "oauth_timestamp";
	static final String NONCE = "oauth_nonce";
	static final String VERSION = "oauth_version";
	static final String POST = "POST";
	static final String DELIMITER = "&";
	private String consumerSecret;
	private String consumerKey;
	private SigningImplementation signer;

	public Signer(SigningImplementation signer, String consumerKey,
			String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.signer = signer;
	}

	public void sign(ConnectionRequest request, Token token) {
		Hashtable params = createParameters();
		token.applyParameters(params);
		String base = createSignatureBase(request, params);
		Log.p(base, Log.DEBUG);
		params.put(SIGNATURE, createSignature(base, token));
		request.addRequestHeader(AUTHORIZATION, createHeader(params));
	}

	protected Hashtable createParameters() {
		Hashtable params = new SortedHashtable();
		params.put(TIMESTAMP, String.valueOf(getTimestamp()));
		params.put(SIGNATURE_METHOD, signer.getId());
		params.put(VERSION, "1.0");
		params.put(NONCE, String.valueOf(getNonce()));
		return params;
	}

	protected String createHeader(Hashtable params) {
		return "OAuth " + toQueryString(params, true, "\"", ",");
	}

	protected String createSignatureBase(ConnectionRequest request,
			Hashtable params) {
		params.put(CONSUMER_KEY, consumerKey);
		return POST + DELIMITER + Encoders.encodeRFC3986(request.getUrl())
				+ DELIMITER + Encoders.encodeRFC3986(toQueryString(params));
	}

	protected String createSignature(String baseString, Token token) {
		byte[] key = Encoders.encodeUTF8(consumerSecret + DELIMITER
				+ token.getSecret());
		byte[] data = Encoders.encodeUTF8(baseString);
		byte[] b = signer.createSignature(key, data);
		return Encoders.encodeBase64(b);
	}

	protected String toQueryString(Hashtable params) {
		return toQueryString(params, false, null, DELIMITER);
	}

	protected String toQueryString(Hashtable params, boolean onlyOauth,
			String surroundWith, String delimiter) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		if (surroundWith == null) {
			surroundWith = "";
		}
		if (delimiter == null) {
			delimiter = DELIMITER;
		}
		for (Enumeration e = params.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			ps.print(Encoders.encodeRFC3986(key) + "=" + surroundWith
					+ Encoders.encodeRFC3986((String) params.get(key))
					+ surroundWith);
			if (e.hasMoreElements()) {
				ps.print(delimiter);
			}
		}
		return os.toString();
	}

	protected long getTimestamp() {
		return System.currentTimeMillis() / 1000L;
	}

	protected long getNonce() {
		return System.currentTimeMillis();
	}
}
