package exceptions;

public class NoPlainsException extends InvalidAreaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307803035507617881L;

	
	public NoPlainsException()
	{
		super("Error: No Plain on current Map");
	}
}
