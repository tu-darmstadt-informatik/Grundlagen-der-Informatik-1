package exceptions;

public class NotEnoughCitiesException extends InvalidAreaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8352668740319857030L;

	public NotEnoughCitiesException()
	{
		super("Error: Not enough Cities on current Map");
	}
}
