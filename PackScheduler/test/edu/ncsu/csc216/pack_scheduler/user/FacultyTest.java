package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


/**
 * Tests the Faculty class. Tests the constructor, getters, setters, equals,
 * hashcode, and toString.
 * 
 * @author Maisa Little
 */
public class FacultyTest {

	/** Test first name. */
	private String firstName = "first";
	/** Test last name */
	private String lastName = "last";
	/** Test id */
	private String id = "flast";
	/** Test email */
	private String email = "first_last@ncsu.edu";
	/** Test hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	// This is a block of code that is executed when the FacultyTest object is
	// created by JUnit. Since we only need to generate the hashed version
	// of the plaintext password once, we want to create it as the FacultyTest
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
		Faculty f = new Faculty(firstName, lastName, id, email, hashPW, 2);
		assertEquals(firstName, f.getFirstName());
		assertEquals(lastName, f.getLastName());
		assertEquals(id, f.getId());
		assertEquals(email, f.getEmail());
		assertEquals(hashPW, f.getPassword());
		assertEquals(2, f.getMaxCourses());

//		String multiPeriod = "test.a@b.c";
		assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, "email@ncsu.edu", hashPW, 1));
	}

	/**
	 * Tests that the setters fail for missing setters for a group of entries.
	 * 
	 * @param firstName  the first name to test
	 * @param lastName   the last name to test
	 * @param id         the id to test
	 * @param email      the email to test
	 * @param hashPW     the password to test
	 * @param maxCourses the maxCourses value to test
	 * @param message    the expected message
	 */
	@ParameterizedTest(name = "{index} => firstName: {0}, lastName: {1}, id: {2}, email: {3}, hashPW: {4}, expected message: {5}")
	@CsvSource({ ",last,flast,first_last@ncsu.edu,ab,18,Invalid first name",
			"first,,flast,first_last@ncsu.edu,ab,18,Invalid last name",
			"first,last,,first_last@ncsu.edu,ab,18,Invalid id", "first,last,flast,,ab,18,Invalid email",
			"first,last,flast,first_last@ncsu.edu,,18,Invalid password",
			"first,last,flast,first_last@ncsu.edu,ab,19,Invalid max courses",
			"first,last,flast,first_last@ncsu.edu,ab,0,Invalid max courses", })
	public void testInvalidNulls(String firstName, String lastName, String id, String email, String hashPW,
			int maxCourses, String message) {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, lastName, id, email, hashPW, maxCourses));
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
				() -> new Faculty(firstName, lastName, id, invalidEmail, hashPW, 2));
		assertEquals(e.getMessage(), "Invalid email");
	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Faculty(firstName, lastName, id, email, hashPW, 2);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",2", s1.toString());
	}

	/**
	 * Test hashCode() method.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User s2 = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User s3 = new Faculty(firstName, lastName + "more", id, email, hashPW, 2);

		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
	}

	/**
	 * Test equals() method.
	 */
	@Test
	public void testEquals() {
		User s1 = new Faculty(firstName, lastName, id, email, hashPW, 2);
		Faculty s2 = new Faculty(firstName, lastName, id, email, hashPW, 2);
		User s3 = new Faculty(firstName, lastName + "more", id, email, hashPW, 2);
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

		s2.setMaxCourses(1);
		assertFalse(s1.equals(s2));
		s2.setMaxCourses(2);

		// have to do differently since hashPW can't be changed
		assertFalse(s1.equals(new Faculty(firstName, lastName, id, email, "different", 2)));

	}
}
