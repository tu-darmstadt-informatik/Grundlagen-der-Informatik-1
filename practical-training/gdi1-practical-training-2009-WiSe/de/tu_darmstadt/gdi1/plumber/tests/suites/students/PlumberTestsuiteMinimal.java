package de.tu_darmstadt.gdi1.plumber.tests.suites.students;

import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.HandleRestartLevelKeystrokeTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.IsCorrectLevelTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.IsSolvedIsLostTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.LoadLevelFromStringTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.PlayGameTest;
import de.tu_darmstadt.gdi1.plumber.tests.testcases.students.RotateTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PlumberTestsuiteMinimal {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Plumber - Minimal");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(LoadLevelFromStringTest.class);
		suite.addTestSuite(IsCorrectLevelTest.class);
		suite.addTestSuite(IsSolvedIsLostTest.class);
		suite.addTestSuite(RotateTest.class);
		suite.addTestSuite(HandleRestartLevelKeystrokeTest.class);
		suite.addTestSuite(PlayGameTest.class);

		//$JUnit-END$
		return suite;
	}

}
