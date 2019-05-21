package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal;

/**
 * 
 * Tests, whether the game implements the restart keystroke correct.
 * 
 * @author Jonas Marczona
 *
 */
public class HandleRestartLevelKeystrokeTest extends TestCase {

	private final String level = "c6m\n364\n421";
	/** Initial level, rotated at 1|1 and 2|2. */
	private final String level2_2and1_1 = "c6m\n354\n422";
	private PlumberTestAdapterMinimal testAdapter; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAdapter = new PlumberTestAdapterMinimal();
		testAdapter.loadLevelFromString(level);
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#handleKeystroke(java.lang.String)}.
	 */
	@Test
	public final void testHandleKeystroke() {
		assertEquals("The initial level is not correct loaded. May the loadLevelFromString-method is wrong? Or the getStringRepresentationOfLevel-method.", level, testAdapter.getStringRepresentationOfLevel().trim());
		testAdapter.handleKeyPressedNew();
		assertEquals("New game should be the same as before.", level, testAdapter.getStringRepresentationOfLevel().trim());
		testAdapter.rotateClockwiseAtPosition(2, 2);
		testAdapter.rotateClockwiseAtPosition(1, 1);
		testAdapter.rotateClockwiseAtPosition(0, 0);
		
		assertFalse("Your level representation does not change. Please implement getStringRepresentationOfLevel correct.", level.equals(testAdapter.getStringRepresentationOfLevel().trim()));
		assertEquals("Your level representation after two rotates (and one illegal click) is invalid.", level2_2and1_1, testAdapter.getStringRepresentationOfLevel().trim());
		testAdapter.handleKeyPressedNew();
		assertEquals("New game should be the same initial level.", level, testAdapter.getStringRepresentationOfLevel().trim());
	}

}

