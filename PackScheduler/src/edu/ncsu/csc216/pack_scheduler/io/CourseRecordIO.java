package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
//import java.util.List;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the list!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the list with all the courses we read!
	    return courses;
	}

	/**
	 * reads the course information from a line and turns it into an object
	 * @param line line of course information
	 * @return new Course object using info from line
	 * @throws IllegalArgumentException if there are too many elements or the file is invalid
	 */
	private static Course readCourse(String line) {
	   Scanner scan = new Scanner(line);
	   scan.useDelimiter(",");
	   
	   try { //separate data into params for Course()
		   String name = scan.next();
		   String title = scan.next();
		   String section = scan.next();
		   int credits = scan.nextInt();
		   String instructorId = scan.next();
		   int enrollmentCap = scan.nextInt();
		   String meetingDays = scan.next();
		  
	      if ("A".equals(meetingDays)) { //if meeting days are arranged
	         if (scan.hasNext()) { //there should be no meeting times
	        	 scan.close();
	        	 throw new IllegalArgumentException("Too many elements.");
	         }
	         else { //use constructor for arranged meetingDays
	        	 scan.close();
	        	 Course c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, 0, 0);
	        	 Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
    			 if(f != null) 
    				 f.getSchedule().addCourseToSchedule(c);
	        	 return c;
	         }
	      }
	      else { //if meeting days are normal, there should be start and end times
	    	  int startTime = scan.nextInt();
	    	  int endTime = scan.nextInt();
		      if (scan.hasNext()) { //but nothing more after the times
		    	  scan.close();
		    	  throw new IllegalArgumentException("Too many elements.");
		      }
		      scan.close();
		      Course c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime); //use constructor for all data
		      Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
		      if(f != null) 
 				 f.getSchedule().addCourseToSchedule(c);
	        	 return c;
	      }
	   } catch (Exception e) {
		   scan.close();
		   throw new IllegalArgumentException("Invalid file.");
	   }
	}
	
	/**
	 * Writes course information provided and exports it to a file
	 * @param fileName the name of the file to be outputted to
	 * @param courses the alphabetically sorted list of courses
	 * @throws IOException if there is an issue outputting to the file
	 */
		public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
			PrintStream fileWriter = new PrintStream(new File(fileName));
    	
			for (int i = 0; i < courses.size(); i++) {
				fileWriter.println(courses.get(i).toString());
			}
    	
			fileWriter.close();
		
	}
}