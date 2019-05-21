package de.tu_darmstadt.gdi1.plumber.tests.suites.students;

import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.KeyboardControlTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.UndoRedoTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PlumberTestsuiteExtended2 {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Plumber - Extended 2");
		//$JUnit-BEGIN$

		suite.addTestSuite(KeyboardControlTest.class);
		suite.addTestSuite(UndoRedoTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
