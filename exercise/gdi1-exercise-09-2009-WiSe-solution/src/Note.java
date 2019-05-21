import java.util.GregorianCalendar;

/**
 * A note is a calendar entry that stores details
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Note extends CalendarEntry
{	
	/**
	 * Variable to store Details of the Note.
	 */
	private String details;

	/**
	 * Create a new note to be added to a calendar.
	 *
	 * @param time The time this note will become relevant.
	 * @param description Additinal information.
	 * @param owner The owner of the note.
	 */
	public Note(GregorianCalendar time, String description, User owner)
	{
		super(time, description, owner);
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
