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

import com.codename1.processing.Result;
import com.codename1.ui.events.ActionEvent;

/**
 * @see https://dev.twitter.com/docs/api#accounts
 * 
 * @author Eric Coolman
 * 
 */
public class AccountService extends TwitterService {
	/**
	 * @see http://dev.twitter.com/docs/api/1/get/account/rate_limit_status
	 */
	public final static String RATE_LIMIT_STATUS = "rate_limit_status";
	/**
	 * @see http://dev.twitter.com/docs/api/1/get/account/verify_credentials
	 */
	public final static String VERIFY_CREDENTIALS = "verify_credentials";
	/**
	 * @see http://dev.twitter.com/docs/api/1/post/account/end_session
	 */
	public final static String END_SESSION = "end_session";
	/**
	 * @see http://dev.twitter.com/docs/api/1/post/account/update_profile
	 */
	public final static String UPDATE_PROFILE = "update_profile";
	/**
	 * @see http://dev.twitter.com/docs/api/1/post/account/update_profile_background_image
	 */
	public final static String UPDATE_PROFILE_BACKGROUND_IMAGE = "update_profile_background_image";
	/**
	 * @see http://dev.twitter.com/docs/api/1/post/account/update_profile_colors
	 */
	public final static String UPDATE_PROFILE_COLORS = "update_profile_colors";
	/**
	 * @see http://dev.twitter.com/docs/api/1/post/account/update_profile_image
	 */
	public final static String UPDATE_PROFILE_IMAGE = "update_profile_image";
	/**
	 * @see http://dev.twitter.com/docs/api/1/get/account/totals
	 */
	public final static String TOTALS = "totals";
	/**
	 * @see http://dev.twitter.com/docs/api/1/get/account/settings
	 */
	public final static String SETTINGS = "settings";
	private final static String METHOD_PREFIX = "account/";
	private final static String GET_METHODS[] = {RATE_LIMIT_STATUS,
			VERIFY_CREDENTIALS, TOTALS, SETTINGS};
	private final static String UNAUTH_METHODS[] = {RATE_LIMIT_STATUS, SETTINGS};
	private final static String UNLIMITED_METHODS[] = {RATE_LIMIT_STATUS,
			END_SESSION, UPDATE_PROFILE, UPDATE_PROFILE_BACKGROUND_IMAGE,
			UPDATE_PROFILE_COLORS, UPDATE_PROFILE_IMAGE, SETTINGS};
	private final static String ARG_INCLUDE_ENTITIES = "include_entities";
	private final static String ARG_SKIP_STATUS = "skip_status";
	private final static String ARG_URL = "url";
	private final static String ARG_LOCATION = "location";
	private final static String ARG_DESCRIPTION = "description";
	private final static String ARG_TILE = "tile";
	private final static String ARG_USE = "use";
	private final static String ARG_BACKGROUND_COLOR = "profile_background_color";
	private final static String ARG_LINK_COLOR = "profile_link_color";
	private final static String ARG_SIDEBAR_COLOR = "profile_sidebar_border_color";
	private final static String ARG_SIDEBAR_FILL_COLOR = "profile_sidebar_fill_color";
	private final static String ARG_TEXT_COLOR = "profile_text_color";
	private final static String ARG_LANG = "lang";
	private final static String ARG_WOEID="trend_location_woeid optional";
	private final static String ARG_SLEEP_TIME_ENABLED="sleep_time_enabled";
	private final static String ARG_START_SLEEP_TIME="start_sleep_time";
	private final static String ARG_END_SLEEP_TIME="end_sleep_time";
	private final static String ARG_TIMEZONE="time_zone";
	
	public AccountService(String method) {
		this(method, isPostMethod(method));
	}

	public AccountService(String method, boolean post) {
		super(METHOD_PREFIX + method, "1", post, isAuthMethod(method),
				isRateLimited(method));
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

	private boolean shouldAddArgument(String applicableMethods[]) {
		final String method = getMethod();
		for (int i = 0; i < applicableMethods.length; i++) {
			if (method.equals(METHOD_PREFIX + applicableMethods[i])) {
				return true;
			}
		}
		return false;
	}

	public void setIncludeEntities(boolean b) {
		String applicableMethods[] = {VERIFY_CREDENTIALS, UPDATE_PROFILE,
				UPDATE_PROFILE_BACKGROUND_IMAGE, UPDATE_PROFILE_COLORS,
				UPDATE_PROFILE_IMAGE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_INCLUDE_ENTITIES, (b == true) ? "true" : "false");
		}
	}

	public void setSkipStatus(boolean b) {
		String applicableMethods[] = {VERIFY_CREDENTIALS, UPDATE_PROFILE,
				UPDATE_PROFILE_BACKGROUND_IMAGE, UPDATE_PROFILE_COLORS,
				UPDATE_PROFILE_IMAGE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_SKIP_STATUS, (b == true) ? "true" : "false");
		}
	}

	public void setProfileUrl(String url) {
		String applicableMethods[] = {UPDATE_PROFILE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_URL, url);
		}
	}

	public void setLocation(String location) {
		String applicableMethods[] = {UPDATE_PROFILE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_LOCATION, location);
		}
	}

	public void setDescription(String description) {
		String applicableMethods[] = {UPDATE_PROFILE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_DESCRIPTION, description);
		}
	}

	public void setImage(InputStream is) {
		String applicableMethods[] = {UPDATE_PROFILE,
				UPDATE_PROFILE_BACKGROUND_IMAGE, UPDATE_PROFILE_IMAGE};
		if (shouldAddArgument(applicableMethods)) {
			// addArgument(ARG_IMAGE, is);
		}
	}

	public void setTile(boolean tile) {
		String applicableMethods[] = {UPDATE_PROFILE_BACKGROUND_IMAGE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_TILE, tile ? "true" : "false");
		}
	}

	public void setUse(boolean use) {
		String applicableMethods[] = {UPDATE_PROFILE_BACKGROUND_IMAGE};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_USE, use ? "true" : "false");
		}
	}

	public void setBackgroundColor(String color) {
		String applicableMethods[] = {UPDATE_PROFILE_COLORS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_BACKGROUND_COLOR, color);
		}
	}

	public void setLinkColor(String color) {
		String applicableMethods[] = {UPDATE_PROFILE_COLORS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_LINK_COLOR, color);
		}
	}

	public void setSidebarColor(String color) {
		String applicableMethods[] = {UPDATE_PROFILE_COLORS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_SIDEBAR_COLOR, color);
		}
	}

	public void setSidebarFillColor(String color) {
		String applicableMethods[] = {UPDATE_PROFILE_COLORS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_SIDEBAR_FILL_COLOR, color);
		}
	}

	public void setTextColor(String color) {
		String applicableMethods[] = {UPDATE_PROFILE_COLORS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_TEXT_COLOR, color);
		}
	}

	public void setLang(String lang) {
		String applicableMethods[] = {SETTINGS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_LANG, lang);
		}
	}

	public void setWhereOnEarthId(String woeid) {
		String applicableMethods[] = {SETTINGS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_WOEID, woeid);
		}
	}

	public void setSleepTimeEnabled(boolean b) {
		String applicableMethods[] = {SETTINGS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_SLEEP_TIME_ENABLED, b ? "true" : "false");
		}
	}

	public void setStartSleepTime(String time) {
		String applicableMethods[] = {SETTINGS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_START_SLEEP_TIME, time);
		}
	}

	public void setEndSleepTime(String time) {
		String applicableMethods[] = {SETTINGS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_END_SLEEP_TIME, time);
		}
	}

	public void setTimezone(String tz) {
		String applicableMethods[] = {SETTINGS};
		if (shouldAddArgument(applicableMethods)) {
			addArgument(ARG_TIMEZONE, tz);
		}
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
		fireResponseListener(new ActionEvent(result));
	}
}
