package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Activity;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;

/**
 * Tests the WolfScheduler class.
 * @author Sarah Heckman
 */
public class CourseCatalogTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course enrollment cap */
	private static final int ENROLLMENT_CAP = 10;
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	

	/**
	 * Resets course_records.txt for use in other tests.
	 */
	@Before
	public void setUp() {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	

	
	
	/**
	 * test two conflicting courses
	 */
	@Test
	public void testAddConflictCourses() {
		CourseCatalog ws = new CourseCatalog();
		
		

		ws.addCourseToCatalog("CSC216", TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		ws.addCourseToCatalog("CSC226", TITLE, "001", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		
		//time conflict; the reason it isn't passing now is because of some error in testing C.checkConflict(E)!!
		try {
			ws.addCourseToCatalog("CSC316", TITLE, "001", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
       		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
	}
	
	/**
	 * Tests WolfScheduler().
	 */
	@Test
	public void testWolfScheduler() {
		//Test with invalid file.  Should have an empty catalog and schedule. 
		CourseCatalog ws1 = new CourseCatalog();
		ws1.loadCoursesFromFile(invalidTestFile);
		
		assertEquals(0, ws1.getCourseCatalog().length);
		ws1.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//Test with valid file containing 8 courses.  Will test other methods in other tests.
		CourseCatalog ws2 = new CourseCatalog();
		ws2.loadCoursesFromFile(validTestFile);
		assertEquals(13, ws2.getCourseCatalog().length);		
	}
	
	/**
	 * Test WolfScheduler.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog ws = new CourseCatalog();
		RegistrationManager.getInstance().getFacultyDirectory().addFaculty("s", "smith", "sesmith5", "@.", "pw", "pw", 3 );
		ws.loadCoursesFromFile(validTestFile);
		
		//Attempt to get a course that doesn't exist
		assertNull(ws.getCourseFromCatalog("CSC492", "001"));
		
		//Attempt to get a course that does exist
		Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(c, ws.getCourseFromCatalog("CSC216", "001"));
		RegistrationManager.getInstance().clearData();
	}
	
	/**
	 * Test WolfScheduler.addCourse().
	 */
	@Test
	public void testAddCourseToSchedule() {
		CourseCatalog ws = new CourseCatalog();
				
		//Attempt to add a new course to the catalog
		assertTrue(ws.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(1, ws.getCourseCatalog().length);
		
		//Attempt to add a course that already exists, even if different section
		assertFalse(ws.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
	}
	
	
	
	/**
	 * Test WolfScheduler.removeCourse().
	 */
	@Test
	public void testRemoveCourse() {
		CourseCatalog ws = new CourseCatalog();
		
		//Attempt to remove from empty schedule
		assertFalse(ws.removeCourseFromCatalog("CSC226", "001"));
		
		//Add some courses and remove them
		assertTrue(ws.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertTrue(ws.addCourseToCatalog("CSC226", "Math", "001", 3, "sj454", 10, "MWF", 2000, 2100));
		assertTrue(ws.addCourseToCatalog("CSC116", "Java", "002", 4, "otk54", 10, "TH", 1000, 1200));
		assertEquals(3, ws.getCourseCatalog().length);

		
		//Check that removing a course that doesn't exist when there are 
		//scheduled courses doesn't break anything
		assertFalse(ws.removeCourseFromCatalog("CSC504", "005"));
		assertEquals(3, ws.getCourseCatalog().length);
		
		
		//Remove CSC226
		assertTrue(ws.removeCourseFromCatalog("CSC226", "001"));
		assertEquals(2, ws.getCourseCatalog().length);
		
		//Remove CSC116
		assertTrue(ws.removeCourseFromCatalog("CSC116", "002"));
		assertEquals(1, ws.getCourseCatalog().length);

		
		//Remove CSC216
		assertTrue(ws.removeCourseFromCatalog(NAME, SECTION));
		assertEquals(0, ws.getCourseCatalog().length);
		
		//Check that removing all doesn't break future adds
		assertTrue(ws.addCourseToCatalog("CSC230", "thing", "001", 1, "ff244", 10, "M", 1110, 1120));
		assertEquals(1, ws.getCourseCatalog().length);

	}
	
	/**
	 * Test WolfScheduler.resetSchedule()
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog ws = new CourseCatalog();
		ws.loadCoursesFromFile(validTestFile);
		assertEquals(13, ws.getCourseCatalog().length);
		
		ws.newCourseCatalog();
		assertEquals(0, ws.getCourseCatalog().length);
	}
	
	/**
	 * Test WolfScheduler.getCourseCatalog().
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog ws = new CourseCatalog();
		ws.loadCoursesFromFile(validTestFile);
		
		//Get the catalog and make sure contents are correct
		//Name, section, title
		String [][] catalog = ws.getCourseCatalog();
		//Row 0
		assertEquals("CSC116", catalog[0][0]);
		assertEquals("001", catalog[0][1]);
		assertEquals("Intro to Programming - Java", catalog[0][2]);
		//Row 1
		assertEquals("CSC116", catalog[1][0]);
		assertEquals("002", catalog[1][1]);
		assertEquals("Intro to Programming - Java", catalog[1][2]);
		//Row 2
		assertEquals("CSC116", catalog[2][0]);
		assertEquals("003", catalog[2][1]);
		assertEquals("Intro to Programming - Java", catalog[2][2]);
		//Row 3
		assertEquals("CSC216", catalog[3][0]);
		assertEquals("001", catalog[3][1]);
		assertEquals("Software Development Fundamentals", catalog[3][2]);
		//Row 4
		assertEquals("CSC216", catalog[4][0]);
		assertEquals("002", catalog[4][1]);
		assertEquals("Software Development Fundamentals", catalog[4][2]);
		//Row 5
		assertEquals("CSC216", catalog[5][0]);
		assertEquals("601", catalog[5][1]);
		assertEquals("Software Development Fundamentals", catalog[5][2]);
		//Row 6
		assertEquals("CSC217", catalog[6][0]);
		assertEquals("202", catalog[6][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[6][2]);
		//Row 7
		assertEquals("CSC217", catalog[7][0]);
		assertEquals("211", catalog[7][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[7][2]);
		//Row 8
		assertEquals("CSC217", catalog[8][0]);
		assertEquals("223", catalog[8][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[8][2]);
		//Row 9
		assertEquals("CSC217", catalog[9][0]);
		assertEquals("601", catalog[9][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[9][2]);
		//Row 10
		assertEquals("CSC226", catalog[10][0]);
		assertEquals("001", catalog[10][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", catalog[10][2]);
		//Row 11
		assertEquals("CSC230", catalog[11][0]);
		assertEquals("001", catalog[11][1]);
		assertEquals("C and Software Tools", catalog[11][2]);
		//Row 12
		assertEquals("CSC316", catalog[12][0]);
		assertEquals("001", catalog[12][1]);
		assertEquals("Data Structures and Algorithms", catalog[12][2]);
	}
	
	/**
	 * Test CourseCatalog.exportSchedule().
	 */
	@Test
	public void testSaveCourseCatalog() {
		//Test that empty schedule exports correctly
		CourseCatalog ws = new CourseCatalog();
		ws.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//Add courses and test that exports correctly
		ws.addCourseToCatalog("CSC216", "Software Development Fundamentals", "002", 3, "ixdoming", 10, "MW", 1330, 1445);
		ws.addCourseToCatalog("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
		assertEquals(2, ws.getCourseCatalog().length);
		ws.saveCourseCatalog("test-files/actual_schedule_export.txt");
		checkFiles("test-files/expected_schedule_export.txt", "test-files/actual_schedule_export.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}