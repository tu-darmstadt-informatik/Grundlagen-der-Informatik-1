package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended1;

/**
 * 
 * Tests, whether the highscore is implemented correctly.
 * 
 * @author Jonas Marczona
 *
 */
public class HighscoreTest extends TestCase {


	private PlumberTestAdapterExtended1 testAdapter; 
	private final String player1 = "player1";
	private final double time1 = (double)50*1000;
	private final String player2 = "player2";
	private final double time2 = (double)49*1000;
	private final String player3 = "player3";
	private final double time3 = (double)52*1000;
	/**
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testAdapter = new PlumberTestAdapterExtended1();
		testAdapter.resetHighscore();
		assertEquals("HighscoreCount is wrong, should be 0 after reset. ", 0, testAdapter.getHighscoreCount());
	}
	
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended1#getPlayernameAtHighscorePosition(int)}.
	 */
	@Test
	public final void testGetPlayernameAtHighscorePosition() {
		testAdapter.addHighscoreEntry(player1, 1, time1);
		assertEquals("Received playername is wrong.", player1, testAdapter.getPlayernameAtHighscorePosition(0));
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(1));
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(-1));
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(Integer.MAX_VALUE));
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended1#getTimeAtHighscorePosition(int)}.
	 */
	@Test
	public final void testGetTimeAtHighscorePosition() {
		testAdapter.addHighscoreEntry(player1, 1, time1);
		assertEquals("Received time is wrong.", time1, testAdapter.getTimeAtHighscorePosition(0));
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(1), (double)-1);
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(-1), (double)-1);
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(Integer.MAX_VALUE), (double)-1);
	}

	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended1#getHighscoreCount()}.
	 */
	@Test
	public final void testGetHighscoreCount() {
		testAdapter.addHighscoreEntry(player1, 1, time1);
		assertEquals("HighscoreCount is wrong. ", 1, testAdapter.getHighscoreCount());
		testAdapter.addHighscoreEntry(player2, 1, time2);
		testAdapter.addHighscoreEntry(player3, 1, time3);
		assertEquals("HighscoreCount is wrong. ", 3, testAdapter.getHighscoreCount());
	}
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended1#resetHighscore()}.
	 */
	@Test
	public final void testResetHighscore() {
		testAdapter.addHighscoreEntry(player1, 1, time1);
		assertEquals("HighscoreCount is wrong. ", 1, testAdapter.getHighscoreCount());
		assertEquals("Received playername is wrong.", player1, testAdapter.getPlayernameAtHighscorePosition(0));
		assertEquals("Received time is wrong.", time1, testAdapter.getTimeAtHighscorePosition(0));
		
		testAdapter.resetHighscore();
		assertEquals("HighscoreCount is wrong. ", 0, testAdapter.getHighscoreCount());
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(0), (double)-1);
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(0));
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(1), (double)-1);
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(1));
		
		
		testAdapter.addHighscoreEntry(player2, 1, time2);
		assertEquals("HighscoreCount is wrong. ", 1, testAdapter.getHighscoreCount());
		assertEquals("Received playername is wrong.", player2, testAdapter.getPlayernameAtHighscorePosition(0));
		assertEquals("Received time is wrong.", time2, testAdapter.getTimeAtHighscorePosition(0));
		
		testAdapter.resetHighscore();
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(2), (double)-1);
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(2));
	}
	
	/**
	 * Test method for {@link de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended1#addHighscoreEntry(java.lang.String, double)}.
	 */
	@Test
	public final void testAddHighscoreEntry() {
		testAdapter.addHighscoreEntry(player1, 1, time1);
		assertEquals("HighscoreCount is wrong. ", 1, testAdapter.getHighscoreCount());
		assertEquals("Received playername is wrong.", player1, testAdapter.getPlayernameAtHighscorePosition(0));
		assertEquals("Received time is wrong.", time1, testAdapter.getTimeAtHighscorePosition(0));
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(1), (double)-1);
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(1));

		
		testAdapter.addHighscoreEntry(player2, 1, time2);
		assertEquals("HighscoreCount is wrong. ", 2, testAdapter.getHighscoreCount());
		assertEquals("Highscore sorts wrong, at position 0 should be the best.", player2, testAdapter.getPlayernameAtHighscorePosition(0));
		assertEquals("Highscore sorts wrong, at position 0 should be the best.", time2, testAdapter.getTimeAtHighscorePosition(0));
		assertEquals("Highscore sorts wrong.", player1, testAdapter.getPlayernameAtHighscorePosition(1));
		assertEquals("Highscore sorts wrong.", time1, testAdapter.getTimeAtHighscorePosition(1));
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(2), (double)-1);
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(2));

		
		testAdapter.addHighscoreEntry(player3, 1, time3);
		assertEquals("HighscoreCount is wrong. ", 3, testAdapter.getHighscoreCount());
		assertEquals("Highscore sorts wrong, at position 0 should be the best.", player2, testAdapter.getPlayernameAtHighscorePosition(0));
		assertEquals("Highscore sorts wrong, at position 0 should be the best.", time2, testAdapter.getTimeAtHighscorePosition(0));
		assertEquals("Highscore sorts wrong.", player1, testAdapter.getPlayernameAtHighscorePosition(1));
		assertEquals("Highscore sorts wrong.", time1, testAdapter.getTimeAtHighscorePosition(1));
		assertEquals("Highscore sorts wrong.", player3, testAdapter.getPlayernameAtHighscorePosition(2));
		assertEquals("Highscore sorts wrong.", time3, testAdapter.getTimeAtHighscorePosition(2));
		assertEquals("Time at an invalid index should be -1.", testAdapter.getTimeAtHighscorePosition(3), (double)-1);
		assertNull("Playername at an invalid index should be null.", testAdapter.getPlayernameAtHighscorePosition(3));


	}

}
