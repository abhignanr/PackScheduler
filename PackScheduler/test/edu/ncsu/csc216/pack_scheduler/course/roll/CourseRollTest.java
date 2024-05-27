package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

class CourseRollTest {
	/** Student to be used when testing */
	private Student s = new Student("law", "steph", "lsteph", "lsteph@ncsu.edu", "pw");
	/** Student to be used when testing */
	private Student s1 = new Student("john", "steph", "jsteph", "jsteph@ncsu.edu", "pw1");
	/** Student to be used when testing */
	private Student s2 = new Student("mark", "brown", "mbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s3 = new Student("bill", "brown", "bbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s4 = new Student("lathew", "brown", "lbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s5 = new Student("shakespeare", "brown", "sbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s6 = new Student("bob", "brown", "mbrown", "bbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s7 = new Student("donatello", "brown", "dbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s8 = new Student("karen", "brown", "kbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s9 = new Student("joe", "biden", "jbrown", "mbrown@ncsu.edu", "pw2");
	/** Student to be used when testing */
	private Student s10 = new Student("callahan", "plug", "cplug", "mbrown@ncsu.edu", "pw2");
	/** Course for testing */
	private Course c = new Course("CSC216", "title", "001", 3, "instructor", 30, "MWTHF");
	@Test
	void testCourseRollInvalid() {
		// test constructing CourseRoll with invalid enrollmentCap throws IAE
		assertThrows(IllegalArgumentException.class, () -> new CourseRoll(c, 251));
		assertThrows(IllegalArgumentException.class, () -> new CourseRoll(c, 9));
		assertThrows(IllegalArgumentException.class, () -> new CourseRoll(null, 30));
		// test setEnrollmentCap with an invalid enrollment cap throws IAE
		CourseRoll cr = new CourseRoll(c, 30);
		assertThrows(IllegalArgumentException.class, () -> cr.setEnrollmentCap(8));
		assertThrows(IllegalArgumentException.class, () -> cr.setEnrollmentCap(251));
	}

	@Test
	void testCourseRoll() {
		CourseRoll cr = new CourseRoll(c, 10);

		assertEquals(10, cr.getEnrollmentCap()); // test enrollmentCap is correct
		assertEquals(10, cr.getOpenSeats()); // test number of open seats is correct

		// add students to CourseRoll
		cr.enroll(s);
		cr.enroll(s1);
		cr.enroll(s2);

		assertEquals(10, cr.getEnrollmentCap()); // test the enrollment stays the same
		assertEquals(7, cr.getOpenSeats()); // test # of open seats has lost 3
	}

	@Test
	void testEnroll() {
		CourseRoll cr = new CourseRoll(c, 10);
		assertThrows(IllegalArgumentException.class, () -> cr.enroll(null)); // test enrolling null student throws IAE

		cr.enroll(s);
		assertThrows(IllegalArgumentException.class, () -> cr.enroll(s)); // test enrolling the same student throws IAE

		// enroll 9 more students into CourseRoll
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		// assert adding a student to a full class adds to waitlist
		 cr.enroll(s10);
		 assertEquals(cr.getNumberOnWaitlist(), 1);
	}

	@Test
	void testDrop() {
		CourseRoll cr = new CourseRoll(c, 10);
		Student student = null;
		
		assertThrows(IllegalArgumentException.class, () -> cr.drop(student)); // assert dropping a null student throws
																				// IAE

		// enroll 10 students into CourseRoll
		cr.enroll(s);
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		assertEquals(0, cr.getOpenSeats()); // test there are no open seats
		cr.enroll(s10);
		assertEquals(0, cr.getOpenSeats()); // test there are no open seats

		cr.drop(s);
		assertEquals(0, cr.getOpenSeats()); // test there are no open seats
		cr.enroll(s);
		cr.drop(s);
		assertEquals(0, cr.getOpenSeats()); // test there are no open seats

		cr.drop(s10);
		cr.drop(s1);
		cr.drop(s2);
		assertEquals(3, cr.getOpenSeats()); // test there are 3 open seats

		cr.drop(s3);
		cr.drop(s4);
		cr.drop(s5);
		cr.drop(s6);
		cr.drop(s7);
		cr.drop(s8);
		cr.drop(s9);
		assertEquals(10, cr.getOpenSeats()); // test there are now 10 open seats
	}

	@Test
	void testCanEnrollStudent() {
		CourseRoll cr = new CourseRoll(c, 10);
		cr.enroll(s); // enroll student s
		assertFalse(cr.canEnroll(s)); // confirm that canEnroll returns false since s is already added

		// enroll 9 more students into CourseRoll
		cr.enroll(s1);
		cr.enroll(s2);
		cr.enroll(s3);
		cr.enroll(s4);
		cr.enroll(s5);
		cr.enroll(s6);
		cr.enroll(s7);
		cr.enroll(s8);
		cr.enroll(s9);
		assertFalse(cr.canEnroll(s1)); // confirm that a student cannot be added since roll is full
	}
	
	@Test
	void testCanAdd() {
		CourseRoll cr = new CourseRoll(c, 10);
		cr.enroll(s);
		cr.setEnrollmentCap(11);
		assertEquals(11, cr.getEnrollmentCap());
	}
}
