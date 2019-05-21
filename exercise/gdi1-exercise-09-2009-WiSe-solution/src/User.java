/**
 * Instances of the user class own CalendarEntries
 *
 * @author Stefan Radomski, Guido Roessling
 * @version 1.0 2009-12-03
 */
public abstract class User {

  /**
   * the given name of this user
   */
  private String givenName;

  /**
   * the family name of this user
   */
  private String familyName;

  /**
   * The constructor for User objects
   *
   * @param givenName the first name.
   * @param familyName the last name.
   */
  public User(String givenName, String familyName) {
    this.givenName = givenName;
    this.familyName = familyName;
  }

  /**
   * returns a textual representation of this user as a String
   *
   * @return the user in a String representation
   */
  public String toString() {
	  StringBuilder sb = new StringBuilder();
	  sb.append(givenName);
	  if (givenName.length() > 0) {
		  sb.append(" ");
	  }
	  sb.append(familyName);
	  return sb.toString();
  }

  /**
   * returns the given name of this user
   *
   * @return the given name of this user
   */
  public String getGivenName() {
    return givenName;
  }

  /**
   * sets the given name of this user
   *
   * @param givenName the new given name of the user
   */
  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  /**
   * returns the family name of this user
   *
   * @return the family name of this user
   */
  public String getFamilyName() {
    return familyName;
  }

  /**
   * sets the family name of this user
   *
   * @param familyName the new family name of the user
   */
  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }
}
