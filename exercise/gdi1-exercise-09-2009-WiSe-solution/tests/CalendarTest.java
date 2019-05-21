import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


/**
 * Test the various calendar functionality.
 */
public class CalendarTest {
	
	/**
	 * @author felix
	 */
	
	private Calendar testCal;
	private Developer paul;
	private Developer mary;
	private Secretary bob;

	private Note note;
	private Illness illness;
	private Reminder reminder;
	private Meeting meeting;
	private Vacation vacation;

	@Before
	public void Init() {
		// test calendar object
		testCal = new Calendar();

		// users init
		paul = new Developer("Paul", "Anderson");
		mary = new Developer("Mary", "Bobkins");
		bob = new Secretary("Adam", "Johanson");

		// note init
		note = new Note(new GregorianCalendar(2009, 1, 0, 16, 0), "my note", paul);
		note.setDetails("test");
		testCal.addCalendarEntry(note);

		// reminder init
		reminder = new Reminder(new GregorianCalendar(2010, 3, 12, 14, 30), new GregorianCalendar(2010, 3, 12, 14, 20), "Water the plants", mary);
		reminder.setPrivate(true);
		testCal.addCalendarEntry(reminder);

		// meeting init
		meeting = new Meeting(new GregorianCalendar(2010, 1, 1, 16, 30), new GregorianCalendar(2010, 1, 1, 17, 0), "test meeting", bob);
		meeting.addParticipant(paul);
		meeting.addParticipant(mary);
		// meeting.addParticipant(bob); --> not possible - bob isn't a developer!

		// illness init
		illness = new Illness(new GregorianCalendar(2010, 0, 1), new GregorianCalendar(2010, 0, 2), "bobs illness", bob);
		illness.setPrivate(true);
		testCal.addCalendarEntry(illness);
		
		// vacation init
		vacation = new Vacation(new GregorianCalendar(2020, 0, 0, 0, 0), new GregorianCalendar(2020, 0, 0, 0, 1), "short vacation", paul);
		testCal.addCalendarEntry(vacation);
	}

	@Test
	public void noteTest() {
		// has note the additional field details?
		Assert.assertEquals(note.getDetails(), "test");
		Assert.assertEquals(note.getDescription(), "my note");
	}

	@Test
	public void reminderTest() {
		// check current alarm time (should be set)
		Assert.assertEquals(reminder.getAlarmTime(), new GregorianCalendar(2010, 3, 12, 14, 20));

		// set new alarm time after the actual time - this should not work
		// -> old alarm time should be set
		try {
			reminder.setAlarmTime(new GregorianCalendar(2010, 3, 12, 14, 40));
		} catch (Exception e) {
		}
		Assert.assertEquals(reminder.getAlarmTime(), new GregorianCalendar(2010, 3, 12, 14, 20));
	}

	@Test
	public void meetingTest() {
		// test method getParticipants
		String[] names = {"Paul", "Mary"};
		User[] participants = meeting.getParticipants();
		
		for (int i=0; i<participants.length; i++) {
			Assert.assertEquals(names[i], participants[i].getGivenName());
		}
		
		// test method setParticipants
		names[0] = "Mary";
		names[1] = "Paul";
		meeting.setParticipants(new Developer[] {mary, paul});
		participants = meeting.getParticipants();
		
		for (int i=0; i<participants.length; i++)
		{
			Assert.assertEquals(names[i], participants[i].getGivenName());
		}
		
		// test removeParticipant()
		meeting.removeParticipant(mary);
		Assert.assertEquals(1, meeting.getParticipants().length);
		Assert.assertEquals("Paul", meeting.getParticipants()[0].getGivenName());
		
		// test addParticipant and hasParticipant
		meeting.addParticipant(mary);
		Assert.assertTrue(meeting.hasParticipant(mary));
	}
	
	@Test
	public void userClassesTest() {
		// check given name of a developer and a secretary
		Assert.assertEquals("Paul", paul.getGivenName());
		Assert.assertEquals("Adam", bob.getGivenName());
		
		// check family name (of a developer and..)
		Assert.assertEquals("Anderson", paul.getFamilyName());
		Assert.assertEquals("Johanson", bob.getFamilyName());
	}
	
	@Test
	public void delegateTest() {
		// check delegate for illness - paul is bobs delegate
		illness.setDelegate(paul);
		Assert.assertEquals(paul, illness.getDelegate());
		
		// although bobs illness is private paul should see it, because he's his delegate
		Assert.assertTrue(illness.isPrivate());
		Assert.assertTrue(testCal.listEntries(paul).contains("bobs illness"));
		
		// mary shouldn't see bobs illness (she isn't his delegate and his illness isn't public)
		Assert.assertFalse(testCal.listEntries(mary).contains("bobs illness"));
		
		// test delegate for vacation
		vacation.setDelegate(bob);
		Assert.assertEquals("Adam", vacation.getDelegate().getGivenName());
	}
	
	@Test
	public void durationTest() {
		// the meeting should be from 16:30 until 17:00 -> 30min
		
		// check getDuration
		Assert.assertEquals(0, meeting.getDuration(GregorianCalendar.DATE));
		Assert.assertEquals(0, meeting.getDuration(GregorianCalendar.HOUR));
		Assert.assertEquals(30, meeting.getDuration(GregorianCalendar.MINUTE));
		Assert.assertEquals(1800, meeting.getDuration(GregorianCalendar.SECOND));
		
		// check spans
		// 16:40 is within 16:30 - 17:00
		Assert.assertTrue(meeting.spans(new GregorianCalendar(2010, 1, 1, 16, 40)));
		// 16:00 is not within 16:30 - 17:00
		Assert.assertFalse(meeting.spans(new GregorianCalendar(2010, 1, 1, 16, 0)));
		
		// check new toString()
		Assert.assertEquals("Mo, 01 Februar 2010 - 16:30:00 von Adam Johanson | test meeting - about 30 minute(s)", meeting.toString());
		meeting.setEndTime(new GregorianCalendar(2011, 1, 1, 18, 0));
		Assert.assertEquals("Mo, 01 Februar 2010 - 16:30:00 von Adam Johanson | test meeting - about 1 year(s)", meeting.toString());
	}
	
	@Test
	public void calendarTest() {
		// test getBetween
		Calendar newCalendar = testCal.getBetween(new GregorianCalendar(2009, 0, 0, 0, 0), new GregorianCalendar(2010, 0, 0, 0, 0));
		
		// pauls note is the only entry that's in 2009
		Assert.assertEquals("Sa, 31 Januar 2009 - 16:00:00 von Paul Anderson | my note\n", newCalendar.listEntries(paul));
		Assert.assertEquals(1, newCalendar.getCalendarEntries().length); // getCalendarEntries should work also for this test
		
		// test addCalendarEntry and hasCalendarEntry
		Assert.assertTrue(newCalendar.hasCalendarEntry(note));
		newCalendar.addCalendarEntry(reminder);
		Assert.assertTrue(newCalendar.hasCalendarEntry(reminder));
		
		// test removeCalendarEntry
		newCalendar.removeCalendarEntry(reminder);
		Assert.assertFalse(newCalendar.hasCalendarEntry(reminder));
		
		// test getCalendarEntries and setCalendarEntries
		CalendarEntry[] testEntries = {meeting, illness};
		newCalendar.setCalendarEntries(testEntries);
		CalendarEntry[] actualEntries = newCalendar.getCalendarEntries();
		for (int i=0; i<testEntries.length; i++) {
			Assert.assertEquals(testEntries[i], actualEntries[i]);
		}
	}
	
	/**
	 * @author Ulf
	 */
	@Test
	public void testDeveloperCreation() {
	    Developer sasha = new Developer("Sasha", "Dole");
	    Assert.assertEquals(sasha.getGivenName(), "Sasha");
	    Assert.assertEquals(sasha.getFamilyName(), "Dole");
	    
	    //You can delete this stuff if you dont need this,
	    //4 example if u use some fancy boolean or stuff to
	    //determine if its a Secretary or a Developer 
	    Assert.assertEquals(sasha.getClass(), Developer.class);
	    Assert.assertEquals(sasha.getClass().getSuperclass(), User.class);
	}
	
	@Test
	public void testSecretaryCreation() {
	    Secretary sasha = new Secretary("Haris", "Pilton");
	    Assert.assertEquals(sasha.getGivenName(), "Haris");
	    Assert.assertEquals(sasha.getFamilyName(), "Pilton");
	    
	    //You can delete this stuff if you dont need this,
	    //4 example if u use some fancy boolean or stuff to
	    //determine if its a Secretary or a Developer
	    Assert.assertEquals(sasha.getClass(), Secretary.class);
	    Assert.assertEquals(sasha.getClass().getSuperclass(), User.class);
	}

	@Test
	public void testMeetingCreation() {
		Meeting meeting = new Meeting(	new GregorianCalendar(2010,4, 3, 18, 30),
										new GregorianCalendar(2010,4, 4, 18, 30),
										"Meeting some Dudes",
										new Developer("Sasha", "Dole"));
		Assert.assertFalse (meeting.isPrivate());
		Assert.assertEquals(meeting.getDescription(), "Meeting some Dudes");
		Assert.assertEquals(meeting.getOwner().getGivenName(), "Sasha");
		
		Developer mrt = new Developer("Mr.","T.");
		meeting.addParticipant(mrt);
		
		Assert.assertTrue(meeting.hasParticipant(mrt));
		
		Secretary hp = new Secretary("Haris","Pilton");
		meeting.addParticipant(hp);
		
		Assert.assertFalse(meeting.hasParticipant(hp));
		
		meeting.removeParticipant(mrt);
		
		Assert.assertFalse(meeting.hasParticipant(mrt));
		
		Assert.assertEquals(meeting.toString(),"Mo, 03 Mai 2010 - 18:30:00 von Sasha Dole | Meeting some Dudes - about 1 day(s)" );
	}
	
	@Test
	public void testVacationCreation() {
		Vacation vacation = new Vacation(	new GregorianCalendar(2010,4, 3,  18, 30),
											new GregorianCalendar(2011,5, 5, 18, 00),
											"PARTEY",
											new Developer("Mr.", "T."));
		vacation.setPrivate(true);
		
		Assert.assertTrue(vacation.isPrivate());
		Assert.assertEquals(vacation.getDescription(), "PARTEY");
		Assert.assertEquals(vacation.getOwner().getGivenName(), "Mr.");
		
		Assert.assertEquals(vacation.toString(),"Mo, 03 Mai 2010 - 18:30:00 von Mr. T. | PARTEY - about 1 year(s)" );
	}
	
	@Test
	public void testIllnessCreation() {
		Illness illness = new Illness(	new GregorianCalendar(2010,4, 4,  18, 30),
										new GregorianCalendar(2010,4, 4, 18, 30, 5),
										"-17CC outa here - where is the greenhouse effect???",
										new Developer("Gott", "hat kein Nachnamen"));
		Assert.assertFalse (illness.isPrivate());
		Assert.assertEquals(illness.getDescription(), "-17CC outa here - where is the greenhouse effect???");
		Assert.assertEquals(illness.getOwner().getFamilyName(), "hat kein Nachnamen");
		
		Assert.assertEquals(illness.toString(),"Di, 04 Mai 2010 - 18:30:00 von Gott hat kein Nachnamen | -17CC outa here - where is the greenhouse effect??? - about 5 second(s)" );	}
	
	@Test
	public void testNoteCreation() {
		Note note = new Note(			new GregorianCalendar(2010,1, 1,  1, 1),
										"download some illegal Stuff",
										new Developer("Anon", "Anonymous"));
		note.setPrivate(true);
		Assert.assertTrue (note.isPrivate());
		Assert.assertEquals(note.getDescription(), "download some illegal Stuff");
		Assert.assertEquals(note.getOwner().getFamilyName(), "Anonymous");
		Assert.assertEquals(note.getDetails(), null);
		
		note.setDetails("/b/tards always provide something... nvm");
		
		Assert.assertEquals(note.getDetails(), "/b/tards always provide something... nvm");
		Assert.assertEquals(note.toString(),"Mo, 01 Februar 2010 - 01:01:00 von Anon Anonymous | download some illegal Stuff" );
	}
	
	@Test
	public void testReminderCreation() {
		Reminder rem = new Reminder(			new GregorianCalendar(2010,2, 1,  1, 1),
												new GregorianCalendar(2010,1, 1,  10, 0),
												"Call Mr. T",
												new Developer("Anon", "Anonymous"));
		rem.setPrivate(true);
		Assert.assertTrue (rem.isPrivate());
		Assert.assertEquals(rem.getDescription(), "Call Mr. T");
		Assert.assertEquals(rem.getOwner().getGivenName(), "Anon");
		Assert.assertEquals(rem.getAlarmTime(), new GregorianCalendar(2010,1, 1,  10, 0));
		
		Assert.assertEquals(rem.toString(),"Mo, 01 März 2010 - 01:01:00 von Anon Anonymous | Call Mr. T" );
	}

	
	@Test
	public void testCalendarCreation()
	{
		Calendar cal = new Calendar();
		Developer bob = new Developer("Bob", "Smith");
		Meeting meeting = new Meeting(			new GregorianCalendar(2010, 3, 12, 18, 30),
												new GregorianCalendar(2010, 4, 12, 18, 30),
												"Meeting with Alex and Joseph",
												new Secretary("Nailin'", "Palin"));
		cal.addCalendarEntry(meeting);
		Assert.assertTrue(cal.listEntries(bob).length() > 0);
		Assert.assertTrue(cal.listEntries(bob).contains("Nailin'"));
		Assert.assertTrue(cal.listEntries(bob).contains("Meeting with Alex and Joseph"));
		Assert.assertTrue(cal.listEntries(bob).contains("2010"));
		Assert.assertTrue(cal.listEntries(bob).contains("April"));
		Assert.assertTrue(cal.listEntries(bob).contains("Mo"));
	}
	
	@Test
	public void testCalendarCreationAgain()
	{
		Calendar cal = new Calendar();

	    Developer mrt = new Developer("Mr.", "T.");
	    Assert.assertEquals(mrt.getGivenName(), "Mr.");
	    Assert.assertEquals(mrt.getFamilyName(), "T.");
	    
	    Developer god = new Developer("Gott", "braucht keinen Nachnamen");
	    Assert.assertEquals(god.getGivenName(), "Gott");
	    Assert.assertEquals(god.getFamilyName(), "braucht keinen Nachnamen");
	    
	    Secretary hp = new Secretary("Haris", "Pilton");
	    Assert.assertEquals(hp.getGivenName(), "Haris");
	    Assert.assertEquals(hp.getFamilyName(), "Pilton");
	    
	    Developer cn = new Developer("chuck", "norris");
	    Assert.assertEquals(cn.getGivenName(), "chuck");
	    Assert.assertEquals(cn.getFamilyName(), "norris");

	    Note note = new Note(	new GregorianCalendar(2010, 3, 12, 14, 30),
	    				     	"Perform Roundhousekick",
	    				     	cn);
	    
	    Assert.assertEquals(note.getDescription(), "Perform Roundhousekick"); 
	    Assert.assertEquals(note.getDetails(),null);
	    Assert.assertFalse(note.isPrivate());
	    Assert.assertTrue(note.isVisible(mrt));
	    Assert.assertTrue(note.isVisible(god));
	    Assert.assertTrue(note.isVisible(hp));
	    Assert.assertTrue(note.isVisible(cn));
	    
	    note.setDetails("In Ya Face");
	    
	    Assert.assertEquals(note.getDetails(),"In Ya Face");
	
	    note.setPrivate(true);
	    
	    Assert.assertFalse(note.isVisible(mrt));
	    Assert.assertFalse(note.isVisible(god));
	    Assert.assertFalse(note.isVisible(hp));
	    Assert.assertTrue(note.isVisible(cn));
	    
	    note.setPrivate(false);
	    
	    Reminder rem = new Reminder(	new GregorianCalendar(2010, 3, 12, 14, 30),
	    								new GregorianCalendar(2010, 3, 12, 14, 20),
	    								"Tell some Chuck Norris Jokes",
	    								god);
	    rem.setPrivate(true);
	    
	    Assert.assertEquals(rem.getDescription(), "Tell some Chuck Norris Jokes"); 
	    Assert.assertTrue(rem.isPrivate());
	    Assert.assertFalse(rem.isVisible(mrt));
	    Assert.assertTrue(rem.isVisible(god));
	    Assert.assertFalse(rem.isVisible(hp));
	    Assert.assertFalse(rem.isVisible(cn));

	    Meeting meet = new Meeting(	new GregorianCalendar(2010, 3, 12, 10, 30),
	    							new GregorianCalendar(2010, 3, 12, 11, 30),
	    							"Meeting about ... wow shiny new shoes",
	    							hp);
	    Assert.assertFalse(meet.hasParticipant(god));
	    meet.addParticipant(god);
	    Assert.assertTrue(meet.hasParticipant(god));
	    
	    Assert.assertEquals(meet.getDescription(), "Meeting about ... wow shiny new shoes"); 
	    Assert.assertFalse(meet.isPrivate());
	    Assert.assertTrue(meet.isVisible(mrt));
	    Assert.assertTrue(meet.isVisible(god));
	    Assert.assertTrue(meet.isVisible(hp));
	    Assert.assertTrue(meet.isVisible(cn));

	    Vacation vac = new Vacation(	new GregorianCalendar(2010, 2, 3),
	    								new GregorianCalendar(2010, 2, 13),
	    								"Create Nightelf",
	    								mrt);
	    
	    Assert.assertEquals(vac.getDelegate(),null);
	    
	    vac.setDelegate(cn);
	    
	    Assert.assertEquals(vac.getDelegate(),cn);
	    
	    Assert.assertEquals(vac.getDescription(), "Create Nightelf"); 
	    Assert.assertFalse(vac.isPrivate());
	    Assert.assertTrue(vac.isVisible(mrt));
	    Assert.assertTrue(vac.isVisible(god));
	    Assert.assertTrue(vac.isVisible(hp));
	    Assert.assertTrue(vac.isVisible(cn));

	    Illness ill = new Illness(	new GregorianCalendar(2010, 3, 3),
	    							new GregorianCalendar(2010, 3, 4),
	    							"Chuck Norris f�ngt sich keine Erk�ltung ein, eine Erk�ltung f�ngt sich Eine von Chuck Norris ein",
	    							cn);
	    
	    Assert.assertEquals(ill.getDelegate(),null);
	    
	    ill.setDelegate(god);
	    
	    Assert.assertEquals(ill.getDelegate(),god);

	    Assert.assertEquals(ill.getDescription(), "Chuck Norris f�ngt sich keine Erk�ltung ein, eine Erk�ltung f�ngt sich Eine von Chuck Norris ein"); 
	    Assert.assertFalse(ill.isPrivate());
	    Assert.assertTrue(ill.isVisible(mrt));
	    Assert.assertTrue(ill.isVisible(god));
	    Assert.assertTrue(ill.isVisible(hp));
	    Assert.assertTrue(ill.isVisible(cn));
	    
	    Calendar earlyApril = cal.getBetween(	new GregorianCalendar(2010, 3, 3),
	    										new GregorianCalendar(2010, 3, 10));

	    cal.listEntries(mrt);
	    cal.listEntries(god);
	    cal.listEntries(hp);
	    cal.listEntries(cn);
	    
	    earlyApril.listEntries(mrt);
	    earlyApril.listEntries(god);
	    earlyApril.listEntries(hp);
	    earlyApril.listEntries(cn);
	}

}
