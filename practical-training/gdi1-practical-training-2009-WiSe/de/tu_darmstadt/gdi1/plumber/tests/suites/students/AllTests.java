package de.tu_darmstadt.gdi1.plumber.tests.suites.students;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Plumber - All tests");
		//$JUnit-BEGIN$
		suite.addTest(PlumberTestsuiteMinimal.suite());
		suite.addTest(PlumberTestsuiteExtended1.suite());
		suite.addTest(PlumberTestsuiteExtended3.suite());
		suite.addTest(PlumberTestsuiteExtended2.suite());
		//$JUnit-END$
		return suite;
	}

}
