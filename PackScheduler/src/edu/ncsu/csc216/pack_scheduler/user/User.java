package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * The abstract user class containing a first name, last name, id, email, and
 * password. This includes getters and setters.
 * 
 * @author David Bradley, Andres Ayala-Lagunas, Lwarence Stephenson
 *
 */
public abstract class User {

	/**
	 * Constructor for the User class
	 * 
	 * @param firstName String holding the first name of the user
	 * @param lastName  String holding the last name of the user
	 * @param id        String holding the id of the user
	 * @param email     String holding the email of the user
	 * @param password  String holding the password of the user
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/** First name of the student */
	private String firstName;
	/** Last name of the student */
	private String lastName;
	/** Unity id of student */
	private String id;
	/** Email of student */
	private String email;
	/** Password hash of student's login info */
	private String password;

	/**
	 * gets student first name
	 * 
	 * @return String first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * gets student last name
	 * 
	 * @return String last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * gets student unity id
	 * 
	 * @return the unity id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * gets the student email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email. If the email is null or an empty string, this will throw an
	 * IllegalArgumentException. If there is not a '@' and '.' symbol, or if the
	 * last '.' comes before the '@', this will trhow an IllegalArgumentException.
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException if the email string is invalid
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (email.lastIndexOf(".") < email.indexOf("@")) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Returns the password hash.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password hash. Throws IllegalArgumentException if the password is
	 * null or an empty string.
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException if the password string is invalid
	 */
	public void setPassword(String password) {
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Sets the first name Throws IllegalArgumentException if the first name is null
	 * or an empty string.
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if the first name string is invalid
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the last name. Throws IllegalArgumentException if the last name is null
	 * or an empty string.
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if the last name string is invalid
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the unity id. Throws IllegalArgumentException if the id is null or an
	 * empty string.
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException if the id string is invalid
	 */
	public void setId(String id) {
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Overrides the hashCode method for the User class
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}

	/**
	 * Overrides the equals method for the User class
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}

}