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

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import ca.coolman.util.Comparator;
import ca.coolman.util.StringComparator;

import com.codename1.processing.Result;
import com.codename1.ui.events.ActionEvent;

/**
 * 
 * For more information on how the paging works, see:
 * 
 * http://dev.twitter.com/docs/working-with-timelines
 * http://dev.twitter.com/docs/api#timelines
 * 
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

	private String since_id;
	private String max_id;

	public TimelineService(String method) {
		super(METHOD_PREFIX + method, "1", false, true, true);
	}

	public void setUserId(String id) {
		String applicableMethods[] = {USER_TIMELINE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_USER_ID, id);
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

	/**
	 * This method is called by readResponse(), to efficiently handle iterating the
	 * timeline. See: https://dev.twitter.com/docs/working-with-timelines
	 * 
	 * NOTE: Twitter uses 64-bit IDs, so we need to use the id_str property
	 * instead.
	 * 
	 * @param id
	 */
	protected void setSinceId(String id) {
		this.since_id = id;
		addArgument(ARG_SINCE_ID, id);
	}

	/**
	 * This method is called by fetchMore(), to efficiently handle iterating the
	 * timeline. See: https://dev.twitter.com/docs/working-with-timelines
	 * 
	 * NOTE: Twitter uses 64-bit IDs, so we need to use the id_str property
	 * instead.
	 * 
	 * @param id
	 */
	protected void setMaxId(String id) {
		addArgument(ARG_MAX_ID, id);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.codename1.io.services.TwitterRESTService#readResponse(java.io.InputStream
	 * )
	 */
	protected void readResponse(InputStream input) throws IOException {
		Result result = Result.fromContent(input, Result.JSON);
		Vector tweets = result.getAsArray("/root");
		if (tweets.size() > 0) {
			final Comparator cmp = new StringComparator();
			for (Enumeration e = tweets.elements(); e.hasMoreElements(); ) {
				Result tweet = Result.fromContent((Hashtable)e.nextElement());
				String id = tweet.getAsString("id_str");
				if ((since_id == null) || cmp.compare(id, since_id) > 0) {
					// this is currently causing twitter to response with no tweets.
					//setSinceId(id);
				}
				if ((max_id == null) || cmp.compare(id, max_id) < 0) {
					// first glance you'd think max_id/since_id is backwards, but
					// read the
					// twitter page noted at the top of this class.
					setMaxId(id);
				}
				
			}
			fireResponseListener(new ActionEvent(result));
		}
	}
}
