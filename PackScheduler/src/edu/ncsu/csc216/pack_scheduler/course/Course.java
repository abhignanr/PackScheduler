/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Course creates object representation of Courses. Its fields are
 * MN_NAME_LENGTH, MAX_NAME_LENGTH, MN_LETTER_COUNT, MAX_LETTER_COUNT,
 * DIGIT_COUNT, SECTION_LENGTH, MAX_CREDITS, MN_CREDITS, name, section, credits,
 * and instructorId. Its methods are getName, setName, getSection, setSection,
 * getCredits, setCredits, getInstructorId, setInstructorId, hashCode, equals,
 * toString, getShortDisplayArray, getMeetingDaysAndTime, and
 * getLongDisplayArray. This is a class that constructs Course, a child of
 * Activity.
 * 
 * @author Maisa Little
 *
 */
public class Course extends Activity implements Comparable<Course> {
	/** section length */
	private static final int SECTION_LENGTH = 3;
	/** maximum credit hours */
	private static final int MAX_CREDITS = 5;
	/** minimum credit hours */
	private static final int MN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor unity ID */
	private String instructorId;
	/** Field holding the validator object for a Course's name */
	private CourseNameValidator validator;
	/** Course Roll of enrolled students */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields. calls super
	 * constructor located in Activity.java
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap the capacity for enrollment
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		this.validator = new CourseNameValidator();
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		this.roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap the capacity for enrollment
	 * @param meetingDays   meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * gets the course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		// Throw exception if the name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Throw exception if the Course name does not reach a final state in the FSM
		try {
			if (!validator.isValid(name)) {
				throw new IllegalArgumentException("Invalid course name.");
			}

		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;
	}

	/**
	 * gets the course section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * sets the course section
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is invalid
	 */
	public void setSection(String section) {
		// if section is null or not three characters
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}

		// if any of section's three characters are not digits
		for (int i = 0; i < SECTION_LENGTH; i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}

		this.section = section;
	}

	/**
	 * gets the course's credit hours
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * sets the course's credit hours
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits are invalid
	 */
	public void setCredits(int credits) {
		// if the credit hours are less than 1 or greater than 5
		if (credits < MN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;
	}

	/**
	 * gets the course instructor's id
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * sets the course instructor's id
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructors id is invalid
	 */
	public void setInstructorId(String instructorId) {
		// if instructor's id is null or empty string
		if (instructorId != null && "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}

		this.instructorId = instructorId;
	}

	/**
	 * hash code override for Course
	 * 
	 * @return int of hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + roll.getEnrollmentCap();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * equals override for Course
	 * 
	 * @param obj is an object being compared
	 * @return true of obj is the same as the Course object; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (roll.getEnrollmentCap() != other.getCourseRoll().getEnrollmentCap())
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;

		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name + "," + super.getTitle() + "," + section + "," + credits + ","
				+ instructorId + "," + roll.getEnrollmentCap() + ",");

		if ("A".equals(super.getMeetingDays())) {
			sb.append(super.getMeetingDays());
		} else {
			sb.append(super.getMeetingDays() + "," + super.getStartTime() + "," + super.getEndTime());
		}
		return sb.toString();
	}

	/**
	 * works with superclass' getShortDisplayArray()
	 * 
	 * @return String array of short info
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = { this.name, this.section, super.getTitle(), super.getMeetingString(),
				roll.getOpenSeats() + "" };
		return shortDisplay;
	}

	/**
	 * envokes the super method
	 * 
	 * @param meetingDays days course meets
	 * @param startTime   time course starts
	 * @param endTime     time course ends
	 * @throws IllegalArgumentException if meeting days are null or contain "S" or
	 *                                  "U"
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		String days = meetingDays;
		if (days == null || days.contains("S") || days.contains("U")) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else {
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
	}

	/**
	 * works with superclass' getLongDisplayArray()
	 * 
	 * @return String array of long info
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = { this.name, this.section, super.getTitle(), this.credits + "", this.instructorId,
				super.getMeetingString(), "" };
		return longDisplay;
	}

	/**
	 * checks to see if two Activity objects are the same and are courses
	 * 
	 * @param activity is an Activity object
	 * @return true if they have the same name, false otherwise
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		Course c = null;

		try {
			c = (Course) activity; // try to cast
			if (this.name.equals(c.name)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	/**
	 * overrides the compareTo method
	 * 
	 * @param o is a Course
	 * @return int if Course is = (0), or other !=0
	 */
	@Override
	public int compareTo(Course o) {
		String thisName = this.getName();
		String thisSection = this.getSection();
		String oName = o.getName();
		String oSection = o.getSection();

		if (thisName.compareTo(oName) == 0) {
			return thisSection.compareTo(oSection);
		} else {
			return thisName.compareTo(oName);
		}
	}

	/**
	 * Returns the course roll.
	 * 
	 * @return the course roll.
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

}