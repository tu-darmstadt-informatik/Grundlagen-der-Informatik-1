package exceptions;

public class NoObstacleException extends InvalidAreaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 325818858775514458L;

	public NoObstacleException()
	{
		super("Error: No Obstacle on current Map");
	}
	
}
