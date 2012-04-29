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
package ca.coolman.social.twitter;

import ca.coolman.auth.oauth1.HmacSha1;
import ca.coolman.auth.oauth1.RequestToken;
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
				protocol + BASEURL + "authenticate", protocol + BASEURL + "authenticate", new HmacSha1());
	}

	/* (non-Javadoc)
	 * @see ca.coolman.auth.oauth1.ServiceProvider#getAuthenticateUrl(ca.coolman.auth.oauth1.RequestToken)
	 */
	public String getAuthenticateUrl(RequestToken requestToken) {
		return super.getAuthenticateUrl(requestToken) + "&force_login=true";
	}
}
