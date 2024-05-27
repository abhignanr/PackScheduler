package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Represents a doubly-linked list implementing using an iterator. Allows for addition, removal, and editing values.
 * @author davidb
 * @param <E> type of objects stored
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** Front of list*/
	ListNode front;
	/** Back of list */
	ListNode back;
	/** size of list */
	int size;
	
	/**
	 * Creates a new LinkedList with two dummy nodes.
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null, front, null);
		size = 0;
	}
	/**
	 * The size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	@Override
	public E set(int arg0, E arg1) {
		if (this.contains(arg1)) {
			throw new IllegalArgumentException();
		}
		return super.set(arg0, arg1);
	}
	
	/**
	 * Returns the listIterator for this linkedlist.
	 * @param arg0 the starting index
	 * @return the list iterator
	 */
	@Override
	public ListIterator<E> listIterator(int arg0) {
		return new LinkedListIterator(arg0);
	}
	
	/**
	 * Adds the element at the specified location. Does not allow duplicates
	 * @param arg0 the index to add at
	 * @param arg1 the value to store there
	 */
	@Override
	public void add(int arg0, E arg1) {
		if (this.contains(arg1)) {
			throw new IllegalArgumentException();
		}
		super.add(arg0, arg1);
	}
	
	/**
	 * List Node for the linked list. Stores the data, the next, and the previous node.
	 * @author davidb
	 */
	private class ListNode {
		/** Object stored in this node*/
		public E data;
		/** Next Node*/
		public ListNode next;
		/** Previous Node*/
		public ListNode prev;
		
		public ListNode(E data) {
			this(data, null, null);
		}
		
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			if (next != null ) {
				next.prev = this;
			}
			this.prev = prev;
			if (prev != null) {
				prev.next = this;
			}
		}
	}
	
	/** 
	 * Iterator for the linked list class. Allows for moving both forwards 
	 * and backwards, as well as adding, removing, and setting elements.
	 *
	 */
	class LinkedListIterator implements ListIterator<E> {
		
		/** Previous node in iterator*/
		private ListNode previous;
		/** next node in iterator */
		private  ListNode next;
		/** index of previous node */
		private int previousIndex;
		/** index of next node */
		private int nextIndex;
		/** last node retrieved. Null if last call was add() or remove() */
		private ListNode lastRetrieved;
		
		/**
		 * Creates a default List iterator with the next node pointing to the index specified.
		 * @param index the starting position of the next() node in the list
		 */
		public LinkedListIterator(int index) { 
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			ListNode current = front;
			for (int i = -1; i <= index; i++) {
				if (i == index - 1) {
					previous = current;
					previousIndex = i;
				}
				else if (i == index) {
					next = current;
					nextIndex = i;
				}
				current = current.next;
			}
			lastRetrieved = null;
		}

		/**
		 * Adds the element at the current index.
		 * @param arg0 the element to store here
		 */
		@Override
		public void add(E arg0) {
			if (arg0 == null) {
				throw new NullPointerException();
			}
			ListNode add = new ListNode(arg0, previous, next);
			next = add;
			lastRetrieved = null;
			size++;
		}

		/** Checks if there is another element after this one
		 * @return if there is another element
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		/** 
		 * Checks if there is an element before this one
		 * @return if there is a node before this
		 */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		/**
		 * Returns the value at the next index. Shifts the indices forward by one as well.
		 * @return the value at the next index.
		 * @throws NoSuchElementException if there is no next element
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			E data = next.data;
			if (next != null) {
				previousIndex++;
				nextIndex++;
				previous = next;
				next = next.next;
			}
			return data;
			
		}

		/**
		 * Returns the next index
		 * @return the next index
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the element at the previous node. Shifts indices backwards as well.
		 * @return the previous element
		 * @throws NoSuchElementException if there is no previous element
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			E data = previous.data;
			lastRetrieved = previous;
			nextIndex--;
			previousIndex--;
			next = previous;
			previous = previous.prev;
			return data;
		}

		/**
		 * Returns the previous index
		 * @return the previous index
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes the node at the last edited index.
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			//remove the node from the linked list by setting it's neighbor's pointers to each other
			lastRetrieved.prev.next = lastRetrieved.next;
			lastRetrieved.next.prev = lastRetrieved.prev;
			//Determine which node the one to remove is and replace with correct node
			if(lastRetrieved == previous) {
				previous = previous.prev;
			} else {
				next = next.next;
			}
			lastRetrieved = null;
			size--;
		}

		/**
		 * Sets the node at the last edited index to the provided value.
		 * @param arg0 the new value
		 * @throws IllegalStateException if lastRetrieved is null
		 * @throws NullPointerException if value to set to is null
		 */
		@Override
		public void set(E arg0) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (arg0 == null) {
				throw new NullPointerException();
			}
			lastRetrieved.data = arg0;
			
		}
		
	}

}
