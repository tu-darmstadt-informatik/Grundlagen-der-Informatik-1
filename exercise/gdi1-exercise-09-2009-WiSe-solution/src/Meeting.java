import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

/**
 * A Meeting is a calendar entry with participants and a start and
 * end time.
 *
 * @author Stefan Radomski, Guido R&ouml;&szlig;ling
 * @version 1.0 2009-12-03
 */
public class Meeting extends CalendarEntryDuration
{
	/**
	 * Variable which stores participants of the meeting
	 */
	private List<User> participants;
	
	/**
	 * Create a new Meeting.
	 *
	 * @param startTime The time the meeting will start.
	 * @param endTime The end time of the meeting.
	 * @param description Additional information regarding the meetin.
	 * @param owner The organizer of the meeting.
	 */
	public Meeting(	GregorianCalendar 	startTime,
					GregorianCalendar 	endTime,
					String 				description,
					User 				owner)
	{
		super(startTime,endTime,description,owner);
		participants = new ArrayList<User>();
	}
	
	/**
	 * Checks if user is Allowed to this Meeting
	 * 
	 * @param user
	 * @return boolean
	 */
	private boolean isUserAllowed(User user)
	{
		return user.getClass() == Developer.class ? true : false;
	}
	
	/**
	 * Adds User to Participant-List if user is allowed.
	 * 
	 * @param newUser
	 */
	public void addParticipant(User newUser)
	{
		if(isUserAllowed(newUser) && !hasParticipant(newUser))
		{
			participants.add(newUser);
		}
		
		//error here
	}
	
	/**
	 * Removes given User from Participant-List
	 * 
	 * Do not invoke error if User is not in List!
	 * 
	 * @param user
	 */
	public void removeParticipant(User user)
	{
		participants.remove(user);
	}
	
	/**
	 * Checks if User is in Participant-List.
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean hasParticipant(User user)
	{
		return participants.contains(user);
	}
	
	/**
	 * Sets Participant-List.
	 * Users who are not allowed are removed from List.
	 * 
	 * @param users
	 */
	public void setParticipants(User[] users)
	{
		participants.clear();
		
		for(int i = 0; i < users.length; i++)
		{
			addParticipant(users[i]);
		}
	}
	
	/**
	 * Returns Participant-List.
	 * 
	 * @return User[]
	 */
	public User[] getParticipants()
	{
		User[] temparray = new User[participants.size()];
		participants.toArray(temparray);
		return temparray; 
	}
}
