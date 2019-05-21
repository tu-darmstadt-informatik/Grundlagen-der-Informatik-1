/**
 * 
 */
package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended3;

/**
 * Tests, whether the solver works correctly.
 * 
 * @author Christian Merfels
 */
public class SolveLevelTest extends TestCase {

	PlumberTestAdapterExtended3 testAdapter;
	LinkedList<String> level;
	String level0 = "c6m\n364\n464";
	String level1 = "16222\n14246\n24a65\n2623k";
	String level2 = "c26234\n12m426\n362146\n366232";
	String unsolvable = "d6l\n364\n464";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAdapter = new PlumberTestAdapterExtended3();
		level = new LinkedList<String>();
		level.add(level0);
		level.add(level1);
		level.add(level2);
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended3#solveLevel()}.
	 */
	@Test
	public final void testSolveLevel() {
		for(int i = 0; i < level.size(); i++) {
			testAdapter.loadLevelFromString(level.get(i));
			testAdapter.solveLevel();
			testAdapter.playGame();
			assertTrue("Level is not solved", testAdapter.isWon());
		}
		
		testAdapter.loadLevelFromString(unsolvable);
		testAdapter.solveLevel();
		testAdapter.playGame();
		
		assertFalse("Unsolvable level was marked as 'solved'", testAdapter.isWon());
	}

}
