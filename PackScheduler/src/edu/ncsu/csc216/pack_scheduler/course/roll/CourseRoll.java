package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;
import edu.ncsu.csc216.pack_scheduler.util.Queue;

/**
 * CourseRoll reperesenting list of enrolled students for a course.
 * @author davidb
 *
 */
public class CourseRoll {
	/** Minimum possible enrolled cap */
	public static final int MIN_ENROLLMENT = 10;
	/** Maximum possible enrolled cap */	
	public static final int MAX_ENROLLMENT = 250;
	/** Enrolled Students*/
	private LinkedAbstractList<Student> roll;
	/** Maximum enrolled students */
	private int enrollmentCap;
	/** Waitlist of students */
	private Queue<Student> waitlist;
	/** Course this roll is for */
	private Course course;
	
	/**
	 * Sets the enrollment cap and then initializes the roll. If the roll is outside of the min/max range (10/250), will throw IAE.
	 * @param c the course this is a roll for
	 * @param enrollmentCap the enrollmentCap
	 * @throws IllegalArgumentException if the enrollmentCap is invalid.
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		setEnrollmentCap(enrollmentCap);
		if(c == null) throw new IllegalArgumentException();
		course = c;
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		waitlist = new LinkedQueue<Student>(10);
	}

	/**
	 * Attempts to enroll the student. If the student is null, already in the course, or the course is full, throws an IAE.
	 * 
	 * If the CourseRoll has reached capacity, the Student should be added 
	 * to the waitlist. That is one reason why roll.add(s) (which is really 
	 * a call to LinkedAbstractList.add()) might throw an IllegalArgumentException. 
	 * If the size of the roll is the same as enrollmentCap attempt to add the 
	 * student to waitlist.  If the waitlist is full, then the Student cannot enroll 
	 * and an IllegalArgumentException is thrown.
	 * @param s the student to add.
	 * @throws IllegalArgumentException if student cannot be added and waitlist is full
	 */
	public void enroll(Student s) {
		if(s == null || !canEnroll(s)) { //if student is null or they cannot enroll in course or its waitlist
			throw new IllegalArgumentException();
		}
		if(roll.size() < enrollmentCap)
			roll.add(s);
		else if (getNumberOnWaitlist() < 10) { //if waitlist isn't full add to that
				this.waitlist.enqueue(s);
		}
		else { //that was last resort, throw exception
			throw new IllegalArgumentException();
		}
	}
	/**
	 * This method returns the number of people on the waitlist for this course.
	 * @return returns number of students on waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

	/**
	 * Drops the student from the roll. Throws IAE if the student is null.
	 * @param s the student to drop
	 */
	public void drop(Student s) {
		if(s == null) throw new IllegalArgumentException();
		try {
			if(roll.contains(s)) {
				roll.remove(s);
				if(!waitlist.isEmpty()) {
					Student next = waitlist.dequeue();
					roll.add(next);
					next.getSchedule().addCourseToSchedule(course);
				}
			} else {
				int size = waitlist.size();
				Queue<Student> newQueue = new LinkedQueue<Student>(10);
				for(int i = 0; i < size; i++) {
					Student next = waitlist.dequeue();
					if(!s.equals(next))newQueue.enqueue(next);
				}
				waitlist = newQueue;
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());			
		}
	}
	
	/**
	 * Returns the number of remaining seats.
	 * @return number of remaining seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Checks if the student can be enrolled. Returns true 
	 * if there is room and the student is not already enrolled.
	 * 
	 * CourseRoll.canEnroll() should return true if the student 
	 * can be added to the waitlist. CourseRoll.canEnroll() should 
	 * return false if the Student is already on the waitlist. 
	 * Both of these checks are in addition to the earlier checks 
	 * of room in the roll and Student is not already on the roll
	 * @param s the student to check
	 * @return if the studetn can be enrolled
	 */
	public boolean canEnroll(Student s) {		
		boolean notOnWaitlist = true;
		
		for (int i = 0; i < waitlist.size(); i++) {
			Student current = waitlist.dequeue();
			//if the student is already on the waitlist, return false
			if (s.equals(current)) { 
				notOnWaitlist = false; //if already in waitlist
			}
			this.waitlist.enqueue(current);
		}
		
		// also check if there is enough room in class or if already enrolled
		return roll.size() <= enrollmentCap && !roll.contains(s) && notOnWaitlist;
	}
	
	/**
	 * Returns the enrollmentCap.
	 * @return the enrollmentCap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Sets the value of enrollmentCap.
	 * @param enrollmentCap the enrollmentCap to set
	 * @throws IllegalArgumentException if the enrollmentCap is invalid.
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if(enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		if (roll != null) {
			if(enrollmentCap >= roll.size()) {
				this.enrollmentCap = enrollmentCap;
				this.roll.setCapacity(enrollmentCap);
			} else {
				throw new IllegalArgumentException("Invalid enrollment cap");
			}
		} else {
			this.enrollmentCap = enrollmentCap;
		}
	}
	
}
