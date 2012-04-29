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
package ca.coolman.social.linkedin;

import ca.coolman.auth.oauth1.HmacSha1;
import ca.coolman.auth.oauth1.ServiceProvider;

/**
 * @author Eric Coolman
 *
 */
public class LinkedInServiceProvider extends ServiceProvider {
	static final String BASEURL = "https://api.linkedin.com/uas/oauth/";
	public LinkedInServiceProvider() {
		this(null);
	}

	private LinkedInServiceProvider(String id) {
		super((id == null) ? "twitter" : id, BASEURL
				+ "requestToken", BASEURL + "accessToken",
				BASEURL + "authenticate", BASEURL + "authorize", new HmacSha1());
	}
}
