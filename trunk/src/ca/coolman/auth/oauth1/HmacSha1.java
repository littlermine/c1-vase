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

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * 
 * @author Eric Coolman
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
