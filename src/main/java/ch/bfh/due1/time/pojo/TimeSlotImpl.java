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
package ch.bfh.due1.time.pojo;

import java.time.LocalDateTime;

import ch.bfh.due1.time.TimeSlot;

public class TimeSlotImpl implements TimeSlot {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -3193619080666868994L;

	private LocalDateTime start;

	private LocalDateTime finish;

	/**
	 * Constructs a time slot object.
	 *
	 * @param start
	 *            the start of the time slot where the condition start &lt;= end
	 *            must be true
	 * @param finish
	 *            the end of the time slot
	 */
	public TimeSlotImpl(LocalDateTime start, LocalDateTime finish) {
		if (start.isAfter(finish)) {
			throw new IllegalArgumentException("Finis time of time slot cannot be smaler than start time");
		}
		this.start = start;
		this.finish = finish;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalDateTime getFinish() {
		return finish;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocalDateTime getStart() {
		return start;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return start.equals(finish);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean includes(LocalDateTime dateTime) {
		return !dateTime.isBefore(start) && !dateTime.isAfter(finish);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean includes(TimeSlot other) {
		return this.includes(other.getStart()) && this.includes(other.getFinish());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean overlaps(TimeSlot other) {
		return other.includes(start) || other.includes(finish) || this.includes(other);
	}

	/**
	 * Tests another time slot object for equality. Two time slot objects are
	 * equal iff their start time and end time are equal.
	 *
	 * @param other
	 *            the object to compare
	 * @return true iff other is a time slot object and the the start time and
	 *         end time are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TimeSlot))
			return false;
		TimeSlot ts = (TimeSlot) other;
		return start.equals(ts.getStart()) && finish.equals(ts.getFinish());
	}

	/**
	 * Returns the hash code of this object.
	 *
	 * @return The hash code.
	 */
	@Override
	public int hashCode() {
		int rval = 37;
		rval += 17 * start.hashCode();
		rval += 17 * finish.hashCode();
		return rval;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int compareTo(TimeSlot other) {
		int rval = this.start.compareTo(other.getStart());
		if (rval == 0) {
			// Start times do not differ -- take finish times, too.
			return this.finish.compareTo(other.getFinish());
		}
		return rval;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean startsBefore(TimeSlot other) {
		return this.getStart().isBefore(other.getStart());
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean startsAfter(TimeSlot other) {
		return this.getStart().isAfter(other.getStart());
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean endsBefore(TimeSlot other) {
		return this.getFinish().isBefore(other.getFinish());
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean endsAfter(TimeSlot other) {
		return this.getFinish().isAfter(other.getFinish());
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean strictlyIncludes(TimeSlot other) {
		return includes(other) && startsBefore(other) && endsAfter(other);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean exactlyMatches(TimeSlot other) {
		return includes(other) && !startsBefore(other) && !endsAfter(other);
	}
}
