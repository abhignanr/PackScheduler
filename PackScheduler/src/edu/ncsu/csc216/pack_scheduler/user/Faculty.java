/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Class for Faculty Objects,  They have everything that a User does, along with maxCourses
 * @author Maisa Little
 *
 */
public class Faculty extends User {
	/**
	 * max courses a faculty memeber can have
	 */
	private int maxCourses;
	
	/**
	 * instance of FacultySchedule
	 */
	private FacultySchedule schedule;
	
	/**
	 * lowest maxCourses can be
	 */
	public static final int MIN_COURSES = 1;
	
	/**
	 * highest maxCourses can be
	 */
	public static final int MAX_COURSES = 3;
	
	/**
	 * constructs a Faculty member
	 * @param firstName first name
	 * @param lastName last name
	 * @param id unity id
	 * @param email email address
	 * @param hashPw password
	 * @param maxCourses max courses to teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPw, int maxCourses) {
		super(firstName, lastName, id, email, hashPw);
		schedule = new FacultySchedule(id);
		setMaxCourses(maxCourses);
	}

	/**
	 * sets max courses
	 * @param maxCourses max courses to teach
	 * @throws IllegalArgumentException if max courses < 1 or > 3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	/**
	 * gets max courses
	 * @return max courses
	 */
	public int getMaxCourses() {
		return this.maxCourses;
	}
	
	/**
	 * This method is a getter for the faculty schedule field
	 * @return returns faculty schedule
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	/**
	 * returns true if the number of scheduled courses is greater than the Faculty’s maxCourses
	 * @return returns true if scheduled courses is greater than max courses
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

	/**
	 * override hashcode method in User
	 * @return hashed password
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * override equals method in User
	 * @return true if equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}
	
	/**
	 * Returns the parameters in a comma-separated format.
	 * 
	 * @return the Faculty information as a String.
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCourses;
	}
}
