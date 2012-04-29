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
 * @see http://dev.twitter.com/docs/api#tweets
 * 
 * @author Eric Coolman
 *
 */
public class TweetService extends TwitterService {
	private final static String METHOD_PREFIX = "statuses/";
	public final static String RETWEETED_BY = ":id/retweeted_by";
	public final static String RETWEETED_BY_IDS = ":id/retweeted_by/ids";
	public final static String RETWEETS = "retweets/:id";
	public final static String SHOW = "show/:id";
	public final static String DESTROY = "destroy/:id";
	public final static String RETWEET = "retweet/:id";
	public final static String UPDATE = "update";
	public final static String UPDATE_WITH_MEDIA = "update_with_media";
	public final static String OEMBED = "oembed";
	private final static String GET_METHODS[] = { RETWEETED_BY, RETWEETED_BY_IDS, RETWEETS, SHOW, OEMBED };
	private final static String UNAUTH_METHODS[] = { RETWEETED_BY, SHOW, OEMBED };
	private final static String UNLIMITED_METHODS[] = { DESTROY, UPDATE  };
	private final static String ARG_COUNT = "count";
	private final static String ARG_PAGE = "page";
	private final static String ARG_ID = "id";
	private final static String ARG_TRIM_USER = "trim_user";
	private final static String ARG_STRINGIFY = "stringify_ids ";
	private final static String ARG_INCLUDE_ENTITIES = "include_entities";
	private final static String ARG_INCLUDE_MY_RETWEET = "include_my_retweet";
	private final static String ARG_REPLY_TO = "in_reply_to_status_id";
	private final static String ARG_STATUS = "status";
	private final static String ARG_LATITUDE = "lat";
	private final static String ARG_LONGITUDE = "long";
	private final static String ARG_PLACE_ID = "place_id";
	private final static String ARG_DISPLAY_COORDINATES = "display_coordinates";
	private final static String ARG_POSSIBLY_SENSITIVE = "possibly_sensitive";
	private final static String ARG_URL = "url";
	private final static String ARG_MAXWIDTH = "maxwidth";
	private final static String ARG_HIDE_MEDIA = "hide_media";
	private final static String ARG_HIDE_THREAD = "hide_thread";
	private final static String ARG_OMIT_SCRIPT = "omit_script";
	private final static String ARG_ALIGN = "align";
	private final static String ARG_RELATED = "related";
	private final static String ARG_LANG = "lang";
	
	private final static int MAX_COUNT = 100;
	private final static int MIN_MAXWIDTH = 250;
	private final static int MAX_MAXWIDTH = 550;
	
	public final static String ALIGN_LEFT = "left";
	public final static String ALIGN_RIGHT = "right";
	public final static String ALIGN_CENTER = "center";
	public final static String ALIGN_NONE = "none";
	
	private String urlPrefix;
	private String urlSuffix;
	
	public TweetService(String method) {
		super(METHOD_PREFIX + method, "1", isPostMethod(method), isAuthMethod(method), isRateLimited(method));
		String url = getUrl();
		int index = url.indexOf(":id");
		if (index != -1) {
			urlPrefix = url.substring(0, index);
			urlSuffix = url.substring(index + 3);
		}
	}

	private final static boolean isPostMethod(String method) {
		for (int i = 0; i < GET_METHODS.length; i++) {
			if (GET_METHODS[i].equals(method)) {
				return false;
			}
		}
		return true;
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

	public boolean isIdRequired() {
		return urlPrefix != null;
	}
	
	public void setUserId(String id) {
		if (isIdRequired()) {
			setUrl(urlPrefix + id + urlSuffix);
		} else {
			String applicableMethods[] = { OEMBED };
			if (shouldAddArgument(applicableMethods)) {
				addArgument(ARG_ID, id);
			}
		}
	}

	public void setCount(int count) {
		if (count > MAX_COUNT) {
			count = MAX_COUNT;
		}
		String applicableMethods[] = { RETWEETED_BY, RETWEETED_BY_IDS };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_COUNT, Integer.toString(count));
		}
	}

	public void setPage(int page) {
		String applicableMethods[] = { RETWEETED_BY, RETWEETED_BY_IDS };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_PAGE, Integer.toString(page));
		}
	}

	public void setStringifyIds(boolean b) {
		String applicableMethods[] = { RETWEETED_BY_IDS };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_STRINGIFY, (b == true) ? "true" : "false");
		}
	}

	public void setTrimUser(boolean b) {
		String applicableMethods[] = { RETWEETS, SHOW, DESTROY, RETWEET, UPDATE };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_TRIM_USER, (b == true) ? "true" : "false");
		}
	}

	public void setIncludeEntities(boolean b) {
		String applicableMethods[] = { RETWEETS, SHOW, DESTROY, RETWEET, UPDATE };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_INCLUDE_ENTITIES, (b == true) ? "true" : "false");
		}
	}

	public void setIncludeMyRetweet(boolean b) {
		String applicableMethods[] = { SHOW };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_INCLUDE_MY_RETWEET, (b == true) ? "true" : "false");
		}
	}

	public void setDisplayCoordinates(boolean b) {
		String applicableMethods[] = { UPDATE, UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_DISPLAY_COORDINATES, (b == true) ? "true" : "false");
		}
	}

	public void setHideMedia(boolean b) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_HIDE_MEDIA, (b == true) ? "true" : "false");
		}
	}

	public void setHideThread(boolean b) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_HIDE_THREAD, (b == true) ? "true" : "false");
		}
	}

	public void setOmitScript(boolean b) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_OMIT_SCRIPT, (b == true) ? "true" : "false");
		}
	}

	public void setLatitude(double d) {
		String applicableMethods[] = { UPDATE, UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_LATITUDE, Double.toString(d));
		}
	}

	public void setLongitude(double d) {
		String applicableMethods[] = { UPDATE, UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_LONGITUDE, Double.toString(d));
		}
	}

	public void setReplyTo(String status_id) {
		String applicableMethods[] = { UPDATE, UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_REPLY_TO, status_id);
		}
	}

	public void setEmbedUrl(String url) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_URL, url);
		}
	}

	public void setLang(String lang) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_LANG, lang);
		}
	}

	public void setMaxWidth(int w) {
		String applicableMethods[] = { OEMBED };
		if (w < MIN_MAXWIDTH) {
			w = MIN_MAXWIDTH;
		} else if (w > MAX_MAXWIDTH) {
			w = MAX_MAXWIDTH;
		}
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_MAXWIDTH, Integer.toString(w));
		}
	}

	public void setStatus(String status) {
		String applicableMethods[] = { UPDATE, UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_STATUS, status);
		}
	}

	public void setRelated(String related) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_RELATED, related);
		}
	}

	public void setAlign(String align) {
		String applicableMethods[] = { OEMBED };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_ALIGN, align);
		}
	}

	public void setPlaceId(String placeId) {
		String applicableMethods[] = { UPDATE, UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_PLACE_ID, placeId);
		}
	}
	
	public void setMedia(Object[] media) {
		String applicableMethods[] = { UPDATE_WITH_MEDIA };
		// https://dev.twitter.com/docs/api/1/get/help/configuration		
	}

	public void setPossiblySensitive(boolean b) {
		String applicableMethods[] = { UPDATE_WITH_MEDIA };
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_POSSIBLY_SENSITIVE, (b == true) ? "true" : "false");
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
