package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Student class to represent a student's information for scheduling. Its fields
 * are firstName, lastName, id, email, password, maxCredits, and MAX_CREDITS.
 * Its methods are Student, Student, getFirstName, getLastName, getId, getEmail,
 * setEmain, getPassword, setPassword, getMaxCredits, setMaxCredits,
 * setFirstName, setLastName, setId, hashCode, equals, and toString.
 * 
 * @author davidb
 * @author sjwill25
 * @author mglittl2
 */
public class Student extends User implements Comparable<Student> {

	/** Max Credits this student can take */
	private int maxCredits;
	/** Max Credits any student can take */
	public static final int MAX_CREDITS = 18;

	/** Schedule for this student */
	private Schedule schedule;

	/**
	 * contructor for a Student
	 * 
	 * @param firstName  first name of student
	 * @param lastName   last name of student
	 * @param id         student unity id
	 * @param email      student email
	 * @param hashPW     student password
	 * @param maxCredits max credits for student to take
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * calls default constructor for Student with max credits set to 18
	 * 
	 * @param firstName student first name
	 * @param lastName  student last name
	 * @param id        student unity id
	 * @param email     student email
	 * @param hashPW    student password
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * Returns the max credits this student can take.
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max credits for this student. Throws IllegalArgumentException if the
	 * max credits is outside of the range 3-18..
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if the credits is outside the range of 3-18,
	 *                                  inclusive.
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns the parameters in a comma-separated format.
	 * 
	 * @return the Student information as a String.
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCredits;
	}

	/**
	 * overrides the compareTo method
	 * 
	 * @param s is a Student you are comparing
	 * @return returns the comparison between the first name, last name, or unity id
	 */
	@Override
	public int compareTo(Student s) {
		if (this.getLastName().compareTo(s.getLastName()) != 0) {
			return this.getLastName().compareTo(s.getLastName());
		}
		if (this.getFirstName().compareTo(s.getFirstName()) != 0) {
			return this.getFirstName().compareTo(s.getFirstName());
		}

		return this.getId().compareTo(s.getId());
	}

	/**
	 * Overrides the hashCode method in User
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}

	/**
	 * Overrides the equals method in User
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}

	/**
	 * Returns the schedule.
	 * 
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * This method checks whether or not a Course can be added to a student's
	 * schedule. It uses the schedules canAdd method.
	 * 
	 * @param c the course to be added
	 * @return whether or not the Course c can be added
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		if (!getSchedule().canAdd(c)) {
			return false;
		}
		// return if adding Course c would exceed the students max allowed credits
		return !((this.schedule.getScheduleCredits() + c.getCredits()) > this.maxCredits);
	}
}