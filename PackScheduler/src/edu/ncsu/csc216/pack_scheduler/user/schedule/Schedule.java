package edu.ncsu.csc216.pack_scheduler.user.schedule;

import java.util.List;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Represents the schedule of a Student. Allows for adding courses, removing the courses, and displaying the schedule.
 * @author davidb
 *
 */
public class Schedule {
	
	/** Default title for the schedule*/
	private static final String DEFAULT_TITLE = "My Schedule";
	
	/** Arraylist of courses in the schedule*/ 
	private ArrayList<Course> schedule;
	/** title of the schedule */
	private String title;
	
	/**
	 * Generates the default schedule. Title is set to 'My Schedule', 
	 * and the arraylist is initally emtpy
	 */
	public Schedule() {
		title = DEFAULT_TITLE;
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Returns the title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the schedule.
	 * @return the schedule
	 */
	public List<Course> getSchedule() {
		return schedule;
	}
	
	/**
	 * Adds the course to the end of the list. If the course is a duplicate of another 
	 * in the list or the same, throws an IllegalArgumentException. Similarly, if the 
	 * course conflicts with another in the list, throws IllegalArgumentException.
	 * Finally, if the course is null, throws a {@link NullPointerException}.
	 * @param c the course to add
	 * @throws IllegalArgumentException if the course conflicts with another course or is a duplicate.
	 * @throws NullPointerException if the course is null
	 * @return true if the operation was successful
	 */
	public boolean addCourseToSchedule(Course c) {
		for (Course existing : schedule) {
			if(existing.equals(c) || c.isDuplicate(existing)) 
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			try {
			c.checkConflict(existing);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(c);
		return true;
	}

	/**
	 * Removes the course from the schedule. Returns true if the course was removed, 
	 * and false if the course could not be found.
	 * @param c the course to remove
	 * @return if it was successful or not
	 */
	public boolean removeCourseFromSchedule(Course c) {
		for(int i = 0; i < schedule.size(); i++) {
			if(schedule.get(i).equals(c)) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets the schedule to the initial state, with the default title and empty list.
	 */
	public void resetSchedule() {
		title = DEFAULT_TITLE;
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Returns an array of course information. The format of each String[] array 
	 * should be {name, section, title, meeting string}.
	 * @return the array of course information		
	 */
	public String[][] getScheduledCourses() {
		return schedule.stream().map((Course c) -> c.getShortDisplayArray()).toArray((size) -> new String[size][]);
	}
	
	/**
	 * Sets the value of title.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if(title == null) throw new IllegalArgumentException("Title cannot be null.");
		this.title = title;
	}
	
	/**
	 * Returns the total number of credits in the schedule.
	 * @return the total number of credits in the schedule
	 */
	public int getScheduleCredits() {
		int total = 0;
		for( Course c : schedule) total += c.getCredits();
		return total;
	}
	
	/**
	 * Returns of the course can be added to the schedule. 
	 * Returns false if the course is null, already in the schedule, 
	 * or conflicts with any course.
	 * @param newCourse the course to check
	 * @return if the course can be added or not
	 */
	public boolean canAdd(Course newCourse) {
		if (newCourse == null) return false;
				
		for (Course c: schedule) {
			if (c.equals(newCourse) || c.getName().equals(newCourse.getName()))
				return false;
			try {
				c.checkConflict(newCourse);
			} catch (Exception e) {
				return false;
			}
		}
		
		return true;
		
	}
}
