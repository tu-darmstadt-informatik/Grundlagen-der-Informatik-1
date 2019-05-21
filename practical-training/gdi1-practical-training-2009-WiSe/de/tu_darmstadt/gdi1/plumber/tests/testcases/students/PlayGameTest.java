package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal;

/**
 * Tests, whether the game can be played correctly.
 * 
 * @author Jonas Marczona
 * @author Fabian Vogt
 *
 */
public class PlayGameTest extends TestCase {

	private final String levelCorrect = "16231\n14325\n24a65\n2623k";
	private final String levelNotCorrect = "16221\n14325\n24a65\n2623k";
	
	private PlumberTestAdapterMinimal testAdapter; 

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAdapter = new PlumberTestAdapterMinimal();
		testAdapter.loadLevelFromString(levelCorrect);
		assertEquals("The initial level is not correct loaded. May the loadLevelFromString-method is wrong? Or the getStringRepresentationOfLevel-method.", levelCorrect, testAdapter.getStringRepresentationOfLevel().trim());
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#playGame()}.
	 */
	@Test
	public final void testPlayGameWon() {
		testAdapter.playGame();
		assertTrue(testAdapter.isWon());
	}
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#playGame()}.
	 */
	@Test
	public final void testPlayGameLost() {
		testAdapter.loadLevelFromString(levelNotCorrect);
		testAdapter.playGame();
		assertTrue(testAdapter.isLost());
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#playGame()}.
	 */
	@Test
	public final void testPlayGameWonWater() {
		testAdapter.playGame();
		assertTrue(testAdapter.isWon());
		
		assertFalse(testAdapter.isFilledWithWater(0, 0));
		assertFalse(testAdapter.isFilledWithWater(1, 0));
		assertFalse(testAdapter.isFilledWithWater(2, 0));
		assertTrue(testAdapter.isFilledWithWater(3, 0));
		assertTrue(testAdapter.isFilledWithWater(4, 0));
		
		assertFalse(testAdapter.isFilledWithWater(0, 1));
		assertFalse(testAdapter.isFilledWithWater(1, 1));
		assertTrue(testAdapter.isFilledWithWater(2, 1));
		assertTrue(testAdapter.isFilledWithWater(3, 1));
		assertTrue(testAdapter.isFilledWithWater(4, 1));

		assertFalse(testAdapter.isFilledWithWater(0, 2));
		assertFalse(testAdapter.isFilledWithWater(1, 2));
		assertTrue(testAdapter.isFilledWithWater(2, 2));
		assertFalse(testAdapter.isFilledWithWater(3, 2));
		assertTrue(testAdapter.isFilledWithWater(4, 2));
		
		assertFalse(testAdapter.isFilledWithWater(0, 3));
		assertFalse(testAdapter.isFilledWithWater(1, 3));
		assertFalse(testAdapter.isFilledWithWater(2, 3));
		assertFalse(testAdapter.isFilledWithWater(3, 3));
		assertTrue(testAdapter.isFilledWithWater(4, 1));

	}
	
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#playGame()}.
	 */
	@Test
	public final void testPlayGameLostWater() {
		testAdapter.loadLevelFromString(levelNotCorrect);
		testAdapter.playGame();
		assertTrue(testAdapter.isLost());
		
		//Row 1
		assertFalse(testAdapter.isFilledWithWater(0, 0));
		assertFalse(testAdapter.isFilledWithWater(1, 0));
		assertFalse(testAdapter.isFilledWithWater(2, 0));
		/*
		 * at this position the water flowed out of the pipe 
		 * it is not defined if this field is filled with water or not.
		 * assert??(testAdapter.isFilledWithWater(3, 0)); 
		 */
		assertFalse(testAdapter.isFilledWithWater(4, 0));
		
		//Row 2
		assertFalse(testAdapter.isFilledWithWater(0, 1));
		assertFalse(testAdapter.isFilledWithWater(1, 1));
		assertTrue(testAdapter.isFilledWithWater(2, 1));
		assertTrue(testAdapter.isFilledWithWater(3, 1));
		assertFalse(testAdapter.isFilledWithWater(4, 1));

		//Row 3
		assertFalse(testAdapter.isFilledWithWater(0, 2));
		assertFalse(testAdapter.isFilledWithWater(1, 2));
		assertTrue(testAdapter.isFilledWithWater(2, 2));
		assertFalse(testAdapter.isFilledWithWater(3, 2));
		assertFalse(testAdapter.isFilledWithWater(4, 2));
		
		//Row 4
		assertFalse(testAdapter.isFilledWithWater(0, 3));
		assertFalse(testAdapter.isFilledWithWater(1, 3));
		assertFalse(testAdapter.isFilledWithWater(2, 3));
		assertFalse(testAdapter.isFilledWithWater(3, 3));
		assertFalse(testAdapter.isFilledWithWater(4, 1));
	}
	
	
	
}
