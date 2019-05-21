package de.tu_darmstadt.gdi1.plumber.tests.suites.students;

import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.GenerateLevelTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.SolveLevelTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PlumberTestsuiteExtended3 {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Plumber - Extended 3");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(GenerateLevelTest.class);
		suite.addTestSuite(SolveLevelTest.class);

		//$JUnit-END$
		return suite;
	}

}
