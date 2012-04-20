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
public class HomeTimeline extends TwitterService {
	private final static String API_METHOD = "statuses/home_timeline"; 
	private final static String ARG_COUNT = "count";
	private final static String ARG_SINCE_ID = "since_id";
	private final static String ARG_MAX_ID = "max_id";
	private final static String ARG_TRIM_USER = "trim_user";
	private final static String ARG_INCLUDE_RETWEETS = "include_rts";
	private final static String ARG_INCLUDE_ENTITIES = "include_entities";
	private final static String ARG_EXCLUDE_REPLIES = "exclude_replies";
	private final static String ARG_CONTRIBUTOR_DETAILS = "contributor_details";
	private final static int MAX_COUNT=200;
	
	public HomeTimeline() {
		super(API_METHOD, "1", false, true, true);
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
		addArgument(ARG_INCLUDE_RETWEETS, b ? "true" : "false");
	}

	public void setIncludeEntities(boolean b) {
		addArgument(ARG_INCLUDE_ENTITIES, b ? "true" : "false");
	}

	public void setExcludeReplies(boolean b) {
		addArgument(ARG_EXCLUDE_REPLIES, b ? "true" : "false");
	}

	public void setIncludeContributorDetails(boolean b) {
		addArgument(ARG_CONTRIBUTOR_DETAILS, b ? "true" : "false");
	}
}
