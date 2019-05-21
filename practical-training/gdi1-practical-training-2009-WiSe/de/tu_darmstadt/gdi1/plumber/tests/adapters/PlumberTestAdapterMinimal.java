package de.tu_darmstadt.gdi1.plumber.tests.adapters;

/**
 * This is the test adapter for the minimal stage of completion. You 
 * <strong>must</strong> implement the method stubs and match them to your concrete 
 * implementation.
 * <br><strong>Important:</strong> This class should not contain any real game
 * logic, you should rather only match the method stubs to your game.
 * <br>Example: in {@link #isCorrectLevel()} you may return the value 
 * <i>myGame.isCorrectlevel()</i>, if you have a variable <i>myGame</i> and a
 * level has before been loaded via {@link #loadLevelFromString(String)}.
 * <br> You should <strong>not</strong> implement the actual logic of the method in <i>this</i> class.
 * <br><br>
 * If you have implemented the minimal stage of completion, you should be able
 * to implement all method stubs. The public and private JUnit tests for the
 * minimal stage of completion will be run on this test adapter. The other test
 * adapters will inherit from this class, because they need the basic methods
 * (like loading a level), too.
 * <br><br>
 * The methods of all test adapters need to function without any kind of user 
 * interaction.
 * 
 * @author Jonas Marczona
 * @author Christian Merfels
 * @author Fabian Vogt
 */
public class PlumberTestAdapterMinimal {

	public PlumberTestAdapterMinimal() {
		// TODO: fill stub.
	}
	
	
	/** 
	 * Construct a level from a string. You will be given a string 
	 * representation of a level and you need to load this into your game.
	 * Line breaks will be delimited by \n. Your game should hold the level
	 * until a new one is loaded; the other methods will assume that this very
	 * level is the current one and that the actions (like rotating elements)
	 * will run on the specified level.
	 * @param levelstring a string representation of the level to load
	 * @see #isCorrectLevel()
	 */
	public void loadLevelFromString(String levelstring) {
		// TODO: fill stub.
	}
	
	/** 
	 * Return the string representation of the current level.
	 * A level loaded with the method {@link #loadLevelFromString()} 
	 * should be the same as the result of 
	 * {@link #getStringRepresentationOfLevel()}
	 * as long as no actions has been performed in the meantime. <br>
	 * But if there were (valid) actions in the meantime this have to be visible in the level representation.<br>
	 * The level format is the same
	 * as the one specified for loading levels. 
	 * 
	 * @return string representation of the current level
	 * 		or null if no valid level was loaded
	 * 
	 * @see #loadLevelFromString(String)
	 * @see #isCorrectLevel()
	 */
	public String getStringRepresentationOfLevel() {
		// TODO: fill stub.
		return null;
	}
	
	/** 
	 * Is the loaded level a syntactically correct level?
	 * See the specification in the task assignment for a definition of
	 * 'syntactical correct'.<br>
	 * You don't need to implement a solvability evaluation here.  
	 *  
	 * @return if the currently loaded level is syntactically correct return true, 
	 * otherwise return false
	 * 
	 * @see #loadLevelFromString(String)
	 * @see #getStringRepresentationOfLevel()
	 */
	public boolean isCorrectLevel() {
		// TODO: fill stub.
		return false;
	}
	
	/**
	 * Has the current level been won? If a level has been loaded, this should
	 * return false. Even if a source and a sink are connected, but the water
	 * hasn't flown yet, this should return false. If a level has been loaded
	 * and the necessary actions have been made (or the source and sink were
	 * connected from the beginning), and the method {@link #playGame()} was
	 * called and the level has been won, the return value has to be 'true'.
	 *     
	 * @return if the current level has been won return true, otherwise return 
	 * false*/
	public boolean isWon() {
		// TODO: fill stub.
		return false;
	}
	
	/** 
	 * Has the current level been lost? In accordance to {@link #isWon()},
	 * you should return 'true' if and only if a level was loaded, 
	 * {@link #playGame()} was called and the source and sink were not
	 * connected in the end. Before or while a game is running, 'false' shall
	 * be returned. {@link #isWon()} and {@link #isLost()} may be 'false', 
	 * but never both be 'true' at the same time.
	 * 
	 * @return  if the level has been lost after the water has flown return true, 
	 * otherwise return false
	 */
	public boolean isLost() {
		// TODO: fill stub.
		return false;
	}	
	
	/** 
	 * Rotate an element at a specified position (clockwise).
	 * <BLOCKQUOTE>
	 * 			<b>Notice:<br></b>
	 * 			[0,0] is the upper left point on the board.<br>
	 *			if a and b were the maximal coordinates:<br>
	 * 			[a,b] is the lower right point on the board,<br> 
	 *			[0,b] is the lower left point on the board<br>
	 *			[a,0] is the upper right point on the board <br>
	 * </BLOCKQUOTE>
	 * @param x the x coordinate of the element to rotate
	 * @param y the y coordinate of the element to rotate
	 */
	public void rotateClockwiseAtPosition(int x, int y) {
		// TODO: fill stub.
	}
	
	/**
	 * Is the element at a specified position filled with water?
	 * 
	 * <BLOCKQUOTE>
	 * 			<b>Notice:<br></b>
	 * 			[0,0] is the upper left point on the board.<br>
	 *			if a and b were the maximal coordinates:<br>
	 * 			[a,b] is the lower right point on the board,<br> 
	 *			[0,b] is the lower left point on the board<br>
	 *			[a,0] is the upper right point on the board <br>
	 * </BLOCKQUOTE>
	 * 
	 * @param x the x coordinate of the element
	 * @param y the y coordinate of the element
	 * @return
	 */
	public boolean isFilledWithWater(int x, int y) {
		// TODO: fill stub.
		return false;
	}
	
	/** 
	 * Rotate an element at a specified position (counter-clockwise). 
	 * 
	 * @see #isFilledWithWater(int, int)
	 * @see #rotateClockwiseAtPosition(int, int)
	 * 
	 * @param x the x coordinate of the element to rotate
	 * @param y the y coordinate of the element to rotate
	 */
	public void rotateCounterClockwiseAtPosition(int x, int y) {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyNewGamePressed()}.
	 */
	public void handleKeyPressedNew() {
		// TODO: fill stub.
	}
	
	/** 
	 * Start and play the game: the water shall start to flow. The flow has to
	 * be ended at the end of this method, either because it reached the sink
	 * or a wall.
	 */
	public void playGame() {
		// TODO: fill stub.
	}
}
