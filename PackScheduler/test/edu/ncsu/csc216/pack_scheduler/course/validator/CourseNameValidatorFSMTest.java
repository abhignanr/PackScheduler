package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests CourseNameValidator's FSM.
 * @author davidb
 *
 */
class CourseNameValidatorFSMTest {

	@ParameterizedTest
	@CsvSource({
		"a123",
		"ab123",
		"abc123",
		"abcd123",
		"a123e",
		"ab123e",
		"abc123e",
		"abcd123e"
	})
	public void testTransitionsValid(String courseName) {
			CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();
			try {
			assertTrue(fsm.isValid(courseName));
			} catch (InvalidTransitionException e) {
				fail("Invalid Transition!");
			}
	}
	
	@ParameterizedTest
	@CsvSource({
		"0",
		".",
		"a.",
		"ab.",
		"abc.",
		"abcd.",
		"abcde",
		"abc1.",
		"abc1f",
		"abc12.",
		"abc12f",
		"abc123.",
		"abc1234",
		"a123e.",
		"a123ef",
		"a123e4",
		"a",
		"ab",
		"abc",
		"abcd",
		"a1",
		"a12"
		
	})
	public void testTransitionsInvalid(String courseName ) {
		CourseNameValidatorFSM fsm = new CourseNameValidatorFSM();

		try {
		assertFalse(fsm.isValid(courseName));
		} catch (InvalidTransitionException e) {
			//do nothing
		}
		
}

}
