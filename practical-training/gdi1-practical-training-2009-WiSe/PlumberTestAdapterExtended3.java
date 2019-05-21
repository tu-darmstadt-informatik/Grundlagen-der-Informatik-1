package de.tu_darmstadt.gdi1.plumber.tests.adapters;

/**
 * This is the test adapter for the third extended stage of completion.
 * Implement all method stubs in order for the tests to work.
 * <br><br>
 * <i>Note:</i> This test adapter inherits from the second extended test 
 * adapter.
 * 
 * @see PlumberTestAdapterMinimal
 * @see PlumberTestAdapterExtended1
 * @see PlumberTestAdapterExtended2
 * 
 * @author Jonas Marczona
 * @author Christian Merfels
 * @author Fabian Vogt
 */
public class PlumberTestAdapterExtended3 extends PlumberTestAdapterExtended2 {
	
	/**
	 * Generate a level with the given width and height.
	 * The generated level has to be solvable. It should be returned as
	 * string representation of a valid level as described in the 
	 * documentation.
	 * 
	 * <br>Counting starts at 1, that means a 3x3 board has a width and 
	 * height of three.
	 * 
	 * @param x the width of the generated level
	 * @param y the height of the generated level
	 */
	public String generateLevel(int width, int height) {
		// TODO: fill stub.
		return "";
	}
	
	/** 
	 * Automatically solve the current level. There shall be a connection 
	 * between the source and the sink in the level, but the water shall
	 * not start to flow automatically.
	 */
	public void solveLevel() {
		// TODO: fill stub.
	}
}