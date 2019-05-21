package GdICalendarTemplate;

/**
 * User Class Definition
 * used by: CalendarEntries
 *
 * @author Ulf Gebhardt
 * @version 1.0
 */
public class User {

	/**
	 * FirstName of the User
	 */
	private String givenName;
	
	/**
	 * FamilyName of the User
	 */
	private String familyName;
	
  /**
   * The constructor for User objects
   *
   * @param givenName FirstName
   * @param familyName LastName
   */
  public User(String givenName, String familyName)
  {
	  setGivenName(givenName);
	  setFamilyName(familyName);
  }
  
  /**
   * Sets givenName.
   * @param newname
   */
  public void setGivenName(String newname)
  {
	  givenName = newname;
  }

  /**
   * Returns givenName
   * @return String
   */
  public String getGivenName()
  {
	 return givenName;  
  }
  
  /**
   * Returns familyName
   * @return String
   */
  public String getFamilyName()
  {
	return familyName;  
  }
  
  /**
   * Sets familyName
   * @param newname
   */
  public void setFamilyName(String newname)
  {
	  familyName = newname;
  }
  
  /**
   * Returns String representing a User
   * @return String UserName + Lastname
   */
  @Override public String toString()
  {
	  return givenName + " " + familyName;
  }
}