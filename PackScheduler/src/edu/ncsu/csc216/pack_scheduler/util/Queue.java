package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Represents a Queue datastructure. Provides operations to enqueue, dequeue, check size and if empty, and se capacity.
 * @author davidb
 *@param <E> the element type to store
 */
public interface Queue<E> {
	
	/**
	 * Adds the element to the back of the queue. If there is no room, 
	 * throws IllegalArgumentException.
	 * @param element the element to add
	 * @throws IllegalArgumentException if the queue cannot take more elements
	 */
	void enqueue(E element);
	
	/**
	 * Removes the front element and returns it. If the queue is empty, 
	 * throws NoSuchElementException.
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	E dequeue();
	
	/**
	 * Returns if there the queue is empty. 
	 * @return true if the queue is empty, and false otherwise.
	 */
	boolean isEmpty();
	
	/**
	 * Returns the size of the Queue
	 * @return the size of the queue
	 */
	int size();
	
	/**
	 * Sets the capacity to the new value. If the new value is less than the 
	 * current size or negative, throws IllegalArgumentException.
	 * @param capacity the capacity to set
	 * @throws IllegalArgumentException if the capacity is invalid
	 */
	void setCapacity(int capacity);
}
