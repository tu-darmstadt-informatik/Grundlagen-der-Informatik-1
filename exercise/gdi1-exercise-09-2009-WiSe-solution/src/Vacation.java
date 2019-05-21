import java.util.GregorianCalendar;

/**
 * Vacation represents an absence of a person due to a vacation
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Vacation extends CalendarEntryDuration
{

	/**
	 * Create a new entry of a person's vacation.
   	 *
   	 * @param startTime When the vacation will start.
   	 * @param endTime When the person will be come back.
   	 * @param description Some additional information about the vacation.
   	 * @param owner The person going on vacation.
   	 */
	public Vacation(GregorianCalendar startTime,
					GregorianCalendar endTime,
		  			String description,
		  			User owner)
	{
		super(startTime,endTime,description,owner);
	}
}
