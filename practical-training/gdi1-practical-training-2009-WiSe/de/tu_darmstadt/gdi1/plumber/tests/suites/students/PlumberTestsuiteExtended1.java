package de.tu_darmstadt.gdi1.plumber.tests.suites.students;

import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.HighscoreTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PlumberTestsuiteExtended1 {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Plumber - Extended 1");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(HighscoreTest.class);

		//$JUnit-END$
		return suite;
	}

}
