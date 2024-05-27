package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * tests FacultyDirectory.java
 * @author Maisa Little
 *
 */
public class FacultyDirectoryTest {
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_COURSES = 2;

	/**
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		//Test that the StudentDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newStudentDirectory().
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile().
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory sd = new FacultyDirectory();
				
		//Test valid file
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> sd.loadFacultyFromFile("test-files/a.txt"));
		assertEquals("Unable to read file test-files/a.txt", e.getMessage());

	}

	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory sd = new FacultyDirectory();
		
		//Test valid Student
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String [][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty() for invalid cases
	 */
	@Test
	public void testAddFacultyInvalidOptions() {
		FacultyDirectory sd = new FacultyDirectory();
		sd.loadFacultyFromFile(validTestFile);
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "", "pw", 1));
		assertEquals("Invalid password", e.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "", 1));
		assertEquals("Invalid password", e2.getMessage());
		Exception f = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pz", "pw", 1));
		assertEquals("Passwords do not match", f.getMessage());
		Exception g = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 20));
		assertEquals("Invalid max courses", g.getMessage());
		Exception g2 = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 0));
		assertEquals("Invalid max courses", g2.getMessage());
		Exception h = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", null, "pw", 1));
		assertEquals("Invalid password", h.getMessage());
		Exception h2 = assertThrows(IllegalArgumentException.class, () -> sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", null, 1));
		assertEquals("Invalid password", h2.getMessage());
		assertFalse(sd.addFaculty("Zack", "King", "awitt", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 1));
		
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		assertFalse(sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
	}
	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory sd = new FacultyDirectory();
				
		//Add Faculty and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertTrue(sd.removeFaculty("awitt"));
		String [][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Norman", facultyDirectory[5][0]);
		assertEquals("Brady", facultyDirectory[5][1]);
		assertEquals("nbrady", facultyDirectory[5][2]);
	}
	
	/**
	 * Tests getFacultyById method. Expects null output if student is not found.
	 */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory sd = new FacultyDirectory();
		sd.loadFacultyFromFile(validTestFile);
		assertEquals("Fiona", sd.getFacultyById("fmeadow").getFirstName());
		assertEquals(null, sd.getFacultyById("nonexistent"));
		
		FacultyDirectory sd2 = new FacultyDirectory();
		
		assertEquals(null, sd2.getFacultyById("zking"));
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory sd = new FacultyDirectory();
		
		//Add a Faculty
		sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		sd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		sd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		assertEquals(3, sd.getFacultyDirectory().length);
		sd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> sd.saveFacultyDirectory("/home.sesmith5/actual_faculty_records.txt"));
		assertEquals("Unable to write to file /home.sesmith5/actual_faculty_records.txt", e.getMessage());
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	

}
