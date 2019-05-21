package TrainSimulation;

/**
 * Class to store Int or Char in one variable.
 */
public class IntChar {
	
	private int i = -1; //intvar
	private char c = 0; //charvar
	/**
	 * Char or int switch
	 * 
	 * false = char
	 * true = int
	 */
	private boolean ci = false; //false = char; true = int
	
	public int getInt() {
		return i;
	}
	
	/**
	 * Sets ci to true.
	 */
	public void setInt(int i) {
		this.i = i;
		setToInt();
	}
	public char getChar() {
		return c;
	}
	
	/**
	 * Sets ci to false.
	 */
	public void setChar(char c) {
		this.c = c;
		setToChar();
	}
	
	/**
	 * Returns true if ci is set to false!
	 */
	public boolean isChar()
	{
		return !ci;
	}
	
	/**
	 * Returns true if ci is true
	 * @return
	 */
	public boolean isInt()
	{
		return ci;
	}
	
	/**
	 * Sets ci to false
	 */
	public void setToChar() {
		this.ci = false;
	}
	
	/**
	 * Sets ci to true
	 */
	public void setToInt() {
		this.ci = true;
	}

}
