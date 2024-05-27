package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Student class. Tests the constructor, getters, setters, equals,
 * hashcode, and toString.
 * 
 * @author SarahHeckman
 */
public class StudentTest {

	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	// This is a block of code that is executed when the StudentTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the StudentTest
	// object is
	// constructed. By automating the hash of the plaintext password, we are
	// not tied to a specific hash implementation. We can change the algorithm
	// easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}

	/**
	 * Tests the constructor to confirm it creates a correct Student object.
	 */
	@Test
	public void testConstructor() {
		Student student = new Student(firstName, lastName, id, email, hashPW, 18);
		assertEquals(firstName, student.getFirstName());
		assertEquals(lastName, student.getLastName());
		assertEquals(id, student.getId());
		assertEquals(email, student.getEmail());
		assertEquals(hashPW, student.getPassword());
		assertEquals(18, student.getMaxCredits());

		String multiPeriod = "test.a@b.c";
		assertDoesNotThrow(() -> new Student(firstName, lastName, id, multiPeriod, hashPW));
	}

	/**
	 * Tests that the setters fail for missing setters for a group of entries.
	 * 
	 * @param firstName  the first name to test
	 * @param lastName   the last name to test
	 * @param id         the id to test
	 * @param email      the email to test
	 * @param hashPW     the password to test
	 * @param maxCredits the maxCredits value to test
	 * @param message    the expected message
	 */
	@ParameterizedTest(name = "{index} => firstName: {0}, lastName: {1}, id: {2}, email: {3}, hashPW: {4}, expected message: {5}")
	@CsvSource({ ",last,flast,first_last@ncsu.edu,ab,18,Invalid first name",
			"first,,flast,first_last@ncsu.edu,ab,18,Invalid last name",
			"first,last,,first_last@ncsu.edu,ab,18,Invalid id", "first,last,flast,,ab,18,Invalid email",
			"first,last,flast,first_last@ncsu.edu,,18,Invalid password",
			"first,last,flast,first_last@ncsu.edu,ab,19,Invalid max credits",
			"first,last,flast,first_last@ncsu.edu,ab,2,Invalid max credits", })
	public void testInvalidNulls(String firstName, String lastName, String id, String email, String hashPW,
			int maxCredits, String message) {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, email, hashPW, maxCredits));
		assertEquals(e.getMessage(), message);
	}

	/**
	 * Tests the setEmail() method with expected invalid conditions.
	 * 
	 * @param invalidEmail the invalid email to test
	 */
	@ParameterizedTest(name = "{index} => {0}")
	@ValueSource(strings = { "test.com", "test@com", "test.a@b" })
	public void testEmailInvalid(String invalidEmail) {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, invalidEmail, hashPW));
		assertEquals(e.getMessage(), "Invalid email");
	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}

	/**
	 * Test hashCode() method.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(firstName, lastName, id, email, hashPW);
		User s2 = new Student(firstName, lastName, id, email, hashPW);
		User s3 = new Student(firstName, lastName + "more", id, email, hashPW);

		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
	}

	/**
	 * Test equals() method.
	 */
	@Test
	public void testEquals() {
		User s1 = new Student(firstName, lastName, id, email, hashPW);
		Student s2 = new Student(firstName, lastName, id, email, hashPW);
		User s3 = new Student(firstName, lastName + "more", id, email, hashPW);
		Object o = new Object();
		Object o2 = null;

		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(o2));
		assertFalse(s2.equals(o));
		assertTrue(s1.equals(s1));

		s2.setFirstName("different");
		assertFalse(s1.equals(s2));
		s2.setFirstName(firstName);

		s2.setLastName("different");
		assertFalse(s1.equals(s2));
		s2.setLastName(lastName);

		s2.setId("different");
		assertFalse(s1.equals(s2));
		s2.setId(id);

		s2.setEmail("different@.");
		assertFalse(s1.equals(s2));
		s2.setEmail(email);

		s2.setMaxCredits(12);
		assertFalse(s1.equals(s2));
		s2.setMaxCredits(18);

		// have to do differently since hashPW can't be changed
		assertFalse(s1.equals(new Student(firstName, lastName, id, email, "different")));

	}

	/**
	 * tests the Student.compareTo(E) method
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student("Alan", "Alexus", "aalexus", "aalexus@ncsu.edu", hashPW);
		Student s2 = new Student("Billy", "Bob", "bbob", "bbob@ncsu.edu", hashPW);
		Student s3 = new Student("Cam", "Calahan", "ccalahan", "ccalahan@ncsu.edu", hashPW);
		Student s4 = new Student("Sam", "Calahan", "scalahan", "ccalahan@ncsu.edu", hashPW);
		Student s5 = new Student("Bam", "Calahan", "bcalahan", "ccalahan@ncsu.edu", hashPW);
		Student s6 = new Student("Cam", "Calahan", "ccalahan2", "ccalahan@ncsu.edu", hashPW);

		// no similarities in name

		assertEquals(0, s1.compareTo(s1));
		assertTrue(s1.compareTo(s2) < 0);
		assertTrue(s3.compareTo(s1) > 0);

		// same last name
		assertTrue(s3.compareTo(s4) < 0);
		assertTrue(s3.compareTo(s5) > 0);

		// same everything but unityID
		assertTrue(s3.compareTo(s6) < 0);
		assertTrue(s6.compareTo(s3) > 0);
	}

	/**
	 * Tests the canAdd method in the Student class
	 */
	@Test
	public void canAddTest() {
		Student s1 = new Student("law", "steph", "lsteph", "lsteph@ncsu.edu", "pw");
		assertFalse(s1.canAdd(null)); // assert canAdd returns false adding a null course

		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 4, "sesmith5", 11, "MW", 1330,
				1445);
		Course c2 = new Course("CSC316", "Software Development", "002", 3, "sesmith5", 11, "MW", 1330, 1445);
		s1.getSchedule().addCourseToSchedule(c);
		assertFalse(s1.canAdd(c));
		assertFalse(s1.canAdd(c2));
	}
}
