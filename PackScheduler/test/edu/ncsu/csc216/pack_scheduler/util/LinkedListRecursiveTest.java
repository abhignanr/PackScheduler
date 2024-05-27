package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Test for LinkedList class
 * @author Abhi Nyalamadugu
 * @author maisa little
 */
public class LinkedListRecursiveTest {
	
	/**
	 * Tests the LinkedAbstractList constructor by verifying that an
	 * LinkedAbstractList can be constructed and the initial values
	 * are set correctly
	 */
	@Test
	void testLinkedAbstractList() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		assertTrue(list.isEmpty());
		
		assertThrows(IllegalArgumentException.class, () -> new LinkedAbstractList<String>(-1));
		list.add("one");
		list.add("two");
		list.add("three");
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests the add method of the LinkedAbstractList by first trying to add
	 * invalid values, such as null and duplicate values, and then
	 * adding valid values only. The valid array list then has more
	 * operations tasked to test it.
	 */
	@Test
	void testAdd() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();
		String invalid = "supercool";
		String valid = "supercold";
		assertThrows(NullPointerException.class, () -> list.add(null));
		try {
			for (int i = 0; i < invalid.length(); i++) {
				list.add(Character.toString(invalid.charAt(i)));
			}
		} catch (Exception e) {
			assertEquals(7, list.size());
		}
		try {
			for (int i = 0; i < valid.length(); i++) {
				pass.add(Character.toString(valid.charAt(i)));
			}
		} catch (Exception e) {
			fail("Valid string failed to be added to list");
		}
		assertEquals(9, pass.size());
		for (int i = 0; i < valid.length(); i++) {
			assertEquals(pass.get(i), Character.toString(valid.charAt(i)), i + "");
			
		}
		
	}

	/**
	 * Tests the remove function by first removing at invalid indexes
	 * and then verifying the list is restructured properly for every removal
	 */
	@Test
	void testRemove() {
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();
		String valid = "supercold";
		String test0 = "suprcold";
		String test1 = "suprcld";
		for (int i = 0; i < valid.length(); i++) {
			pass.add(Character.toString(valid.charAt(i)));
		}
		assertThrows(IndexOutOfBoundsException.class, () -> pass.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.remove(9));
		String result = pass.remove(3);
		assertEquals("e", result);
		assertEquals(8, pass.size());
		for (int i = 0; i < test0.length(); i++) {
			assertEquals(Character.toString(test0.charAt(i)), pass.get(i));
		}
		result = pass.remove(5);
		assertEquals("o", result);
		assertEquals(7, pass.size());
		for (int i = 0; i < test1.length(); i++) {
			assertEquals(Character.toString(test1.charAt(i)), pass.get(i));
		}
	}

	/**
	 * tests set method
	 */
	@Test
	void testSet() {
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();
		String valid = "supercold";
		String test0 = "very_cold";
		for (int i = 0; i < valid.length(); i++) {
			pass.add(Character.toString(valid.charAt(i)));
		}
		
		assertThrows(NullPointerException.class, () -> pass.set(0, null));
		assertThrows(IllegalArgumentException.class, () -> pass.set(0, "o"));
		
		assertDoesNotThrow(() -> pass.set(0, "v"));
		assertDoesNotThrow(() -> pass.set(3, "y"));
		assertDoesNotThrow(() -> pass.set(1, "e"));
		assertDoesNotThrow(() -> pass.set(4, "_"));
		assertDoesNotThrow(() -> pass.set(2, "r"));
		for (int i = 0; i < test0.length(); i++) {
			assertEquals(Character.toString(test0.charAt(i)), pass.get(i));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> pass.set(-1, "z"));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.set(9, "z"));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.set(10, "z"));
		
	}

	@Test
	void testGet() {
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();
		String valid = "supercold";
		
		for (int i = 0; i < valid.length(); i++) {
			pass.add(Character.toString(valid.charAt(i)));
		}
		
		for (int i = 0; i < valid.length(); i++) {
			assertEquals(pass.get(i), Character.toString(valid.charAt(i)));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> pass.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.get(9));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.get(10));
	}

	/**
	 * tests add at a certain index
	 */
	@Test
	void testAddAtSpot() {
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();
		
		String valid = "supercold";
		
		for (int i = 0; i < valid.length(); i++) {
			pass.add(Character.toString(valid.charAt(i)));
		}
		
		assertDoesNotThrow(() -> pass.add(1, "M"));
		assertThrows(IllegalArgumentException.class, () -> pass.add(0, "s"));
		assertThrows(NullPointerException.class, () -> pass.add(0, null));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.add(110, "**"));
	}
	
	/**
	 * tests removing an item
	 */
	@Test
	void testRemoveMore() {
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();
		
		String valid = "supercold";
		
		for (int i = 0; i < valid.length(); i++) {
			pass.add(Character.toString(valid.charAt(i)));
		}
		
		assertEquals("p", pass.remove(2));
		assertEquals("s", pass.remove(0));
		assertFalse(pass.remove("&"));
		assertTrue(pass.remove("e"));
	}
	
	/**
	 * copy TSLinkedListRecursiveTest.testAddIntE
	 */
	@Test
	void testAddIntE() {
		LinkedListRecursive<String> pass = new LinkedListRecursive<String>();

		assertDoesNotThrow(() -> pass.add(0, "apple"));
		assertDoesNotThrow(() -> pass.add(0, "orange"));
		assertEquals(2, pass.size());
		assertEquals("orange", pass.get(0));
		assertEquals("apple", pass.get(1));
		assertDoesNotThrow(() -> pass.add(1, "banana"));
		assertEquals("orange", pass.get(0));
		assertEquals("banana", pass.get(1));
		assertEquals("apple", pass.get(2));

	}
}
