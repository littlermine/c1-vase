/**
 * 
 */
package ca.coolman.auth.oauth1;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * @author ecoolman
 * 
 */
public class HmacSha1 extends SigningImplementation {
	public final static String ID = "HMAC-SHA1";
	
	public String getId() {
		return ID;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.auth.oauth1.OAuthSigner#createSignature(java.lang.String,
	 * java.lang.String)
	 */
	protected byte[] createSignature(byte[] key, byte[] data) {
		SHA1Digest digest = new SHA1Digest();
		HMac mac = new HMac(digest);
		mac.init(new KeyParameter(key));
		mac.update(data, 0, data.length);
		byte[] b = new byte[digest.getDigestSize()];
		mac.doFinal(b, 0);
		return b;
	}

}
