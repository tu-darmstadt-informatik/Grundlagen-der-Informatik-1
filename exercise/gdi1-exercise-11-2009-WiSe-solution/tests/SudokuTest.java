import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SudokuTest {

	/**
	 * Test cases for the solver. Public, because you can also use them to use
	 * them for game initialization!
	 */
	public final static int[][] testCase1 = new int[][] {
			{ 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
			{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
			{ 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
			{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
			{ 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

	public final static int[][] testCase1Result = new int[][] {
			{ 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
			{ 1, 9, 8, 3, 4, 2, 5, 6, 7 }, { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
			{ 4, 2, 6, 8, 5, 3, 7, 9, 1 }, { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
			{ 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
			{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

	public final static int[][] testCase2 = new int[][] {
			{ 1, 0, 2, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 0, 4, 0, 0, 5, 0, 0, 0, 0 },
			{ 0, 6, 0, 0, 7, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 2, 0 },
			{ 0, 8, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 8, 0, 0 } };

	public final static int[][] testCase2Result = new int[][] {
			{ 1, 5, 2, 3, 4, 6, 7, 8, 9 }, { 4, 7, 3, 1, 8, 9, 2, 5, 6 },
			{ 6, 9, 8, 5, 2, 7, 1, 3, 4 }, { 2, 4, 1, 6, 5, 3, 9, 7, 8 },
			{ 5, 6, 9, 2, 7, 8, 3, 4, 1 }, { 8, 3, 7, 4, 9, 1, 6, 2, 5 },
			{ 3, 8, 4, 9, 1, 2, 5, 6, 7 }, { 7, 1, 6, 8, 3, 5, 4, 9, 2 },
			{ 9, 2, 5, 7, 6, 4, 8, 1, 3 } };
	
	final int[][] testField3 = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 4, 5, 6, 7 },
			{ 0, 0, 2, 0, 0, 0, 4, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 4, 0, 0, 0, 2, 0, 0 }, { 0, 0, 5, 4, 0, 2, 1, 0, 0 },
			{ 0, 0, 6, 0, 0, 0, 0, 0, 0 }, { 0, 0, 7, 0, 0, 0, 0, 0, 0 } };

	//From template
	public final int[][] fullField = new int[][] { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
			{ 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
			{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
			{ 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
	
	final int[][] sudoku17_1 = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 1, 0}, { 0, 6, 0, 3, 0, 0, 0, 0, 0},
			{9, 0, 0, 0, 0, 0, 0, 7, 0}, { 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 4, 0, 0, 6, 0, 0}, { 0, 0, 6, 0, 0, 0, 5, 0, 3},
			{0, 0, 0, 0, 9, 0, 0, 0, 0}, { 0, 2, 0, 0, 0, 0, 4, 0, 0},
			{0, 0, 8, 0, 1, 7, 0, 0, 0}
			};
	
	/**
	 * The test instance.
	 */
	private Sudoku sudoku;

	/**
	 * Sets up the test environment.
	 */
	@Before
	public void setUp() {
		sudoku = new Sudoku();
	}

	/**
	 * Timing check
	 */
	/*
	//This one is quite nasty
	@Test
	public void testTimingField17()
	{
		Assert.assertNull(sudoku.solve(sudoku17_1));
	}*/
	
	/**
	 * Timing check2
	 */
	@Test
	public void testTimingField3()
	{
		Assert.assertNull(sudoku.solve(testField3));
	}
	
	/**
	 * Test if predefined fullfield is ok.
	 */
	@Test
	public void testFullField()
	{
		//Question was raised - so i do the test
		Assert.assertFalse(sudoku.reject(fullField));
	}
	
	/**
	 * Tests the {@link Sudoku#reject(int[][])} method.
	 */
	@Test
	public void testReject() {
		int[][] config = new int[9][9];

		// Empty field is always valid!
		Assert.assertFalse("reject false negative: rejected empty field!",
				sudoku.reject(config));

		// Check rows
		config = new int[9][9];
		config[5][1] = 9;
		config[5][7] = 9;
		Assert.assertTrue("reject false negative: row check failed!", sudoku
				.reject(config));

		config[5][7] = 6;
		Assert.assertFalse("reject false positive: row check failed!", sudoku
				.reject(config));

		// Check columns
		config = new int[9][9];
		config[5][1] = 9;
		config[8][1] = 9;
		Assert.assertTrue("reject false negative: column check failed!", sudoku
				.reject(config));

		config[8][1] = 3;
		Assert.assertFalse("reject false positive: column check failed!",
				sudoku.reject(config));

		// Check 3x3 fields
		config = new int[9][9];
		config[0][0] = 7;
		config[2][2] = 7;
		Assert.assertTrue("reject false negative: 3x3 field check failed!",
				sudoku.reject(config));

		config[2][2] = 4;
		Assert.assertFalse("reject false positive: 3x3 field check failed!",
				sudoku.reject(config));

		config[2][3] = 7; // out of top-left field
		Assert.assertFalse("reject false positive: 3x3 field check failed!",
				sudoku.reject(config));

		// Check with real-world example
		Assert.assertFalse("reject false positive: real world example", sudoku
				.reject(testCase1Result));
	}

	/**
	 * Tests the {@link Sudoku#getNextFreeField(int[][])} method.
	 */
	@Test
	public void testNextFreeField() {
		int[][] config = new int[9][9];

		// Full & empty field
		Assert.assertEquals(
				"getNextFreeField failed: should return 0 if field is empty!",
				0, sudoku.getNextFreeField(config));
		Assert.assertEquals(
				"getNextFreeField failed: should return -1 if field is full!",
				-1, sudoku.getNextFreeField(testCase1Result));

		// Normal fields
		config[0][0] = 5;
		Assert.assertEquals("getNextFreeField failed!", 1, sudoku
				.getNextFreeField(config));
		config[0][1] = 5;
		Assert.assertEquals("getNextFreeField failed!", 2, sudoku
				.getNextFreeField(config));
		config[0] = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Assert.assertEquals("getNextFreeField failed for second row!", 9,
				sudoku.getNextFreeField(config));
	}

	/**
	 * Tests the {@link Sudoku#getNextExtension(int[][], int)} method.
	 */
	@Test
	public void testNextExtension() {
		int[][] config = new int[9][9];
		config[4][6] = 8; // Field 42

		int[][] newConfig = sudoku.getNextExtension(config, 42);
		Assert.assertEquals(
				"getNextExtension failed: did not increment (correct) field!",
				9, newConfig[4][6]);
		Assert.assertNotSame(
				"getNextExtension failed: did not create a new array!", config,
				newConfig);
		Assert.assertNotSame(
				"getNextExtension failed: did not clone sub-arrays!",
				config[0], newConfig[0]);

		newConfig = sudoku.getNextExtension(newConfig, 42);
		Assert
				.assertEquals(
						"getNextExtension failed: did not return null if field already is 9",
						null, newConfig);
	}

	/**
	 * Tests the {@link Sudoku#solve(int[][])} method.
	 */
	@Test
	public void testSolve() {
		// Test cases
		Assert.assertTrue("solve failed for test case 1", Arrays.deepEquals(
				sudoku.solve(testCase1), testCase1Result));

		Assert.assertTrue("solve failed for test case 2", Arrays.deepEquals(
				sudoku.solve(testCase2), testCase2Result));

		// Finished field should not be modified
		Assert.assertTrue("solve failed for finished field", Arrays.deepEquals(
				sudoku.solve(testCase1Result), testCase1Result));
	}
}
