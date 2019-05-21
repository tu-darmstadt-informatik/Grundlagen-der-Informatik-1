



import GdICalendarTemplate.Calendar;
import GdICalendarTemplate.Calendar;
import GdICalendarTemplate.CalendarEntry;
import GdICalendarTemplate.CalendarEntry;
import GdICalendarTemplate.User;
import GdICalendarTemplate.User;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import org.junit.Test;


/**
 * Test the various calendar functionality.
 */
public class CalendarTest{
	@Test
	public void testUserCreation() {
	    User sasha = new User("Sasha", "Dole");
	    Assert.assertEquals(sasha.getGivenName(), "Sasha");
	    Assert.assertEquals(sasha.getFamilyName(), "Dole");
	}

	@Test
	public void testCalendarEntryCreation() {
		CalendarEntry meeting = new CalendarEntry(new GregorianCalendar(2010,
				3, 12, 18, 30), "Meeting with Alex and Joseph", new User(
				"Sasha", "Dole"));
		Assert.assertFalse(meeting.isPrivate());
		Assert.assertEquals(meeting.getDescription(), "Meeting with Alex and Joseph");
		Assert.assertEquals(meeting.getOwner().getGivenName(), "Sasha");
	}

	@Test
	public void testCalendarCreation() {
		Calendar cal = new Calendar();
		User bob = new User("Bob", "Smith");
		CalendarEntry meeting = new CalendarEntry(new GregorianCalendar(2010,
				3, 12, 18, 30), "Meeting with Alex and Joseph", new User(
				"Sasha", "Dole"));
		cal.addCalendarEntry(meeting);
		Assert.assertTrue(cal.listEntries(bob).length() > 0);
		Assert.assertTrue(cal.listEntries(bob).contains("Sasha"));
		Assert.assertTrue(cal.listEntries(bob).contains("Meeting with Alex and Joseph"));
		Assert.assertTrue(cal.listEntries(bob).contains("2010"));
		Assert.assertTrue(cal.listEntries(bob).contains("April"));
		Assert.assertTrue(cal.listEntries(bob).contains("Mo"));
	}

}
