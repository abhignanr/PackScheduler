package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayListTest {
	/**
	 * Tests the ArrayList constructor by verifying that an
	 * ArrayList can be constructed and the initial values
	 * are set correctly
	 */
	@Test
	void testArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
	}

	/**
	 * Tests the add method of the ArrayList by first trying to add
	 * invalid values, such as null and duplicate values, and then
	 * adding valid values only. The valid array list then has more
	 * operations tasked to test it.
	 */
	@Test
	void testAdd() {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> pass = new ArrayList<String>();
		ArrayList<String> growth = new ArrayList<String>();
		String invalid = "supercool";
		String valid = "supercold";
		String grow = "qwertyuiopasdfg";
		assertThrows(NullPointerException.class, () -> list.add(0, null));
		try {
			for (int i = 0; i < invalid.length(); i++) {
				list.add(i, Character.toString(invalid.charAt(i)));
			}
		} catch (Exception e) {
			assertEquals(7, list.size());
		}
		try {
			for (int i = 0; i < valid.length(); i++) {
				pass.add(0, Character.toString(valid.charAt(i)));
			}
		} catch (Exception e) {
			fail("Valid string failed to be added to list");
		}
		assertEquals(9, pass.size());
		for (int i = 0; i < valid.length(); i++) {
			assertEquals(pass.get(valid.length() - i - 1), Character.toString(valid.charAt(i)));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> pass.add(-1, "h"));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.add(10, "h"));
		
		try {
			for (int i = 0; i < grow.length(); i++) {
				growth.add(i, Character.toString(grow.charAt(i)));
			}
		} catch (Exception e) {
			fail("Valid string failed to be added to list");
		}
	}

	/**
	 * Tests the remove function by first removing at invalid indexes
	 * and then verifying the list is restructured properly for every removal
	 */
	@Test
	void testRemove() {
		ArrayList<String> pass = new ArrayList<String>();
		String valid = "supercold";
		String test0 = "suprcold";
		String test1 = "suprcld";
		for (int i = 0; i < valid.length(); i++) {
			pass.add(i, Character.toString(valid.charAt(i)));
		}
		assertThrows(IndexOutOfBoundsException.class, () -> pass.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.remove(9));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.remove(10));
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

	@Test
	void testSet() {
		ArrayList<String> pass = new ArrayList<String>();
		String valid = "supercold";
		String test0 = "very_cold";
		for (int i = 0; i < valid.length(); i++) {
			pass.add(i, Character.toString(valid.charAt(i)));
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
		ArrayList<String> pass = new ArrayList<String>();
		String valid = "supercold";
		
		for (int i = 0; i < valid.length(); i++) {
			pass.add(i, Character.toString(valid.charAt(i)));
		}
		
		for (int i = 0; i < valid.length(); i++) {
			assertEquals(pass.get(i), Character.toString(valid.charAt(i)));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> pass.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.get(9));
		assertThrows(IndexOutOfBoundsException.class, () -> pass.get(10));
	}

}
