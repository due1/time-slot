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

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represent a period or time slot of date-and-time values. See <a href =
 * "http://www.martinfowler.com/ap2/range.html">http://www.martinfowler.com/ap2/range.html</a>
 */
public interface TimeSlot extends Serializable, Comparable<TimeSlot> {
	/**
	 * Denotes the empty time slot.
	 */
	public static final String EMPTY = "Empty time slot";

	/**
	 * Returns the end of the time slot.
	 *
	 * @return the end of the time slot
	 */
	public LocalDateTime getFinish();

	/**
	 * Returns the start of the time slot.
	 *
	 * @return the start of the time slot
	 */
	public LocalDateTime getStart();

	/**
	 * Returns true iff start &gt;= end.
	 *
	 * @return returns true iff start &gt;= end
	 */
	public boolean isEmpty();

	/**
	 * Returns true iff NOT (dateTime &lt; this.start() AND dateTime &lt;
	 * this.end()).
	 *
	 * @param dateTime
	 *            a date-time to check.
	 * @return whether the date-time is within the time slot
	 */
	public boolean includes(LocalDateTime dateTime);

	/**
	 * Returns true iff this.includes(ts.start()) && this.includes(ts.end()).
	 *
	 * @param ts
	 *            the time slot to check
	 * @return whether the argument is within the time slot
	 */
	public boolean includes(TimeSlot ts);

	/**
	 * Returns true iff ts.includes(this.getStart()) OR
	 * ts.includes(this.getFinish()) OR this.includes(ts).
	 *
	 * @param ts
	 *            the time slot to check
	 * @return whether the ts is within the time slot
	 */
	public boolean overlaps(TimeSlot ts);

	/**
	 * Compares this time slot with another one. Returns -1 if the start time of
	 * this time slot is smaller than the start time of the other time slot, or
	 * if the two start times are equal but the finish time of this time slot is
	 * smaller than the finish time of the other. Returns 0 if the start time of
	 * this time slot equals the start time of the other and the finish time of
	 * this time slot equals the finish time of the other. Returns +1 otherwise.
	 *
	 * @param ts
	 *            another time slot
	 * @return -1 iff this time slot is smaller than the given time slot, 0 iff
	 *         this time slot is equal to the given time slot, +1 otherwise
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TimeSlot ts);

	/**
	 * Checks if this date range starts before the given date range.
	 *
	 * @param ts
	 *            the given date range
	 * @return true if this time slot starts before the given time slot, false
	 *         otherwise
	 */
	public boolean startsBefore(TimeSlot ts);

	/**
	 * Checks if this time slot starts after the given time slot.
	 *
	 * @param ts
	 *            the given time slot
	 * @return true if this time slot starts after the given time slot, false
	 *         otherwise
	 */
	public boolean startsAfter(TimeSlot ts);

	/**
	 * Checks if this time slot ends before the given time slot.
	 *
	 * @param ts
	 *            the given time slot
	 * @return true if this time slot ends before the given time slot, false
	 *         otherwise
	 */
	public boolean endsBefore(TimeSlot ts);

	/**
	 * Checks if this time slot ends after the given time slot.
	 *
	 * @param ts
	 *            the given time slot.
	 * @return true if this time slot ends after the given time slot, false
	 *         otherwise
	 */
	public boolean endsAfter(TimeSlot ts);

	/**
	 * Checks if this time slot strictly includes the given time slot.
	 *
	 * @param ts
	 *            the given time slot
	 * @return true if this.includes(ts) and this.startsBefore(ts) and
	 *         this.endsAfter(ts), false otherwise.
	 */
	public boolean strictlyIncludes(TimeSlot ts);

	/**
	 * Checks if this time slot exactly matches the given time slot.
	 *
	 * @param ts
	 *            the given time slot.
	 * @return true if this.includes(ts) and not startsBefore(ts) and not
	 *         endsAfter(ts), false otherwise.
	 */
	public boolean exactlyMatches(TimeSlot ts);
}
