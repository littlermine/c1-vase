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

/**
 * @see https://dev.twitter.com/docs/api#friends-followers
 * 
 * @author Eric Coolman
 *
 */
public class FriendshipService extends TwitterService {
	public final static String FOLLOWERS = "followers/ids";
	public final static String FRIENDS = "friends/ids";
	public final static String EXISTS = "exists";
	public final static String INCOMING = "incoming";
	public final static String OUTGOING = "outgoing";
	public final static String SHOW = "show";
	public final static String CREATE = "create";
	public final static String DESTROY = "destroy";
	public final static String LOOKUP = "lookup";
	public final static String UPDATE = "update";
	public final static String NO_RETWEET_IDS = "no_retweet_ids";

	private final static String METHOD_PREFIX = "friendships/";
	private final static String POST_METHODS[] = {};
	private final static String UNLIMITED_METHODS[] = {};
	private final static String UNAUTH_METHODS[] = {};
	
	public FriendshipService(String method) {
		super(getMethodPrefix(method) + method, "1", isPostMethod(method), isAuthMethod(method), isRateLimited(method));
	}

	private final static String getMethodPrefix(String method) {
		if (FOLLOWERS.equals(method) || FRIENDS.equals(method)) {
			return "";
		}
		return METHOD_PREFIX; 
	}
	
	private final static boolean isPostMethod(String method) {
		for (int i = 0; i < POST_METHODS.length; i++) {
			if (POST_METHODS[i].equals(method)) {
				return true;
			}
		}
		return false;
	}

	private final static boolean isAuthMethod(String method) {
		for (int i = 0; i < UNAUTH_METHODS.length; i++) {
			if (UNAUTH_METHODS[i].equals(method)) {
				return false;
			}
		}
		return true;
	}

	private final static boolean isRateLimited(String method) {
		for (int i = 0; i < UNLIMITED_METHODS.length; i++) {
			if (UNLIMITED_METHODS[i].equals(method)) {
				return false;
			}
		}
		return true;
	}

	private boolean shouldAddArgument(String applicableMethods[]) {
		final String method = getMethod();
		for (int i = 0; i < applicableMethods.length; i++) {
			if (method.equals(METHOD_PREFIX + applicableMethods[i])) {
				return true;
			}
		}
		return false;
	}
	
}
