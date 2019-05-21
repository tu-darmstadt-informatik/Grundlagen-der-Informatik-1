package exceptions;

public class NotACityException extends InvalidCoordinatesException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6574511033482201479L;

	public NotACityException() {
		super("Error: Coordinates are not a City");
	}
}
