package TrainSimulation;

import exceptions.InvalidAreaException;
import exceptions.InvalidCoordinatesException;
import exceptions.NoObstacleException;
import exceptions.NoPlainsException;
import exceptions.NotACityException;
import exceptions.NotEnoughCitiesException;

public class TrainSimulation {

	//Constants for the Map
	public final char map_city 		= '#';
	public final char map_plain		= '0';
	public final char map_swamp 	= '~';
	public final char map_mountain 	= '^';
	public final char map_train		= '=';
	
	/**
	 * Variable to store Map-Char-Representation
	 */
	private char[][] area;
	
	/**
	 * Constructor sets Map-Char-Representation and throws Exceptions
	 * if its not valid.
	 * 
	 * @param area Map-Char-Representation
	 * @throws InvalidAreaException
	 */
	public TrainSimulation(char[][] area) throws InvalidAreaException
	{
		setArea(area); //use setter-method to check given area
	}
	
	/**
	 * Flood-Algorithm to replace all Positions which are reachable
	 * from pos (x/y) with map_train.
	 * 
	 * A Position is reachable when (x/y) == map_plain || (x/y) == map_city.
	 * 
	 *  Cities are not replaced.
	 *  
	 *  Modifies this.area
	 * 
	 * @param x X-Coordinate
	 * @param y >-Coordinate
	 */
	private void floodArrayWithTrain(int x, int y)
	{
		//check if position is valid
		if(areCoordinatesValid(x, y))
		{
			//skip/anchor -> area not reachable
			if(area[x][y] != map_plain && area[x][y] != map_city)
			{
				return;
			}
			
			//replace only map_plain not map_city
			if(area[x][y] == map_plain)
			{
				area[x][y] = map_train;
			}
			
			//flood-algorithm-recursive call
			floodArrayWithTrain(x,y+1);
			floodArrayWithTrain(x,y-1);
			floodArrayWithTrain(x+1,y);
			floodArrayWithTrain(x-1,y);
		}
	}
	
	private boolean areCoordinatesValid(IntChar[][] array, int x, int y)
	{
		if(		x < 0 ||
				y < 0 ||
				array.length <= x ||
				array[x].length <= y)
		{
			return false;
		}
		
		return true;
	}
	
	private boolean areCoordinatesValid(char[][] array, int x, int y)
	{
		if(		x < 0 ||
				y < 0 ||
				array.length <= x ||
				array[x].length <= y)
		{
			return false;
		}
		
		return true;
	}
	
	private boolean areCoordinatesValid(int x, int y)
	{
		return areCoordinatesValid(area, x, y);
	}
	
	/**
	 * Replaces all map_plain in this.area
	 * if field is reachable
	 * 
	 * Coordinate have to point to a city.
	 * 
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @throws InvalidCoordinatesException
	 */
	public void reachable(int x, int y) throws InvalidCoordinatesException
	{
		//check if coordinates are valid
		if(!areCoordinatesValid(x, y))
		{
			throw new InvalidCoordinatesException("Error: Coordinates are not valid");
		}
		
		//check if position is a city
		if(area[x][y] != map_city)
		{
			throw new NotACityException();
		}
		
		//flood-algorithm
		floodArrayWithTrain(x,y);
	}
	
	/**
	 * Floods given are with Costs-parameters; only replaces
	 * reachable fields.
	 * 
	 * @param array Array with chars/costs-values
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @param value Current costs for the field, if its reachable
	 * @return new Array
	 */
	private IntChar[][] floodArrayWithCosts(IntChar[][] array, int x, int y, int value)
	{
		//check Coordinates are valid
		if(areCoordinatesValid(array,x, y)) //recursion-anchor
		{
			//is Coordinate an integer and value of the field is > current costs
			//  -> replace with current costs
			if(array[x][y].isInt() && array[x][y].getInt() > value)
			{
				array[x][y].setInt(value);
			} else
			{
				//is Coordinate a char and value of the field is map_plain or map_city
				// -> set current costs @ current field
				if(	 array[x][y].isChar() &&
					(array[x][y].getChar() == map_plain ||
					 array[x][y].getChar() == map_city)
				  )
				{
					array[x][y].setInt(value);
				} else
				{
					return array; //recursion-anchor
				}
			}
			
			//Flood-Algorithm
			array = floodArrayWithCosts(array,x,y+1,value+1);
			array = floodArrayWithCosts(array,x,y-1,value+1);
			array = floodArrayWithCosts(array,x+1,y,value+1);
			array = floodArrayWithCosts(array,x-1,y,value+1);
		}
		
		return array;
	}
	
	/**
	 * Returns a integer-field(2-dim) with "costs" of the
	 * all reachable fields from pos (x/y)
	 * 
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @return integer-field(2-dim)
	 * @throws InvalidCoordinatesException
	 */
	public int[][] analyzeCosts(int x, int y) throws InvalidCoordinatesException
	{
		//Check if array is ok
		if(!isAreaSizeOk())
		{
			//should not happen, but to be sure
			throw new InvalidCoordinatesException("Error: Area-Array was not ok");
		}
		
		//Check Coordinates
		if(!areCoordinatesValid(x, y))
		{
			throw new InvalidCoordinatesException("Error: Coordinates are not valid");
		}
		
		//check if position is a city
		if(area[x][y] != map_city)
		{
			throw new NotACityException();
		}
		
		//New array to operate on
		IntChar[][] tarea = new IntChar[area.length][area[0].length];
		for(int i=0; i<area.length;i++)
		{
			for(int j=0; j<area[i].length;j++)
			{
				tarea[i][j] = new IntChar(); //create element
				tarea[i][j].setChar(area[i][j]); //set to value from this.area
			}
		}
		
		//flood Area with Costs-Values
		tarea = floodArrayWithCosts(tarea,x,y,0);
		
		//size is ok! -> created object locally
		int[][] result = new int[tarea.length][tarea[0].length];
		
		//Convert IntChar[][] -> int[][]
		for(int i=0;i<tarea.length;i++)
		{
			for(int j=0;j<tarea[i].length;j++)
			{
				if(tarea[i][j].isInt()) //Calculated Costs in this field?
				{
					//set calculated Costs to result-array
					result[i][j] = tarea[i][j].getInt();
				} else
				{
					//if value is still a char -> set to -1
					result[i][j] = -1;
				}
			}
		}
			
		//return int[][]
		return result;
	}
	
	/**
	 * @return String Returns a String-Representation of the current this.area property.
	 */
	public String toString()
	{
		//result variable
		String result = "";
		
		//check if area has a length
		if(area.length > 0)
		{	
			for(int i=0; i<area[0].length; i++) //count lines
			{
				for(int j=0;j<area.length;j++) //count within lines(columns)
				{
					//add char to result
					result += area[j][i];
				}
				
				//end of line -> add return
				result += "\n";
			}
		}
		
		return result;
	}
	
	/**
	 * Returns true if element is at least count-times present in this.area. else false
	 * 
	 * @param element Element to look for
	 * @param count How many times should the Element appear before returning true
	 * @return All required Elements found ? true : false
	 */
	private boolean hasElement(char element, int count)
	{
		for(int i=0; i<area.length;i++)
		{
			for(int j=0; j<area[i].length;j++)
			{
				if(area[i][j] == element) //pos = element?
				{
					count--; //count down
					
					if(count <= 0) //no more elements required
					{
						return true; //all found -> true
					}
				}
			}
		}
		
		return false; //not all found -> false
	}
	
	/**
	 * @return returns true if this.area has two or more cities else false
	 */
	private boolean hasTwoCities()
	{
		return hasElement(map_city,2);
	}
	
	/**
	 * @return returns true if this.area has one or more plains else false
	 */
	private boolean hasPlainsElement()
	{
		return hasElement(map_plain,1);
	}
	
	/**
	 * @return returns true if this.area has one or more terrainObstacle else false
	 * 
	 * TerrainObstacle = map_mountain, map_swamp
	 */
	private boolean hasTerrainObstacle()
	{
		if(hasElement(map_swamp,1))
		{
			return true;
		}
		
		return hasElement(map_mountain,1);
	}
	
	/**
	 * Checks size of the area, returns true if its ok, else false
	 */
	private boolean isAreaSizeOk()
	{
		if(area.length == 0)
		{
			return false;
		}
		
		int length = area[0].length;
		
		for(int i=1; i<area.length;i++)
		{
			if(area[i].length != length)
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns true if this.area is valid
	 * 
	 * @return
	 * @throws InvalidAreaException
	 */
	private boolean isAreaValid() throws InvalidAreaException
	{
		if(!isAreaSizeOk())
		{
			throw new InvalidAreaException("Error: Area-Size(lines/cloumns) was not ok");
		}
		
		//Two-Cities
		if(!hasTwoCities())
		{
			throw new NotEnoughCitiesException();
		}
		
		//One-Plain
		if(!hasPlainsElement())
		{
			throw new NoPlainsException();
		}
		
		//One-TerrainObstacle
		if(!hasTerrainObstacle())
		{
			throw new NoObstacleException();
		}
		
		return true;
	}
	
	public char[][] getArea() {
		return area;
	}
	public void setArea(char[][] area) throws InvalidAreaException {
		
		this.area = area;
		
		isAreaValid();
	}

}
