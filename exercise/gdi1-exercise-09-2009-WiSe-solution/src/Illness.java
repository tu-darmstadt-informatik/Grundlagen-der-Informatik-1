import java.util.GregorianCalendar;

/**
 * Illness is a type of CalendarEntry that has a start and end time
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Illness extends CalendarEntryDuration {

	/**
	 * Create a new period of illness as it might be reported by a user.
	 *
	 * @param startTime The day the user went absent due to her illness.
	 * @param endTime The day the user plans to return.
	 * @param description Some additional information.
	 * @param owner The user being ill.
	 */
	public Illness(	GregorianCalendar startTime,
		  			GregorianCalendar endTime,
		  			String description,
		  			User owner)
	{
		super(startTime,endTime,description,owner);
	}

}
