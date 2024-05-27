/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This interface calls an abstract method.  It has no fields.  It has an abstract method called checkConflict.
 * @author Maisa Little
 */
public interface Conflict {

	/**
	 * abstract method that checks for an exception created in ConflictException class
	 * @param possibleConflictingActivity is an activity that may conflict time with another
	 * @throws ConflictException if the activity times overlap
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
