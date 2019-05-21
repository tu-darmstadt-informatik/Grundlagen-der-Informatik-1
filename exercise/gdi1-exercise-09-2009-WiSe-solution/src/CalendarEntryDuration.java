import java.util.GregorianCalendar;

/**
 * This Class represents a CalendarEntry with a duration.
 * 
 * @author Ulf Gebhardt
 * @version 1.0
 */

public class CalendarEntryDuration extends CalendarEntry
{
    /**
     * Start-Time of the Duration
     */
	private GregorianCalendar startTime;

	/**
	 * End-Time of the Duration
	 */
	private GregorianCalendar endTime;
	
	/**
	 * Delegate which replaces absent Person
	 */
	private User delegate;

	/**
	 * Constructor of CalendarEntryDuration.
	 * 
	 * You have to pass a aditional start- and end-Time.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param description
	 * @param owner
	 */
	public CalendarEntryDuration(   GregorianCalendar startTime,
			 						GregorianCalendar endTime,
			 						String description,
			 						User owner)
	{
		super(startTime,description,owner);
		setStartTime(startTime);
		setEndTime(endTime);
	}
	
	public GregorianCalendar getStartTime() {
		return startTime;
	}

	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = startTime;
	}

	public GregorianCalendar getEndTime() {
		return endTime;
	}

	public void setEndTime(GregorianCalendar endTime) {
		this.endTime = endTime;
	}
	
	public User getDelegate() {
		return delegate;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}
	
	/**
	 * returns Duration of CalendarEntry on given field.
	 * 
	 * @param field
	 * @return integer
	 */
	public int getDuration(int field)
	{
		double timedif = endTime.getTimeInMillis() - startTime.getTimeInMillis();
		
		switch(field)
		{
			case GregorianCalendar.DATE:
			{
				return (int) Math.floor(timedif / (1000*60*60*24));  
			}
			case GregorianCalendar.HOUR:
			{
				return (int) Math.floor(timedif / (1000*60*60));
			}
			case GregorianCalendar.MINUTE:
			{
				return (int) Math.floor(timedif / (1000*60));
			}
			case GregorianCalendar.SECOND:
			{
				return (int) Math.floor(timedif / (1000));
			}
			default:
			{
				return 0;
			}
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public boolean spans(GregorianCalendar date)
	{
		if(	(date.getTime().getTime() - startTime.getTime().getTime()) 	>= 0 &&
			(date.getTime().getTime() - endTime.getTime().getTime()) 	<= 0 )
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns true if CalendarEntry is between start- and end-Time else false.
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	@Override
	public boolean isBetween(GregorianCalendar start, GregorianCalendar end)
	{
		if(	(startTime.getTime().getTime() >= start.getTime().getTime() 	&&
			 startTime.getTime().getTime() <= end.getTime().getTime()		) ||
			(endTime.getTime().getTime() >= start.getTime().getTime() 		&&
			 endTime.getTime().getTime() <= end.getTime().getTime()			))
		{
			return true;
		}
		  
		return false;
	}
	
	/**
	 * Checks if given User is allowed to view this CalendarEntry.
	 * 
	 * @param user
	 * @return boolean
	 */
	@Override
	public boolean isVisible(User user)
	{
		if(user == this.getOwner() || !isPrivate() || user == delegate)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 *  returns a String representation of this CalendarEntry.
	 *  
	 *  Warning, do not count months and years!!!
	 *
	 * @return a String representing this object
	 */
	@Override
	public String toString()
	{
		String result = super.toString() + " - about ";
		
		if(getDuration(GregorianCalendar.DATE) > 0)
		{
			if(Math.floor((double)getDuration(GregorianCalendar.DATE) / 365) > 0)
			{
				return result + (int)Math.floor((double)getDuration(GregorianCalendar.DATE) / 365) + " year(s)";
			}
			
			return result + getDuration(GregorianCalendar.DATE) + " day(s)";
		}
		
		if(getDuration(GregorianCalendar.HOUR) > 0)
		{
			return result + getDuration(GregorianCalendar.HOUR) + " hour(s)";
		}
		
		if(getDuration(GregorianCalendar.MINUTE) > 0)
		{
			return result + getDuration(GregorianCalendar.MINUTE) + " minute(s)";
		}
		
		if(getDuration(GregorianCalendar.SECOND) > 0)
		{
			return result + getDuration(GregorianCalendar.SECOND) + " second(s)";
		}
		
		return super.toString();
	}
}