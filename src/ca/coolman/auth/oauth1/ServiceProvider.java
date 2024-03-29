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

/**
 * @author Eric Coolman
 * 
 */
public class ServiceProvider {
	private String id;
	private String requestTokenUrl;
	private String accessTokenUrl;
	private String authenticateUrl;
	private String authorizeUrl;
	private SigningImplementation signer;

	/**
	 * @param id
	 * @param requestTokenUrl
	 * @param accessTokenUrl
	 * @param authenticateUrl
	 * @param signer
	 */
	public ServiceProvider(String id, String requestTokenUrl,
			String accessTokenUrl, String authenticateUrl, String authorizeUrl,
			SigningImplementation signer) {
		super();
		this.id = id;
		this.requestTokenUrl = requestTokenUrl;
		this.accessTokenUrl = accessTokenUrl;
		this.authenticateUrl = authenticateUrl;
		this.authorizeUrl = authorizeUrl;
		this.signer = signer;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the requestTokenUrl
	 */
	public String getRequestTokenUrl() {
		return requestTokenUrl;
	}
	/**
	 * @return the accessTokenUrl
	 */
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	
	/**
	 * @return the authenticateUrl
	 */
	public String getAuthenticateUrl(RequestToken requestToken) {
		return authenticateUrl + '?' + RequestToken.TOKEN + '='
				+ requestToken.getToken();
	}
	
	/**
	 * @return the authenticateUrl
	 */
	public String getAuthorizeUrl(RequestToken requestToken) {
		return authorizeUrl + '?' + RequestToken.TOKEN + '='
				+ requestToken.getToken();
	}

	/**
	 * @return the signer
	 */
	public SigningImplementation getSigner() {
		return signer;
	}
}
