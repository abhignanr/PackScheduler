/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;
import java.util.Arrays;

/**
 * This is the custom ArrayList class for the PackScheduler. This class extends
 * AbstractList. It holds fields for a list of generic type E, and a field for
 * the size of the ArrayList. It also has functionality for basic manipulations
 * such as add, remove, set, and get.
 * 
 * @author Lawrence Stephenson, David Severin, Andres Ayala-Lagunas
 * 
 * @param <E> The type of element being stored
 */
public class ArrayList<E> extends AbstractList<E> {

	/** constant holding the initial capacity of the list */
	private static final int INIT_SIZE = 10;
	/** field holding the list of generic type E */
	private E[] list;
	/** int field holding the number of elements in the list */
	private int size;

	/**
	 * Constructor for the ArrayList class. Sets the list field equal to a new
	 * Object array of size INIT_SIZE, and type casts that to E
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}

	/**
	 * The add method adds an element at the specified index. Starting from the
	 * specified index, it shifts each element to the right one to make room for the
	 * new element. It then increments the size field by one.
	 * 
	 * @throws NullPointerException      if the element to be added is null
	 * @throws IndexOutOfBoundsException if the index is less than 0, or greater
	 *                                   than size
	 * @throws IllegalArgumentException  if the element to be added is already in
	 *                                   the list
	 */
	@Override
	public void add(int index, E element) {
		// check if element to be added is null
		if (element == null) {
			throw new NullPointerException();
		}
		// check if index is out of bounds
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		// check if the element to be added is already in the list
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		// check if size equals the length of the list
		if (size == list.length) {
			growArray();
		}
		// set i equal to size, which will start you at an empty index after using grow
		// array, then while i is >= index, set each index at i equal to the index
		// before it
		for (int i = size; i > index; i--) {
			list[i] = list[i - 1];
		}
		list[index] = element;
		size++;
	}

	/**
	 * This is a private helper that doubles the capacity of the list. It uses the
	 * Array.copyOf() method.
	 */
	private void growArray() {
		this.list = Arrays.copyOf(list, size * 2);
	}

	/**
	 * The remove method removes the element at the specified index, and returns the
	 * removed element. Starting at index, it shifts every element to the left one,
	 * and decrements the size field by one.
	 * 
	 * @throws IndexOutOfBoundsException if the index is less than zero, or greater
	 *                                   than or equal to size
	 */
	@Override
	public E remove(int index) {
		// if index is out of bounds
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		// save the element at the specified index
		E removed = list[index];

		// starting at index, while i is less than size, set the element at index i to
		// the element after it
		for (int i = index; i < size; i++) {
			list[i] = list[i + 1];
		}

		size--; // decrement size by one
		list[size] = null; // make sure the element at the size of the list is null
		return removed; // return the removed element
	}

	/**
	 * The set method sets the element at the specified index to the given element.
	 * It then returns the element that was changed.
	 * 
	 * @throws NullPointerException      if the element to be added is null
	 * @throws IndexOutOfBoundsException if the index is less than 0, or greater
	 *                                   than size
	 * @throws IllegalArgumentException  if the element to be added is already in
	 *                                   the list
	 */
	@Override
	public E set(int index, E element) {
		// if the element parameter is null
		if (element == null) {
			throw new NullPointerException();
		}
		// check if the element to be added is already in the list
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		// if index is out of bounds
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E removed = list[index]; // save the removed element
		list[index] = element; // set element at specified index to the given element parameter
		return removed; // return the removed element
	}

	/**
	 * The get method returns the element at the specified index.
	 * 
	 * @throws IndexOutOfBoundsException if the index is less than 0, or greater
	 *                                   than or equal to size.
	 */
	@Override
	public E get(int index) {
		// if index is out of bounds
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		// return element at specified index
		return list[index];
	}

	/**
	 * The size method returns the value stored in the size field.
	 */
	@Override
	public int size() {
		return size;
	}
}
