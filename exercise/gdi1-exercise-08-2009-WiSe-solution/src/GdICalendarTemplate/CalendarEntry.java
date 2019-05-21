package GdICalendarTemplate;

/**
 * Import Time/Date/Converter Functionality
 */
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * All entries in the Calendar are instances of the class CalendarEntry.
 *
 * @author Ulf Gebhardt
 * @version 1.0
 */
public class CalendarEntry {
 
	/**
	 * DateTime Stamp
	 */
	private GregorianCalendar time;
	/**
	 * Description what to do/remind
	 */
	private String description;
	/**
	 * Owner of Entry
	 */
	private User owner;
	/**
	 * Determines if Entry is visible for all. 
	 */
	private boolean priv;
	
	/**
	 * Constructor of CalendarEntry
	 * 
	 * @param time Timestamp (GregorianCalendar)
	 * @param description Description(String)
	 * @param owner (User)
	 */
	public CalendarEntry(GregorianCalendar time, String description, User owner)
	{
		setTime(time);
		setDescription(description);
		setOwner(owner);
	}

	/**
	 * Returns the string-representation of
	 * a CalendarEntry of the following form:
	 * 
	 * DAYSTRING, DAY MONTH YEAR - HOUR:MIN:SEC by USERFIRSTNAME USERLASTNAME | DESCRIPTION
	 * 
	 * @return String
	 */
	@Override public String toString()
	{
		StringBuffer temp = new StringBuffer();
	
		SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy - HH:mm:ss", Locale.GERMAN);
		
		temp.append(sdf.format(time.getTime()));
		temp.append(" by ");
		temp.append(owner.toString());
		temp.append(" | ");
		temp.append(description);
		
		return temp.toString();
	}
	
	/**
	 * Set Timestamp
	 * 
	 * @param newtime GregorianCalendar
	 */
	public void setTime(GregorianCalendar newtime)
	{
		time = newtime;
	}
	
	/**
	 * Set Timestamp
	 * 
	 * @return GregorianCalendar
	 */
	public GregorianCalendar getTime()
	{
		return time;
	}
	
	/**
	 * set new Description
	 * 
	 * @param newdesc String
	 */
	public void setDescription(String newdesc)
	{
		description = newdesc;
	}
	
	/**
	 * Returns Description
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Set new Owner
	 * 
	 * @param newowner User
	 */
	public void setOwner(User newowner)
	{
		owner = newowner;
	}
	
	/**
	 * Returns current Owner
	 * 
	 * @return User
	 */
	public User getOwner()
	{
		return owner;
	}
	
	/**
	 * Set Private-Flag
	 * 
	 * @param newpriv boolean
	 */
	public void setPrivate(boolean newpriv)
	{
		priv = newpriv;
	}
	
	/**
	 * Returns true if Entry is Private.
	 * 
	 * @return boolean
	 */
	public boolean isPrivate()
	{
		return priv;
	}
}
