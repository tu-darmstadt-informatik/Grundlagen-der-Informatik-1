/**
 * 
 */
package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import junit.framework.TestCase;

import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended3;

/**
 * Tests, whether the game implementation can successfully generate level.
 * 
 * @author Christian Merfels
 * 
 */
public class GenerateLevelTest extends TestCase {

	PlumberTestAdapterExtended3 testAdapter;
	
	public GenerateLevelTest() {
		testAdapter = new PlumberTestAdapterExtended3();
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended3#generate(int, int)}.
	 */
	@Test
	public final void testGenerateLevel() {
		String newLevel;
		for(int i=0; i < 25; i++) {
			newLevel = testAdapter.generateLevel(randomDimension(15), randomDimension(10));
			testAdapter.loadLevelFromString(newLevel);
			assertTrue("Level is not a correct level", testAdapter.isCorrectLevel());
		}
	}
	
	/**
	 * Helper function. Returns a random integer x with 2 <= x <= pMax.
	 * @param pMax the biggest possible integer
	 * @return a random integer
	 */
	private final int randomDimension(final int pMax) {
		return (int) ((Math.random()*(pMax-1))+2);
	}

}
