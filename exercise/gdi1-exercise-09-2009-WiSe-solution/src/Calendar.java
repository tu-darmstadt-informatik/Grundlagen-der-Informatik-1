import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Instances of the Calendar class contain CalendarEntries.
 *
 * @author Stefan Radomski, Guido Roessling
 * @version 1.0
 */
public class Calendar {

	/**
	 * the internal storage for calendar entries
	 */
	private List<CalendarEntry> calendarEntries;

	/**
	 * Construct a new calendar object.
	 */
	public Calendar()
	{
		calendarEntries = new ArrayList<CalendarEntry>();
	}

	/**
	 * Add a CalendarEntry to this calendar instance.
	 *
	 * @param calEntry
	 *          The entry to add.
	 */
	public void addCalendarEntry(CalendarEntry calEntry)
	{
		if(!hasCalendarEntry(calEntry))
		{
			calendarEntries.add(calEntry);
		}
	}
	
	/**
	 * Removes a CalendarEntry of this calendar instance.
	 * 
	 * @param calEntry Entry to be removed
	 */
	public void removeCalendarEntry(CalendarEntry calEntry)
	{
		calendarEntries.remove(calEntry);
	}
	
	/**
	 * Checks if a CalendarEntry is part of this Calendar.
	 * 
	 * @param calEntry CalendarEntry to be checked
	 * @return boolean True if Entry is part of Calendar else false.
	 */
	public boolean hasCalendarEntry(CalendarEntry calEntry)
	{
		return calendarEntries.contains(calEntry);
	}
	
	/**
	 * Removes all CalendarEntries from Calendar and replace them
	 * with given Array of CalendarEntries.
	 * 
	 * No checks are performed if Entries are valid!
	 * 
	 * @param calEntries Array of CalendarEntry
	 */
	public void setCalendarEntries(CalendarEntry[] calEntries)
	{
		calendarEntries = Arrays.asList(calEntries);
	}
	
	/**
	 * Returns all CalendarEntries in this Calendar.
	 * 
	 * @return CalendarEntry[] Array of CalendarEntries of Calendar.
	 */
	public CalendarEntry[] getCalendarEntries()
	{
		CalendarEntry[] temparray = new CalendarEntry[calendarEntries.size()];
		calendarEntries.toArray(temparray);
		return temparray; 
	}

  /**
   * Return a textual representation of all CalendarEntries visible for the
   * given user.
   *
   * @param user
   *          The user object for whom to display the CalendarEntries.
   * @return A String representing all the CalendarEntries.
   */
  public String listEntries(User user)
  {
	  StringBuffer sb = new StringBuffer();
	  
	  Iterator<CalendarEntry> it = calendarEntries.iterator();
	  
	  while(it.hasNext())
	  {
		  CalendarEntry ce = it.next();
		  if(ce.isVisible(user))
		  {
			  sb.append(ce.toString());
			  sb.append("\n");
		  }
	  }
	  
	  return sb.toString();
  }
  
  /**
   * Returns new Calendar-Instance which contains only the Entries of
   * this Calendar with are between start- and end-Time.
   * 
   * @param start GregorianCalendar
   * @param end GregorianCalendar
   * @return Calendar
   */
  public Calendar getBetween(GregorianCalendar start, GregorianCalendar end)
  {
	  Calendar tempcal = new Calendar();
	  
	  Iterator<CalendarEntry> it = calendarEntries.iterator();
	  
	  while(it.hasNext())
	  {
		  CalendarEntry ce = it.next();
		  
		  if(ce.isBetween(start,end))
		  {
			  tempcal.addCalendarEntry(ce);
		  }
	  }
	  
	  return tempcal;
  }

  /**
   * Create a new calendar, three users and add some appointments to the
   * calendar.
   *
   * @param args
   *          command-line arguments (ignored in this application)
   */
  public static void main(String[] args) {
	    Calendar cal = new Calendar();

	    Developer paul = new Developer("Paul", "Anderson");
	    Developer mary = new Developer("Mary", "Bobkins");
	    Secretary bob = new Secretary("Adam", "Johanson");

	    Reminder plants = new Reminder(
	        new GregorianCalendar(2010, 3, 12, 14, 30),
	        new GregorianCalendar(2010, 3, 12, 14, 20),
	             "Water the plants", paul);
	    plants.setPrivate(true);
	    cal.addCalendarEntry(plants);

	    Meeting dailyScrum = new Meeting(
	        new GregorianCalendar(2010, 3, 12, 10, 30),
	        new GregorianCalendar(2010, 3, 12, 11, 30),
	             "Discuss yesterdays work", paul);
	    dailyScrum.addParticipant(mary);
	    cal.addCalendarEntry(dailyScrum);

	    Vacation spain = new Vacation(
	        new GregorianCalendar(2010, 2, 3),
	        new GregorianCalendar(2010, 2, 13),
	           "Enjoying the beach in spain", mary);
	    spain.setDelegate(bob);
	    cal.addCalendarEntry(spain);

	    Illness cold = new Illness(
	        new GregorianCalendar(2010, 3, 3),
	        new GregorianCalendar(2010, 3, 4),
	            "Caught a cold, will be back tomorrow", bob);
	    cold.setDelegate(mary);
	    cal.addCalendarEntry(cold);

	    Calendar earlyApril = cal.getBetween(new GregorianCalendar(2010, 3, 3),
	            new GregorianCalendar(2010, 3, 10));

	    System.out.println(cal.listEntries(bob));
	    System.out.println(earlyApril.listEntries(bob));
  }
}
