/**
 * A Developer is a special type of user
 *
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Developer extends User{

	/**
	 * Create a new Developer.
	 *
	 * @param givenName The developers first name.
	 * @param familyName The developers last name.
	 */
	public Developer(String givenName, String familyName)
	{
		super(givenName, familyName);
	}
}
