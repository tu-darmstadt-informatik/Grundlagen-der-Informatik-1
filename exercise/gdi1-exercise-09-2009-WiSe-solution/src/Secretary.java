/**
 * A secretary is a special user for calendars.
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Secretary extends User{

	/**
	 * Creates a new secretary
	 *
	 * @param givenName The first name.
	 * @param familyName The last name.
	 */
	public Secretary(String givenName, String familyName)
	{
		super(givenName,familyName);
	}
}
