package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal;

/**
 * 
 * Tests, whether the game implements the check 
 * for syntactical correctness the right way.
 * 
 * @author Fabian Vogt
 *
 */
public class IsCorrectLevelTest extends TestCase {
	
	private PlumberTestAdapterMinimal testAdapter = new PlumberTestAdapterMinimal();
	
	private HashMap<String, Boolean> levels = new HashMap<String, Boolean>();

	public IsCorrectLevelTest() {
		super();
		
		levels.put("a6m\n364\n464", true);
		levels.put("16222\n14246\n24a65\n2623k", true);
		levels.put("b26234\n12n426\n362146\n366232", true);
		levels.put("36624\n5l212\n41634\n3143d", true);
		levels.put("66m\n364\n464", false);
		levels.put("c6m\n364\n4k4", false);
		levels.put("c63\n364\n454", false);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterMinimal#isCorrectLevel()}.
	 */
	@Test
	public final void testIsCorrectLevel() {
		int i = 0;
		for (String level : levels.keySet()) {
			i++;
			this.testAdapter.loadLevelFromString(level);
			assertEquals("Your implementation identified a correct level as incorrect "
					+ "or an incorrect level as correct.\n" + level + "\n", 
					levels.get(level), 
					Boolean.valueOf(this.testAdapter.isCorrectLevel()));
		}
	}

}
