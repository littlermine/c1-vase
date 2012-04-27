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
	 * @param src plaintext string
	 * @return encoded string
	 */
	public static String encodeRFC3986(String src) {
		return Util.encodeUrl(src);
	}

	/**
	 * Convert a string from a percent encoded string as defined in RFC 3986. See
	 * http://www.ietf.org/rfc/rfc3986.txt
	 * 
	 * @param src encoded string
	 * @return decoded string
	 */
	public static String decodeRFC3986(String src) {
	// return Util.decode(src, null, true);
		return null;
	}
}
