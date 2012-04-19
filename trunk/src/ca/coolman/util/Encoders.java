/**
 * 
 */
package ca.coolman.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64Encoder;

import com.codename1.io.Log;
import com.codename1.io.Util;

/**
 * @author ecoolman
 * 
 */
public class Encoders {
	/**
	 * Encode buffer as a base-64 string. See
	 * http://en.wikipedia.org/wiki/Base64
	 * 
	 * @param buffer
	 * @return
	 */
	public static String encodeBase64(byte buffer[]) {
		return encodeBase64(buffer, buffer.length);
	}

	/**
	 * Encode buffer as a base-64 string. See
	 * http://en.wikipedia.org/wiki/Base64
	 * 
	 * @param buffer
	 * @param length
	 * @return
	 */
	public static String encodeBase64(byte buffer[], int length) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Base64Encoder encoder = new Base64Encoder();
		try {
			encoder.encode(buffer, 0, length, os);
		} catch (IOException e) {
			Log.e(e);
			return null;
		}
		return os.toString();
	}

	/**
	 * Encode string as byte array of UTF-8 characters.
	 * 
	 * @param string
	 * @return
	 */
	public static byte[] encodeUTF8(String string) {
		try {
			return string.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(e);
			return string.getBytes();
		}
	}

	/**
	 * Convert a string to a percent encoded string as defined in RFC 3986. See
	 * http://www.ietf.org/rfc/rfc3986.txt
	 * 
	 * @param src
	 * @return
	 */
	public static String encodeRFC3986(String src) {
		return Util.encodeUrl(src);
	}
}
