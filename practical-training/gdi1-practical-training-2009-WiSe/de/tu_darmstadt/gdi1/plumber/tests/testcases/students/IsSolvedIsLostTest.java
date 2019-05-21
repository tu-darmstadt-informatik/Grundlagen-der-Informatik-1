package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal;

/**
 * Tests, whether solved/lost level are correctly recognized.
 * 
 * @author Christian Merfels
 *
 */
public class IsSolvedIsLostTest extends TestCase {
	
	PlumberTestAdapterMinimal testAdapter;
	LinkedList<String> solvedLevels, lostLevels;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		testAdapter = new PlumberTestAdapterMinimal();

		solvedLevels = new LinkedList<String>();
		
		solvedLevels.add("c6m\n462\n464");
		solvedLevels.add("16231\n14325\n24a65\n2623k");
		solvedLevels.add("566314\n313k41\n366662\n5b6142\n466264");
		
		lostLevels = new LinkedList<String>();
		
		lostLevels.add("c6m\n364\n464");
		lostLevels.add("16222\n14246\n24a65\n2623k");
		lostLevels.add("c26234\n12m426\n362146\n366232");
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#isWon()}.
	 */
	@Test
	public final void testIsSolved() {
		for(String level : solvedLevels) {
			System.out.println(level.toString());
			testAdapter.loadLevelFromString(level);
			testAdapter.playGame();
			System.out.println("lost: " + testAdapter.isLost() + "\tsolved: " + testAdapter.isWon());
			assertTrue("Game has not been recognized as solved.", testAdapter.isWon());
		}
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#isLost()}.
	 */
	@Test
	public final void testIsLost() {
		for(String level : lostLevels) {
			testAdapter.loadLevelFromString(level);
			testAdapter.playGame();
			assertTrue("Game has not been recognized as lost.", testAdapter.isLost());
		}
	}

}
