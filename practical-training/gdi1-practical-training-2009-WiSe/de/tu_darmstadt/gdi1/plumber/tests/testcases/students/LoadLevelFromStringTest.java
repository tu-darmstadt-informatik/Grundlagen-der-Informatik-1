package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal;

/**
 * Tests, whether levels can be loaded from strings.
 * 
 * @author Fabian Vogt
 *
 */
public class LoadLevelFromStringTest extends TestCase {
	
	private PlumberTestAdapterMinimal testAdapter = new PlumberTestAdapterMinimal();
	
	private List<String> levels = new LinkedList<String>();

	public LoadLevelFromStringTest() {
		super();
		
		levels.add("c6m\n364\n464");
		levels.add("16222\n14246\n24a65\n2623k");
		levels.add("b26234\n12n426\n362146\n366232");
		levels.add("36624\n5l212\n41634\n3143d");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#loadLevelFromString(java.lang.String)}.
	 */
	@Test
	public final void testLoadLevelFromString() {
		
		for (int i = 0; i < this.levels.size(); i++) {
			String level = this.levels.get(i);
			testAdapter.loadLevelFromString(level);
			String representation = testAdapter.getStringRepresentationOfLevel();
			
			representation = representation.replace("\r", "");
			representation = representation.replaceAll("[\n][\n]*", "\n");
			representation = representation.replaceAll("^#[^\n]*\n", "");
			representation = representation.replaceAll("\n\\Z", "");
			
			assertEquals("The string representation of level " + i + " does not match the expected representation.", level, representation);
		}
		
	}

}
