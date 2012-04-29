/**
 * 
 */
package ca.coolman.util;

import java.util.Calendar;

/**
 * @author Eric Coolman
 *
 */
public class TestDateTime {
	public static void main(String args[]) {
		String timestamp = "Fri Apr 21 04:11:57 +0000 2012";
		Calendar calendar = DateTime.parseFullDate(timestamp);
		System.out.println("Timestamp: " + timestamp);
		System.out.println("Friendly time: " + DateTime.getFriendlyTimestamp(calendar.getTime().getTime()));
		System.out.println("Month/day: " + DateTime.getMonthAndDay(calendar.getTime().getTime()));
		System.err.println("tzoffset of timestamp: " + calendar.getTimeZone().getRawOffset());
		System.err.println("tzoffset of now: " + Calendar.getInstance().getTimeZone().getRawOffset());
		
	}

}
