package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended2;

/**
 * Tests, whether the game can handle undo/redo. 
 * 
 * @author Jonas Marczona
 */
public class UndoRedoTest extends TestCase {

	/** The adapter under test. */
	private PlumberTestAdapterExtended2 testAdapter;
	/** Initial level. */
	private final String level = "c6m\n364\n421";
	/** Initial level, rotated at 1|1. */
	private final String level1_1 = "c6m\n354\n421";
	/** Initial level, rotated at 2|2. */
	private final String level2_2 = "c6m\n364\n422";
	/** Initial level, rotated at 1|1 and 2|2. */
	private final String level2_2and1_1 = "c6m\n354\n422";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAdapter = new PlumberTestAdapterExtended2();
		testAdapter.loadLevelFromString(level);
		assertEquals("The initial level is not correct loaded. May the loadLevelFromString-method is wrong? Or the getStringRepresentationOfLevel-method.", level, testAdapter.getStringRepresentationOfLevel().trim());
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended2#undo()}.
	 */
	@Test
	public final void testUndo() {
		testAdapter.rotateClockwiseAtPosition(1, 1);
		testAdapter.undo();
		assertEquals("Position 1|1 was rotated - but also undone. Level should be in initial state.", level, testAdapter.getStringRepresentationOfLevel().trim());

		testAdapter.rotateClockwiseAtPosition(1, 1);

		testAdapter.rotateClockwiseAtPosition(1, 1);
		testAdapter.rotateClockwiseAtPosition(2, 2);
		testAdapter.undo();
		testAdapter.undo();
		assertEquals("Only position 1|1 should be rotated, all other changes are undone.", level1_1, testAdapter.getStringRepresentationOfLevel().trim());

		testAdapter.rotateClockwiseAtPosition(0, 0); //illegal clicks should not matter.
		testAdapter.undo(); //undoes the click to 1|1 in line 49
		assertEquals("No position should be rotated anymore.", level, testAdapter.getStringRepresentationOfLevel().trim());

		testAdapter.undo();
		assertEquals("More undoes than actions should be the initial level.", level, testAdapter.getStringRepresentationOfLevel().trim());
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended2#redo()}.
	 */
	@Test
	public final void testRedo() {
		testAdapter.rotateClockwiseAtPosition(1, 1);
		testAdapter.undo();
		testAdapter.redo();
		assertEquals("Only position 1|1 should be rotated one time.", level1_1, testAdapter.getStringRepresentationOfLevel().trim());
		testAdapter.undo();

		// here we should have the initial level again.

		testAdapter.rotateClockwiseAtPosition(1, 1);

		testAdapter.rotateClockwiseAtPosition(1, 1);
		testAdapter.rotateClockwiseAtPosition(2, 2);
		testAdapter.undo();
		testAdapter.undo();
		testAdapter.redo();
		testAdapter.redo();

		assertEquals("Only position 2|2 should be rotated one time.", level2_2, testAdapter.getStringRepresentationOfLevel().trim());

		testAdapter.rotateClockwiseAtPosition(0, 0); // illegal "clicks" should be no moves for undo
		testAdapter.undo(); // at this point the click to 2|2 should be undone.
		assertEquals("No position should be rotated anymore.", level, testAdapter.getStringRepresentationOfLevel().trim());
		testAdapter.redo();
		testAdapter.rotateClockwiseAtPosition(1, 1);

		assertEquals("Position 1|1 and 2|2 should be rotated.", level2_2and1_1, testAdapter.getStringRepresentationOfLevel().trim());

		testAdapter.undo();
		assertEquals("Only position 2|2 should be rotated one time.", level2_2, testAdapter.getStringRepresentationOfLevel().trim());
	}
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended2#redo()}.
	 */
	@Test
	public final void testRedo_two() {
		testAdapter.redo();
		assertEquals("Redo without any actions before should be take no effect.", level, testAdapter.getStringRepresentationOfLevel().trim());
		
		testAdapter.rotateCounterClockwiseAtPosition(1, 1);
		testAdapter.redo(); //no effect
		testAdapter.undo();
		testAdapter.redo(); 
		
		testAdapter.redo(); //no effect
		assertEquals("More redoes than undoes should end in a level where only position 1|1 is rotated.", level1_1, testAdapter.getStringRepresentationOfLevel().trim());
	}
}
