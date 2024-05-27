package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Invalid Transition Exception representing an invalid transition between states.
 * @author davidb
 *
 */
public class InvalidTransitionException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a default Invalid transition exception with the provided message.
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}

	/**
	 * Creates a default Invalid transition exception with the provided message.
	 * @param message the message for the exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

}
