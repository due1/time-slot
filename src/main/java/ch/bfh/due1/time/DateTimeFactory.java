/*
 * Copyright (c) 2016 Berner Fachhochschule, Switzerland.
 *
 * Project Time Slot.
 *
 * A small library dealing with time slots. Useful for the treatment of
 * recurring events. See also http://martinfowler.com/apsupp/recurring.pdf
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.due1.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This utility class offers static methods to create a local date/time object
 * given date/time information.
 */
public class DateTimeFactory {
	public enum TimeSlotTypeSpec {
		MINUTES, TENMINUTES, FITHTEENMINUTES, HOURS, DAYS
	}

	/**
	 * Not used.
	 */
	private DateTimeFactory() {
	}

	/**
	 * Returns a local date/time object with current date and time.
	 *
	 * @return a date/time object
	 */
	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static LocalDateTime roundTo(LocalDateTime dateTime, TimeSlotTypeSpec spec) {
		// Takes current time and truncates seconds and nanoseconds to zero.
		LocalDateTime tmp = dateTime.truncatedTo(ChronoUnit.MINUTES);
		switch (spec) {
		case MINUTES:
			return tmp;
		case TENMINUTES:
			// 0 <= minToAdjust <= 9
			int minToAdjust = tmp.getMinute() % 10;
			// subtract minToAdjust from tmp to get a date/time value having 0,
			// 10, 20, ..., 50 minutes only
			return tmp.minusMinutes(minToAdjust);
		case FITHTEENMINUTES:
			int min = tmp.getMinute();
			// assume 0 <= min <= 59
			int finalMin;
			if (0 <= min && min <= 14) {
				finalMin = 0;
			} else if (15 <= min && min <= 29) {
				finalMin = 15;
			} else if (30 <= min && min <= 44) {
				finalMin = 30;
			} else {
				finalMin = 45;
			}
			return LocalDateTime.of(tmp.getYear(), tmp.getMonthValue(), tmp.getDayOfMonth(), tmp.getHour(), finalMin);
		case HOURS:
			return tmp.truncatedTo(ChronoUnit.HOURS);
		case DAYS:
			return tmp.truncatedTo(ChronoUnit.DAYS);
		default:
			throw new UnsupportedOperationException("Can support one out of: " + TimeSlotTypeSpec.values());
		}
	}
}
