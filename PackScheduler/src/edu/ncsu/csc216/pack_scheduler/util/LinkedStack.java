package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Represents a Stack implemented using the LinkedAbstractList class. Elements are put at the front of the Linked list to make the stack.
 * @author davidb
 * @param <E> the element type stored
 */
public class LinkedStack<E> implements Stack<E> {

	/** Linkedlist used to implement Stack */
	LinkedAbstractList<E> stack;
	
	/**
	 * Default constructor; just inititializes the underlying datastructure.
	 * @param capacity the capacity to set
	 */
	public LinkedStack(int capacity) {
		stack = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * Pushes the element onto the stack. Throws IllegalArgumentException if the capacity has been reached
	 * @throws IllegalArgumentException if the capacity is reached 
	 * @param element the element to add
	 */
	@Override
	public void push(E element) {
		stack.add(0, element);		
	}

	/**
	 * Returns the element at the top of the stack.
	 * @return the elemnt at the top
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() {
		if(stack.isEmpty()) throw new EmptyStackException();
		return stack.remove(0);
	}

	/**
	 * Returns if the stack is empty.
	 * @return true if the stack has no elements; false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the size of the stack.
	 * @return the size
	 */
	@Override
	public int size() {
		return stack.size();
	}

	/**
	 * Sets the capacity of the stack. If the new capacity is less than the previous, throws IllegalArgumentException.
	 * @param capacity the new capacity of the stack.
	 * @throws IllegalArgumentException if the capacity is less than the current capacity
	 */
	@Override
	public void setCapacity(int capacity) {
		stack.setCapacity(capacity);
	}

}
