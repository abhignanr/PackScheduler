package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the SortedList class.
 * @author sjwill25
 * @author davidb
 * @author mglittl2
 */
public class SortedListTest {
	/**
	 * tests the sortedList class
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertFalse(list.contains("apple"));
		
		// Test that the list grows by adding at least 11 elements
		String[] fruits = {"apple", "cantelope", "banana", "blueberry", "strawberry", "watermelon", "pinapple", "orange", "pear", "peach", "blackberry"};
		for (String fruit : fruits)
			list.add(fruit);
		
		assertEquals(11, list.size());
		//Remember the list's initial capacity is 10
	}

	/**
	 * tests the SortedList.add(E) method
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		list.add("apple"); //front
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		
		list.add("raspberry"); //back
		assertEquals(3, list.size());
		assertEquals("raspberry", list.get(list.size() -  1));
		
		list.add("cantelope"); //middle
		assertEquals(4, list.size());
		assertEquals("cantelope", list.get(2));
		

		//null
		assertThrows(NullPointerException.class, () -> list.add(null));
		assertEquals(4, list.size());

		//duplicate
		assertThrows(IllegalArgumentException.class, () -> list.add("banana"));
		assertEquals(4, list.size());
	}
	
	/**
	 * tests the SortedList.get(#) method
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		// empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		
		list.add("apple");
		list.add("banana");
		
		// index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		
		// element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
	}
	
	/**
	 * tests the SortedList.remove(#) method
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		//Add some elements to the list - at least 4
		
		list.add("apple");
		list.add("banana");
		list.add("cantelope");
		list.add("peach");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

		String fruit = list.remove(1);
		assertEquals("banana", fruit);
		
		// Test removing the last element
		String fruit2 = list.remove(list.size() - 1);
		assertEquals("peach", fruit2);
		
		// Test removing the first element
		String fruit3 = list.remove(0);
		assertEquals("apple", fruit3);
		
		// Test removing the last element
		String fruit4 = list.remove(0);
		assertEquals("cantelope", fruit4);
		
	}
	
	/**
	 * tests the SortedList.indexOf(E) method
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		//indexOf on an empty list
		assertEquals(-1, list.indexOf("apple"));
		
		//add some elements
		list.add("apple");
		list.add("pear");
		list.add("peach");
		list.add("pineapple");
		list.add("raspberry");
		
		//various calls to indexOf for elements in the list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("peach"));
		assertEquals(2, list.indexOf("pear"));
		assertEquals(3, list.indexOf("pineapple"));
		assertEquals(4, list.indexOf("raspberry"));
		
		//and not in the list
		assertEquals(-1, list.indexOf("banana"));
		assertEquals(-1, list.indexOf("blackberry"));
		
		//checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
		
	}
	
	/**
	 * tests the SortedList.clear() method
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		// Clear the list
		list.clear();
		// Test that the list is empty
		assertEquals(0, list.size());
	}

	/**
	 * tests the SortedList.isEmpty() method
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		//Test if empty
		assertEquals(0, list.size());
		//add element
		list.add("apple");
		//test if empty
		assertFalse(list.isEmpty());
	}

	/**
	 * tests the SortedList.contains(E) method
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		// Test the empty list case
		assertFalse(list.contains("something"));
		// Add some elements
		list.add("banana");
		list.add("pear");
		// Test some true and false cases
		assertTrue(list.contains("pear"));
		assertFalse(list.contains("peach"));
	}
	
	/**
	 * tests the SortedList.equals(E) method
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		// Make two lists the same and one list different
		list1.add("apple");
		list2.add("apple");
		list3.add("banana");
		//Test for equality and non-equality
		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));
	}
	
	/**
	 * tests the SortedList.hashCode() method
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		// Make two lists the same and one list different
		list1.add("apple");
		list2.add("apple");
		list3.add("banana");
		// Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
	}

}
 