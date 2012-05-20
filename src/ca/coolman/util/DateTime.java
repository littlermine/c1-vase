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
import java.util.Vector;

/**
 * Utility class for working with dates with J2ME support.
 * 
 * @author Eric Coolman
 * 
 */
public class DateTime {
	private static final String SHORT_MONTHS[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec"};
	private static final String vsuffix[] = {"not yet", "one second ago", " seconds ago", "a minute ago",
			" minutes ago", "an hour ago", " hours ago", "yesterday", " days ago"};
	private static final String suffix[] = {"0m", "1s", "s", "1m", "m", "1h", "h", "1d", "d"};

	private static final long SECOND = 1L;
	private static final long MINUTE = 60L * SECOND;
	private static final long HOUR = 60L * MINUTE;
	private static final long DAY = 24L * HOUR;
	private static final long WEEK = 7L * DAY;

	public static String getFriendlyTimestamp(long time) {
		return getFriendlyTimestamp(time, true);
	}

	/**
	 * 
	 * @param time timestamp in milliseconds from Jan 1, 1970 in GMT
	 * @return
	 */
	public static String getFriendlyTimestamp(long time, boolean verbose) {
		long now = Calendar.getInstance().getTime().getTime();
		long delta = (now - time) / 1000L;
		String tokens[];
		if (verbose) {
			tokens = vsuffix;
		} else {
			tokens = suffix;
		}
		if (delta < 0L) {
			return tokens[0];
		}
		if (delta < (1L * MINUTE)) {
			return delta == 1L ? tokens[1] : delta + tokens[2];
		}
		if (delta < 2L * MINUTE) {
			return tokens[3];
		}
		if (delta < 45L * MINUTE) {
			return (delta / MINUTE) + tokens[4];
		}
		if (delta < 90L * MINUTE) {
			return tokens[5];
		}
		if (delta < DAY) {
			return (delta / HOUR) + tokens[6];
		}
		if (delta < 48 * HOUR) {
			return tokens[7];
		}
		if (delta < WEEK) {
			return delta / DAY + tokens[8];
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
		// Calendar impl January is not necessarily either 0 or 1.
		int month = calendar.get(Calendar.MONTH) - Calendar.JANUARY;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return SHORT_MONTHS[month] + " " + day;
	}

	/**
	 * Parse a date with a given date format. The date format specified follows
	 * the same format as the J2SE SimpleDateFormat (see
	 * http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)
	 * Although currently this method only fully supports a subset (YyMDHmsZ),
	 * all other tokens will simply be filtered out of the result (ie. treated
	 * the same as static text tokens).
	 * 
	 * The date returned will be adjusted from the timestamp's (optionally)
	 * given timezone to the current local timezone.
	 * 
	 * <pre>
	 *   // Format returned by Twitter Timeline service
	 *   Fri Apr 20 12:04:27 +0000 2012
	 * //EEE MMM dd HH:mm:ss Z yyyy
	 * 
	 *   // Format returned by Twitter Search service
	 *   Sat, 19 May 2012 00:07:11 +0000
	 * //EEE, dd MMM yyyy HH:mm:ss Z
	 * </pre>
	 * 
	 * @param datestamp date and time stamp
	 * @param pattern the pattern of the date and timestamp
	 * @return Calendar object with date in local timezone
	 * @throws IllegalArgumentException if the date can't be parsed with the
	 *             given format.
	 */
	public static Calendar parseDatestamp(String datestamp, String pattern) {
		Vector tokens = parseDatePattern(pattern);
		return parseDatestamp(datestamp, tokens);
	}

	/**
	 * Parse a date with a given date format. The date format specified follows
	 * the same format as the J2SE SimpleDateFormat (see
	 * http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)
	 * Although currently this method only fully supports a subset (YyMDHmsZ),
	 * all other tokens will simply be filtered out of the result (ie. treated
	 * the same as static text tokens).
	 * 
	 * The date returned will be adjusted from the timestamp's (optionally)
	 * given timezone to the current local timezone.
	 * 
	 * <pre>
	 *   // Format returned by Twitter Timeline service
	 *   Fri Apr 20 12:04:27 +0000 2012
	 * //EEE MMM dd HH:mm:ss Z yyyy
	 * 
	 *   // Format returned by Twitter Search service
	 *   Sat, 19 May 2012 00:07:11 +0000
	 * //EEE, dd MMM yyyy HH:mm:ss Z
	 * </pre>
	 * 
	 * @param datestamp date and time stamp
	 * @param pattern the precompiled pattern of the date and timestamp
	 * @return Calendar object with date in local timezone
	 * @throws IllegalArgumentException if the date can't be parsed with the
	 *             given format.
	 */
	public static Calendar parseDatestamp(String datestamp, Vector pattern) {
		int startIndex = 0;
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		int tzHours = -1;
		int tzMinutes = -1;
		try {
			for (int i = 0; i < pattern.size(); i++) {
				String token = (String) pattern.elementAt(i);
				// preview assists with parsing values not separated by spaces,
				// ie. yyyyMMdd - the yyyy would otherwise be problematic.
				char preview = 0;
				if (i < (pattern.size() - 1)) {
					preview = ((String)pattern.elementAt(i + 1)).charAt(0);
				}
				int len = token.length();
				if ((token.charAt(0) == 'Z') || ((len > 3) && (preview == '*'))) {
					int index = datestamp.indexOf(' ', startIndex);
					if (index == -1) {
						index = datestamp.length();
					}
					len = index - startIndex;
				}
				String s = datestamp.substring(startIndex, startIndex + len);
				switch (token.charAt(0)) {
					case 'Z' :
						char tzSign = s.charAt(0);
						s = s.substring(1);
						// set the index to point to divider between hours and
						// minutes. Hour can be
						// one or two digits, minutes is always 2 digits.
						int index = 2;
						if (len == 4) {
							index--;
						}
						tzHours = Integer.parseInt(s.substring(0, index));
						tzMinutes = Integer.parseInt(s.substring(index));
						if (tzSign != '-') {
							tzHours = -tzHours;
							tzMinutes = -tzMinutes;
						}
						break;
					case 'y' :
					case 'Y' :
						int year = Integer.valueOf(s).intValue();
						if (len == 2) {
							if (year > 50) {
								year += 1900;
							} else {
								year += 2000;
							}
						}
						if (year > 2100 || year < 1000) {
							throw new IllegalArgumentException("Illegal year specified: " + year);
						}
						System.out.println("Year:"+year);
						calendar.set(Calendar.YEAR, year);
						break;
					case 'M' :
						int month;
						if (len == 2) {
							month = Integer.parseInt(s) - 1;
						} else {
							month = fromMonthToInt(s);
						}
						if (month < 0 || month > 11) {
							throw new IllegalArgumentException("Illegal month value specified: " + month);
						}
						calendar.set(Calendar.MONTH, Calendar.JANUARY + month);
						break;
					case 'd' :
						int day = Integer.parseInt(s);
						if (day < 1 || day > 31) {
							throw new IllegalArgumentException("Illegal day value specified: " + day);
						}
						calendar.set(Calendar.DAY_OF_MONTH, day);
						break;
					case 'H' :
						int hour = Integer.parseInt(s);
						if (hour < 0 || hour > 23) {
							throw new IllegalArgumentException("Illegal hour value specified: " + hour);
						}
						calendar.set(Calendar.HOUR_OF_DAY, hour);
						break;
					case 'm' :
						int minute = Integer.parseInt(s);
						if (minute < 0 || minute > 59) {
							throw new IllegalArgumentException("Illegal minute value specified: " + minute);
						}
						calendar.set(Calendar.MINUTE, minute);
						break;
					case 's' :
						int seconds = Integer.parseInt(s);
						if (seconds < 0 || seconds > 59) {
							throw new IllegalArgumentException("Illegal seconds value specified: " + seconds);
						}
						calendar.set(Calendar.SECOND, seconds);
						break;
				}
				startIndex += len;
			}
		} catch (IllegalArgumentException iae) {
			throw iae;
		} catch (Throwable t) {
			throw new IllegalArgumentException("Error parsing date at position " + startIndex);
		}
		TimeZone localTimezone = Calendar.getInstance().getTimeZone();
		calendar.setTimeZone(localTimezone);
		// If timezone offset not part of date, the date passed will be treated
		// as if it's local timezone.
		if (tzHours != -1) {
			// Adjusting the time to be GMT time. Doing this here allows
			// tzoffset to
			// be specified before or after actual time.
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + tzHours);
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + tzMinutes);
			// Now adjust the time again to local time.
			calendar.set(Calendar.MILLISECOND, localTimezone.getRawOffset());
		}
		return calendar;
	}

	/**
	 * Parse the date pattern, an optimization, to prevent re-parsing the date
	 * pattern if many dates in same format need to be handled.
	 * 
	 * The vector will contain each unique token of the timestamp.  Any unrecognized
	 * tokens are represented with '*', for the length of token, which will get
	 * filtered by the date parser.
	 * 
	 * @param pattern
	 * @return parsed pattern.
	 */
	public static Vector parseDatePattern(String pattern) {
		Vector tokens = new Vector();
		String tmp = null;
		String valid = "adDEFGHhKkMmsSuwWXyYzZ";
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			boolean isValid = valid.indexOf(ch) != -1;
			if (isValid == false) {
				ch = '*';
			}
			if (tmp == null) {
				tmp = "" + ch;
				continue;
			} else if (ch == tmp.charAt(0)) {
				tmp += ch;
			} else {
				tokens.addElement(tmp);
				tmp = "" + ch;
			}
		}
		if (tmp != null) {
			tokens.addElement(tmp);
		}
		return tokens;
	}

	/**
	 * This will return a 0-based month based on the month string (ie. January =
	 * 0).
	 * 
	 * @param month
	 * @return
	 */
	private static int fromMonthToInt(String month) {
		for (int i = 0; i < SHORT_MONTHS.length; i++) {
			if (month.toLowerCase().startsWith(SHORT_MONTHS[i].toLowerCase())) {
				return i;
			}
		}
		return -1;
	}
}
