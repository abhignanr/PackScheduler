package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Implementation of the Linked Abstract List class
 * @param <E> inferred type of the list
 * @author Andres Ayala-Lagunas
 *
 */
public class LinkedAbstractList<E> extends java.util.AbstractList<E> {

	/** The capacity of the List */
	private int capacity;
	/** Integer field holding the number of elements in the list */
	private int size;
	/** Denotes a ListNode of type E as the front of the list */
	private ListNode front;
	/** the ListNode at the end of the list */
	private ListNode back;
	
	/**
	 * Constructor for the LinkedAbstractList class
	 * @param c The capacity of the LinkedAbstractList
	 * @throws IllegalArgumentException if given capacity
	 * is less than 0 or less than the current size of the list
	 */
	public LinkedAbstractList(int c) {
		if (c < 0) {
			throw new IllegalArgumentException();
		}
		
		front = null;
		size = 0;
		capacity = c;
		back = null;
	}
	
	/**
	 * Adds an element to the LinkedAbstractList class at a given index
	 * @param index The index to add the element to
	 * @param element The element to be added
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than the size
	 * @throws IndexOutOfBoundsException if size is equal to the capacity
	 * @throws IllegalArgumentException if element to be added is null
	 * @throws IllegalArgumentException if element is duplicate
	 */
	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		
		for (int i = 0; i < size(); i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}

		ListNode newNode = new ListNode(element);

		if (front == null) {
			front = newNode;
			front.setNext(null);
			back = newNode;
		} else if (index == 0) {
			ListNode temp = front;
			newNode.setNext(temp);
			front = newNode;
		} else if (index == this.size) {
			back.setNext(newNode);
			back = newNode;
			
		} else {
			ListNode behind = front;
			for (int i = 0; i < index - 1; i++) {
				behind = behind.getNext();
			}
			if (behind.getNext() != null) {
				newNode.setNext(behind.getNext());
			}
			behind.setNext(newNode);
		}
		size++;
	}
	
	/**
	 * Removes an element at a given index
	 * @param index The index of the element to be removed
	 * @return The value of the removed element
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to size
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E data = get(index);
		if (index == 0) {
			if (front.getNext() == null) {
				front = null;
			} else {
				front = front.getNext();
			}
			size--;
			return data;
		} else {
			ListNode behind = front;
			for (int i = 0; i < index - 1; i++) {
				behind = behind.getNext();
			}
			if (behind.getNext().getNext() != null) {
				behind.setNext(behind.getNext().getNext());
			} else {
				behind.setNext(null);
			}
			if (index == this.size - 1) {
				back = behind;
			}
			size--;
		}
		return data;
	}
	
	/**
	 * Sets the element at a certain index to a certain element
	 * @param index The index of the element to be set
	 * @param element The element to change to
	 * @return The data of the element at the given index before it is set
	 * @throws NullPointerException if element is null
	 * @throws IndexOutOfBoundsException if index less than 0 or index is greater than or equal to size
	 * @throws IllegalArgumentException if element is duplicate
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size(); i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		E data;
		if (index == 0) {
			data = front.getData();
			front.setData(element);
			return data;
		}
		
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		data = current.getData();
		current.setData(element);
		return data;
	}
	
	/**
	 * Gets the size of the list
	 * @return The size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Gets the data of the node in a given index within the list
	 * @param index The index of the data to be gotten
	 * @return The value of the data at the index
	 * @throws IndexOutOfBoundsException if the index is less than  0 or greater than or equal to size
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == 0) {
			return front.getData();
		}
		ListNode current = front;
		
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		
		return current.getData();
	}
	
	/**
	 * ListNode class used in the LinkedAbstractList class to manage order
	 * @author Andres Ayala-Lagunas
	 */
	private class ListNode {
		/** The data held by the ListNode */
		private E data;
		/** The next ListNode */
		private ListNode next;
		
		/** Constructor for the ListNode class with only
		 * one parameter
		 * @param data The data stored in the node
		 */
		public ListNode(E data) {
			this(data, null);
		}
		
		/** Constructor for the ListNode class using both
		 * data and next parameters
		 * @param data The data stored in the node
		 * @param next The next node in the list
		 */
		public ListNode(E data, ListNode next) {
			setData(data);
			setNext(next);
		}
		
		/** Sets the next node
		 * @param next The node to be set as next
		 */
		public void setNext(ListNode next) {
			this.next = next;
		}
		
		/** Gets the next node
		 * @return The next ListNode
		 */
		public ListNode getNext() {
			return this.next;
		}
		
		/** Sets the data of the node
		 * @param data The data to be set
		 */
		public void setData(E data) {
			this.data = data;
		}
		
		/** Gets the data of the node
		 * @return The data of the node
		 */
		public E getData() {
			return this.data;
		}
	}
	
	/**
	 * Sets the capacity. If the capacity is less than the current size (or less than 0), will throw an {@link IllegalArgumentException}
	 * @throws IllegalArgumentException if the capacity is less than the current size
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		if(capacity < this.size || capacity < 0) throw new IllegalArgumentException();
		this.capacity = capacity;
	}
	
}
