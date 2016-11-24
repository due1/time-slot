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
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class DateTimeFactoryTest {

	@Test
	public void testRoundToOneMinute() {
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime nextToMinute = DateTimeFactory.roundTo(current, DateTimeFactory.TimeSlotTypeSpec.MINUTES);
		assertTrue(nextToMinute.isBefore(current) || nextToMinute.equals(current));
		assertTrue(nextToMinute.getSecond() == 0);
		assertTrue(nextToMinute.getNano() == 0);
	}

	@Test
	public void testRoundToTenMinutes() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nextToTenMinutes = DateTimeFactory.roundTo(now, DateTimeFactory.TimeSlotTypeSpec.TENMINUTES);
		assertTrue(nextToTenMinutes.getSecond() == 0);
		int diff = now.getMinute() % 10;
		assertTrue(now.getMinute() == nextToTenMinutes.getMinute() + diff);
	}

	@Test
	public void testRoundToFifteenMinutes() {
		LocalDateTime dt1 = LocalDateTime.of(2016, 11, 23, 9, 0);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 0),
				DateTimeFactory.roundTo(dt1, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt2 = LocalDateTime.of(2016, 11, 23, 9, 1);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 0),
				DateTimeFactory.roundTo(dt2, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt3 = LocalDateTime.of(2016, 11, 23, 9, 14);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 0),
				DateTimeFactory.roundTo(dt3, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt4 = LocalDateTime.of(2016, 11, 23, 9, 15);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 15),
				DateTimeFactory.roundTo(dt4, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt5 = LocalDateTime.of(2016, 11, 23, 9, 16);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 15),
				DateTimeFactory.roundTo(dt5, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt6 = LocalDateTime.of(2016, 11, 23, 9, 29);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 15),
				DateTimeFactory.roundTo(dt6, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt7 = LocalDateTime.of(2016, 11, 23, 9, 30);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 30),
				DateTimeFactory.roundTo(dt7, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt8 = LocalDateTime.of(2016, 11, 23, 9, 31);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 30),
				DateTimeFactory.roundTo(dt8, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt9 = LocalDateTime.of(2016, 11, 23, 9, 44);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 30),
				DateTimeFactory.roundTo(dt9, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt10 = LocalDateTime.of(2016, 11, 23, 9, 45);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 45),
				DateTimeFactory.roundTo(dt10, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt11 = LocalDateTime.of(2016, 11, 23, 9, 46);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 45),
				DateTimeFactory.roundTo(dt11, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
		LocalDateTime dt12 = LocalDateTime.of(2016, 11, 23, 9, 59);
		assertEquals(LocalDateTime.of(2016, 11, 23, 9, 45),
				DateTimeFactory.roundTo(dt12, DateTimeFactory.TimeSlotTypeSpec.FITHTEENMINUTES));
	}

	@Test
	public void testRoundToHours() {
		LocalDateTime dt1 = LocalDateTime.of(2016, 11, 23, 15, 34);
		assertEquals(LocalDateTime.of(2016, 11, 23, 15, 0),
				DateTimeFactory.roundTo(dt1, DateTimeFactory.TimeSlotTypeSpec.HOURS));
	}

	@Test
	public void testRoundToDays() {
		LocalDateTime dt1 = LocalDateTime.of(2016, 11, 23, 15, 34);
		assertEquals(LocalDateTime.of(2016, 11, 23, 0, 0),
				DateTimeFactory.roundTo(dt1, DateTimeFactory.TimeSlotTypeSpec.DAYS));
	}
}
