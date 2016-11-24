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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class TimeSlotTest {
	public final String DEFAULTTIMESLOTFACTORYNAME = "ch.bfh.due1.time.pojo.TimeSlotFactoryImpl";

	private TimeSlotFactory factory;

	/**
	 * Constructs a time slot factory object by using the
	 * <code>timeslotfactory.name</code> property. If not set then the
	 * <code>ch.bfh.due1.time.TimeSlotFactoryImpl</code> is used.
	 */
	@Before
	public void setUp() throws Exception {
		String className = System.getProperty("timeslotfactory.name", DEFAULTTIMESLOTFACTORYNAME);
		Class<?> clazz = Class.forName(className);
		this.factory = (TimeSlotFactory) clazz.newInstance();
	}

	@Test
	public void testCreation1() {
		// valid time slot specification
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		assertNotNull(ts);
		assertEquals(start, ts.getStart());
		assertEquals(finish, ts.getFinish());
	}

	@Test
	public void testCreation2() {
		// valid time slot specification
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 15);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		assertNotNull(ts);
		assertEquals(start, ts.getStart());
		assertEquals(finish, ts.getFinish());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreation3() {
		// invalid time slot specification
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 45);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 15);
		@SuppressWarnings("unused")
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
	}

	/**
	 * Tests inclusion of date time instance in time slot instance.
	 */
	@Test
	public void testIncludesDateTimeInsances() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		assertFalse(ts.includes(LocalDateTime.of(2016, 11, 24, 9, 14)));
		assertTrue(ts.includes(LocalDateTime.of(2016, 11, 24, 9, 15)));
		assertTrue(ts.includes(LocalDateTime.of(2016, 11, 24, 9, 45)));
		assertFalse(ts.includes(LocalDateTime.of(2016, 11, 24, 9, 46)));
	}

	@Test
	public void testIncludesTimeSlotInstances() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		assertTrue(ts.includes(ts));
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 16);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 44);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.includes(ts1));
		assertFalse(ts1.includes(ts));
	}

	/**
	 * Tests the overlapping of a time slot with another time slot.
	 */
	@Test
	public void testOverlap() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		// equal time slot
		LocalDateTime s0 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f0 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts0 = this.factory.createTimeSlot(s0, f0);
		assertTrue(ts0.overlaps(ts));
		assertTrue(ts.overlaps(ts0));
		// time slot overlapping the end
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 20);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts1.overlaps(ts));
		assertTrue(ts.overlaps(ts1));
		// time slot overlapping the beginning
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 40);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertTrue(ts2.overlaps(ts));
		assertTrue(ts.overlaps(ts2));
		// time slot overlapping the beginning and the end
		LocalDateTime s3 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f3 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts3 = this.factory.createTimeSlot(s3, f3);
		assertTrue(ts3.overlaps(ts));
		assertTrue(ts.overlaps(ts3));
		// disjoint time slot
		LocalDateTime s4 = LocalDateTime.of(2016, 11, 24, 9, 0);
		LocalDateTime f4 = LocalDateTime.of(2016, 11, 24, 9, 10);
		TimeSlot ts4 = this.factory.createTimeSlot(s4, f4);
		assertFalse(ts4.overlaps(ts));
		assertFalse(ts.overlaps(ts4));
	}

	/**
	 * Tests equality and hash code.
	 */
	@Test
	public void testEqualsAndHashCode() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		// equal time slot
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts1.equals(ts));
		assertTrue(ts.equals(ts1));
		assertTrue(ts.hashCode() == ts1.hashCode());
		// unequal time slot
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts2.equals(ts));
		assertFalse(ts.equals(ts2));
		assertFalse(ts.hashCode() == ts2.hashCode());
	}

	@Test
	public void testIsEmpty() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		// same time
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 15);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		assertTrue(ts.isEmpty());
	}

	/**
	 * Test the comparison of time slots.
	 */
	@Test
	public void testCompareTo() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 20);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.compareTo(ts1) < 0);
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertTrue(ts.compareTo(ts2) < 0);
		LocalDateTime s3 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f3 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts3 = this.factory.createTimeSlot(s3, f3);
		assertTrue(ts.compareTo(ts3) == 0);
		LocalDateTime s4 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f4 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts4 = this.factory.createTimeSlot(s4, f4);
		assertTrue(ts.compareTo(ts4) > 0);
		LocalDateTime s5 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f5 = LocalDateTime.of(2016, 11, 24, 9, 40);
		TimeSlot ts5 = this.factory.createTimeSlot(s5, f5);
		assertTrue(ts.compareTo(ts5) > 0);
	}

	@Test
	public void testStartsBefore() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 20);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.startsBefore(ts1));
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts.startsBefore(ts2));
	}

	@Test
	public void testStartsAfter() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.startsAfter(ts1));
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 20);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts.startsAfter(ts2));
	}

	@Test
	public void testEndsBefore() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.endsBefore(ts1));
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 40);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts.endsBefore(ts2));
	}

	@Test
	public void testEndsAfter() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 40);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.endsAfter(ts1));
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts.endsAfter(ts2));
	}

	@Test
	public void testStrictlyIncludes() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 16);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 44);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.strictlyIncludes(ts1));
		assertFalse(ts1.strictlyIncludes(ts));
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts.strictlyIncludes(ts2));
	}

	@Test
	public void testExactlyMatches() {
		LocalDateTime start = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime finish = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts = this.factory.createTimeSlot(start, finish);
		LocalDateTime s1 = LocalDateTime.of(2016, 11, 24, 9, 15);
		LocalDateTime f1 = LocalDateTime.of(2016, 11, 24, 9, 45);
		TimeSlot ts1 = this.factory.createTimeSlot(s1, f1);
		assertTrue(ts.exactlyMatches(ts1));
		assertTrue(ts1.exactlyMatches(ts));
		LocalDateTime s2 = LocalDateTime.of(2016, 11, 24, 9, 10);
		LocalDateTime f2 = LocalDateTime.of(2016, 11, 24, 9, 50);
		TimeSlot ts2 = this.factory.createTimeSlot(s2, f2);
		assertFalse(ts.exactlyMatches(ts2));
		assertFalse(ts2.exactlyMatches(ts));
	}
}
