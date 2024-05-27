package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This class creates a special type of exception.  It has a field called serialVersionUID.  It has methods called ConflictException (2 constructors).
 * @author Maisa Little
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}
	
	/**
	 * constructor with a specific error message
	 * @param message message to be printed
	 */
	public ConflictException(String message) {
		super(message);
	}
}
