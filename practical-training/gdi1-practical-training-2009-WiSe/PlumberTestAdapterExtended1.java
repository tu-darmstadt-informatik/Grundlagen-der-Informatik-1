package de.tu_darmstadt.gdi1.plumber.tests.adapters;

/**
 * This is the test adapter for the first extended stage of completion.
 * Implement all method stubs in order for the tests to work.
 * <br><br>
 * <i>Note:</i> This test adapter inherits from the minimal test adapter.
 * 
 * @see PlumberTestAdapterMinimal
 * 
 * @author Jonas Marczona
 * @author Christian Merfels
 * @author Fabian Vogt
 */
public class PlumberTestAdapterExtended1 extends PlumberTestAdapterMinimal {

	/**
	 * Add a highscore entry to the highscore with the given playername and 
	 * time.
	 *  
	 * @param playername the name which shall appear in the highscore
	 * @param time the time which was needed
	 */
	public void addHighscoreEntry(String playername, int reached_level, double time) {
		// TODO: fill stub.
	}
	
	/** 
	 * Return the number of highscore entries in your highscore store.
	 *  
	 * @return the number of highscore entries
	 */
	public int getHighscoreCount() {
		// TODO: fill stub.
		return -1;
	}
	
	/** 
	 * Clear the highscore store and delete all entries.
	 */
	public void resetHighscore() {
		// TODO: fill stub.
	}
	
	/** 
	 * Get the playername of a highscore entry at a given position.
	 * <strong>Note:</strong> The position counting starts at zero. The first entry should contain the <i>best</i> result.
	 * <p>
	 * See the specification in the task assignment for a definition of 'best' in the highscore.<br>
	 * </p>
	 * @param position the position of the highscore entry in the highscore 
	 * store 
	 * @return the playername of the highscore entry at the specified position
	 * or null if the position is invalid 
	 */
	public String getPlayernameAtHighscorePosition(int position) {
		// TODO: fill stub.
		return null;
	}
	
	/** 
	 * Get the needed time of a highscore entry at a given position.
	 * <strong>Note:</strong> The position counting starts at zero. The first entry should contain the <i>best</i> result.
	 * <p>
	 * See the specification in the task assignment for a definition of 'best' in the highscore.<br>
	 * </p>
	 * @param position the position of the highscore entry in the highscore 
	 * store 
	 * @return the time of the highscore entry at the specified position
	 * or -1 if the position is invalid 
	 */
	public double getTimeAtHighscorePosition(int position) {
		// TODO: fill stub.
		return -1;
	}
}
