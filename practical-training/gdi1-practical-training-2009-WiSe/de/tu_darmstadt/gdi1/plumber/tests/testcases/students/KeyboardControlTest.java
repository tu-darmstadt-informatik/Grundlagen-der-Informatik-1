package de.tu_darmstadt.gdi1.plumber.tests.testcases.students;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.plumber.tests.adapters.PlumberTestAdapterExtended2;

/**
 * Tests, whether the keyboard control works as specified.
 * 
 * @author Jonas Marczona
 * @author Fabian Vogt
 *
 */
public class KeyboardControlTest extends TestCase {
	
	PlumberTestAdapterExtended2 adapter;
	String level = "c6m\n364\n464";
	String level_0_1 = "c5m\n364\n464";
	String level_1_2 = "c6m\n364\n454";

	@Before
	public void setUp() {
		adapter = new PlumberTestAdapterExtended2();
	}

	@Test
	public final void testHandleArrowSpaceKeystrokes() {
		adapter.loadLevelFromString(level);
		
		adapter.handleKeyPressedSpace();
		assertEquals("You cannot rotate a source!", level, adapter.getStringRepresentationOfLevel().trim());
		
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedSpace();
		assertEquals("Either you did not change the focus correctly or you did not handle the rotation correctly!", level_0_1, adapter.getStringRepresentationOfLevel().trim());
		
		adapter.handleKeyPressedSpace();
		assertEquals("Either you did not change the focus correctly or you did not handle the rotation correctly!", level, adapter.getStringRepresentationOfLevel().trim());
		
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedDown();
		adapter.handleKeyPressedDown();
		adapter.handleKeyPressedLeft();
		adapter.handleKeyPressedSpace();
		assertEquals("Either you did not change the focus correctly or you did not handle the rotation correctly!", level_1_2, adapter.getStringRepresentationOfLevel().trim());
		adapter.handleKeyPressedSpace();
		adapter.handleKeyPressedSpace();
		adapter.handleKeyPressedSpace();
		adapter.handleKeyPressedLeft();
		adapter.handleKeyPressedUp();
		adapter.handleKeyPressedUp();
		adapter.handleKeyPressedRight();
		
		assertEquals("Either you did not change the focus correctly or you did not handle the rotation correctly!", level, adapter.getStringRepresentationOfLevel().trim());
		
		adapter.handleKeyPressedSpace();
		assertEquals("Either you did not change the focus correctly or you did not handle the rotation correctly!", level_0_1, adapter.getStringRepresentationOfLevel().trim());
		
	}

}
