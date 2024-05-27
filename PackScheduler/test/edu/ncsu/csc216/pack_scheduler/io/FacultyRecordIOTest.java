package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;
/**
 * Tests the StudentRecordIO class. Tests the ReadStudentRecords and WriteStudentRecords methods.
 * Testing utilizes a helper method checkFiles to compare if two files are equivalent.
 * @author mglittl2
 * @author dsbradle
 * @author sjwill25
 */
class FacultyRecordIOTest {
	/**
	 * a valid Faculty
	 */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
	
	/**
	 * a valid Faculty
	 */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";

	/**
	 * a string array of the valid students
	 */
	private String [] validFaculty = {validFaculty0, validFaculty1, validFaculty2, validFaculty3,
	        validFaculty4, validFaculty5, validFaculty6, validFaculty7};

	/**
	 * a string of the hashCoded pw
	 */
	private String hashPW;
	
	/**
	 * hash algorithm string
	 */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Sets up for tests
	 */
	@BeforeEach
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validFaculty.length; i++) {
	            validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	/**
	 * Tests readStudentRecords assuming that it is valid
	 */
	@Test
	public void testValidReadStudentRecords() {
		try {
			LinkedList<Faculty> records = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");
			assertEquals(8, records.size());
			for (int i = 0; i < validFaculty.length; i++) {
				assertEquals(validFaculty[i], records.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + "test-files/student_records.txt");
		}
	}
	/**
	 * Tests readFacultyRecords assuming that it is invalid
	 */
	@Test
	public void testInvalidReadFacultyRecords() {
			LinkedList<Faculty> records;
			try {
				records = FacultyRecordIO.readFacultyRecords("test-files/invalid_student_records.txt");
				assertEquals(0, records.size());
			} catch (FileNotFoundException e) {
				fail("Unexpected FileNotFoundException");	
			}
	}

	/**
	 * tests writeFacultyRecords assuming that the file will be written to correctly
	 */
	@Test
	public void testValidWriteFacultyRecords() {
		LinkedList<Faculty> records = new LinkedList<Faculty>();
//		records.addAll(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15)));
		records.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 2));
		records.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3));
		records.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 1));
		
		
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", records);
		} catch (IOException e) {
			fail("Cannot write to faculty records file");
		}
		
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}

	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
				//The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}		
}