package de.tu_darmstadt.gdi1.plumber.tests.adapters;

import de.tu_darmstadt.gdi1.plumber.ui.GameWindow;

/**
 * This is the test adapter for the second extended stage of completion.
 * Implement all method stubs in order for the tests to work.
 * <br><br>
 * <i>Note:</i> This test adapter inherits from the first extended test adapter
 * 
 * @see PlumberTestAdapterMinimal
 * @see PlumberTestAdapterExtended1
 * 
 * @author Jonas Marczona
 * @author Christian Merfels
 * @author Fabian Vogt
 */
public class PlumberTestAdapterExtended2 extends PlumberTestAdapterExtended1 {

	/**
	 * Undo the last action. The state of the game shall be the same as before
	 * the last action. Action is here defined as "rotating game elements".
	 * Do nothing if there is no action to undo.
	 */
	public void undo() {
		// TODO: fill stub.
	}
	
	/**
	 * Redo the last action. The state of the game shall be the same as before
	 * the last "undo". 
	 * Do nothing if there is no action to redo.
	 */
	public void redo() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keySpacePressed()}
	 */
	public void handleKeyPressedSpace() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyUndoPressed()}
	 */
	public void handleKeyPressedUndo() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyRedoPressed()}
	 */
	public void handleKeyPressedRedo() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyUpPressed()}
	 */
	public void handleKeyPressedUp() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyDownPressed()}
	 */
	public void handleKeyPressedDown() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyLeftPressed()}
	 */
	public void handleKeyPressedLeft() {
		// TODO: fill stub.
	}
	
	/**
	 * Like {@link GameWindow#keyRightPressed()}
	 */
	public void handleKeyPressedRight() {
		// TODO: fill stub.
	}
}