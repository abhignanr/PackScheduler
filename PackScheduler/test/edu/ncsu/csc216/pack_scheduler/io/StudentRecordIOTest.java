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

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;
/**
 * Tests the StudentRecordIO class. Tests the ReadStudentRecords and WriteStudentRecords methods.
 * Testing utilizes a helper method checkFiles to compare if two files are equivalent.
 * @author mglittl2
 * @author dsbradle
 * @author sjwill25
 */
class StudentRecordIOTest {
	/**
	 * a valid student
	 */
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	
	/**
	 * a valid student
	 */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	
	/**
	 * a valid student
	 */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	
	/**
	 * a valid student
	 */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	
	/**
	 * a valid student
	 */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	
	/**
	 * a valid student
	 */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	
	/**
	 * a valid student
	 */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	
	/**
	 * a valid student
	 */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	
	/**
	 * a valid student
	 */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	
	/**
	 * a valid student
	 */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/**
	 * a string array of the valid students
	 */
	private String [] validStudents = {validStudent3, validStudent6, validStudent4, validStudent5,
	        validStudent2, validStudent8, validStudent0, validStudent9, validStudent1, validStudent7};

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
	        
	        for (int i = 0; i < validStudents.length; i++) {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
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
			SortedList<Student> records = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
			assertEquals(10, records.size());
			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], records.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + "test-files/student_records.txt");
		}
	}
	/**
	 * Tests readStudentRecords assuming that it is invalid
	 */
	@Test
	public void testInvalidReadStudentRecords() {
			SortedList<Student> records;
			try {
				records = StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt");
				assertEquals(0, records.size());
			} catch (FileNotFoundException e) {
				fail("Unexpected FileNotFoundException");	
			}
	}

	/**
	 * tests writeStudentRecords assuming that the file will be written to correctly
	 */
	@Test
	public void testValidWriteStudentRecords() {
		SortedList<Student> records = new SortedList<Student>();
		records.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	
		
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", records);
		} catch (IOException e) {
			fail("Cannot write to student records file");
		}
		
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	/**
	 * tests writeStudentRecords assuming that the file will notbe written to correctly
	 */
	@Test
	public void testInvalidWriteStudentRecords() {
		SortedList<Student> records = new SortedList<Student>();
		records.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	
		
		Exception a = assertThrows(IOException.class, () -> StudentRecordIO.writeStudentRecords("/home.sesmith5/actual_student_records.txt", records));
		assertEquals("/home.sesmith5/actual_student_records.txt (No such file or directory)", a.getMessage());
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