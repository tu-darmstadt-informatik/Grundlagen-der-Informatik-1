import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * This class represents an entry in our calendar. All entries in the Calendar
 * are instances of the class CalendarEntry.
 *
 * @author Stefan Radomski, Guido Roessling
 * @version 1.0
 */
public abstract class CalendarEntry {

  /**
   * the description of this calendar entry
   */
  private String description;

  /**
   * the owner of this calendar entry
   */
  private User owner;

  /**
   * mark if this entry is private (only visible to the user)
   */
  private boolean priv;

  /**
   * the date and time for this entry
   */
  protected GregorianCalendar time;

  /**
   * Construct a new CalendarEntry object.
   *
   * @param time the GregorianCalendar representing the date and time of the
   *            entry.
   * @param description a String describing the nature of the entry.
   * @param owner the User object who owns this entry.
   */
  public CalendarEntry(GregorianCalendar time, String description, User owner)
  {
	  this.time = time;
	  this.description = description;
	  this.owner = owner;
  }

  /**
   * Check whether this entry is visible for the given user.
   *
   * @param user The user who wants to see calendar entries.
   * @return A boolean value, indicating the visibility.
   */
  public boolean isVisible(User user) {
    return (user.equals(owner) || !priv);
  }

  /**
   * returns the description of this entry
   *
   * @return the description of this entry
   */
  public String getDescription() {
    return description;
  }

  /**
   * assigns a new description for this entry
   *
   * @param description
   *            the new description for this entry
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * returns the owner of this entry
   *
   * @return the ower of this entry
   */
  public User getOwner() {
    return owner;
  }

  /**
   * assigns a new owner for this entry
   *
   * @param time the new owner for this entry
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

  /**
   * Check whether this is a private entry.
   *
   * @return The privacy flag of the entry.
   */
  public boolean isPrivate() {
    return priv;
  }

  /**
   * Set the privacy of this entry.
   *
   * @param priv set to true for private or false for public (default).
   */
  public void setPrivate(boolean priv) {
    this.priv = priv;
  }

  /**
   * returns the date and time of this entry
   *
   * @return the date and time of this entry
   */
  public GregorianCalendar getTime() {
    return time;
  }

  /**
   * assigns a new date and time for this entry
   *
   * @param time the new date and time for this entry
   */
  public void setTime(GregorianCalendar time)
  {
    this.time = time;
  }
 
  /**
   * Returns true if CalendarEntry is between start- and end-Time else false.
   * 
   * @param start
   * @param end
   * @return boolean
   */
  public boolean isBetween(GregorianCalendar start, GregorianCalendar end)
  {
	  if(	time.getTime().getTime() >= start.getTime().getTime() 	&&
			time.getTime().getTime() <= end.getTime().getTime()		)
	  {
		  return true;
	  }
	  
	  return false;
  }

  /**
   * returns a String representation of this CalendarEntry.
   *
   * @return a String representing this object
   */
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy - kk:mm:ss");
    
    sb.append(sdf.format(time.getTime()));
    sb.append(" von ").append(owner.getGivenName()).append(" ");
    sb.append(owner.getFamilyName());
    sb.append(" | ").append(description);
    
    return sb.toString();
  }
}
