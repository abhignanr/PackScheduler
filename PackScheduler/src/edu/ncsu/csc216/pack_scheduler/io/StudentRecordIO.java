package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * StudentRecordIO is a class with static methods for reading/writing Student records.  Its methods are readStudentRecords, readStudent
 * , and writeStudentRecords. It has no fields.
 * @author mglittl2
 * @author dsbradle
 * @author sjwill25
 */
public class StudentRecordIO {

	/**
	 * Reads in the student records and returns them as an Sorted List. Invalid records will be ignored.
	 * @param fileName the file's name to pull from
	 * @return the student records
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		
		while (input.hasNextLine()) {
			try {
				students.add(readStudent(input.nextLine()));
			} catch (IllegalArgumentException e) {
				// ignore, since don't care about invalid records
			}
		}
		input.close();
		return students;
	}
	
	/**
	 * Attempts to create a student from the line provided. If the information provided is invalid in any way, will throw IllegalArgumentException.
	 * @param line the line to create the student from
	 * @return the completed student
	 * @throws IllegalArgumentException if the line is an invalid representation of a student
	 */
	private static Student readStudent(String line) {
		//Similar to using Scanner with delimiter, but creates an array instead of a queue
		String tokens[] = line.split(",");
		if (tokens.length != 6)
			throw new IllegalArgumentException("Invalid number of tokens in line");
		
		try {
			return new Student(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}



	/**
	 * Method to output the Student directory information into a file
	 * @param fileName name of file to write the info into
	 * @param studentDirectory Sorted List of Student objects
	 * @throws IOException if file cannot be written
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream write = new PrintStream(new File(fileName));

    	for (int i = 0; i < studentDirectory.size(); i++) {
    	    write.println(studentDirectory.get(i).toString());
    	}

    	write.close();
		
	}
	

}