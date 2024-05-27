/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the ConflictException class.
 * @author Maisa Little
 */
class ConflictExceptionTest {

	/**
	 * tests the default ConflictException constructor (no parameters)
	 */
	@Test
	void testConflictException() {
		ConflictException ce = new ConflictException("Schedule conflict.");
	    assertEquals("Schedule conflict.", ce.getMessage());
	}

	/**
	 * tests the ConflictException constructor with a custom message
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

}
