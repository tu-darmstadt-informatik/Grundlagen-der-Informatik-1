import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import acm.gui.IntField;
import acm.gui.TableLayout;
import acm.gui.TablePanel;
import acm.program.Program;

public class Sudoku extends Program {

	/**
	 * Fixed boardsize - alter this if you want to make smaller/bigger boards
	 * Not tested! (is this possible?^^)
	 */
	public static int boardsize = 9;
	
	/**
	 * Simple avoiding some warnings
	 */
	private static final long serialVersionUID = 1L;
	
	
	// the model of the game board
	IntField[][] board;

	/**
	 * Construct a new game of Sudoku.
	 *
	 * Initializes the game board with all zeroes.
	 */
	public Sudoku() {
		super();
		board = new IntField[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = new IntField(0);
			}
		}
	}

	/**
	 * Initialize a new game of Sudoku with a given configuration.
	 *
	 * @param values
	 *            The configuration of the game board.
	 */
	public Sudoku(int[][] values) {
		this();
		setConfiguration(values);
	}

	/**
	 * Initialize the view component.
	 */
	@Override
	public void init() {
		setTitle("Sudoku");
		setLayout(new TableLayout(4, 3));
		for (int i = 0; i < 9; i++) {
			add(assembleInnerTable(i));
		}
		add(new JButton("Solve"));
		addActionListeners();
	}

	/**
	 * Assemble a single 3x3 field.
	 *
	 * @param n
	 *            The number of the 3x3 field to assemble a table component for.
	 * @return The TablePanel containing a 3x3 field.
	 */
	private TablePanel assembleInnerTable(int n) {
		TablePanel tp = new TablePanel(3, 3);
		for (int i = 0; i < 9; i++) {
			// we assemble 3x3 field-wise and have to adjust array indices
			// accordingly
			int row = 3 * (n / 3) + i / 3;
			int col = 3 * (n % 3) + i % 3;

			// The constructor made sure these are instantiated IntFields
			IntField intField = board[row][col];

			// Register a KeyListener to suppress non-digit entries
			intField.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					// don't care
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// don't care
				}

				@Override
				public void keyTyped(KeyEvent e) {
					try {
						// try to parse the pressed keys value into an Integer
						Integer.parseInt(String.valueOf(e.getKeyChar()));
					} catch (NumberFormatException nfe) {
						// consume the event and stop it from propagating
						e.consume();
					}
				}
			});
			tp.add(intField);
		}
		// draw a solid black border around every 3x3 field
		tp.setBorder(BorderFactory.createLineBorder(Color.black));
		return tp;
	}

	/**
	 * Initialize the game board with the given arrays of ints.
	 *
	 * @param init The 9x9 two-dimensional array with int values from 0..9
	 */
	public void setConfiguration(int[][] init) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (init[i][j] > 0 && init[i][j] <= 9) {
					board[i][j].setValue(init[i][j]);
				} else {
					board[i][j].setValue(0);
				}
			}
		}
	}

	/**
	 * Return the current configuration of the game board.
	 *
	 * @return The current configuration.
	 */
	public int[][] getConfiguration() {
		int[][] tmp = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				tmp[i][j] = board[i][j].getValue();
			}
		}
		return tmp;
	}

	// if no solution was found, color every field red
	public void colorForFailure() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j].setBackground(new Color(255, 0, 0));
			}
		}
	}

	// if there was a solution, color the new values fields green
	public void colorForSuccess(int[][] solution) {
		int[][] actual = getConfiguration();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (solution[i][j] != actual[i][j]) {
					board[i][j].setBackground(new Color(0, 255, 0));
					board[i][j].setValue(solution[i][j]);
				} else {
					board[i][j].setBackground(new Color(255, 255, 255));
				}
			}
		}
	}

	/**
	 * The ActionListeners method to process pressed buttons.
         *
         * @param e ActionEvent
         */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Solve")) {
			// The solve button was pressed, find a solution
			int[][] solution = solve(getConfiguration());
			if (solution != null) {
				colorForSuccess(solution);
			} else {
				colorForFailure();
			}
		}
	}
	
	/**
	 * Checks if board is valid. Returns false if it is valid, else true.
	 * 
	 * public because requested
	 * 
	 * @param board Sudoku Configuration
	 * @return false if valid, true if not.
	 */
	public boolean reject(int[][] board)
	{
		//Check input
		if(board == null)
		{
			return true;
		}
		
		{ //Check if 1-9 is max 1x in every row & line
			
			//Helper-Vars - Start with false
			boolean[] row  = new boolean[boardsize];
			boolean[] line = new boolean[boardsize];
			
			for(int i=0; i < boardsize;i++) //rows
			{
				for(int j=0; j < boardsize; j++) //lines
				{
					{ //row
						int t = board[i][j]; //value @ act pos //row
				
						if(		t > 0 && //skip 0 and wrong input!
							t <= boardsize)
						{
							if(row[t-1]) //already seen? 
							{
								//Number already found in row
								return true;
							} else
							{
								//Set to true - do not accept this number a second time. 
								row[t-1] = true;
							}
						}
					} //row
				
					{ //line
						int t = board[j][i];  //value @ act pos //line
				
						if(		t > 0 && //skip 0 and wrong input!
								t <= boardsize)
						{
							if(line[t-1])  //already seen?
							{
								//Number already found in line
								return true;
							} else
							{
								//Set to true - do not accept this number a second time.
								line[t-1] = true;
							}
						}
					} //line
				}
			
				//Reset helper-vars, new line/row
				Arrays.fill(row,  false);
				Arrays.fill(line, false);
			}
		} //Check if 1-9 is max 1x in every row&line
		
		{ //Check board-3x3-blocks
			
			//Helper-Var - Starts with false
			boolean[] block  = new boolean[boardsize];
			
			for(int n=0; n < 3; n++) //Count block-lines
			{
				for(int m=0; m < 3; m++) //Count block-rows
				{
					for(int i=0; i < (boardsize/3); i++) //Count line within block
					{
						for(int j=0; j < (boardsize/3); j++) //Count row within block
						{
							int t = board[n*3+i][m*3+j]; //Value @ act pos
							
							if(		t > 0 && //skip 0 and wrong input!
									t <= boardsize)
							{
								if(block[t-1]) //Number already seen?
								{
									//Number already found in Block
									return true;
								} else
								{
									//Set to true - do not accept this number a second time.
									block[t-1] = true;
								}
							}
						}
					}
					
					//Reset - new block
					Arrays.fill(block,  false);
				}
			}
		} //Check board-3x3-blocks
		
		
		//All Checked & OK
		return false;
	}
	
	/**
	 * Returns position of the next field which is 0 (free field).
	 * 
	 * Position-Encoding: 	row*boardsize + line
	 * 						row and line starting by 0.
	 * 						first item is 0,
	 * 						last item is boardsize*boardsize -1
	 * 
	 * public because assumed it is requested
	 * 
	 * @param board
	 * @return Position of next free field or -1 if no free field was found.
	 */
	public int getNextFreeField(int[][] board)
	{
		//Just Checking - should never happen, but method is public... you know...
		if(board == null)
		{
			return -1;
		}
		
		//Search
		for(int i=0; i < boardsize; i++) //row
		{
			for(int j=0; j < boardsize; j++) //line
			{
				if(board[i][j] == 0) //Is act pos 0 ?  
				{
					return i*boardsize + j; //Position Calculation
				}
			}
		}
		
		//Field has no empty fields
		return -1;
	}
	
	/**
	 * Returns new Sudoku configuration with altered (incremented by 1)
	 * value at given position pos.
	 * 
	 * public because assumed it is requested
	 * 
	 * @param board Given Sudoku configuration
	 * @param pos position to be altered
	 * @return Sudoku configuration or null
	 */
	public int[][] getNextExtension(int[][] board, int pos)
	{
		//Decrypt 1-dim-pos to 2-dim-pos 
		int i = pos/boardsize; 
		int j = pos-i*boardsize;
		
		if(		board != null && //Board ok?
				i < boardsize && //row ok?
				j < boardsize && //line ok?
				board[i][j] < boardsize) //value @ pos < boardsize?
		{
			int[][] newboard = new int[boardsize][boardsize];
			
			{ //clone board -> newboard
				for(int k = 0; k < boardsize; k++)
				{
					System.arraycopy(board[k], 0, newboard[k], 0, board[k].length);
				}
			} //clone board -> newboard
			
			newboard[i][j] += 1; // inc value @ pos -> +1 
			return newboard; //return the new board
		}
		
		return null; //Something was wrong - see if clause
	}
	
	/**
	 * Solve the given Sudoku configuration and return the result.
	 *
	 * @param configuration A 9x9 two-dimensional array, columns first.
	 * @return The solution for this game of Sudoku or null if not solvable.
	 */
	public int[][] solve(int[][] configuration)
	{
		return solve(configuration,getNextFreeField(configuration));
	}
	/**
	 * Solve the given Sudoku configuration and return the result.
	 *
	 * @param configuration A 9x9 two-dimensional array, columns first.
	 * @param pos position to be altered (start with getNextFreeField(configuration))
	 * @return The solution for this game of Sudoku or null if not solvable.
	 */
	public int[][] solve(int[][] configuration, int pos)
	{	
		//Recursion-anchor
		if(pos == -1) //all fields are done, or startcall was wrong
		{
			return configuration;
		}
		
		//Recursion
		while(true) //Always do^^
		{
			configuration = getNextExtension(configuration,pos); //getNextExt checks if pos/config is valid!

			if(configuration == null)
			{
				return null; //Could not alter act pos any further, not solvable, try alter fields before filled in
			}
			
			if(!reject(configuration)) //is generated config ok?
			{
				int[][] tboard = solve(configuration,getNextFreeField(configuration)); //Fill in empty fields recursivly.
					
				if(tboard != null) // was solvable 
				{
					return tboard;
				} //else config was not solvable - alter fields filled in before.
			}
		}
	}

	/**
	 * Main-Method
	 * 
	 * some start-soduko-defs
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		@SuppressWarnings("unused")
		final int[][] emptyField = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

		@SuppressWarnings("unused")
		final int[][] fullField = new int[][] { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
				{ 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
				{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
				{ 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

		final int[][] actualField1 = new int[][] {
				{ 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
				{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
				{ 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
				{ 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		@SuppressWarnings("unused")
		final int[][] actualField2 = new int[][] {
				{ 1, 0, 2, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 0, 4, 0, 0, 5, 0, 0, 0, 0 },
				{ 0, 6, 0, 0, 7, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 2, 0 },
				{ 0, 8, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 8, 0, 0 } };

		new Sudoku(actualField1).start();
	}

}
