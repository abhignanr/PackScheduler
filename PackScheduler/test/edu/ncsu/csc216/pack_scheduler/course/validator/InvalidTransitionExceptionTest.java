package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test Class for InvalidTransitionException
 * @author davidb
 *
 */
class InvalidTransitionExceptionTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException()}.
	 */
	@Test
	void testInvalidTransitionException() {
		InvalidTransitionException e = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", e.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException#InvalidTransitionException(java.lang.String)}.
	 */
	@Test
	void testInvalidTransitionExceptionString() {
		InvalidTransitionException e = new InvalidTransitionException("New Message");
		assertEquals("New Message", e.getMessage());
	}

}
