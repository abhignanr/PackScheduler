/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test for the check conflict method of Activity
 * @author Maisa Little
 */
class ActivityTest {

	/**
	 * Test method for checkConflict no conflicts
	 */
	@Test
	public void testCheckConflict() {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 11, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 11, "TH", 1330, 1445);
	    
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Test method for checkConflict with a conflict
	 */
	@Test
	public void testCheckConflictWithConflict() {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 11, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 11, "M", 1330, 1445);
	    Activity a3 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "lbottomley2", 11, "WF", 1330, 1445);
	    Activity a4 = new Course("CSC217", "Software Development Fundamentals Lab", "211", 1, "scharles4", 11, "W", 1445, 1545);
	    Activity a5 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "002", 3, "scharles4", 11, "W", 1545, 1615);
		
	    //checks to see if exact same, but one is one more day than the other
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    //insures commutativity
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	    
	    //one starts right as other ends
	    //single day
	    Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
	    assertEquals("Schedule conflict.", e3.getMessage());
	    
	  //one starts right as other ends
	    Exception e4 = assertThrows(ConflictException.class, () -> a4.checkConflict(a5));
	    assertEquals("Schedule conflict.", e4.getMessage());
	   	}
	
}
