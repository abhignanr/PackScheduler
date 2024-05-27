package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test for Schedule class.
 * @author davidb
 *
 */
class ScheduleTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#Schedule()}.
	 */
	@Test
	void testSchedule() {
		Schedule s = new Schedule();
		assertEquals("My Schedule", s.getTitle());
		assertEquals(0, s.getSchedule().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#addCourseToSchedule(edu.ncsu.csc216.pack_scheduler.course.Course)}.
	 */
	@Test
	void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/course_records.txt");
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC216", "001"));
		
		//tests null
		assertThrows(NullPointerException.class, () -> s.addCourseToSchedule(null));
		
		// tests .isDuplicate IAE
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(cc.getCourseFromCatalog("CSC216", "002")));
		assertEquals("You are already enrolled in CSC216", e2.getMessage());
		
		// tests .equals IAE
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "001", 3, "semsith5", 10, "TH", 1330, 1445)));
		assertEquals("You are already enrolled in CSC216", e3.getMessage());
		
		// tests conflicts
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(new Course("CSC226", "Discrete Mathematics", "001", 3, "semsith5", 10, "TH", 1400, 1500)));
		assertEquals("The course cannot be added due to a conflict.", e4.getMessage());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#removeCourseFromSchedule(edu.ncsu.csc216.pack_scheduler.course.Course)}.
	 */
	@Test
	void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/course_records.txt");
		
		assertFalse(s.removeCourseFromSchedule(cc.getCourseFromCatalog("CSC216", "001")));
		
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC216", "001"));
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC116", "002"));
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC226", "001"));
		
		assertTrue(s.removeCourseFromSchedule(cc.getCourseFromCatalog("CSC216", "001")));
		List<Course> l = s.getSchedule();
		assertAll("List after removeal", 
				() -> assertEquals(cc.getCourseFromCatalog("CSC116", "002"), l.get(0)), 
				() -> assertEquals(cc.getCourseFromCatalog("CSC226", "001"), l.get(1)));
		
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#resetSchedule()}.
	 */
	@Test
	void testResetSchedule() {
		Schedule s = new Schedule();
		s.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "001", 3, "semsith5", 11, "TH", 1330, 1445));
		s.setTitle("My Awesome Schedule");
		assertEquals("My Awesome Schedule", s.getTitle());
		assertEquals(1, s.getSchedule().size());
		s.resetSchedule();
		assertEquals("My Schedule", s.getTitle());
		assertEquals(0, s.getSchedule().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#getScheduledCourses()}.
	 */
	@Test
	void testGetScheduledCourses() {
		Schedule s = new Schedule();
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/course_records.txt");
		
		Course c1 = cc.getCourseFromCatalog("CSC216", "001");
		Course c2 = cc.getCourseFromCatalog("CSC116", "002");
		Course c3 = cc.getCourseFromCatalog("CSC226", "001");
		
		String[][] expected = {
				{c1.getName(), c1.getSection(), c1.getTitle(), c1.getMeetingString(), c1.getCourseRoll().getEnrollmentCap() + ""},
				{c2.getName(), c2.getSection(), c2.getTitle(), c2.getMeetingString(), c2.getCourseRoll().getEnrollmentCap() + ""},
				{c3.getName(), c3.getSection(), c3.getTitle(), c3.getMeetingString(), c3.getCourseRoll().getEnrollmentCap() + ""}
		};
		
		s.addCourseToSchedule(c1);
		s.addCourseToSchedule(c2);
		s.addCourseToSchedule(c3);
		String[][] actual = s.getScheduledCourses();
		
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j], "Course " + i + " failed for element " + j);
			}
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule#setTitle(java.lang.String)}.
	 */
	@Test
	void testSetTitle() {
		Schedule s = new Schedule();
		assertEquals("My Schedule", s.getTitle());
		s.setTitle("My Awesome Schedule");
		assertEquals("My Awesome Schedule", s.getTitle());
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setTitle(null));
		assertEquals("Title cannot be null.", e.getMessage());
		
	}
	
	/**
	 * Tests that the getScheduleCredits method works.
	 */
	@Test
	void testGetScheduleCredits() {
		Schedule s = new Schedule();
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(0, s.getScheduleCredits());
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC216", "001"));
		assertEquals(3, s.getScheduleCredits());
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC217", "202"));
		assertEquals(4, s.getScheduleCredits());
	}
	
	/**
	 * Tests the canAdd method.
	 */
	@Test
	void testCanAdd() {
		Schedule s = new Schedule();
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile("test-files/course_records.txt");
		
		assertTrue(s.canAdd(cc.getCourseFromCatalog("CSC116", "001")));
		s.addCourseToSchedule(cc.getCourseFromCatalog("CSC116", "001"));

		assertFalse(s.canAdd(null));
		assertFalse(s.canAdd(cc.getCourseFromCatalog("CSC116", "001")));
		assertFalse(s.canAdd(cc.getCourseFromCatalog("CSC217", "202")));
		
		assertTrue(s.canAdd(cc.getCourseFromCatalog("CSC216", "001")));
		
		
		
	}
}
