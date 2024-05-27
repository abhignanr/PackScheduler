package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * 
 * Stack implementation using ArrayList.
 * @author Maisa Little
 *
 * @param <T> element type in ArrayStack
 */
public class ArrayStack<T> implements Stack<T> {

	/**
	 * list of items
	 */
	private ArrayList<T> list; 
	
	/**
	 * capacity of ArrayStack
	 */
	private int capacity;
	
	/**
	 * constructs an ArrayStack
	 * @param capacity capacity of ArrayStack
	 */
	public ArrayStack(int capacity) {
		this.list = new ArrayList<T>();
		setCapacity(capacity);
	}

	/**
	 * Pushes the element to the top of the stack
	 * @param element the element to add
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	@Override
	public void push(T element) {
		if (this.list.size() >= this.capacity) {
			throw new IllegalArgumentException();
		}
		this.list.add(this.list.size(), element);
		}

	/**
	 * Pops the element on the top of the stack
	 * @return the element on the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public T pop() {
		if (this.list.size() == 0) {
			throw new EmptyStackException();
		}
		return this.list.remove(this.list.size() - 1);
	}

	/**
	 * If the stack is empty
	 * @return true if the stack is empty, and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	/**
	 * Returns the size of the stack
	 * @return the size
	 */
	@Override
	public int size() {
		return this.list.size();
	}

	/**
	 * Sets the capacity of the stack. If the capacity is less than current size or 0, throws IllegalArgumentException 
	 * @param capacity the new capacity.
	 * @throws IllegalArgumentException if the capacity is less than the current capacity
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}
