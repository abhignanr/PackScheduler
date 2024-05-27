/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue is a custom Queue class that implements the Queue interface.
 * @author Maisa Little
 * @param <E> element type
 */
public class LinkedQueue<E> implements Queue<E> {

	/** 
	 * LinkedAbstractList used to implement Queue 
	 */
	LinkedAbstractList<E> queue;
	
	/**
	 * constructs a LinkedQueue
	 * @param capacity capacity of LinkedQueue
	 */
	public LinkedQueue(int capacity) {
		this.queue = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * Adds the element to the back of the queue. If there is no room, 
	 * throws IllegalArgumentException.
	 * @param element the element to add
	 * @throws IllegalArgumentException if the queue cannot take more elements
	 */
	@Override
	public void enqueue(E element) {
		this.queue.add(element);
	}

	/**
	 * Removes the front element and returns it. If the queue is empty, 
	 * throws NoSuchElementException.
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public E dequeue() {
		if (this.queue.size() == 0) {
			throw new NoSuchElementException();
		}
		
		E removed = this.queue.remove(0);
		return removed;
	}

	/**
	 * Returns if there the queue is empty. 
	 * @return true if the queue is empty, and false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return this.queue.size() == 0;
	}

	/**
	 * Returns the size of the Queue
	 * @return the size of the queue
	 */
	@Override
	public int size() {
		return this.queue.size();
	}

	/**
	 * Sets the capacity to the new value. If the new value is less than the 
	 * current size or negative, throws IllegalArgumentException.
	 * @param capacity the capacity to set
	 * @throws IllegalArgumentException if the capacity is invalid
	 */
	@Override
	public void setCapacity(int capacity) {
		this.queue.setCapacity(capacity);
	}

}
