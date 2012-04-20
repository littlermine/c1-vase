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

import java.util.Enumeration;
import java.util.Hashtable;

import ca.coolman.auth.oauth1.SignedService;

import com.codename1.io.services.TwitterRESTService;

/**
 * @author Eric Coolman
 * 
 */
public class TwitterService extends TwitterRESTService implements SignedService {
	private boolean rateLimited;
	private boolean authRequired;
	private Hashtable arguments; 
	private String method;
	
	public TwitterService(String method, String version, boolean post,
			boolean authRequired, boolean rateLimited) {
		super(method, version, post);
		this.method = method;
		this.rateLimited = rateLimited;
		this.authRequired = authRequired;
		this.arguments = new Hashtable();
	}

	protected String getMethod() {
		return method;
	}
	
	/**
	 * @return the rateLimited
	 */
	public boolean isRateLimited() {
		return rateLimited;
	}

	/**
	 * @return the authRequired
	 */
	public boolean isAuthRequired() {
		return authRequired;
	}

	/* (non-Javadoc)
	 * @see com.codename1.io.ConnectionRequest#addArgument(java.lang.String, java.lang.String)
	 */
	public void addArgument(String key, String value) {
		this.arguments.put(key, value);
		super.addArgument(key, value);
	}

	public void applyParameters(Hashtable target) {
		for (Enumeration e = arguments.keys(); e.hasMoreElements() ; ) {
			String key = (String)e.nextElement();
			target.put(key, arguments.get(key));
		}
	}
}
