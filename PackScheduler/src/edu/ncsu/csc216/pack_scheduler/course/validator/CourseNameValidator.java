package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Validator for course names
 * @author Andres Ayala-Lagunas
 */
public class CourseNameValidator {
	/** Initial state */
	private State init = new InitialState();
	/** L state */
	private State l = new OneLetterState();
	/** 2L state */
	private State ll = new TwoLetterState();
	/** 3L state */
	private State lll = new ThreeLetterState();
	/** 4L state */
	private State llll = new FourLetterState();
	/** D state */
	private State d = new OneDigitState();
	/** 2D state */
	private State dd = new TwoDigitState();
	/** 3D state */
	private State ddd = new ThreeDigitState();
	/** Suffix state */
	private State s = new SuffixState();
	/** The current state */
	State state = init;
	
	/**
	 * Identifies the next character of input and calls the appropriate method
	 * @param courseName the name of the course
	 * @throws InvalidTransitionException if the transition is invalid
	 * @return if the state change was successful
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		state = init;
		for (Character c : courseName.toCharArray()) {
			if (Character.isDigit(c)) {
				state.onDigit();
			} else if (Character.isLetter(c)) {
				state.onLetter();
			} else {
				state.onOther();
			}
		}
		
		return state == s || state == ddd;
	}
	
	/**
	 * Abstract state class for all state objects
	 * @author Andres Ayala-Lagunas
	 */
	public abstract class State {
		/**
		 * Method for even that a letter
		 * is added.
		 * @throws InvalidTransitionException if the transition cannot exit this state
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * Method for event that a digit
		 * is added.
		 * @throws InvalidTransitionException if the transition cannot exit this state
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Method for event that other
		 * character is added.
		 * @throws InvalidTransitionException when input other than digit or letter is found.
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * State representing the initial state
	 * @author Andres Ayala-Lagunas
	 */
	public class InitialState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			state = l;
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	/**
	 * State representing the one letter state
	 * @author Andres Ayala-Lagunas
	 */
	public class OneLetterState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			state = ll;
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			state = d;
		}
	}
	
	/**
	 * State representing the two letter state
	 * @author Andres Ayala-Lagunas
	 */
	public class TwoLetterState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			state = lll;
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			state = d;
		}
	}
	
	/**
	 * State representing the three letter state
	 * @author Andres Ayala-Lagunas
	 */
	public class ThreeLetterState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			state = llll;
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			state = d;
		}
	}
	
	/**
	 * State representing the four letter state
	 * @author Andres Ayala-Lagunas
	 */
	public class FourLetterState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			state = d;
		}
	}
	
	/**
	 * State representing the one digit state
	 * @author Andres Ayala-Lagunas
	 */
	public class OneDigitState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must have 3 digits.");
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			state = dd;
		}
	}
	
	/**
	 * State representing the two digit state
	 * @author Andres Ayala-Lagunas
	 */
	public class TwoDigitState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must have 3 digits.");
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			state = ddd;
		}
	}
	
	/**
	 * State representing the three digit state
	 * @author Andres Ayala-Lagunas
	 */
	public class ThreeDigitState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			state = s;
		}
		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name can only have 3 digits.");
		}
	}
	
	/**
	 * State representing the suffic state
	 * @author Andres Ayala-Lagunas
	 */
	public class SuffixState extends State {
		/**
		 * Method for when a letter is added.
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}		
		/**
		 * Method for when a digit is added.
		 */
		public void onDigit() throws InvalidTransitionException{
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
}
