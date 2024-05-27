package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Represents a Queue using the ArrayList datastructure.
 * @author davidb
 * @param <E> the element type to store
 */
public class ArrayQueue<E> implements Queue<E> {
	/** Capacity of the ArrayQueue */
	int capacity;
	
	/** ArrayList to store elements in */
	ArrayList<E> list;
	
	/**
	 * Initializes a basic ArrayQueue with the provided capacity.
	 * @param capacity the starting capacity of the ArrayQueue
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Returns if there the queue is empty. 
	 * @return true if the queue is empty, and false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * Returns the size of the Queue
	 * @return the size of the queue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the capacity to the new value. If the new value is less than the 
	 * current size or negative, throws IllegalArgumentException.
	 * @param capacity the capacity to set
	 * @throws IllegalArgumentException if the capacity is invalid
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) 
			throw new IllegalArgumentException();
		
		this.capacity = capacity;		
	}

	/**
	 * Adds the element to the back of the queue. If there is no room, 
	 * throws IllegalArgumentException.
	 * @param element the element to add
	 * @throws IllegalArgumentException if the queue cannot take more elements
	 */
	@Override
	public void enqueue(E element) {
		if(list.size() + 1 > capacity) throw new IllegalArgumentException("No room in Queue");
		list.add(element);
		
	}

	/**
	 * Removes the front element and returns it. If the queue is empty, 
	 * throws NoSuchElementException.
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public E dequeue() {
		if(isEmpty()) throw new NoSuchElementException();
		return list.remove(0);
	}

}
