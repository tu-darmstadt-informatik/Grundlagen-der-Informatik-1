/*============================================================================*/

package de.tu_darmstadt.gdi1.plumber.ui;

/*============================================================================*/

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import de.tu_darmstadt.gdi1.plumber.exceptions.InternalFailureException;
import de.tu_darmstadt.gdi1.plumber.exceptions.ParameterOutOfRangeException;

/*============================================================================*/

/**
 * Base class for the main game window. Derive from this class for implementing
 * your custom solution.
 * 
 * @author Steven Arzt, Oren Avni, Jonas Marczona
 * @version 1.1
 */
public abstract class GameWindow extends JFrame implements KeyListener {

	/* ======================================================================== */

	private static final long serialVersionUID = -2646785578035515024L;

	/* ======================================================================== */

	protected GamePanel gamePanel = null;
	
	/* ======================================================================== */

	/**
	 * Creates a new instance of the GameWindow class
	 * 
	 * @param windowTitle The title of the game window
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public GameWindow(String windowTitle) {
		super(windowTitle);

		// We may need to correct the window title
		if (windowTitle == null || windowTitle.equals(""))
			setTitle("Plumber Student Implementation");

		// Create the game panel
		gamePanel = createGamePanel ();
		if (gamePanel == null)
			throw new RuntimeException("The game panel may not be null");

		// Configure the frame
		addKeyListener( this );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	/* ======================================================================== */

	/**
	 * Override this method to create your own custom game panel.
	 * 
	 * @return An instance of the GamePanel you want to use
	 */
	protected abstract GamePanel createGamePanel ();

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the left arrow key is pressed. Override
	 * this method to implement your custom solution
	 */
	protected void keyLeftPressed() {		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the right arrow key is pressed. Override
	 * this method to implement your custom solution
	 */
	protected void keyRightPressed() {		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the up arrow key is pressed. Override
	 * this method to implement your custom solution
	 */
	protected void keyUpPressed() {		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the down arrow key is pressed. Override
	 * this method to implement your custom solution
	 */
	protected void keyDownPressed() {		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the "q" key is pressed. Override this
	 * method to implement your custom solution
	 */
	protected void keyQuitPressed(){		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the "n" key is pressed. Override this
	 * method to implement your custom solution
	 */
	protected void keyNewGamePressed() {		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the backspace key is pressed. Override
	 * this method to implement your custom solution
	 */
	protected void keyUndoPressed() {		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the return key is pressed. Override this
	 * method to implement your custom solution
	 */
	protected void keyRedoPressed(){		
	}

	/* ======================================================================== */

	/**
	 * Method that is invoked whenever the space key is pressed. Override this
	 * method to implement your custom solution
	 */
	protected void keySpacePressed() {		
	}
	
	/* ======================================================================== */
	
	/**
	 * Method that is invoked whenever a key that is not explicitly handled has
	 * been pressed. Override this method to implement your custom solution.
	 */
	protected void keyOtherPressed (KeyEvent key){		
	}
	
	/* ======================================================================== */

	/**
	 * This method consumes a KeyEvent caused by the user pressing a key. If the
	 * key is "known", the appropriate method key*Pressed will be called.
	 * 
	 * @see #keyUpPressed()
	 * @see #keyLeftPressed()
	 * @see #keyRightPressed()
	 * @see #keyDownPressed()
	 * @see #keyNewGamePressed()
	 * @see #keyRedoPressed()
	 * @see #keyUndoPressed()
	 */
	public void keyPressed(KeyEvent key) {
		// retrieve the key code and call the appropriate method, if any
		switch (key.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			keyLeftPressed();
			break;

		case KeyEvent.VK_RIGHT:
			keyRightPressed();
			break;

		case KeyEvent.VK_UP:
			keyUpPressed();
			break;

		case KeyEvent.VK_DOWN:
			keyDownPressed();
			break;

		case KeyEvent.VK_Q:
			keyQuitPressed();
			break;

		case KeyEvent.VK_N:
			keyNewGamePressed();
			break;

		case KeyEvent.VK_BACK_SPACE:
			keyUndoPressed();
			break;

		case KeyEvent.VK_ENTER:
			keyRedoPressed();
			break;
			
		case KeyEvent.VK_SPACE:
			keySpacePressed();
			break;

		default:
			keyOtherPressed(key);
			break;
		}
	}

	/* ======================================================================== */

	public void keyReleased(KeyEvent key) {
		// nothing to be done here
	}

	/* ======================================================================== */

	public void keyTyped(KeyEvent key) {
		// nothing to be done here
	}

	/* ======================================================================== */

	/**
	 * Returns the game panel used by this game window
	 * 
	 * @return The game panel used by this game window
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/* ======================================================================== */

	/**
	 * Notifies the game window that a new level has been loaded
	 * 
	 * @param width
	 *            The width of the level just loaded
	 * @param height
	 *            The height of the level just loaded
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public void notifyLevelLoaded(int width, int height)
			throws ParameterOutOfRangeException, InternalFailureException {
		// Check the parameters
		if (width <= 0)
			throw new ParameterOutOfRangeException("Game Window width invalid");
		if (height <= 0)
			throw new ParameterOutOfRangeException("Game Window height invalid");

		// Notify the panel
		gamePanel.notifyLevelLoaded(width, height);
	}

	/* ======================================================================== */

}

/* ============================================================================ */
