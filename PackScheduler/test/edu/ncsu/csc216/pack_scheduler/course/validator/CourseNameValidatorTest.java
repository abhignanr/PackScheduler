package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests CourseNameValidator's FSM.
 * @author davidb
 *
 */
class CourseNameValidatorTest {

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
			CourseNameValidator fsm = new CourseNameValidator();
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
		"a123e4"		
	})
	void testTransitionsInvalid(String courseName ) {
		CourseNameValidator fsm = new CourseNameValidator();
		try {
			assertThrows(InvalidTransitionException.class, () -> fsm.isValid(courseName));
		} catch (Exception e) {
			fail("Should be invalid transitions!");
		}
		//		try {
//			assertFalse(fsm.isValid(courseName));
//		} catch (InvalidTransitionException e) {/*do nothing*/}
//		
}
	
	@ParameterizedTest
	@CsvSource({
		"a",
		"ab",
		"abc",
		"abcd",
		"a1",
		"a12"
	})
	void testTransitionsFalse(String courseName) {
		CourseNameValidator fsm = new CourseNameValidator();
		try {
			assertFalse(fsm.isValid(courseName));
		} catch (InvalidTransitionException e) {
			fail("Should be valid transitions!");
		}
	}

}
