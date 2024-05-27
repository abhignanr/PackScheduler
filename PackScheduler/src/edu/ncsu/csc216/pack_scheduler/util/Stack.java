package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Represents an stack.
 * @author davidb
 * @param <E> the type of the elements
 */
public interface Stack<E> {

	/**
	 * Pushes the element to the top of the stack
	 * @param element the element to add
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	void push(E element);
	
	/**
	 * Pops the element on the top of the stack
	 * @return the element on the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	E pop();
	
	/**
	 * If the stack is empty
	 * @return true if the stack is empty, and false otherwise
	 */
	boolean isEmpty();
	/**
	 * Returns the size of the stack
	 * @return the size
	 */
	int size();
	/**
	 * Sets the capacity of the stack. If the capacity is less than current or 0, throws IllegalArgumentException 
	 * @param capacity the new capacity.
	 * @throws IllegalArgumentException if the capacity is less than the current capacity
	 */
	void setCapacity(int capacity);
}
