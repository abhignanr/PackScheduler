package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Provides methods to read faculty records from files and write to files.
 * @author davidb
 *
 */
public class FacultyRecordIO {
	/**
	 * Reads a list if faculty in from a file and returns it. Invalid Records will be ignored.
	 * @param fileName the name of the file to read from
	 * @return the list of faculty records
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		
		while (input.hasNextLine()) {
			try {
				faculty.add(readFaculty(input.nextLine()));
			} catch (IllegalArgumentException e) {
				// ignore, since don't care about invalid records
			}
		}
		input.close();
		return faculty;
	}

	/**
	 * Parses a faculty from a line.
	 * @param nextLine the line to parse from
	 * @return  the created faculty
	 * @throws IllegalArgumentException if the line is invalid
	 */
	private static Faculty readFaculty(String nextLine) {
		String tokens[] = nextLine.split(",");
		if(tokens.length != 6) throw new IllegalArgumentException();
		try {
			return new Faculty(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	/**
	 * Method to output the Faculty directory information into a file
	 * @param fileName name of file to write the info into
	 * @param faculty Linked List of Faculty objects
	 * @throws IOException if file cannot be written
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> faculty) throws IOException {
		try (PrintStream write = new PrintStream(new File(fileName))) {
			for (Faculty f : faculty) {
				write.println(f.toString());	
			}
		}	
	}

}
