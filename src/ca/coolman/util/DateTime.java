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
package ca.coolman.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.codename1.io.Util;

/**
 * Utility class for working with dates with J2ME support.
 * 
 * @author Eric Coolman
 * 
 */
public class DateTime {
	private static final String SHORT_MONTHS[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
		"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	private static final long SECOND = 1L;
	private static final long MINUTE = 60L * SECOND;
	private static final long HOUR = 60L * MINUTE;
	private static final long DAY = 24L * HOUR;
	private static final long WEEK = 7L * DAY;

	/**
	 * 
	 * @param time timestamp in milliseconds from Jan 1, 1970 in GMT
	 * @return
	 */
	public static String getFriendlyTimestamp(long time) {
		long now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime().getTime();
		long delta = (now - time) / 1000L;

		if (delta < 0L) {
			return "not yet";
		}
		if (delta < (1L * MINUTE)) {
			return delta == 1L ? "one second ago" : delta
					+ " seconds ago";
		}
		if (delta < 2L * MINUTE) {
			return "a minute ago";
		}
		if (delta < 45L * MINUTE) {
			return (delta/MINUTE) + " minutes ago";
		}
		if (delta < 90L * MINUTE) {
			return "an hour ago";
		}
		if (delta < DAY) {
			return (delta / HOUR) + " hours ago";
		}
		if (delta < 48 * HOUR) {
			return "yesterday";
		}
		if (delta < WEEK) {
			return delta/DAY + " days ago";
		}
		return getMonthAndDay(time);
	}

	/**
	 * Get the date and month of the timestamp, in the local timezone
	 * 
	 * @param time time in milliseconds from Jan 1st, 1970 in GMT
	 * @return Month and day of time in local timezone.
	 */
	public static final String getMonthAndDay(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return SHORT_MONTHS[month] + " " + day;
	}
	
	/**
	 * Parse a full date string, as returned by services such as, for example,
	 * Twitter. Fri Apr 20 12:04:27 +0000 2012
	 * 
	 * @param date full date time stamp
	 * @return Calendar object with date in GMT
	 */
	public static Calendar parseFullDate(String date) {
		String dateTokens[] = Util.split(date, " ");
		if (dateTokens.length != 6) {
			return null;
		}
		String timeTokens[] = Util.split(dateTokens[3], ":");
		if (timeTokens.length != 3) {
			return null;
		}
		int month = fromShortMonthToInt(dateTokens[1]);
		if (month < 0 || month > 11) {
			return null;
		}
		int day = Integer.valueOf(dateTokens[2]).intValue();
		if (day < 0 || day > 31) {
			return null;
		}
		int year = Integer.valueOf(dateTokens[5]).intValue();
		if (year > 2100 || year < 1000) {
			return null;
		}
		int hour = Integer.valueOf(timeTokens[0]).intValue();
		if (hour < 0 || hour > 23) {
			return null;
		}
		int minute = Integer.valueOf(timeTokens[1]).intValue();
		if (minute < 0 || minute > 59) {
			return null;
		}
		int seconds = Integer.valueOf(timeTokens[2]).intValue();
		if (seconds < 0 || seconds > 59) {
			return null;
		}
		String timezone = dateTokens[4];
		boolean tzNegative = timezone.charAt(0) == '-';
		int tzHours = Integer.parseInt(timezone.substring(1, 3));
		int tzMins = Integer.parseInt(timezone.substring(3));
		if (tzNegative) {
			hour += tzHours;
			minute += tzMins;
		} else {
			hour -= tzHours;
			minute -= tzMins;
		}
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, seconds);

		// calendar.setTimeZone(value)
		return calendar;
	}

	private static int fromShortMonthToInt(String month) {
		for (int i = 0; i < SHORT_MONTHS.length; i++) {
			if (SHORT_MONTHS[i].equalsIgnoreCase(month)) {
				return i;
			}
		}
		return -1;
	}
}
