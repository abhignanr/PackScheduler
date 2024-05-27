package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A class that creates a LinkedList through recursive methods/algorithms.
 * @author Maisa Little
 *
 * @param <E> type of data held in list
 */
public class LinkedListRecursive<E> {

	/**
	 * number of elements in LinkedList
	 */
	private int size;
	
	/**
	 * frontmost element in LinkedList
	 */
	private ListNode front;
	
	/**
	 * constructs a LinkedList
	 */
	public LinkedListRecursive() {
		this.size = 0;
		this.front = null;
	}
	
	/**
	 * checks to see if list is empty
	 * @return true if list size is 0; false otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * returns size
	 * @return size of list
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * adds element to list
	 * @param element element to add
	 * @throws IllegalArgumentException if duplicate
	 * @throws NullPointerException if element is null
	 * @return true if added to list
	 */
	public boolean add(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		else if (contains(element)) {
			throw new IllegalArgumentException();
		}
		else if (isEmpty()) {
			ListNode newNode = new ListNode(element, null);
			this.front = newNode;
			this.size++;
			return true;
		}
		else {
			front.add(this.size - 1, element);
			this.size++;
			return true;
		}
	}
	
	/**
	 * adds element to list
	 * @param index location to add
	 * @param element element to add
	 * @throws IllegalArgumentException if duplicate
	 * @throws IndexOutOfBoundsException if index is not in range of list
	 * @throws NullPointerException if element is null
	 * @return true if added to list
	 */
	public boolean add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		else if (contains(element)) {
			throw new IllegalArgumentException();
		}
		else if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException();
		}
		else if (index == 0) { // if add to front
			if (isEmpty()) { // if nothing in list
				this.front = new ListNode(element, null);
			}
			else {
				ListNode newNode = new ListNode(element, this.front);
				this.front = newNode;
			}
			this.size++;
			return true;
		}
		else {
			front.add(index - 1, element);
			this.size++;
			return true;
		}
	}
	
	/**
	 * gets element at given index
	 * @param index index to get element from
	 * @throws IndexOutOfBoundsException if index is out of range
	 * @return element data at index
	 */
	public E get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			return front.get(index);
		}
	}
	
	/**
	 * remove element from list
	 * @param element element to remove
	 * @return true if removed
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		}
		else if (!contains(element)) {
			return false;
		}
		else if (isEmpty()) {
			return false;
		}
		else if (front.data.equals(element)){
			front = front.next;
			this.size--;
			return true;
		}
		else {
			return front.remove(element);
		}
	}
	
	/**
	 * removes element at given index
	 * @param index index to remove from
	 * @throws IndexOutOfBoundsException if index is out of range
	 * @return data removed
	 */
	public E remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		else if (index == 0) {
			E data = this.front.data;
			this.front = front.next;
			this.size--;
			return data;
		}
		else {
			return front.remove(index);
		}
	}
	
	/**
	 * sets an elements data
	 * @param index index to set
	 * @param element new element
	 * @throws IndexOutOfBoundsException if index is out of range
	 * @throws IllegalArgumentException if duplicate
	 * @throws NullPointerException if element is null
	 * @return data previously there
	 */
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		else if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		else if (contains(element)) {
			throw new IllegalArgumentException();
		}
		else {
			return front.set(index, element);
		}
	}
	
	/**
	 * checks to see if an element is in the list
	 * @param element element to check for
	 * @return true if in list, false otherwise
	 */
	public boolean contains(E element) {
		if (isEmpty()) {
			return false;
		}
		ListNode current = this.front;
		return current.contains(element);
	}
	
	/**
	 * Creates node objects that make up the LinkedListRecursive
	 * @author Maisa Little
	 *
	 */
	public class ListNode {
		
		/**
		 * nodes data
		 */
		public E data;
		
		/**
		 * node after current node
		 */
		public ListNode next;
		
		/**
		 * constructs a ListNode
		 * @param data nodes data
		 * @param next node after current node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * adds element to end of list
		 * @param index index to add at
		 * @param element element to add
		 */
		public void add(int index, E element) {
			if (index == 0) {
				ListNode theNext = this.next;
				this.next = new ListNode(element, theNext);
			}
			else if(next != null) {
				next.add(index - 1, element);
			}
		}
		
		/**
		 * gets value at given index
		 * @param index index to find
		 * @return data at index
		 */
		public E get(int index) {
			if (index == 0) {
				return this.data;
			}
			else if (next == null) {
				return null;
			}
			else {
				return next.get(index - 1);
			}
		}
		
		/**
		 * remove element from list
		 * @param index index to remove from
		 * @return data of removed element
		 */
		public E remove(int index) {
			if (index == 1) {
				E info = next.data;
				this.next = next.next;
				size--;
				return info;
			}
			else {
				if (next == null) {
					return null;
				}
				return next.remove(index - 1);
			}
		}
		
		/**
		 * remove element from list
		 * @param element element to remove
		 * @return true if removed
		 */
		public boolean remove(E element) {
			if (this.next.data.equals(element)) {
				this.next = this.next.next;
				size--;
				return true;
			}
			else {
				return next != null && next.remove(element);
			}
		}
		
		/**
		 * sets data in node
		 * @param index index to set at
		 * @param element new data
		 * @return old data
		 */
		public E set(int index, E element) {
			if (index == 0) {
				E info = this.data;
				this.data = element;
				return info;
			}
			else {
				return next.set(index - 1, element);
			}
		}
		
		/**
		 * private helper method for LinkedListRecursive.contains
		 * @param element element to check for
		 * @return true if in list, false otherwise
		 */
		public boolean contains(E element) {
			if (this.data.equals(element)) {
				return true;
			}
			else {
				return this.next != null && this.next.contains(element);
			}
		}
	}
}
