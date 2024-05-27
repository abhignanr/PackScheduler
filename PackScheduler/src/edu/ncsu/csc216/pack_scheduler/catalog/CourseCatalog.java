package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;
/**
 * WolfScheduler is a scheduling system for a student, similar to MyPackPortal's enrollment wizard feature, it maintains the catalog and selected schedule.
 * Its fields are title, schedule, and catalog.  Its methods are WolfScheduler, getCourseFromCatalog, addCourseToSchedule, removeCourseFromSchedule, 
 * resetSchedule, getCourseCatalog, getScheduledCourses, getFullScheduledCourses, setScheduleTitle, getScheduleTitle, and exportSchedule.
 * @author Maisa Little
 */
public class CourseCatalog {
	/** all courses*/
	SortedList<Course> catalog;

	/**
	 * constructor for WolfScheduler. Creates an empty catalog.
	 */
	public CourseCatalog() {
		this.catalog = new SortedList<Course>();
		
	}
	
	/**
	 * Re-initializes the catalog as an empty SortedList
	 */
	public void newCourseCatalog() {
		this.catalog = new SortedList<Course>();
	}
	
	/**
	 * Loads course from the file into this catalog.
	 * @param fileName the file to load from
	 * @throws IllegalArgumentException if the file does not exist.
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) { //thrown by method in courseRecordIO
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
	
	/**
	 * adds a course to the catalog
	 * @param name name of course
	 * @param title title of course
	 * @param section activity section
	 * @param credits credits for course
	 * @param instructorId instructor's Id
	 * @param enrollmentCap the capacity for enrollment
	 * @param meetingDays the days the class meets
	 * @param startTime the time the class starts
	 * @param endTime the time the class ends
	 * @return true if course is added false if it is already in schedule or not added
	 * @throws IllegalArgumentException if course is invalid
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		Course toAdd = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		for (int r = 0; r < this.catalog.size(); r++) { //making sure it isn't already in schedule
				if (toAdd.compareTo(this.catalog.get(r)) == 0) {
					return false;
				}
			
		}
		this.catalog.add(toAdd);
		return true;
	}

	/**
	 * Attempts to remove the course from the catalog. Returns true if this is successful, and false if the course is not in the list.
	 * @param name name of the course to remove
	 * @param section section of the course
	 * @return if this succeeded
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		Course toRemove = this.getCourseFromCatalog(name, section);
		if(toRemove == null)
				return false;
		
		this.catalog.remove(this.catalog.indexOf(toRemove));
		return true;
	}
	
	/**
	 * returns a course from the catalog using its name and section number
	 * @param name name of course
	 * @param section course section
	 * @return a course in the catalog, null if none found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		int size = this.catalog.size(); //how many items are in the catalog
		
		for (int i = 0; i < size; i++) {
			Course currentCourse = this.catalog.get(i);
			String currentName = currentCourse.getName();
			String currentSection = currentCourse.getSection();
			
			if (name.equals(currentName) && section.equals(currentSection)) {
				return currentCourse;
			}
		}
		
		return null; //if no match is found
	}
	
	
	/**
	 * returns a 2d string array with a row containing a courses name section and title for each course in catalog
	 * @return 2d array of courses
	 */
	public String[][] getCourseCatalog() {
        String [][] catalogArr = new String[catalog.size()][];
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            catalogArr[i] = c.getShortDisplayArray();
        }
        return catalogArr;
    }
	
	/**
	 * exports the students schedule using a method from CourseRecordIO
	 * @param filename file students schedule will be saved to
	 * @throws IllegalArgumentException if the file cannot be saved
	 */
	public void saveCourseCatalog(String filename) {
		try {
			CourseRecordIO.writeCourseRecords(filename, this.catalog);
		}
		catch (IOException ioe) { //thrown by CourseRecordIO
			throw new IllegalArgumentException("The file cannot be saved.");
		}
		
	}
	
	
	
}