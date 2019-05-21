package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal;

/**
 * Tests a basic feature of the game: the ability of rotating certain game 
 * elements clockwise and counterclockwise.
 * 
 * @author Christian Merfels
 * 
 */
public class RotateTest extends TestCase {

	PlumberTestAdapterMinimal testAdapter;
	String level = "c6m\n364\n421";
	HashMap<Integer, String> rotatedLevelClockwise, rotatedLevelCounterClockwise;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAdapter = new PlumberTestAdapterMinimal();
		rotatedLevelClockwise = new HashMap<Integer, String>();
		rotatedLevelCounterClockwise = new HashMap<Integer, String>();
		
		rotatedLevelClockwise.put(0, "c6m\n364\n421");
		rotatedLevelClockwise.put(1, "c5m\n364\n421");
		rotatedLevelClockwise.put(2, "c5m\n364\n421");
		rotatedLevelClockwise.put(3, "c5m\n164\n421");
		rotatedLevelClockwise.put(4, "c5m\n154\n421");
		rotatedLevelClockwise.put(5, "c5m\n153\n421");
		rotatedLevelClockwise.put(6, "c5m\n153\n321");
		rotatedLevelClockwise.put(7, "c5m\n153\n341");
		rotatedLevelClockwise.put(8, "c5m\n153\n342");
		
		rotatedLevelCounterClockwise.put(0, "c6m\n364\n421");
		rotatedLevelCounterClockwise.put(1, "c5m\n364\n421");
		rotatedLevelCounterClockwise.put(2, "c5m\n364\n421");
		rotatedLevelCounterClockwise.put(3, "c5m\n464\n421");
		rotatedLevelCounterClockwise.put(4, "c5m\n454\n421");
		rotatedLevelCounterClockwise.put(5, "c5m\n452\n421");
		rotatedLevelCounterClockwise.put(6, "c5m\n452\n221");
		rotatedLevelCounterClockwise.put(7, "c5m\n452\n211");
		rotatedLevelCounterClockwise.put(8, "c5m\n452\n213");
	}
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#rotateClockwiseAtPosition(int, int)}.
	 */
	@Test
	public final void testRotateClockwiseAtPosition() {
		testAdapter.loadLevelFromString(level);
		String levelIs, levelShouldBe;
		for(int i = 0; i < 3; i++) {
			for(int n=0; n < 3; n++) {
				testAdapter.rotateClockwiseAtPosition(n, i);
				levelIs = normalizeLevel(testAdapter.getStringRepresentationOfLevel());
				levelShouldBe = rotatedLevelClockwise.get(i*3+n);
				assertEquals("Wrong element in rotation detected.", levelShouldBe, levelIs);
			}
		}
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#rotateCounterClockwiseAtPosition(int, int)}.
	 */
	@Test
	public final void testRotateCounterClockwiseAtPosition() {
		testAdapter.loadLevelFromString(level);
		String levelIs, levelShouldBe;
		for(int i = 0; i < 3; i++) {
			for(int n=0; n < 3; n++) {
				testAdapter.rotateCounterClockwiseAtPosition(n, i);
				levelIs = normalizeLevel(testAdapter.getStringRepresentationOfLevel());
				levelShouldBe = rotatedLevelCounterClockwise.get(i*3+n);
				assertEquals("Wrong element in rotation detected.", levelShouldBe, levelIs);
			}
		}
	}
	
	/**
	 * Trim the level and remove line breaks. 
	 * @param level the level to work on
	 * @return the same level but in somewhat weak normalized form
	 */
	private final String normalizeLevel(final String level) {
		String normalized = level;
		
		normalized.trim();
		
		if(normalized.endsWith("\r\n")) {
			normalized = normalized.substring(0, normalized.length() - 3);
		}else if(normalized.endsWith("\n") || normalized.endsWith("\r")) {
			normalized = normalized.substring(0, normalized.length() - 1);
		}
		
		return normalized;
	}

}
