package TrainSimulation;

import exceptions.InvalidAreaException;
import exceptions.InvalidCoordinatesException;
import acm.program.DialogProgram;

public class TrainDialog extends DialogProgram{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2064310234134016114L;

  	/**
  	 * Instance of the Train-Simulation
  	 */
  	private TrainSimulation sim;
  	
  	private static String terminate = new String("Program will terminate now");
  
  	/**
	 * Loads a map and interacts with the user
	 */
	public void run()  {
		//Example-Array
		//Attention: This is columnwise
		char arr[][] = { { '0', '0', '#', '0' }, { '0', '~', '0', '^' }, { '0', '~', '0', '#' },
				{ '0', '^', '0', '0' }, { '0', '0', '#', '~' } };

		//initiate new TrainSimulation object with the given area
		//catch correct exceptions
		try
		{
			sim = new TrainSimulation(arr);
		}catch(InvalidAreaException e)
		{
			//show-errortype and terminate
			println(e.getMessage());
			println(terminate);
			return;
		}
		
		//print Area as String to help user to decide which coordinate is correct
		println(sim.toString());
		
		//ask the user for x- and y-coordinates, calculate reachable areas and show the result
		while(true)
		{	
			//ask for x-Coordinate -> -1 = exit
			int x = this.readInt("X-Coordinate of a City");
			if(x == -1)
			{
				println(terminate);
				return;
			}
			
			//ask for y-Coordinate -> -1 = exit
			int y = this.readInt("Y-Coordinate of a City");
			if(y == -1)
			{
				println(terminate);
				return;
			}
			
			boolean exit = true;
			
			try
			{
				//Test Costs -> Not needed
				/*int[][] costs = sim.analyzeCosts(x, y);
				
				String result = "";
				
				if(costs.length > 0)
				{	
					for(int i=0; i<costs[0].length; i++) //count lines
					{
						for(int j=0;j<costs.length;j++) //count within lines
						{
							result +=  Integer.toString(costs[j][i])+" ";
						}
						
						result += "\n";
					}
				}
				
				println(result);*/
				
				//calculate reachable areas
				sim.reachable(x, y);
				
			} catch (InvalidCoordinatesException e)
			{
				//Invalid Coordinates, ask again
				println("Coordinates are invalid, try again");
				exit = false;
			}
			
			//print area -> overview for the user if coordinates were wrong,
			//else show result of reachable-calculation
			println(sim.toString());
			
			if(exit)
			{
				//exit if calculation was successfull
				println(terminate);
				return;
			}
		}

	}
	
	 /**
	   * Main method. This is automatically called by the environment when your
	   * program starts.
	   * 
	   * @param args
	   */
	  public static void main(String[] args) {
	    new TrainDialog().start();
	  }
}
