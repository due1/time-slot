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

/**
 * Defines the interface for a factory class which creates time slots.
 *
 * @author Eric Dubuis
 */
public interface TimeSlotFactory {
	/**
	 * Constructs a time slot object. The limits of the time slot are specified
	 * by java.time.LocalDateTime objects.
	 *
	 * @param start
	 *            the start of the time slot where the condition start &lt;= end
	 *            must be true
	 * @param end
	 *            the end of the time slot
	 * @return a TimeSlot object.
	 */
	public TimeSlot createTimeSlot(LocalDateTime start, LocalDateTime end);
}
