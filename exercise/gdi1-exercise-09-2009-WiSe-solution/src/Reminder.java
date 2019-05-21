import java.util.GregorianCalendar;

/**
 * A reminder is a calendar entry that appears at a defined
 * point in time but has no duration.
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Reminder extends CalendarEntry
{	
	/**
	 * The time when to remind the owner about this entry.
	 */
	private GregorianCalendar alarmTime;

	/**
	 * Create a new reminder to be added in a calendar.
	 *
	 * @param time The time this reminder references.
	 * @param alarmTime When to alert the owner about this reminder.
	 * @param description Some descriptive information.
	 * @param owner The user owning this entry.
	 */
	public Reminder(GregorianCalendar 	time, 
                  	GregorianCalendar 	alarmTime, 
                  	String 				description,
                  	User 				owner)
	{
		super(time,description,owner);
		setAlarmTime(alarmTime);
	}

	public GregorianCalendar getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(GregorianCalendar alarmTime) {
		if(alarmTime.before(time))
		{
			this.alarmTime = alarmTime;
		}
	}
}
