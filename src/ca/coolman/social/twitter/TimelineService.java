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
 * @author Eric Coolman
 * 
 */
public class TimelineService extends TwitterService {
	public final static String HOME_TIMELINE = "home_timeline";
	public final static String MENTIONS = "mentions";
	public final static String RETWEETED_BY_ME = "retweeted_by_me";
	public final static String RETWEETED_TO_ME = "retweeted_to_me";
	public final static String RETWEETS_OF_ME = "retweets_of_me";
	public final static String USER_TIMELINE = "user_timeline";
	public final static String RETWEETED_BY_USER = "retweeted_by_user";
	public final static String RETWEETED_TO_USER = "retweeted_to_user";

	private final static String METHOD_PREFIX = "statuses/";
	private final static String ARG_COUNT = "count";
	private final static String ARG_SINCE_ID = "since_id";
	private final static String ARG_MAX_ID = "max_id";
	private final static String ARG_TRIM_USER = "trim_user";
	private final static String ARG_INCLUDE_RETWEETS = "include_rts";
	private final static String ARG_INCLUDE_ENTITIES = "include_entities";
	private final static String ARG_EXCLUDE_REPLIES = "exclude_replies";
	private final static String ARG_CONTRIBUTOR_DETAILS = "contributor_details";
	private final static String ARG_USER_ID = "user_id";
	private final static String ARG_SCREEN_NAME = "screen_name";
	private final static int MAX_COUNT = 200;

	public TimelineService(String method) {
		super(METHOD_PREFIX + method, "1", false, true, true);
	}

	public void setUserId(int id) {
		String applicableMethods[] = {USER_TIMELINE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_USER_ID, Integer.toString(id));
		}
	}

	public void setScreenName(String name) {
		String applicableMethods[] = {USER_TIMELINE, RETWEETED_TO_USER,
				RETWEETED_BY_USER};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_SCREEN_NAME, name);
		}
	}

	public void setId(String id) {
		// Twitter documentation:
		// The ID or screen name of the user for whom to return results for.
		// Note this isn't a query string parameter but a change to the end of
		// the URL.
		// Example Values: 12345, noradio

		String applicableMethods[] = {RETWEETED_TO_USER, RETWEETED_BY_USER};
		if (shouldAddArgument(applicableMethods)) {
			// addArgument(ARG_SCREEN_NAME, name);
		}
	}

	public void setCount(int count) {
		if (count > MAX_COUNT) {
			count = MAX_COUNT;
		}
		addArgument(ARG_COUNT, Integer.toString(count));
	}

	public void setSinceId(int id) {
		addArgument(ARG_SINCE_ID, Integer.toString(id));
	}

	public void setMaxId(int id) {
		addArgument(ARG_MAX_ID, Integer.toString(id));
	}

	public void setTrimUser(boolean b) {
		addArgument(ARG_TRIM_USER, b ? "true" : "false");
	}

	public void setIncludeRetweets(boolean b) {
		String applicableMethods[] = {HOME_TIMELINE, MENTIONS, USER_TIMELINE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_INCLUDE_RETWEETS, b ? "true" : "false");
		}
	}

	public void setIncludeEntities(boolean b) {
		addArgument(ARG_INCLUDE_ENTITIES, b ? "true" : "false");
	}

	public void setExcludeReplies(boolean b) {
		String applicableMethods[] = {HOME_TIMELINE, USER_TIMELINE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_EXCLUDE_REPLIES, b ? "true" : "false");
		}
	}

	public void setIncludeContributorDetails(boolean b) {
		String applicableMethods[] = {HOME_TIMELINE, MENTIONS, USER_TIMELINE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_CONTRIBUTOR_DETAILS, b ? "true" : "false");
		}
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
