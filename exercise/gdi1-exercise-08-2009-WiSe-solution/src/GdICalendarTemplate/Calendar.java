package GdICalendarTemplate;

/**
 * Import TimeClasses
 */
import java.util.GregorianCalendar;

/**
 * Instances of the Calendar class contain CalendarEntries.
 *
 * @author Ulf Gebhardt
 * @version 1.0
 */
public class Calendar {

  /**
   * Array of CalendarEntry. Startsize is 0.
   */
  private CalendarEntry[] calendarEntries = new CalendarEntry[0];

  /**
   * Constructor -> Not used, Std Constructor
   */
  public Calendar()
  {
  }

  /**
   * Adds a CalendarEntry to the array in Calendar.
   * @param calEntry
   */
  public void addCalendarEntry(CalendarEntry calEntry)
  {
	  CalendarEntry[] ce = calendarEntries;
	  calendarEntries = new CalendarEntry[ce.length+1];
	  System.arraycopy(ce, 0, calendarEntries, 0, ce.length);
	  calendarEntries[calendarEntries.length-1] = calEntry;
  }

  /**
   * Returns String containing Entry.tostring values
   * separated by linebreak.
   * Does not include Private Elements from foreign
   * People. 
   * 
   * @param user
   * @return String
   */
  public String listEntries(User user)
  {
	  StringBuffer s = new StringBuffer();
	  for (int i = 0; i < calendarEntries.length; i++)
	  {
		  if(!calendarEntries[i].isPrivate() || calendarEntries[i].getOwner() == user)
		  {
			  s.append(calendarEntries[i].toString());
			  s.append("\n");
		  }
	  }
	  
	  return s.toString();
  }

  /**
   * Start the Programm,
   * 
   * Add some Users, Entries, Print content.
   * @param args
   */
  public static void main(String[] args) {
    // please leave this method alone for now...
    // create a new calendar
    Calendar cal = new Calendar();

    // create some users
    User paul = new User("Paul", "Anderson");
    User mary = new User("Mary", "Bobkins");
    User bob = new User("Adam", "Johanson");

    // create some entries
    CalendarEntry plants = new CalendarEntry(
			       new GregorianCalendar(2010, 3, 12, 14, 30),
			       "Water the plants", paul);
    plants.setPrivate(true);
    cal.addCalendarEntry(plants);
    
    CalendarEntry cinema = new CalendarEntry(
			       new GregorianCalendar(2010, 3, 12, 18, 30),
			       "Meet Mary for cinema", paul);
    cal.addCalendarEntry(cinema);
    
    CalendarEntry call = new CalendarEntry(
			      new GregorianCalendar(2010, 3, 13, 9, 30),
			      "Call Susi for an appointment with Ron", mary);
    cal.addCalendarEntry(call);
    
    CalendarEntry lunch = new CalendarEntry(
			      new GregorianCalendar(2010, 3, 13, 12, 00),
			      "Lunch with Paul", bob);
    lunch.setPrivate(true);
    cal.addCalendarEntry(lunch);
  
    // print out the list of entries
    System.out.println(cal.listEntries(bob));
  }
}
