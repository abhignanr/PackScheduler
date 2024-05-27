package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the util.ArrayStack class
 * @author Maisa Little
 *
 */
public class ArrayStackTest {

	/**
	 * ArrayStack for each test case
	 */
	Stack<String> stack;
	
	/**
	 * create a new stack for each test class
	 */
	@BeforeEach
	public void setUp() {
		stack = new ArrayStack<String>(10);
	}
	
	/**
	 * tests ArrayStack.push
	 */
	@Test
	void testPush() {
		assertDoesNotThrow(() -> stack.push("Blue"));
		assertDoesNotThrow(() -> stack.push("Green"));
		assertDoesNotThrow(() -> stack.push("Orange"));
		assertDoesNotThrow(() -> stack.push("Purple"));
		assertDoesNotThrow(() -> stack.push("Red"));
		assertDoesNotThrow(() -> stack.push("Grey"));
		assertDoesNotThrow(() -> stack.push("Black"));
		assertDoesNotThrow(() -> stack.push("White"));
		assertDoesNotThrow(() -> stack.push("Clear"));
		assertDoesNotThrow(() -> stack.push("Yellow"));
		
		//capacity has been reached
		assertThrows(IllegalArgumentException.class, () -> stack.push("Pink"));
	}

	/**
	 * tests ArrayStack.pop
	 */
	@Test
	void testPop() {
		//pop with no elements
		assertThrows(EmptyStackException.class, () -> stack.pop());
		
		//put some elements in stack
		assertDoesNotThrow(() -> stack.push("Maroon"));
		assertDoesNotThrow(() -> stack.push("Navy"));
		assertEquals(stack.pop(), "Navy");
	}
	
	/**
	 * tests ArrayStack.isEmpty
	 */
	@Test
	void testIsEmpty() {
		assertTrue(stack.isEmpty());
		assertDoesNotThrow(() -> stack.push("Green"));
		assertFalse(stack.isEmpty());
	}
	
	/**
	 * tests ArrayStack.size
	 */
	@Test
	void testSize() {
		assertEquals(stack.size(), 0);
		assertDoesNotThrow(() -> stack.push("Green"));
		assertEquals(stack.size(), 1);
		assertDoesNotThrow(() -> stack.push("Blue"));
		assertDoesNotThrow(() -> stack.push("Pink"));
		assertDoesNotThrow(() -> stack.push("Grey"));
		assertEquals(stack.size(), 4);
	}
	
	/**
	 * tests ArrayStack.capacity
	 */
	@Test
	void testCapacity() {
		stack.push("one");
		stack.push("two");
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(1));
		assertDoesNotThrow(() -> stack.setCapacity(100));
	}
}
