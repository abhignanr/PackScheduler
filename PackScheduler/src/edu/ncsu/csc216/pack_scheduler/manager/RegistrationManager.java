package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This is the registration manager class. It has fields for a singleton
 * instance of itself, a courseCatalog, studentDirectory, registrar, and
 * currentUser.
 * 
 * @author David Bradley, Andres Ayala-Lagunas, Lwarence Stephenson
 *
 */
public class RegistrationManager {

	/** Registration Manager field holding a single instance of this class */
	private static RegistrationManager instance;
	/** Course Catalog field holding a courseCatalog object */
	private CourseCatalog courseCatalog;
	/** Student Directory field holding a studentDirectory object */
	private StudentDirectory studentDirectory;
	/** Faculty Directory */
	private FacultyDirectory facultyDirectory;
	/** User field holding the registrar */
	private User registrar;
	/** User field holding the current user */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** String field holding the name of the registrar properties file */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * RegistrationManager constructor. Calls the createRegistrar method,
	 * initializes courseCatalog, and initializes studentDirectory.
	 */
	private RegistrationManager() {
		createRegistrar();
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
		this.facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Create registrar method creates a new registrar using information from the
	 * registrar properties file.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			this.registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * The hashPW method returns a hashed password.
	 * 
	 * @param pw A string holding the password
	 * @return A string holding the hashed pw
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * The getInstance method creates a single new instance of the
	 * RegistrationManager class, and then returns that object.
	 * 
	 * @return An instance of the RegistrationManager class
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Getter method for the courseCatalog field.
	 * 
	 * @return The CourseCatalog object in the courseCatalog field
	 */
	public CourseCatalog getCourseCatalog() {
		return this.courseCatalog;
	}

	/**
	 * Getter method for the studentDirectory field.
	 * 
	 * @return The StudentDirectory object in the studentDirectory field
	 */
	public StudentDirectory getStudentDirectory() {
		return this.studentDirectory;
	}
	
	/**
	 * Returns the facultyDirectory.
	 * @return the facultyDirectory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}
	
	/**
	 * adds a course to the faculty's schedule
	 * @param course course to be added to schedule
	 * @param faculty faculty whose schedule needs to be updated
	 * @return true if course was added
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (!currentUser.equals(registrar)) {
			throw new IllegalArgumentException();
		}

		return faculty.getSchedule().addCourseToSchedule(course);
	}
	
	/**
	 * removes a course from faculty's schedule
	 * @param course course to be removed from schedule
	 * @param faculty faculty whose schedule needs to be updated
	 * @return returns true if course was removed
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		boolean removed = false;
		if(currentUser == null || !currentUser.equals(registrar))
			throw new IllegalArgumentException();
		removed = faculty.getSchedule().removeCourseFromSchedule(course);
		return removed;
	}
	
	/**
	 * resets faculty's schedule
	 * @param faculty faculty whose schedule needs to be reset
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (!currentUser.equals(registrar)) {
			throw new IllegalArgumentException();
		}
		if (currentUser != null) {
			faculty.getSchedule().resetSchedule();
		}
	}

	/**
	 * The login method takes a user's id and password, and sets the currentUser
	 * field to either a Student or a registrar, depending on who the information
	 * given pertains to. If there is no user who matches the id provided, 
	 * throws an IllegalArgumentException.
	 * 
	 * @param id       String holding the value of a user's id
	 * @param password String holding the value of a user's password
	 * @throws IllegalArgumentException if the id does not exist in the database
	 * @return A boolean value for whether or not the user was successfully logged
	 *         in
	 */
	public boolean login(String id, String password) {
		if(currentUser != null)
			return false;
		
		Student s = this.getStudentDirectory().getStudentById(id);
		Faculty f = this.getFacultyDirectory().getFacultyById(id);
		if(s == null && f == null && !this.registrar.getId().equals(id))
			throw new IllegalArgumentException("User doesn't exist.");
		
		String localHashPW = hashPW(password);
		
		if (this.registrar.getPassword().equals(localHashPW) && this.registrar.getId().equals(id)) {
			this.currentUser = registrar;
			return true;
		} else if (s != null && s.getPassword().equals(localHashPW)) {
			this.currentUser = s;
			return true;
		} else if (f != null && f.getPassword().equals(localHashPW)) {
			this.currentUser = f;
			return true;
		}
		
		return false;
	}

	/**
	 * The logout method is used to reset the currentUser field by setting its value
	 * to null
	 */
	public void logout() {
		this.currentUser = null;
	}

	/**
	 * Getter method for the currentUser field.
	 * 
	 * @return The user object stored in the currentUser field.
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}

	/**
	 * The clearData method is used to refresh the data tied to the
	 * RegistrationManager class. This method sets relevant fields to null, or a new
	 * version of the object.
	 */
	public void clearData() {
		this.courseCatalog.newCourseCatalog();
		this.studentDirectory.newStudentDirectory();
		this.facultyDirectory.newFacultyDirectory();
		this.currentUser = null;
	}

	/**
	 * The registrar class provides functionality for a registrar, similar to a
	 * student. It is an inner class of RegistrationManager, and, like student, also
	 * extends the User superclass. This class takes a firstName, lastName, id,
	 * email, and a hashedPW.
	 * 
	 * @author David Bradley, Andres Ayala-Lagunas, Lawrence Stephenson
	 *
	 */
	private static class Registrar extends User {
		/**
		 * This is the constructor for the registrar class which calls the constructor
		 * of the super class for certain parameters.
		 * 
		 * @param firstName String holding the value of the registrar's first name
		 * @param lastName  String holding the value of the registrar's last name
		 * @param id        String holding the value of the registrar's id
		 * @param email     String holding the value of the registrar's email
		 * @param hashPW    String holding the value of the registrar's hashed password
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped and false otherwise
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s); 
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
}
